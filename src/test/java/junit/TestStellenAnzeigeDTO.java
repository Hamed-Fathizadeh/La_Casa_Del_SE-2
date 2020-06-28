package junit;


import com.vaadin.ui.Image;
import junit.util.ImageConverter;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.ContainerAnzeigen;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.exception.DatabaseException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.assertTrue;


public class TestStellenAnzeigeDTO {



    private int id = 1;
    private LocalDate datum = LocalDate.now();
    private Date zeitstempel = Date.valueOf(LocalDate.now());
    private String titel = "Business Analyst";
    private String beschreibung =  "hallo wir suchen einen Absolventen";
    private int status = 1;
    private String standort  = "Köln";
    private String bundesland = "Nordrhein-Westfalen";
    private String firmenname = "BargoBank";
    private String hauptsitz  = "Köln";
    private String suchbegriff = "IT";
    private double bewertung = 4.0;
    private String art = "Feste Anstellung";
    private int anzahlNeuBewerbung = 5;
    private String branche = "Banken";




    File file = new File("src/main/webapp/VAADIN/themes/demo/img/Unknown.png");
    private byte[] image = ImageConverter.getImage(file);





    StellenanzeigeDTO stanz = new StellenanzeigeDTO();

    public TestStellenAnzeigeDTO() throws IOException {
    }


    @Test

    public void createStellenAnzeige() throws DatabaseException {

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
        stanz.setHatNeuBewerbung(anzahlNeuBewerbung);
        stanz.setBranche(branche);


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
        Assertions.assertEquals(branche, stanz.getBranche());

        assertTrue( stanz.getUnternehmenLogo() instanceof Image);
        Assertions.assertEquals(anzahlNeuBewerbung, stanz.getanzahlNeuBewerbung());






        //Konstruktor ohne Unternehmenslogo
        StellenanzeigeDTO stanz2 = new StellenanzeigeDTO(id, datum, zeitstempel, titel, beschreibung, status,
                standort, bundesland, firmenname, hauptsitz, suchbegriff, art,null,bewertung,null, anzahlNeuBewerbung);


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
        Assertions.assertEquals(stanz.getanzahlNeuBewerbung(), stanz2.getanzahlNeuBewerbung());


        //Konstruktor mit Unternehmenslogo und mit Bewertung

        StellenanzeigeDTO stanz3 = new StellenanzeigeDTO(id, datum, zeitstempel, titel, beschreibung, status,
                standort, bundesland, firmenname, hauptsitz, suchbegriff, art, image, bewertung, branche,anzahlNeuBewerbung);




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
        Assertions.assertEquals(stanz.getBranche(), stanz3.getBranche());

///     Test ContainerAnzDAO Tobias
        Unternehmen unternehmen = new Unternehmen();
        unternehmen.setStellenanzeige(stanz);
        ContainerAnzeigen.getInstance().setAnzeige(unternehmen);
        ContainerAnzeigen.getInstance().updateAnzeige(stanz);
        ContainerAnzeigen.getInstance().deleteAnzeige(stanz);

    }


}
