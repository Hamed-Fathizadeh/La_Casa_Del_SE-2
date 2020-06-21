package junit.util;
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

        Assertions.assertEquals(1, bewerb.getBewerbungID());
        Assertions.assertSame(null,bewerb.getLebenslauf());
        Assertions.assertEquals(1, bewerb.getStatus());//wrong
        Assertions.assertEquals(2, bewerb.getStudentID());
        Assertions.assertEquals("Aachen",bewerb.getUnternehmenHauptsitz());
        Assertions.assertEquals(0.0, bewerb.getRating());
        Assertions.assertEquals("salma.hassouni@outlook.com",bewerb.getEmailStudent());
        Assertions.assertEquals(2,bewerb.getAnzeigeID());
        Assertions.assertSame(null,bewerb.getDatum());
        Assertions.assertEquals("description",bewerb.getDescription());

}}

