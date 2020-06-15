package junit;


import com.vaadin.ui.DateField;
import com.vaadin.ui.Image;
import junit.util.ImageConverter;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.User;
import org.bonn.se.services.db.exception.DatabaseException;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import junit.util.RandomString;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;


public class TestStellenAnzeigeDTO {



    private int id = 1;
    private LocalDate datum = LocalDate.now();
    private Date zeitstempel = Date.valueOf(LocalDate.now());
    private String titel = "Business Analyst";
    private String beschreibung =  "hallo wir suchen einen Absolventen";
    private int status = 1;
    private String standort  = " Köln";
    private String bundesland = "Nordrhein-Westfalen";
    private String firmenname = "BargoBank";
    private String hauptsitz  = "Köln";
    private String suchbegriff = "Analyst";
    private double bewertung = 4.0;
    private String art = "Feste Anstellung";




   File file = new File("src/main/resources/Unknown.png");
   private byte[] image = ImageConverter.getImage(file);





    StellenanzeigeDTO stanz = new StellenanzeigeDTO();

    public TestStellenAnzeigeDTO() throws IOException {
    }


    @Test

    public void createStellenAnzeige() throws DatabaseException {

        //Setter und Getter Test
        stanz.setId(id);
        stanz.setDatum(datum);
        stanz.setZeitstempel(zeitstempel);
        stanz.setTitel(titel);
        stanz.setBeschreibung(beschreibung);
        stanz.setStatus(status);
        stanz.setStandort(standort);
        stanz.setBundesland(bundesland);
        stanz.setFirmenname(firmenname);
        stanz.setHauptsitz(hauptsitz);
        stanz.setSuchbegriff(suchbegriff);
        stanz.setBewertung(bewertung);
        stanz.setArt(art);
        stanz.setUnternehmenLogo(image);


        Assertions.assertEquals(id, stanz.getId());
        Assertions.assertEquals(datum, stanz.getDatum());
        Assertions.assertEquals(zeitstempel, stanz.getZeitstempel());
        Assertions.assertEquals(titel, stanz.getTitel());
        Assertions.assertEquals(beschreibung, stanz.getBeschreibung());
        Assertions.assertEquals(status, stanz.getStatus());
        Assertions.assertEquals(standort, stanz.getStandort());
        Assertions.assertEquals(bundesland, stanz.getBundesland());
        Assertions.assertEquals(firmenname, stanz.getFirmenname());
        Assertions.assertEquals(hauptsitz, stanz.getHauptsitz());
        Assertions.assertEquals(suchbegriff, stanz.getSuchbegriff());
        Assertions.assertEquals(bewertung, stanz.getBewertung());
        Assertions.assertEquals(art, stanz.getArt());

        assertTrue( stanz.getUnternehmenLogo() instanceof Image);






        //Konstruktor ohne Unternehmenslogo
        StellenanzeigeDTO stanz2 = new StellenanzeigeDTO(id, datum, zeitstempel, titel, beschreibung, status,
                standort, bundesland, firmenname, hauptsitz, suchbegriff, art);


        Assertions.assertEquals(stanz.getId(), stanz2.getId());
        Assertions.assertEquals(stanz.getDatum(), stanz2.getDatum());
        Assertions.assertEquals(stanz.getZeitstempel(),stanz2.getZeitstempel());
        Assertions.assertEquals(stanz.getTitel(), stanz2.getTitel());
        Assertions.assertEquals(stanz.getBeschreibung(), stanz2.getBeschreibung());
        Assertions.assertEquals(stanz.getStatus(), stanz2.getStatus());
        Assertions.assertEquals(stanz.getStandort(), stanz2.getStandort());
        Assertions.assertEquals(stanz.getBundesland(), stanz2.getBundesland());
        Assertions.assertEquals(stanz.getFirmenname(), stanz2.getFirmenname());
        Assertions.assertEquals(stanz.getHauptsitz(), stanz2.getHauptsitz());
        Assertions.assertEquals(stanz.getSuchbegriff(), stanz2.getSuchbegriff());
        Assertions.assertEquals(stanz.getArt(), stanz2.getArt());


        //Konstruktor mit Unternehmenslogo und mit Bewertung

        StellenanzeigeDTO stanz3 = new StellenanzeigeDTO(id, datum, zeitstempel, titel, beschreibung, status,
                standort, bundesland, firmenname, hauptsitz, suchbegriff, art, image, bewertung);




        Assertions.assertEquals(stanz.getId(), stanz3.getId());
        Assertions.assertEquals(stanz.getDatum(), stanz3.getDatum());
        Assertions.assertEquals(stanz.getZeitstempel(), stanz3.getZeitstempel());
        Assertions.assertEquals(stanz.getTitel(), stanz3.getTitel());
        Assertions.assertEquals(stanz.getBeschreibung(), stanz3.getBeschreibung());
        Assertions.assertEquals(stanz.getStatus(), stanz3.getStatus());
        Assertions.assertEquals(stanz.getStandort(), stanz3.getStandort());
        Assertions.assertEquals(stanz.getBundesland(), stanz3.getBundesland());
        Assertions.assertEquals(stanz.getFirmenname(), stanz3.getFirmenname());
        Assertions.assertEquals(stanz.getHauptsitz(), stanz3.getHauptsitz());
        Assertions.assertEquals(stanz.getSuchbegriff(), stanz3.getSuchbegriff());
        Assertions.assertEquals(stanz.getArt(), stanz3.getArt());
        Assertions.assertNotNull(stanz3.getUnternehmenLogo());
        Assertions.assertTrue(stanz3.getUnternehmenLogo() != null);
        Assertions.assertEquals(stanz.getBewertung(), stanz3.getBewertung());





    }


}
