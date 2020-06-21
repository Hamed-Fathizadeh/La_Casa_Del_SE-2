package junit.util;
import com.vaadin.ui.Image;
import org.bonn.se.model.objects.dto.*;
import org.bonn.se.services.util.IllegalException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

import java.sql.Date;

public class BewerbungDTOTest {
    private String description = "description";
    private int bewerbungId = 1;
    private byte[] lebenslauf = null;
    private int status = 1;
    private int studentID = 2;
    private String unternehmenHauptsitz = "Aachen";
    private Double rating = 0.0;
    private String emailStudent = "salma.hassouni@outlook.com";
    private int AnzeigeID = 2;
    private Date datum = null;
    //from here
    private String unternehmenName="ONCP";
    private Date student_g_datum=null;
    private String student_studiengang="Reseaux informatique";
    private String student_ausbildung="genie Logiciel";
    private String  student_kontakt_nr="017643517588";
    private int student_benachrichtigung=1;
    private String student_hoester_abschluss="Bachelor";
    private String student_vorname="salma";
    private String student_nachname="hassouni";
    private boolean bewerbung_markiert=true;


    BewerbungDTO bewerb = new BewerbungDTO();
    @Test
    public void setUp() {
        bewerb.setAnzeigeID(AnzeigeID);
        bewerb.setBewerbungID(bewerbungId);
        bewerb.setStudentID(2);
        bewerb.setDatum((java.sql.Date) datum);
        bewerb.setDescription(description);
        bewerb.setRating(rating);
        bewerb.setEmailStudent(emailStudent);
        bewerb.setLebenslauf(lebenslauf);
        bewerb.setUnternehmenHauptsitz(unternehmenHauptsitz);
        bewerb.setStatus(status);

        bewerb.setUnternehmenName("ONCP");
        bewerb.setStudent_g_datum(student_g_datum);
        bewerb.setStudent_studiengang(student_studiengang);
        bewerb.setStudent_ausbildung(student_ausbildung);
        bewerb.setStudent_kontakt_nr(student_kontakt_nr);
        bewerb.setStudent_benachrichtigung(student_benachrichtigung);
        bewerb.setStudent_hoester_abschluss(student_hoester_abschluss);//done
        bewerb.setStudent_vorname(student_vorname);//pass
        bewerb.setStudent_nachname(student_nachname);//pass
        bewerb.setBewerbung_markiert(bewerbung_markiert);//pass

        Assertions.assertEquals("Reseaux informatique",bewerb.getStudent_studiengang());
        Assertions.assertEquals("genie Logiciel",bewerb.getStudent_ausbildung());
        Assertions.assertEquals("017643517588",bewerb.getStudent_kontakt_nr());
        Assertions.assertEquals(1,bewerb.getStudent_benachrichtigung());
        Assertions.assertEquals("Bachelor",bewerb.getStudent_hoester_abschluss());
        Assertions.assertEquals("hassouni",bewerb.getStudent_nachname());
        Assertions.assertEquals("salma",bewerb.getStudent_vorname());
        Assertions.assertTrue(bewerb.isBewerbung_markiert());
        Assertions.assertEquals("ONCP",bewerb.getUnternehmenName());
        Assertions.assertEquals(1, bewerb.getBewerbungID());
        Assertions.assertSame(null,bewerb.getLebenslauf());
        Assertions.assertEquals(1, bewerb.getStatus());
        Assertions.assertEquals(2, bewerb.getStudentID());
        Assertions.assertEquals("Aachen",bewerb.getUnternehmenHauptsitz());
        Assertions.assertEquals(0.0, bewerb.getRating());
        Assertions.assertEquals("salma.hassouni@outlook.com",bewerb.getEmailStudent());
        Assertions.assertEquals(2,bewerb.getAnzeigeID());
        Assertions.assertSame(null,bewerb.getDatum());
        Assertions.assertEquals("description",bewerb.getDescription());

}}

