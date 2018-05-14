package pdi;
import commons.Image;

public class Histograma {

	private Image imagem;
	//posição média do X
	private int meioX;
	//posição média do Y
	private int meioY;
	//posiçao final do X
	private int finalX;
	//posicao final do Y
	private int finalY;
	
	public Histograma(Image imagem) {
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
	
    public int[] getHistograma() {
    	//chama histograma da imagem inteira
        return getHistograma(0,0, finalX, finalY);
    }
    
    public int[] getHistogramaQuadrante(int quadrante) {
    	//avalia qual quadrante deve usar
    	switch (quadrante) {
          case 1:  
        	  return getHistograma(0,0, meioX, meioY);
          case 2:  
        	  return getHistograma(meioX, 0, finalX, meioY);
          case 3:  
        	  return getHistograma(0, meioY, meioX, finalY);
          case 4:  
        	  return getHistograma(meioX, meioY, finalX, finalY);
    	}
		return null;
    }
    
    //calcula média entre os pontos (iniX, iniY) e (finX, finY)
    public int[] getHistograma(int iniX, int iniY, int finX, int finY ) {
    	int[] data = new int[256];
        for (int x = iniX; x < finX; x++) {
            for (int y = iniY; y < finY; y++) {
            	data[imagem.getPixel(x, y)]++;
            }
        }
    	return data;
    }

}
