package org.example.photo_wizard.commons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Information {

    private final Image imagem;
	//posição média do X
	private final int meioX;
	//posição média do Y
	private final int meioY;
	//posiçao final do X
	private final int finalX;
	//posicao final do Y
	private final int finalY;

    public Information(Image image) {
        this.imagem = image;
    	//posição média do X
    	meioX = imagem.getLargura()/2;
    	//posição média do Y
    	meioY = imagem.getAltura()/2;
    	//posiçao final do X
    	finalX = imagem.getLargura();
    	//posicao final do Y
    	finalY = imagem.getAltura();
    }

    public int getMedia() {
    	//chama cálculo de média passando a imagem inteira
        return getMedia(0,0, finalX, finalY);
    }
    
    public int getMediaQuadrante(int quadrante) {
    	//avalia qual quadrante deve usar
    	switch (quadrante) {
          case 1:  
        	  return getMedia(0,0, meioX, meioY);
          case 2:  
        	  return getMedia(meioX, 0, finalX, meioY);
          case 3:  
        	  return getMedia(0, meioY, meioX, finalY);
          case 4:  
        	  return getMedia(meioX, meioY, finalX, finalY);
    	}
		return 0;
    }
    
    //calcula média entre os pontos (iniX, iniY) e (finX, finY)
    public int getMedia(int iniX, int iniY, int finX, int finY ) {
        int total = 0;
        for (int x = iniX; x < finX; x++) {
            for (int y = iniY; y < finY; y++) {
                total += imagem.getPixel(x, y);
            }
        }
        return total / ((finX - iniX) * (finY - iniY));
    }

    public int getMediana() {
       //Retorna mediana da imagem completa
       return getMediana(0,0, finalX, finalY);
    }
    
    public int getMedianaQuadrante(int quadrante) {
    	//avalia qual quadrante deve usar
    	switch (quadrante) {
          case 1:  
        	  return getMediana(0,0, meioX, meioY);
          case 2:  
        	  return getMediana(meioX, 0, finalX, meioY);
          case 3:  
        	  return getMediana(0, meioY, meioX, finalY);
          case 4:  
        	  return getMediana(meioX, meioY, finalX, finalY);
    	}
		return 0;
    }
    
    public int getMediana(int iniX, int iniY, int finX, int finY ) {
        //Criar lista para ordenar os valores dos pixels
        List<Integer> pixels = new ArrayList<>();
        for (int x = iniX; x < finX; x++) {
            for (int y = iniY; y < finY; y++) {
                pixels.add(imagem.getPixel(x, y));
            }
        }
        Collections.sort(pixels);
        return pixels.get((int) Math.ceil(pixels.size() / 2));
    }

    public int getModa() {
        //Retorna Moda da imagem completa
        return getModa(0,0, finalX, finalY);
    }
     
    public int getModaQuadrante(int quadrante) {
     	//avalia qual quadrante deve usar
     	switch (quadrante) {
           case 1:  
         	  return getModa(0,0, meioX, meioY);
           case 2:  
         	  return getModa(meioX, 0, finalX, meioY);
           case 3:  
         	  return getModa(0, meioY, meioX, finalY);
           case 4:  
         	  return getModa(meioX, meioY, finalX, finalY);
     	}
 		return 0;
     }
     
    public int getModa(int iniX, int iniY, int finX, int finY ) {
         //Tabela para armazenar a ocorrência de cada cor
    	 int[] histogram = new int[256];
         for (int x = iniX; x < finX; x++) {
             for (int y = iniY; y < finY; y++) {
            	 histogram[imagem.getPixel(x, y)]++;
             }
         }
         int mode = 0;
         int index = 0;
         for (int i = 0; i < histogram.length; i++) {
             if (histogram[i] > mode) {
                 mode = histogram[i];
                 index = i;
             }
         }
         return index;
     }
    
    public int getVariancia() {
     	//chama cálculo de média passando a imagem inteira
         return getVariancia(0,0, finalX, finalY);
     }
     
    public int getVarianciaQuadrante(int quadrante) {
     	//avalia qual quadrante deve usar
     	switch (quadrante) {
           case 1:  
         	  return getVariancia(0,0, meioX, meioY);
           case 2:  
         	  return getVariancia(meioX, 0, finalX, meioY);
           case 3:  
         	  return getVariancia(0, meioY, meioX, finalY);
           case 4:  
         	  return getVariancia(meioX, meioY, finalX, finalY);
     	}
 		return 0;
     }
     
     //calcula média entre os pontos (iniX, iniY) e (finX, finY)
    public int getVariancia(int iniX, int iniY, int finX, int finY ) {
         int average = getMedia(iniX, iniY, finX, finY);
         int total = 0;
         for (int x = iniX; x < finX; x++) {
             for (int y = iniY; y < finY; y++) {
                 total += Math.pow((imagem.getPixel(x, y) - average), 2);
             }
         }
         return (total / ((finX - iniX) * (finY - iniY)));
     }
     
    public int qtdPixelMaior(int valor){
    	 return qtdPixelMaior(valor, 0,0, finalX, finalY);
     }
     
    public int qtdPixelMaiorInferior(int valor){
    	 return qtdPixelMaior(valor, meioX, meioY, finalX, finalY);
     }
     
    public int qtdPixelMaior(int valor, int iniX, int iniY, int finX, int finY){
    	 int qtd = 0;
    	 for (int x = iniX; x < finX; x++) {
             for (int y = iniY; y < finY; y++) {
            	 if (imagem.getPixel(x, y) > valor){
            		 qtd++;
            	 }
             }
         }
    	 return qtd;
     }
    
    public int qtdPixelMenor(int valor){
   	 return qtdPixelMenor(valor, 0,0, finalX, finalY);
    }
    
   public int qtdPixelMenorSuperior(int valor){
   	 return qtdPixelMenor(valor, 0, 0, meioX, meioY);
    }
    
   public int qtdPixelMenor(int valor, int iniX, int iniY, int finX, int finY){
   	 int qtd = 0;
   	 for (int x = iniX; x < finX; x++) {
            for (int y = iniY; y < finY; y++) {
           	 if (imagem.getPixel(x, y) < valor){
           		 qtd++;
           	 }
            }
        }
   	 return qtd;
    }
    
    public String getInfoMedia() {
        String sb = "Média imagem completa: " + getMedia() + "\n" +
                "Média primeiro quadrante: " + getMediaQuadrante(1) + "\n" +
                "Média segundo quadrante: " + getMediaQuadrante(2) + "\n" +
                "Média terceiro quadrante: " + getMediaQuadrante(3) + "\n" +
                "Média quarto quadrante: " + getMediaQuadrante(4) + "\n";
        return sb;
    }
    
    public String getInfoMediana() {
        String sb = "Mediana imagem completa: " + getMediana() + "\n" +
                "Mediana primeiro quadrante: " + getMedianaQuadrante(1) + "\n" +
                "Mediana segundo quadrante: " + getMedianaQuadrante(2) + "\n" +
                "Mediana terceiro quadrante: " + getMedianaQuadrante(3) + "\n" +
                "Mediana quarto quadrante: " + getMedianaQuadrante(4) + "\n";
        return sb;
    }
    
    public String getInfoModa() {
        String sb = "Moda imagem completa: " + getModa() + "\n" +
                "Moda primeiro quadrante: " + getModaQuadrante(1) + "\n" +
                "Moda segundo quadrante: " + getModaQuadrante(2) + "\n" +
                "Moda terceiro quadrante: " + getModaQuadrante(3) + "\n" +
                "Moda quarto quadrante: " + getModaQuadrante(4) + "\n";
        return sb;
    }

    public String getInfoVariancia() {
        String sb = "Variância imagem completa: " + getVariancia() + "\n" +
                "Variância primeiro quadrante: " + getVarianciaQuadrante(1) + "\n" +
                "Variância segundo quadrante: " + getVarianciaQuadrante(2) + "\n" +
                "Variância terceiro quadrante: " + getVarianciaQuadrante(3) + "\n" +
                "Variância quarto quadrante: " + getVarianciaQuadrante(4) + "\n" +
                "Variância metade superior: " + getVariancia(0, 0, finalX, meioY) + "\n";
        return sb;
    }

}
