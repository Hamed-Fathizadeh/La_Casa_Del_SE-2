package junit;


import org.bonn.se.gui.component.ComboBoxNiveau;
import org.bonn.se.services.util.DatenStudentProfil;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;



public class TestComboBoxNiveau {

    @Test
    public void testKonstruktor(){

        String caption = "hallo";



        Assertions.assertDoesNotThrow(
                () -> new ComboBoxNiveau(caption, DatenStudentProfil.getCollection())
        );
    }
}
