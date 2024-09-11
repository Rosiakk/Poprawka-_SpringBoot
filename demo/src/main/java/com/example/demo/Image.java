package com.example.demo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class Image {
    public BufferedImage image;

    public Image(){
        this.image =  new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);

        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.black);

        graphics.fillRect(0, 0, 512, 512);
    }
    public String encodeToString() {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, "png", bos);
            byte[] imageBytes = bos.toByteArray();
            imageString = Base64.getEncoder().encodeToString(imageBytes);

            bos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageString;
    }

    public static void drawPixel(BufferedImage image, Pixel pixel){
        Graphics graphics = image.getGraphics();
        graphics.setColor(pixel.color);
        graphics.fillRect(pixel.x, pixel.y, 1, 1);
    }

}

//curl -X POST http://localhost:8080/register
