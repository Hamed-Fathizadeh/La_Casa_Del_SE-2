package junit;



import com.vaadin.server.FileResource;
import com.vaadin.ui.Image;
import org.bonn.se.services.util.ImageConverter;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;


public class TestImageConverter {
    private static final FileResource profil2 = new FileResource(new File("src/main/resources/Unknown.png"));
    private static final FileResource menu = new FileResource(new File("src/main/resources/Unknown_profil.png"));
    @Test
    public void testGetUnknownProfilImage() {
        Assertions.assertTrue(ImageConverter.getUnknownProfilImage() instanceof Image);
    }
    @Test
    public void testGetUnknownMenuImage() {
        Assertions.assertTrue(ImageConverter.getUnknownMenuImage() instanceof  Image);
    }
    @Test
    public void testConvertImagetoProfil() {
        byte[] bild1=null;
        byte[] bild2=new byte[1];
        bild2[0]=-112;
        Assertions.assertTrue(ImageConverter.convertImagetoProfil(bild1) instanceof Image);
        Assertions.assertTrue(ImageConverter.convertImagetoProfil(bild2) instanceof Image);
    }


    @Test
    public void testGetStream(){
        byte[] bild1=null;
        byte[] bild2=new byte[1];
        bild2[0]=-112;
    }
    @Test
    public void testConvertImagetoMenu() {
        byte[] bild1=null;
        byte[] bild2=new byte[1];
        bild2[0]=-112;
        Assertions.assertTrue(ImageConverter.convertImagetoMenu(bild1) instanceof Image);
        Assertions.assertTrue(ImageConverter.convertImagetoMenu(bild2) instanceof Image);
    }

}
