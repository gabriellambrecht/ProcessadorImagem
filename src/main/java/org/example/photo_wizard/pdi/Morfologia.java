package org.example.photo_wizard.pdi;

import org.example.photo_wizard.commons.Image;

public class Morfologia {

	private Image imagem;
	private final Image imagemOriginal;
	//posiçao final do X
	private final int finalX;
	//posicao final do Y
	private final int finalY;
	
	
	public Morfologia(Image imagemOriginal) {
	    //guarda imagem original
		this.imagemOriginal = imagemOriginal;
		//cria nova imagem
		imagem = new Image(imagemOriginal);
    	//posiçao final do X
    	finalX = imagem.getLargura();
    	//posicao final do Y
    	finalY = imagem.getAltura();
	}
	
	public Image dilatacao(){
		int[][] kernel = new int[][]{
            {0, 10, 0},
            {10, 10, 10},
            {0, 10, 0}};
        convolucionaDilatacao(kernel);
		return imagem;
	}
	
	public Image erosao(){
		int[][] kernel = new int[][]{
            {0, 10, 0},
            {10, 10, 10},
            {0, 10, 0}};
        convolucionaErosao(kernel);
		return imagem;
	}
	
	public Image AfinamentoHolt(){
		Image img = new Image(imagem);
	    boolean mudou = true;
	    boolean primeiro = false;
	    while (mudou) {
	        mudou = false;
	        primeiro = !primeiro;
	        for (int x=1; x<(finalX-1); x++) {
	            for (int y=1; y< (finalY-1); y++) {
	                if (imagem.getPixel(x, y) == 255) {
	                   	if(testeHolt(x,y, primeiro)){
	                		img.setPixel(x, y, imagem.getPixel(x, y));
	                	} else{	
	                		img.setPixel(x, y, 0);
	                        mudou = true;
	                	}
	                }
	            }
	        }
	        imagem = new Image(img);
	    }
	    return img;
	}
	
    public boolean testeHolt(int x, int y, boolean primeiro) {
        //apura edges (Para deixar a formula mais clara)
        boolean edgeC  = imagem.isedge(x,y);
        //Se for primeiro
        if(primeiro){
            return !edgeC || (v(imagem.L(x, y)) && v(imagem.S(x, y)) && (v(imagem.N(x, y)) || v(imagem.O(x, y))));
        } else {
            return !edgeC || (v(imagem.O(x, y)) && v(imagem.N(x, y)) && (v(imagem.S(x, y)) || v(imagem.L(x, y))));
        }
    }
    
    public Image AfinamentoHoltJuntas(){
		Image img = new Image(imagem);
	    boolean mudou = true;
	    while (mudou) {
	        mudou = false;
	        for (int x=1; x<(finalX-1); x++) {
	            for (int y=1; y< (finalY-1); y++) {
	                if (imagem.getPixel(x, y) == 255) {
	                   	if(testeHoltJuntas(x,y)){
	                		img.setPixel(x, y, imagem.getPixel(x, y));
	                	} else{	
	                		img.setPixel(x, y, 0);
	                        mudou = true;
	                	}
	                }
	            }
	        }
	        imagem = new Image(img);
	    }
	    return img;
	}
	
    public boolean testeHoltJuntas(int x, int y) {
        //apura edges (Para deixar a formula mais clara)
        boolean edgeC  = imagem.isedge(x,y);
        boolean edgeL  = imagem.isedge(x+1,y);
        boolean edgeS  = imagem.isedge(x,y+1);
        boolean edgeSE = imagem.isedge(x+1,y+1);
        //retorna o resultado da função
        return v(imagem.getPixel(x, y)) && (!edgeC || (edgeL && v(imagem.N(x, y)) && v(imagem.S(x, y)))
                || (edgeS && v(imagem.O(x, y)) && v(imagem.L(x, y)))
                || (edgeL && edgeSE && edgeS));
    }
    
    private boolean v(int pixel){
        return pixel == 255;
    }
	
	private void convolucionaDilatacao(int[][] kernel) {
		int maxPixel = 0;
	    for (int x = 1; x < finalX - 1; x++) {
	        for (int y = 1; y < finalY - 1; y++) {
	        	if (!isEqual(x,y, kernel)){
		        	maxPixel = maxPixel(x,y, kernel);
		        	setPixelSoma(x, y, maxPixel, kernel);		
	        	}
	        }
	    }
    }
	
	private void convolucionaErosao(int[][] kernel) {
		int minPixel = 0;
	    for (int x = 1; x < finalX - 1; x++) {
	        for (int y = 1; y < finalY - 1; y++) {
	        	if (!isEqual(x,y, kernel)){
		        	minPixel = minPixel(x,y, kernel);
		        	setPixelSub(x, y, minPixel, kernel);		
	        	}
	        }
	    }
    }
	
	private int maxPixel(int x, int y, int[][] kernel){
	   int maior = Integer.MIN_VALUE;
	   int value = 0;
	   for (int i = 0; i < 3; i++) {
           for (int j = 0; j < 3; j++) {
        	   //se a posição do kernel tem valor
        	   if (kernel[i][j] != 0){
                   value = imagemOriginal.getPixel(x + (i - 1), y + (j - 1));
                   if (value > maior){
                      	maior = value;
                   } 
        	   }
           }
       }
       return maior;
	}
	
	private int minPixel(int x, int y, int[][] kernel){
	   int menor = Integer.MAX_VALUE;
	   int value = 0;
	   for (int i = 0; i < 3; i++) {
           for (int j = 0; j < 3; j++) {
        	   //se a posição do kernel tem valor
        	   if (kernel[i][j] != 0){
                   value = imagemOriginal.getPixel(x + (i - 1), y + (j - 1));
                   if (value < menor){
                	   menor = value;
                   } 
        	   }
           }
       }
       return menor;
	}
	
	private boolean isEqual(int x, int y, int[][] kernel){
		int value = Integer.MIN_VALUE;
		boolean igual = true;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
	    	   //se a posição do kernel tem valor
				if (kernel[i][j] != 0){
					if(value == Integer.MIN_VALUE){
						value = imagemOriginal.getPixel(x + (i - 1), y + (j - 1));
					} else{
						if (value != imagemOriginal.getPixel(x + (i - 1), y + (j - 1))){
							igual = false;
						}
					}
				}
			}
		}
		return igual;
	}
	
	private void setPixelSoma(int x, int y, int valor, int[][] kernel){
		int value = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
	    	   //se a posição do kernel tem valor
				if (kernel[i][j] != 0){
				   //soma valor máximo do pixel com o valor do kernel
					value = valor + kernel[i][j];
					//se ultrapassou o limite
					if (value > 255){
						value = 255;
					}
					imagem.setPixel(x + (i - 1), y + (j - 1),value);
				}
			}
		}
	}
	
	private void setPixelSub(int x, int y, int valor, int[][] kernel){
		int value = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
	    	   //se a posição do kernel tem valor
				if (kernel[i][j] != 0){
				   //soma valor máximo do pixel com o valor do kernel
					value = valor - kernel[i][j];
					//se ultrapassou o limite
					if (value < 0){
						value = 0;
					}
					imagem.setPixel(x + (i - 1), y + (j - 1),value);
				}
			}
		}
	}
}
