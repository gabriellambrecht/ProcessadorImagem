package org.example.photo_wizard.pdi;

import org.example.photo_wizard.commons.Image;
import org.example.photo_wizard.commons.Information;

public class Alteracoes {

	private final Image imagem;
	//posição média do X
	private final int meioX;
	//posição média do Y
	private final int meioY;
	//posiçao final do X
	private final int finalX;
	//posicao final do Y
	private final int finalY;
	
	public Alteracoes(Image imagem) {
        this.imagem = imagem;
    	//posição média do X
    	meioX = imagem.getLargura()/2;
    	//posição média do Y
    	meioY = imagem.getAltura()/2;
    	//posiçao final do X
    	finalX = imagem.getLargura();
    	//posicao final do Y
    	finalY = imagem.getAltura();
	}
	
	public Image setBrancoMaiorMedia(){
		int media = new Information(imagem).getMedia();
		setPixelMaior(media, 255, 0, 0, finalX, finalY);
		return imagem;
	}
	
	public Image setBrancoMaiorMediaQuadrante(int quadrante) {
		return setValorMaiorMediaQuadrante(quadrante, 255);
	}
	
	public Image setValorMaiorMediaQuadrante(int quadrante, int valor) {
		int media = new Information(imagem).getMediaQuadrante(quadrante);
    	//avalia qual quadrante deve usar
    	switch (quadrante) {
          case 1:
        	  setPixelMaior(media, valor, 0,0, meioX, meioY);
        	  break;
          case 2:
        	  setPixelMaior(media, valor, meioX, 0, finalX, meioY);
        	  break;
          case 3:
        	  setPixelMaior(media, valor, 0, meioY, meioX, finalY);
        	  break;
          case 4:
        	  setPixelMaior(media, valor, meioX, meioY, finalX, finalY);
        	  break;
    	}
		return imagem;
	}
	
	public Image setValorMenorMediaQuadrante(int quadrante, int valor) {
		int media = new Information(imagem).getMediaQuadrante(quadrante);
    	//avalia qual quadrante deve usar
    	switch (quadrante) {
          case 1:
        	  setPixelMenor(media, valor, 0,0, meioX, meioY);
        	  break;
          case 2:
        	  setPixelMenor(media, valor, meioX, 0, finalX, meioY);
        	  break;
          case 3:
        	  setPixelMenor(media, valor, 0, meioY, meioX, finalY);
        	  break;
          case 4:
        	  setPixelMenor(media, valor, meioX, meioY, finalX, finalY);
        	  break;
    	}
		return imagem;
	}
	
	public Image setValorMaiorModaQuadrante(int quadrante, int valor) {
		int moda = new Information(imagem).getModaQuadrante(quadrante);
    	//avalia qual quadrante deve usar
    	switch (quadrante) {
          case 1:
        	  setPixelMaior(moda, valor, 0,0, meioX, meioY);
        	  break;
          case 2:
        	  setPixelMaior(moda, valor, meioX, 0, finalX, meioY);
        	  break;
          case 3:
        	  setPixelMaior(moda, valor, 0, meioY, meioX, finalY);
        	  break;
          case 4:
        	  setPixelMaior(moda, valor, meioX, meioY, finalX, finalY);
        	  break;
    	}
		return imagem;
	}
	
	public Image setValorMaiorMedianaQuadrante(int quadrante, int valor) {
		int mediana = new Information(imagem).getMedianaQuadrante(quadrante);
    	//avalia qual quadrante deve usar
    	switch (quadrante) {
          case 1:
        	  setPixelMaior(mediana, valor, 0,0, meioX, meioY);
        	  break;
          case 2:
        	  setPixelMaior(mediana, valor, meioX, 0, finalX, meioY);
        	  break;
          case 3:
        	  setPixelMaior(mediana, valor, 0, meioY, meioX, finalY);
        	  break;
          case 4:
        	  setPixelMaior(mediana, valor, meioX, meioY, finalX, finalY);
        	  break;
    	}
		return imagem;
	}
	
	public Image setValorMenorMedianaQuadrante(int quadrante, int valor) {
		int mediana = new Information(imagem).getMedianaQuadrante(quadrante);
    	//avalia qual quadrante deve usar
    	switch (quadrante) {
          case 1:
        	  setPixelMenor(mediana, valor, 0,0, meioX, meioY);
        	  break;
          case 2:
        	  setPixelMenor(mediana, valor, meioX, 0, finalX, meioY);
        	  break;
          case 3:
        	  setPixelMenor(mediana, valor, 0, meioY, meioX, finalY);
        	  break;
          case 4:
        	  setPixelMenor(mediana, valor, meioX, meioY, finalX, finalY);
        	  break;
    	}
		return imagem;
	}
	
	public Image threshold(int threshold){
	    for (int x = 0; x < finalX; x++) {
	        for (int y = 0; y < finalY; y++) {
		       	if (imagem.getPixel(x, y) >= threshold){
		       		imagem.setPixel(x, y, 255);
		       	} else {
		       		imagem.setPixel(x, y, 0);
		       	}
	        }
	    }
	    return imagem;
	}
	
	public void setPixelMaior(int valor, int novoValor, int iniX, int iniY, int finX, int finY){
   	   for (int x = iniX; x < finX; x++) {
            for (int y = iniY; y < finY; y++) {
           	 if (imagem.getPixel(x, y) >= valor){
           		imagem.setPixel(x, y, novoValor);
           	 }
            }
        }
    }
	
	public void setPixelMenor(int valor, int novoValor, int iniX, int iniY, int finX, int finY){
	   	 for (int x = iniX; x < finX; x++) {
	            for (int y = iniY; y < finY; y++) {
	           	 if (imagem.getPixel(x, y) <= valor){
	           		imagem.setPixel(x, y, novoValor);
	           	 }
	            }
	        }
	    }
}
