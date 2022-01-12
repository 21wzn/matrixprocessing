package matrixprocessing.utils;

import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;




public class ImageUtils {

    public static BufferedImage extractAlphaChannel(BufferedImage image) {
        BufferedImage output = new BufferedImage(image.getColorModel(),image.copyData(null),image.isAlphaPremultiplied(),null);
        for (int x = 0; x < output.getWidth(); x++) {
            for (int y = 0; y < output.getHeight(); y++) {
                output.setRGB(x,y, (output.getRGB(x,y)>>24)&0xff<<24);
            }
        }
        return output;
    }

    public static BufferedImage extractRedChannel(BufferedImage image) {
        BufferedImage output = new BufferedImage(image.getColorModel(),image.copyData(null),image.isAlphaPremultiplied(),null);
        for (int x = 0; x < output.getWidth(); x++) {
            for (int y = 0; y < output.getHeight(); y++) {
                output.setRGB(x,y, (output.getRGB(x,y)>>16)&0xff<<16);
            }
        }
        return output;
    }

    public static BufferedImage extractGreenChannel(BufferedImage image) {
        BufferedImage output = new BufferedImage(image.getColorModel(),image.copyData(null),image.isAlphaPremultiplied(),null);
        for (int x = 0; x < output.getWidth(); x++) {
            for (int y = 0; y < output.getHeight(); y++) {
                output.setRGB(x,y, (output.getRGB(x,y)>>8)&0xff<<8);
            }
        }
        return output;
    }
    public static BufferedImage extractBlueChannel(BufferedImage image) {
        BufferedImage output = new BufferedImage(image.getColorModel(),image.copyData(null),image.isAlphaPremultiplied(),null);
        for (int x = 0; x < output.getWidth(); x++) {
            for (int y = 0; y < output.getHeight(); y++) {
                output.setRGB(x,y, output.getRGB(x,y)&0xff);
            }
        }
        return output;
    }

}
