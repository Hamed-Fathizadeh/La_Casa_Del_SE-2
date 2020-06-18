package junit;

import org.bonn.se.model.objects.entitites.Bewertung;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;



public class BewertungTest {

        private Date datum = null;
        private int anzahlSterne = 2;
        private String firmenname = "Muster";
        private String hauptsitz = "J";
        private int studentID = 1;


        Bewertung bwt = new Bewertung();

        //Setter und Getter Methode Test

    @Test
    public void getDatum() {
        bwt.setDatum(datum);
        bwt.setAnzahlSterne(anzahlSterne);
        bwt.setFirmenname(firmenname);
        bwt.setHauptsitz(hauptsitz);
        bwt.setStudentID(studentID);

        //Test
        Bewertung bwt1 = new Bewertung(datum, anzahlSterne,firmenname,hauptsitz,studentID);
        Assertions.assertEquals(bwt.getDatum(), bwt1.getDatum());
        Assertions.assertEquals(bwt.getAnzahlSterne(), bwt1.getAnzahlSterne());
        Assertions.assertEquals(bwt.getFirmenname(), bwt1.getFirmenname());
        Assertions.assertEquals(bwt.getHauptsitz(), bwt1.getHauptsitz());
        Assertions.assertEquals(bwt.getStudentID(), bwt1.getStudentID());

    }
}