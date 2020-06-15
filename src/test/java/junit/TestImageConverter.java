package junit;



import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import org.bonn.se.model.dao.UserDAO;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.ImageConverter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;
import com.vaadin.server.FileResource;
import java.io.File;
import com.vaadin.ui.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.sql.SQLException;


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

    }

}
    /*
    @Test
    void convertImagetoProfil() {
    }

    @Test
    void convertImagetoMenu() {
    }

 */

