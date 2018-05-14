package pdi;

import commons.Image;

public class Transformacoes {

	private Image imagem;
	private Image imagemOriginal;
	//posi��o m�dia do X
	private int meioX;
	//posi��o m�dia do Y
	private int meioY;
	//posi�ao final do X
	private int finalX;
	//posicao final do Y
	private int finalY;
	
	public Transformacoes(Image imagemOriginal) {
        //guarda imagem original
		this.imagemOriginal = imagemOriginal;
		//cria nova imagem
		imagem = new Image(imagemOriginal.getLargura(), imagemOriginal.getAltura());
    	//posi��o m�dia do X
    	meioX = imagemOriginal.getLargura()/2;
    	//posi��o m�dia do Y
    	meioY = imagemOriginal.getAltura()/2;
    	//posi�ao final do X
    	finalX = imagemOriginal.getLargura();
    	//posicao final do Y
    	finalY = imagemOriginal.getAltura();
	}
	
	public Image espelhaHorizontal(){
		double[][] matriz  = new double[][]{
			{-1, 0, 0},
			{0, 1, 0},
			{0, 0, 1}};
        aplicaTransformacao(matriz, 0, 0, finalX, finalY);
		return imagem;
	}
	
	public Image espelhaVertical(){
		double[][] matriz  = new double[][]{
			{1, 0, 0},
			{0, -1, 0},
			{0, 0, 1}};
        aplicaTransformacao(matriz, 0, 0, finalX, finalY);
		return imagem;
	}
	
	public Image amplia(double tamanho){
		double[][] matriz  = new double[][]{
			{tamanho,0,0}, 
            {0,tamanho,0}, 
            {0,0,1}};
        aplicaTransformacao(matriz, 0, 0, finalX, finalY);
		return imagem;
	}
	
	public Image reduz(double tamanho){
		double[][] matriz  = new double[][]{
			{1 / tamanho,0,0}, 
            {0,1 / tamanho,0}, 
            {0,0,1}};
        aplicaTransformacao(matriz, 0, 0, finalX, finalY);
		return imagem;
	}
	
	public Image rotacaoAngulo(int angulo){
		//transforma angulo em radianos
		double radianos = Math.toRadians(angulo);
		double[][] matriz  = new double[][]{
			{Math.cos(radianos), - Math.sin(radianos), 0},
            {Math.sin(radianos), Math.cos(radianos), 0},
            {0, 0, 1}};
        aplicaTransformacao(matriz, 0, 0, finalX, finalY);
		return imagem;
	}

	public void aplicaTransformacao(double[][] matriz, int iniX, int iniY, int finX, int finY){
   	 for (int x = iniX; x < finX; x++) {
            for (int y = iniY; y < finY; y++) {
				int pixel = imagemOriginal.getPixel(x, y);
				double tmpX = x - meioX;
				double tmpY = y - meioY;
				int novoX = (int) Math.round(tmpX * matriz[0][0] + tmpY * matriz[0][1] + 1 * matriz[0][2]);
				int novoY = (int) Math.round(tmpX * matriz[1][0] + tmpY * matriz[1][1] + 1 * matriz[1][2]);
				novoX += meioX;
				novoY += meioY;
		        // Pixel position is right
		        if (novoX < finalX && novoY < finalY && novoX >= 0 && novoY >= 0) {
		        	imagem.setPixel(novoX, novoY, pixel);
		        }
            }
        }
    }
	
}
