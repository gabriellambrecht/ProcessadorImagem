package org.example.photo_wizard.pdi;

import org.example.photo_wizard.commons.Image;
import org.example.photo_wizard.commons.Information;

public class Filtros {

    private final Image imagem;
    private final Image imagemOriginal;
    //posiçao final do X
    private final int finalX;
    //posicao final do Y
    private final int finalY;
    //informacoes
    private final Information informacoes;

    public Filtros(Image imagemOriginal) {
        //guarda imagem original
        this.imagemOriginal = imagemOriginal;
        //cria nova imagem
        imagem = new Image(imagemOriginal);
        //posiçao final do X
        finalX = imagemOriginal.getLargura();
        //posicao final do Y
        finalY = imagemOriginal.getAltura();
        //informacoes
        informacoes = new Information(imagemOriginal);
    }

    public Image filtroGauss() {
        int[][] kernel = new int[][]{
                {1, 2, 1},
                {2, 4, 2},
                {1, 2, 1}};
        convoluciona(kernel);
        return imagem;
    }

    public Image deteccaoPrewitt(int threshold) {
        int[][] kernelX = new int[][]{
                {-1, 0, 1},
                {-1, 0, 1},
                {-1, 0, 1}};
        int[][] kernelY = new int[][]{
                {1, 1, 1},
                {0, 0, 0},
                {-1, -1, -1}};
        convolucionaFiltroDirecional(threshold, kernelX, kernelY);
        return imagem;
    }

    public Image deteccaoCanny(int threshold) {
        int[][] kernelX = new int[][]{
                {-1, 0, 1},
                {-1, 0, 1},
                {-1, 0, 1}};
        int[][] kernelY = new int[][]{
                {1, 1, 1},
                {0, 0, 0},
                {-1, -1, -1}};
        convolucionaFiltroDirecional(threshold, kernelX, kernelY);
        return imagem;
    }

    public Image deteccaoLaplaciano(int threshold) {
        int[][] kernel = new int[][]{
                {0, -1, 0},
                {-1, 4, -1},
                {0, -1, 0}};
        convolucionaFiltroLaplaciano(threshold, kernel);
        return imagem;
    }

    private void convolucionaFiltroLaplaciano(int threshold, int[][] kernel) {
        double value;
        for (int x = 1; x < finalX - 1; x++) {
            for (int y = 1; y < finalY - 1; y++) {
                value = aplicaKernel(x, y, kernel);
                value -= minKernel(x, y, kernel);
                value = value * (255.0 / maxKernel(x, y, kernel));
                int pixel = 0;
                if (value > threshold) {
                    pixel = 255;
                }
                imagem.setPixel(x, y, pixel);
            }
        }
    }

    private void convolucionaFiltroDirecional(int threshold, int[][] kernelX, int[][] kernelY) {
        double value;
        for (int x = 1; x < finalX - 1; x++) {
            for (int y = 1; y < finalY - 1; y++) {
                value = Math.pow(aplicaKernel(x, y, kernelX), 2);
                value += Math.pow(aplicaKernel(x, y, kernelY), 2);
                value = Math.sqrt(value);
                int pixel = 0;
                if (value > threshold) {
                    pixel = 255;
                }
                imagem.setPixel(x, y, pixel);
            }
        }
    }

    public Image filtroMediana() {
        convolucionaMediana();
        return imagem;
    }

    private void convolucionaMediana() {
        int valueKernel;
        for (int x = 1; x < finalX - 1; x++) {
            for (int y = 1; y < finalY - 1; y++) {
                valueKernel = informacoes.getMediana(x - 1, y - 1, x + 1, y + 1);
                imagem.setPixel(x, y, valueKernel);
            }
        }
    }

    private void convoluciona(int[][] kernel) {
        int value;
        for (int x = 1; x < finalX - 1; x++) {
            for (int y = 1; y < finalY - 1; y++) {
                value = aplicaKernel(x, y, kernel) / somaKernel(kernel);
                imagem.setPixel(x, y, value);
            }
        }
    }

    private int aplicaKernel(int x, int y, int[][] kernel) {
        int value = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                value += imagemOriginal.getPixel(x + (i - 1), y + (j - 1)) * kernel[i][j];
            }
        }
        return value;
    }

    private int somaKernel(int[][] kernel) {
        int valueKernel = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                valueKernel += kernel[i][j];
            }
        }
        return valueKernel;
    }

    private int minKernel(int x, int y, int[][] kernel) {
        int value = 0;
        int menor = Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                value += imagemOriginal.getPixel(x + (i - 1), y + (j - 1)) * kernel[i][j];
                if (value < menor) {
                    menor = value;
                }
            }
        }
        return menor;
    }

    private int maxKernel(int x, int y, int[][] kernel) {
        int value = 0;
        int maior = Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                value += imagemOriginal.getPixel(x + (i - 1), y + (j - 1)) * kernel[i][j];
                if (value < maior) {
                    maior = value;
                }
            }
        }
        return maior;
    }
}
