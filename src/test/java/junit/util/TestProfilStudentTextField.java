package junit.util;

import org.bonn.se.gui.component.ProfilStudentTextField;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TestProfilStudentTextField {

    @Test
    public void testKonstruktor(){
        String caption = "caption";
        String value = "value";
        Assertions.assertDoesNotThrow(
                () -> new ProfilStudentTextField(caption, value)
        );


    }
}
