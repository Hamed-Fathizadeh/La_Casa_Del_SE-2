package org.bonn.se.services.util;

import com.vaadin.server.StreamResource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class MyImageSource implements StreamResource.StreamSource {
    ByteArrayOutputStream imagebuffer = null;

    // This method generates the stream contents
    public InputStream getStream () {
        // Create an image
        File imageFile = new File("/Users/tobiasfellechner/IdeaProjects/SE-Projekt/src/main/webapp/image/prof1.jpg");
        BufferedImage image = null;
        try {
            int destHoehe = 100;
            int destBreite = 100;
            image = ImageIO.read(imageFile);
            int hoehe = image.getHeight();
            int breite = image.getWidth();

            int x = (breite/2)-destBreite/2;
            int y = (hoehe/2)+(destHoehe/2);

            image = image.getSubimage(x, y, 70, 70);

            System.out.println(x);
            System.out.println(y);

        } catch (IOException e) {
            e.printStackTrace();
        }

        assert image != null;
        Graphics2D drawable = image.createGraphics();

        // Draw something static
            drawable.drawImage(image,null,0,0);

        // Draw something dynamic
        try {
            // Write the image to a buffer
            imagebuffer = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", imagebuffer);

            // Return a stream from the buffer
            return new ByteArrayInputStream(
                    imagebuffer.toByteArray());
        } catch (IOException e) {
            return null;
        }
    }


}
