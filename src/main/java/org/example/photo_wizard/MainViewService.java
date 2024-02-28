package org.example.photo_wizard;

import org.example.photo_wizard.commons.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainViewService {

    public Image loadImage(File file) {
        try {
            BufferedImage reader = ImageIO.read(file);
            return new Image(reader);
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

}
