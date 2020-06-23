package junit;


import org.bonn.se.gui.component.OrtField;
import org.bonn.se.gui.component.OrtPlzTextField;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TestOrtPlzTextField {
    @Test
    public void test() {
        Assertions.assertDoesNotThrow(
                () -> new OrtPlzTextField()
        );

        OrtPlzTextField ort = new OrtPlzTextField();
        Assertions.assertNull(ort.getOrt());
        Assertions.assertNotNull(ort.getPlz());
        Assertions.assertNull(ort.getBunesland());
        Assertions.assertNotNull(ort.getOrtField());
        Assertions.assertNotNull(ort.getPlzField());
    }
}
