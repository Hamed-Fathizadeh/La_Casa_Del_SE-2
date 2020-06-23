package junit;


import org.bonn.se.gui.component.RegistrationTextField;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TestRegistrationTextField {
    @Test
    public void testKonstruktor(){
        String caption = "caption";
        Assertions.assertDoesNotThrow(
                () -> new RegistrationTextField(caption)
        );


    }
}
