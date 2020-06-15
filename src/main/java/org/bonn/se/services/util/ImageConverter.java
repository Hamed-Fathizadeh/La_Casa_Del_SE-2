package org.bonn.se.services.util;

import com.vaadin.server.FileResource;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Image;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;

public class ImageConverter {


    private static final FileResource menu = new FileResource(new File("src/main/resources/Unknown_profil.png"));
    private static final FileResource profil = new FileResource(new File("src/main/resources/Unknown.png"));

    public static Integer getInt(){
        return 2;
    }
    public static Image getUnknownProfilImage() {

        return new Image("",profil);
    }

    public static Image getUnknownMenuImage() {
        return new Image("",menu);
    }


    public static Image convertImagetoProfil(byte[] bild) {

        if(bild == null ) {
            return getUnknownProfilImage();
        }
        Image image;

        StreamResource.StreamSource streamSource = new StreamResource.StreamSource() {
            public InputStream getStream() {
                return (bild == null) ? null : new ByteArrayInputStream(
                        bild);
            }
        };

        image = new Image(
                null, new StreamResource(
                streamSource, Arrays.toString(bild)));
        image.setWidth("150px");
        image.setHeight("150px");

        return image;
    }

    public static Image convertImagetoMenu(byte[] bild) {

        if(bild == null ) {
            return getUnknownMenuImage();
        }
        new Image();
        Image image;

        StreamResource.StreamSource streamSource = new StreamResource.StreamSource() {
            public InputStream getStream() {
                return (bild == null) ? null : new ByteArrayInputStream(
                        bild);
            }
        };

        image = new Image(
                null, new StreamResource(
                streamSource, Arrays.toString(bild)));
        image.setWidth("50px");
        image.setHeight("50px");

        return image;
    }

}
