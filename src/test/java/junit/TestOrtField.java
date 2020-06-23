package junit;

import org.bonn.se.gui.component.OrtField;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TestOrtField {

    @Test
    public void testKonstruktor() {
        String caption = "caption";
        Assertions.assertDoesNotThrow(
                () -> new OrtField(caption)
        );
    }





}
