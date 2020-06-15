package junit;

import org.bonn.se.model.objects.entitites.Adresse;
import org.bonn.se.services.util.IllegalException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TestAdresse {


    private String strasse = "Hauptstrasse 7";
    private String plz = "53123";
    private String ort = "Bonn";
    private String email = "MaxMustermann@gmail.com";
    private String bundesland = "Nordrhein-Westfalen";


    Adresse adr = new Adresse();

    @Test
    public void createAdresse() {
        //Set und Get
        adr.setStrasse(strasse);
        adr.setPlz(plz);
        adr.setOrt(ort);
        adr.setEmail(email);
        adr.setBundesland(bundesland);


        Assertions.assertEquals(email, adr.getEmail());

        //Kontruktor Test


        Adresse adr2 = new Adresse(strasse, plz, ort);
        Assertions.assertEquals(adr.getStrasse(), adr2.getStrasse());
        Assertions.assertEquals(adr.getPlz(), adr2.getPlz());
        Assertions.assertEquals(adr.getOrt(), adr2.getOrt());

        Adresse adr3 = new Adresse(strasse, plz, ort, bundesland);
        Assertions.assertEquals(adr.getStrasse(), adr3.getStrasse());
        Assertions.assertEquals(adr.getPlz(), adr3.getPlz());
        Assertions.assertEquals(adr.getOrt(), adr3.getOrt());
        Assertions.assertEquals(adr.getBundesland(), adr3.getBundesland());
    }

/*
    @Test
    public void testException() {
        Assert.assertThrows(IllegalException.class, () -> {
            adr.setPlz("1234567");
        });
    }

 */
}

