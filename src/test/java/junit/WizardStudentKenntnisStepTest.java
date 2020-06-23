package junit;

import org.bonn.se.gui.window.wizard.WizardStudentKenntnisStep;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;


public class WizardStudentKenntnisStepTest {

    WizardStudentKenntnisStep wz = new WizardStudentKenntnisStep();

    public WizardStudentKenntnisStepTest() throws IOException {
    }


    @Test
    public void getCaptionTest(){

        Assertions.assertEquals("Kenntnisse", wz.getCaption());

    }


    @Test
    public void onBackTest(){
        Assertions.assertEquals(true, wz.onBack());
    }

}