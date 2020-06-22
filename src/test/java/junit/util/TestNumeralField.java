package junit.util;

import org.bonn.se.gui.component.NumeralField;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TestNumeralField {
    @Test
    public void testKonstruktor(){
        String caption = "caption";

        Assertions.assertDoesNotThrow(
                () -> new NumeralField(caption)
        );


    }
}
