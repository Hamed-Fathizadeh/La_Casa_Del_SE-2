package org.bonn.se.services.util;

import com.vaadin.server.FileResource;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Image;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

public class ImageConverter {

    private static final File menu = new File("src/main/webapp/VAADIN/themes/demo/images/Unknown_profil.png");
    private static final File profil = new File("src/main/webapp/VAADIN/themes/demo/images/Unknown.png");

    public static Image getUnknownProfilImage() {
        Image image = null;
        image.setSource(new FileResource(profil));
        return image;
    }

    public static Image getUnknownMenuImage() {
        Image image = null;
        image.setSource(new FileResource(menu));
        return image;
    }


    public static Image convertImagetoProfil(byte[] bild) {
        Image image;

        StreamResource.StreamSource streamSource = new StreamResource.StreamSource() {
            public InputStream getStream() {
                return (bild == null) ? null : new ByteArrayInputStream(
                        bild);
            }
        };

        image = new Image(
                null, new StreamResource(
                streamSource, "streamedSourceFromByteArray"));
        image.setWidth("150px");
        image.setHeight("170px");

        return image;
    }

    public static Image convertImagetoMenu(byte[] bild) {
        Image image;

        StreamResource.StreamSource streamSource = new StreamResource.StreamSource() {
            public InputStream getStream() {
                return (bild == null) ? null : new ByteArrayInputStream(
                        bild);
            }
        };

        image = new Image(
                null, new StreamResource(
                streamSource, "streamedSourceFromByteArray"));
        image.setWidth("30px");
        image.setHeight("30px");

        return image;
    }

}
