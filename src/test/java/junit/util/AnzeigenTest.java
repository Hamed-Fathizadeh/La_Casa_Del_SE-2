package junit.util;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import org.bonn.se.gui.component.Anzeigen;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AnzeigenTest {
    //public Anzeigen(String str, List<StellenanzeigeDTO> dataInput){
      //  super();

    List<StellenanzeigeDTO> dataInput;
    Anzeigen anz;

/*
        setId(id);
        setDatum(datum);
        setZeitstempel(zeitstempel);
        setTitel(titel);
        setBeschreibung(beschreibung);
        setStatus(status);
        setStandort(standort);
        setBundesland(bundesland);
        setFirmenname(firmenname);
        setHauptsitz(hauptsitz);
        setSuchbegriff(suchbegriff);
        setArt(art);
        setHatNeuBewerbung(anzahlNeuBewerbung);
        setBranche(branche);
 */
    @Test
    public void getTest() {

      // Assert.assertTrue( instanceof  );
        dataInput = new ArrayList<StellenanzeigeDTO>();
        for (int i = 0; i < 10; i++) {
            StellenanzeigeDTO san = new StellenanzeigeDTO();
            san.setId(i);
            san.setDatum(LocalDate.now());
            san.setZeitstempel(Date.valueOf(LocalDate.now()));
            san.setTitel("titel"+i);
            san.setBeschreibung("beschreibung"+i);
            san.setStatus(i);
            san.setStandort("stand"+i);
            san.setBundesland("bundes"+i);
            san.setFirmenname("firmen"+i);
            san.setHauptsitz("haupt"+i);
            san.setSuchbegriff("such"+i);
            san.setArt("art"+i);
            san.setHatNeuBewerbung(i);
            san.setBranche("branche"+i);

            dataInput.add(san);
        }
        try {
            anz = new Anzeigen("str", null);
            Assert.assertEquals(dataInput, anz.getData());
            Assert.assertEquals(10,anz.setGesamtNeuBewerbungen(10));
            Assert.assertEquals(10, anz.getGesamtNeuBewerbungen());

            System.out.println(anz.setGesamtNeuBewerbungen(10));
        }catch (NullPointerException e){
            e.printStackTrace();
            System.out.println("ini catch");
        }




    }



    /*
    @Test
    public void setGesamtNeuBewerbungen() {
    }

    @Test
   public void getAnzahlNeuBewerbungen() {
    }

    @Test
    void setAnzahlNeuBewerbungen() {
    }

    @Test
    void getData() {
    }

    @Test
    void getAnzahlRow() {
    }

    @Test
    void setData() {
    }

    @Test
    void setUp() {
    }*/
}