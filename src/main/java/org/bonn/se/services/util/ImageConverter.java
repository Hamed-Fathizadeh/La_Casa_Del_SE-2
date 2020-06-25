package org.bonn.se.services.util;

import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Image;
import net.bytebuddy.utility.RandomString;

import java.io.ByteArrayInputStream;

public class ImageConverter {


    private static ThemeResource menu = new ThemeResource("img/Unknown_profil.png");
    private static ThemeResource profil = new ThemeResource("img/Unknown.png");
    private static RandomString gen = new RandomString(8 );
    private static ThemeResource status_rot = new ThemeResource("img/Anzeigen/rot.png");
    private static ThemeResource status_gruen = new ThemeResource("img/Anzeigen/gruen.png");
    private static ThemeResource status_orange = new ThemeResource("img/Anzeigen/orange.png");
    private static ThemeResource markierung = new ThemeResource("img/Anzeigen/makierung.png");


    public static Image getMarkierung() {
        Image marker = new Image(null, markierung);

        return marker;
    }


    public static Image getStatus_rot() {
        Image rot = new Image(null, status_rot);
        rot.setDescription("Offline");
        return rot;
    }

    public static Image getStatus_gruen() {
        Image gruen = new Image(null, status_gruen);
        gruen.setDescription("Online");
        return gruen;
    }

    public static Image getStatus_orange() {
        Image orange = new Image(null, status_orange);
        orange.setDescription("Entwurf");
        return orange;
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
