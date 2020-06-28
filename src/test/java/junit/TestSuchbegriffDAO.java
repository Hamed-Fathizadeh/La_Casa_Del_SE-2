package junit;

import org.bonn.se.gui.component.Neugkeiten;
import org.bonn.se.model.dao.SuchbegriffDAO;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TestSuchbegriffDAO {


    SuchbegriffDAO sb1 = SuchbegriffDAO.getInstance();

    @Test
    public void getInstance(){
        SuchbegriffDAO sb = SuchbegriffDAO.getInstance();
    }


    @Test
    public void getSuchbegriff(){


        Assertions.assertDoesNotThrow(
                ()->  sb1.getSuchbegriffe()
        );
    }
    @Test
    public void getAbschluss(){

        Assertions.assertDoesNotThrow(
                ()->  sb1.getAbschluss()
        );
    }

    @Test
    public void getSprachNiveau(){
        Assertions.assertDoesNotThrow(
                ()->  sb1.getSpracheNiveau()
        );
    }

    @Test
    public void getITNiveau(){
        Assertions.assertDoesNotThrow(
                ()->  sb1.getITNiveau()
        );
    }

    @Test
    public void getEinstellungsArt(){
        Assertions.assertDoesNotThrow(
                ()->  sb1.getEinstellungsart()
        );
    }

    @Test
    public void getBranche(){
        Assertions.assertDoesNotThrow(
                ()->  sb1.getBranche()
        );
    }


}
