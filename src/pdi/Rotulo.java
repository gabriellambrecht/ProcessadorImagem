package pdi;

public class Rotulo {
	//posi��o x inicial
	private int xInicial;
	//posi��o y inicial
	private int yInicial;
	//posi��o x inicial
	private int xFinal;
	//posi��o y inicial
	private int yFinal;
	//n�mero do r�tulo
	private int numero;
	//area
	private int area;
	//periometro
	private int perimetro;
	//circularidade
	private double circularidade;
	
	public Rotulo (int numero, int x, int y) {
		//numero do rotulo
		this.numero = numero;
		//posi��o x
        xInicial = x;
        xFinal = x;
        //posi��o y
        yInicial = y;
        yFinal = y;
	}

	public void atuPosicao (int x, int y){
		if(x < xInicial){
			xInicial = x;
		}
		if(y < yInicial){
			yInicial = y;
		}
		if(x > xFinal){
			xFinal = x;
		}
		if(y > yFinal){
			yFinal = y;
		}
	}
	
	private void calculaCircularidade(){
		if (perimetro==0 || area==0){
			circularidade = 0;
		} else {
			circularidade = Math.pow(perimetro, 2) / (4 * Math.PI * area);
		}
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
		calculaCircularidade();
	}
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getPerimetro() {
		return perimetro;
	}

	public void setPerimetro(int perimetro) {
		this.perimetro = perimetro;
		calculaCircularidade();
	}
	
	public double getCircularidade() {
		return circularidade;
	}
	
}
