package junit;

import org.bonn.se.model.objects.entitites.Bewertung;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;



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
        Assert.assertEquals(bwt.getDatum(), bwt1.getDatum());
        Assert.assertEquals(bwt.getAnzahlSterne(), bwt1.getAnzahlSterne());
        Assert.assertEquals(bwt.getFirmenname(), bwt1.getFirmenname());
        Assert.assertEquals(bwt.getHauptsitz(), bwt1.getHauptsitz());
        Assert.assertEquals(bwt.getStudentID(), bwt1.getStudentID());

    }
}