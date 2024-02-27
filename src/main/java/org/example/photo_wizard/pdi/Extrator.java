package org.example.photo_wizard.pdi;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import org.example.photo_wizard.commons.Image;

public class Extrator {

	private final Image imagem;
	//imagem rotulada
	private Image imagemRotulada;
	//posiçao final do X
	private final int finalX;
	//posicao final do Y
	private final int finalY;
	//rotulos
	private final Map<Integer, Rotulo> rotulos = new HashMap<Integer, Rotulo>();
	
	public Extrator(Image imagem) {
        this.imagem = new Alteracoes(new Image(imagem)).threshold(150);
    	//posiçao final do X
    	finalX = imagem.getLargura();
    	//posicao final do Y
    	finalY = imagem.getAltura();
	}
	
	public String extraiQuadrado(){
		rotulaImagem();
        Collection<Rotulo> colecao = rotulos.values();
        Iterator<Rotulo> iterator = colecao.iterator();
        //se não tem imagens
        if(!iterator.hasNext()){
        	return "Não foi encontrado o quadrado";
        }
        StringBuilder sb = new StringBuilder();
    	Rotulo rot = iterator.next();
        sb.append("Área: ").append(rot.getArea()).append("\n");
        sb.append("Perímetro: ").append(rot.getPerimetro()).append("\n");
        return sb.toString();
	}
	
	public String extraiCirculos(){
		rotulaImagem();
        Collection<Rotulo> colecao = rotulos.values();
        Iterator<Rotulo> iterator = colecao.iterator();
        //se não tem imagens
        if(!iterator.hasNext()){
        	return "Não foram encontrados círculos";
        }
        StringBuilder sb = new StringBuilder();
        int sequencial = 0;
        while (iterator.hasNext()) {
        	Rotulo rot = iterator.next();
        	sequencial++;
        	sb.append("Círculo ").append(sequencial).append("\n");
            sb.append("Área: ").append(rot.getArea()).append("\n");
            sb.append("Perímetro: ").append(rot.getPerimetro()).append("\n");
            sb.append("Circularidade: ").append(rot.getCircularidade()).append("\n\n");
            
        }
        return sb.toString();
	}
	
	public String extraiMaiorCirculo(){
		rotulaImagem();
        Collection<Rotulo> colecao = rotulos.values();
        Iterator<Rotulo> iterator = colecao.iterator();
        //se não tem imagens
        if(!iterator.hasNext()){
        	return "Não foram encontrados círculos";
        }
        StringBuilder sb = new StringBuilder();
        Rotulo maiorCirculo = iterator.next();
        while (iterator.hasNext()) {
        	Rotulo rot = iterator.next();
        	//se o rotulo lido tem mais area que o rotulo
        	if(rot.getArea() > maiorCirculo.getArea()){
        		maiorCirculo = rot;
        	}
        }
        sb.append("Área: ").append(maiorCirculo.getArea()).append("\n");
        sb.append("Perímetro: ").append(maiorCirculo.getPerimetro()).append("\n");
        return sb.toString();
	}
	
	public void rotulaImagem(){
		//cria imagem rotulada em branco
		imagemRotulada = new Image(finalX, finalY);
		//cria pilha de rotulos
		Stack<Integer> pilha = new Stack<Integer>();
		//novo valor do pixel
		int novoValor = 0;
		//percorre imagem
		for (int x=1; x < finalX; x++) {
			for (int y=1; y < finalY; y++) {
				if (imagem.getPixel(x, y) == 255){
					novoValor = buscaRotulo(x,y);
					//busca menor rotulo branco ao redor
					if(novoValor==0){
						novoValor = pilha.size() + 1;
						pilha.push(novoValor);
					}
					//procura rotulo ao redor
					imagemRotulada.setPixel(x, y, novoValor);
	           	}
	        }
	    }
		//percorre a imagem rotulada eliminado 
		while(!pilha.isEmpty()){
			//desempilha item
			int rotulo = pilha.pop();
			boolean mudou = true;
			do{
				mudou = false;
				for (int x=1; x < finalX; x++) {
					for (int y=1; y < finalY; y++) {
						if (imagemRotulada.getPixel(x, y) == rotulo){
							novoValor = buscaRotulo(x,y);
							//se novo valor é menor que o encontrado
							if(novoValor<rotulo){
								//procura rotulo ao redor
								imagemRotulada.setPixel(x, y, novoValor);
								mudou = true;
							}
			           	}
			        }
			    }
			} while (mudou);
		}
		Rotulo rot;
		//gera tabela de rotulos
		//percorre imagem
		for (int x=1; x < finalX; x++) {
			for (int y=1; y < finalY; y++) {
				int valor = imagemRotulada.getPixel(x, y);
				if (valor!=0){
					if(rotulos.containsKey(valor)){
						rot = rotulos.get(valor);
						rot.atuPosicao(x, y);
					} else {
						rot = new Rotulo(valor, x, y);
						rotulos.put(valor, rot);
					}
	           	}
	        }
	    }
		//carrega area e perimetro
		Collection<Rotulo> colecao = rotulos.values();
	    Iterator<Rotulo> iterator = colecao.iterator();
	    while (iterator.hasNext()) {
	    	rot = iterator.next();
	    	rot.setArea(calculaArea(rot.getNumero()));
	    	rot.setPerimetro(calculaPerimetro(rot.getNumero()));
		}
		

	}
	
	private int buscaRotulo(int x, int y){
	   int menor = Integer.MAX_VALUE;
	   int value = 0;
	   for (int i = 0; i < 3; i++) {
	       for (int j = 0; j < 3; j++) {
	           value = imagemRotulada.getPixel(x + (i - 1), y + (j - 1));
	           if (value!= 0 && value < menor){
	        	   menor = value;
	           } 
	       }
	   }
	   if (menor==Integer.MAX_VALUE){
		   return 0;
	   } else {
		   return menor;   
	   }
	}
	
	private int calculaArea(int rotulo){
		int qtdPixel = 0;
		for (int x = 1; x < finalX; x++) {
            for (int y = 1; y < finalY; y++) {
           	   if (imagemRotulada.getPixel(x, y) == rotulo){
           		   qtdPixel++;
           	   }
            }
        }
		return qtdPixel;
	}
	
	private int calculaPerimetro(int rotulo){
		int qtdPixel = 0;
		for (int x = 1; x < finalX; x++) {
            for (int y = 1; y < finalY; y++) {
           	   if (imagemRotulada.getPixel(x, y) == rotulo){
           		   //se pixel estiver na borda
           		   if (imagem.isedge(x, y)){
           			   qtdPixel++;
           		   }
           	   }
            }
        }
		return qtdPixel;
	}
	
	
}