package junit;

import org.bonn.se.gui.component.Neugkeiten;
import org.bonn.se.model.objects.entitites.ContainerNeuigkeiten;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TestNeuigkeiten {

    @Test
    public void TestNeuigkeiten(){
        ContainerNeuigkeiten con = ContainerNeuigkeiten.getInstance();

        Neugkeiten neug =  new Neugkeiten(con);

        Assertions.assertDoesNotThrow(
                ()->  new Neugkeiten(con)
        );

    }
}
