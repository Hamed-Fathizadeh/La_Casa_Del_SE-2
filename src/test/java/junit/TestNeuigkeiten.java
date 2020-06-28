package junit;

import org.bonn.se.gui.component.Neugkeiten;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.ContainerNeuigkeiten;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestNeuigkeiten {

    @Test
    public void testNeuigkeiten(){
        ContainerNeuigkeiten con = ContainerNeuigkeiten.getInstance();


        Neugkeiten neug =  new Neugkeiten(con);

        Assertions.assertDoesNotThrow(
                ()->  new Neugkeiten(con)
        );



    }
}
