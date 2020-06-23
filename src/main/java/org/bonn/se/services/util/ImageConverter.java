package org.bonn.se.services.util;

import com.vaadin.server.FileResource;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Image;
import net.bytebuddy.utility.RandomString;

import java.io.ByteArrayInputStream;
import java.io.File;

public class ImageConverter {


    private static final FileResource menu = new FileResource(new File("src/main/resources/Unknown_profil.png"));
    private static final FileResource profil = new FileResource(new File("src/main/resources/Unknown.png"));
    private static RandomString gen = new RandomString(8 );
    public static Image getUnknownProfilImage() {
        return new Image("",profil);
    }

    public static Image getUnknownMenuImage() {

        return new Image("",menu);
    }


    public static Image convertImagetoProfil(byte[] bild) {

        if(bild == null ) {
            return getUnknownProfilImage();
        } else {
            Image image;

            StreamResource.StreamSource streamSource = (StreamResource.StreamSource) () -> new ByteArrayInputStream(
                    bild);

            image = new Image(
                    null, new StreamResource(
                    streamSource, gen.nextString()));
            image.setWidth("150px");
            image.setHeight("150px");

            return image;
        }
    }

    public static Image convertImagetoMenu(byte[] bild) {

        if(bild == null ) {
            return getUnknownMenuImage();
        } else {
            Image image;

            StreamResource.StreamSource streamSource = (StreamResource.StreamSource) () -> new ByteArrayInputStream(
                    bild);

            image = new Image(
                    null, new StreamResource(
                    streamSource,gen.nextString() ));
            image.setWidth("50px");
            image.setHeight("50px");

            return image;
        }
    }

}
