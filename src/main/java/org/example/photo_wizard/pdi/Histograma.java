package org.example.photo_wizard.pdi;

import org.example.photo_wizard.commons.Image;

public class Histograma {

    private final Image imagem;
    //posição média do X
    private final int meioX;
    //posição média do Y
    private final int meioY;
    //posiçao final do X
    private final int finalX;
    //posicao final do Y
    private final int finalY;

    public Histograma(Image imagem) {
        this.imagem = imagem;
        //posição média do X
        meioX = imagem.getLargura() / 2;
        //posição média do Y
        meioY = imagem.getAltura() / 2;
        //posiçao final do X
        finalX = imagem.getLargura();
        //posicao final do Y
        finalY = imagem.getAltura();
    }

    public int[] getHistograma() {
        //chama histograma da imagem inteira
        return getHistograma(0, 0, finalX, finalY);
    }

    public int[] getHistogramaQuadrante(int quadrante) {
        //avalia qual quadrante deve usar
        return switch (quadrante) {
            case 1 -> getHistograma(0, 0, meioX, meioY);
            case 2 -> getHistograma(meioX, 0, finalX, meioY);
            case 3 -> getHistograma(0, meioY, meioX, finalY);
            case 4 -> getHistograma(meioX, meioY, finalX, finalY);
            default -> null;
        };
    }

    //calcula média entre os pontos (iniX, iniY) e (finX, finY)
    public int[] getHistograma(int iniX, int iniY, int finX, int finY) {
        int[] data = new int[256];
        for (int x = iniX; x < finX; x++) {
            for (int y = iniY; y < finY; y++) {
                data[imagem.getPixel(x, y)]++;
            }
        }
        return data;
    }

}
