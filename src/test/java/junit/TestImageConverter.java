package junit;



import org.bonn.se.model.dao.UserDAO;
import org.bonn.se.services.db.exception.DatabaseException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;

import java.sql.SQLException;


public class TestImageConverter {

    @Test
    public void test() {
        Assert.assertTrue(true);
        Assertions.assertTrue(true);
        Assertions.assertThrows(DatabaseException.class, () -> {
            UserDAO.getInstance().getUser("asdfasdfasdf");
        });
    }









    /*
    @Test
    void getUnknownProfilImage() {
    }

    @Test
    void getUnknownMenuImage() {
    }

    @Test
    void convertImagetoProfil() {
    }

    @Test
    void convertImagetoMenu() {
    }

 */
}