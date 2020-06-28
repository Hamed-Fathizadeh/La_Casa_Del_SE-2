package junit;

import junit.util.RandomString;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.ContainerNeuigkeiten;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TestContainerAnzDAO {
    RandomString gen = new RandomString(8, ThreadLocalRandom.current());

    @Test
    public void loadStellenanzeige() {

        ContainerNeuigkeiten containerNeuigkeiten = ContainerNeuigkeiten.getInstance();

        containerNeuigkeiten.load();
        List<StellenanzeigeDTO> liste = containerNeuigkeiten.getListe();
        Assert.assertEquals(containerNeuigkeiten.getListe().size(),liste.size());

        Date date =     Date.valueOf(gen.nextDate());
        LocalDate localDate = LocalDate.of(2020,2,1);
        Date date1 =     Date.valueOf(localDate);



        containerNeuigkeiten.loadSuche("1","Bonn","Nordrhein-Westfalen"
        ,"Ganzer Ort","Erweitert","Praktikum", localDate,"Banken");

        containerNeuigkeiten.loadSuche("IT","Bonn","Nordrhein-Westfalen"
          ,"+10 km ","Erweitert","Feste Anstellung", localDate,"Banken");

        containerNeuigkeiten.loadNeuigkeiten("Alle");
        containerNeuigkeiten.loadNeuigkeiten("Test");

        containerNeuigkeiten.loadUnternehmenAnzeigen("bargobank@bank.de");
        Unternehmen unternehmen = new Unternehmen();
        unternehmen.setEmail("bargobank@bank.de");
        unternehmen.setHauptsitz("Köln");

        unternehmen.setCname("BargoBank");
        containerNeuigkeiten.loadNeuBewerbungen(unternehmen);

    }
}
