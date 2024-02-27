package org.example.photo_wizard.commons;

import org.example.photo_wizard.pdi.ImageConverterGray;

import java.awt.image.BufferedImage;

/**
 * Class responsible for the image information
 *
 * @author JonataBecker
 */
public class Image {

    /**
     * Image width
     */
    private final int width;
    /**
     * Image height
     */
    private final int height;
    /**
     * Gray converter
     */
    private final ImageConverterGray grayConverter;
    /**
     * Image data
     */
    private final int[][] data;

    /**
     * Image information contruction
     *
     * @param imagem
     */
    public Image(BufferedImage imagem) {
        this.width = imagem.getWidth();
        this.height = imagem.getHeight();
        this.grayConverter = new ImageConverterGray();
        this.data = convertImage(imagem);
    }

    /**
     * Image information contruction
     *
     * @param imagem
     */
    public Image(Image imagem) {
        this.width = imagem.getLargura();
        this.height = imagem.getAltura();
        this.grayConverter = new ImageConverterGray();
        this.data = new int[width][height];
        for (int x = 0; x < width; x++) {
            System.arraycopy(imagem.data[x], 0, data[x], 0, height);
        }
    }

    public Image(int width, int height) {
        this.width = width;
        this.height = height;
        this.data = new int[width][height];
        this.grayConverter = new ImageConverterGray();
    }

    /**
     * Execute conversion to gray
     *
     * @param imagem
     * @return int[][]
     */
    private int[][] convertImage(BufferedImage imagem) {
        return grayConverter.convert(imagem);
    }

    /**
     * Return the image information
     *
     * @return BufferedImage
     */
    public BufferedImage getBufferdImage() {
        return grayConverter.convertToBuffer(data, width, height);
    }

    public int getLargura() {
        return width;
    }

    /**
     * Return the imagem height
     *
     * @return int
     */
    public int getAltura() {
        return height;
    }

    public boolean isedge(int x, int y) {
        // Verifica se os vizinhos pretos estao entre 2 e 6
        int vizinhos;
        //soma cada pixel Preto
        vizinhos = preto(NO(x, y)) + preto(N(x, y)) + preto(NE(x, y)) +
                preto(O(x, y)) + preto(L(x, y)) +
                preto(SO(x, y)) + preto(S(x, y)) + preto(SE(x, y));
        if (vizinhos < 2 || vizinhos > 6) {
            return false;
        }
        //Calcula conectividade do ponto procurando quantas transições do branco para preto
        int conectividade = (O(x, y) < SO(x, y) ? 1 : 0)
                + (SO(x, y) < S(x, y) ? 1 : 0)
                + (S(x, y) < SE(x, y) ? 1 : 0)
                + (SE(x, y) < L(x, y) ? 1 : 0)
                + (L(x, y) < NE(x, y) ? 1 : 0)
                + (NE(x, y) < N(x, y) ? 1 : 0)
                + (N(x, y) < NO(x, y) ? 1 : 0)
                + (NO(x, y) < O(x, y) ? 1 : 0);
        //se conectividade for 1
        return conectividade == 1;
    }

    private int preto(int pixel) {
        return (pixel == 255 ? 1 : 0);
    }

    //funções do ponto cardeal
    public int NO(int x, int y) {
        return getPixel(x - 1, y - 1);
    }

    public int N(int x, int y) {
        return getPixel(x, y - 1);
    }

    public int NE(int x, int y) {
        return getPixel(x + 1, y - 1);
    }

    public int O(int x, int y) {
        return getPixel(x - 1, y);
    }

    public int L(int x, int y) {
        return getPixel(x + 1, y);
    }

    public int SO(int x, int y) {
        return getPixel(x - 1, y + 1);
    }

    public int S(int x, int y) {
        return getPixel(x, y + 1);
    }

    public int SE(int x, int y) {
        return getPixel(x + 1, y + 1);
    }

    /**
     * Return the pixel value
     *
     * @param x
     * @param y
     * @return int
     */
    public int getPixel(int x, int y) {
        return data[x][y];
    }

    /**
     * Define a pixel value
     *
     * @param x
     * @param y
     * @param pixel
     */
    public void setPixel(int x, int y, int pixel) {
        if (width > x && height > y && pixel <= 255) {
            data[x][y] = pixel;
        }
    }

}
