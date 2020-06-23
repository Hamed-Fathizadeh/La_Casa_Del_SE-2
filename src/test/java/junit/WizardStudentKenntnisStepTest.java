package junit;

import org.bonn.se.gui.component.Bewerbungen;
import org.bonn.se.gui.window.wizard.WizardStudentKenntnisStep;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


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



    // diese methode kann nicht getestet werden, wegen UI.getCurrent()
    /*
    @Test
    public void onAdvanceTest(){
        Assertions.assertDoesNotThrow(
                ()-> wz.onAdvance()
        );
    }
*/

}