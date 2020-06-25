package junit;


import org.bonn.se.control.ComponentControl;
import org.bonn.se.gui.component.ComboBoxNiveau;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Collection;


public class TestComboBoxNiveau {

    @Test
    public void testKonstruktor(){

        String caption = "hallo";

        Collection<String> abschluss = ComponentControl.getInstance().getAbschluss();

        Assertions.assertDoesNotThrow(
                () -> new ComboBoxNiveau(abschluss)
        );
    }
}
