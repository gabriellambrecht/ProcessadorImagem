package commons;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Information {

    private final Image imagem;
	//posi��o m�dia do X
	private int meioX;
	//posi��o m�dia do Y
	private int meioY;
	//posi�ao final do X
	private int finalX;
	//posicao final do Y
	private int finalY;

    public Information(Image image) {
        this.imagem = image;
    	//posi��o m�dia do X
    	meioX = imagem.getLargura()/2;
    	//posi��o m�dia do Y
    	meioY = imagem.getAltura()/2;
    	//posi�ao final do X
    	finalX = imagem.getLargura();
    	//posicao final do Y
    	finalY = imagem.getAltura();
    }

    public int getMedia() {
    	//chama c�lculo de m�dia passando a imagem inteira
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
    
    //calcula m�dia entre os pontos (iniX, iniY) e (finX, finY)
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
         //Tabela para armazenar a ocorr�ncia de cada cor
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
     	//chama c�lculo de m�dia passando a imagem inteira
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
     
     //calcula m�dia entre os pontos (iniX, iniY) e (finX, finY)
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
        StringBuilder sb = new StringBuilder();
        sb.append("M�dia imagem completa: ").append(getMedia()).append("\n");
        sb.append("M�dia primeiro quadrante: ").append(getMediaQuadrante(1)).append("\n");
        sb.append("M�dia segundo quadrante: ").append(getMediaQuadrante(2)).append("\n");
        sb.append("M�dia terceiro quadrante: ").append(getMediaQuadrante(3)).append("\n");
        sb.append("M�dia quarto quadrante: ").append(getMediaQuadrante(4)).append("\n");
        return sb.toString();
    }
    
    public String getInfoMediana() {
        StringBuilder sb = new StringBuilder();
        sb.append("Mediana imagem completa: ").append(getMediana()).append("\n");
        sb.append("Mediana primeiro quadrante: ").append(getMedianaQuadrante(1)).append("\n");
        sb.append("Mediana segundo quadrante: ").append(getMedianaQuadrante(2)).append("\n");
        sb.append("Mediana terceiro quadrante: ").append(getMedianaQuadrante(3)).append("\n");
        sb.append("Mediana quarto quadrante: ").append(getMedianaQuadrante(4)).append("\n");
        return sb.toString();
    }
    
    public String getInfoModa() {
        StringBuilder sb = new StringBuilder();
        sb.append("Moda imagem completa: ").append(getModa()).append("\n");
        sb.append("Moda primeiro quadrante: ").append(getModaQuadrante(1)).append("\n");
        sb.append("Moda segundo quadrante: ").append(getModaQuadrante(2)).append("\n");
        sb.append("Moda terceiro quadrante: ").append(getModaQuadrante(3)).append("\n");
        sb.append("Moda quarto quadrante: ").append(getModaQuadrante(4)).append("\n");
        return sb.toString();
    }

    public String getInfoVariancia() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vari�ncia imagem completa: ").append(getVariancia()).append("\n");
        sb.append("Vari�ncia primeiro quadrante: ").append(getVarianciaQuadrante(1)).append("\n");
        sb.append("Vari�ncia segundo quadrante: ").append(getVarianciaQuadrante(2)).append("\n");
        sb.append("Vari�ncia terceiro quadrante: ").append(getVarianciaQuadrante(3)).append("\n");
        sb.append("Vari�ncia quarto quadrante: ").append(getVarianciaQuadrante(4)).append("\n");
        sb.append("Vari�ncia metade superior: ").append(getVariancia(0,0, finalX, meioY)).append("\n");
        return sb.toString();
    }

}
