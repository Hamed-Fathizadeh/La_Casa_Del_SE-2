package junit;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.services.util.ConcreteFactoryCollHbrs;
import org.bonn.se.services.util.DTOFactory;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

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
    private int anzeigeID = 2;
    private Date datum = null;
    private String unternehmenName="ONCP";
    private Date studentGDatum=null;
    private String studentStudiengang="Reseaux informatique";
    private String studentAusbildung="genie Logiciel";
    private String  studentKontaktNr="017643517588";
    private int studentBenachrichtigung=1;
    private String studentHoesterAbschluss="Bachelor";
    private String studentVorname="salma";
    private String studentNachname="hassouni";
    private boolean bewerbungMarkiert=true;



    DTOFactory bewerbungF = new ConcreteFactoryCollHbrs();
    BewerbungDTO bewerb = bewerbungF.createBewerbungDTO();
    @Test
    public void setUp() {
        bewerb.setAnzeigeID(anzeigeID);
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
        bewerb.setStudentGDatum(studentGDatum);
        bewerb.setStudentStudiengang(studentStudiengang);
        bewerb.setStudentAusbildung(studentAusbildung);
        bewerb.setStudentKontaktNr(studentKontaktNr);
        bewerb.setStudentBenachrichtigung(studentBenachrichtigung);
        bewerb.setStudentHoesterAbschluss(studentHoesterAbschluss);
        bewerb.setStudentVorname(studentVorname);
        bewerb.setStudentNachname(studentNachname);
        bewerb.setBewerbungMarkiert(bewerbungMarkiert);

        Assertions.assertEquals("Reseaux informatique",bewerb.getStudentStudiengang());
        Assertions.assertEquals("genie Logiciel",bewerb.getStudentAusbildung());
        Assertions.assertEquals("017643517588",bewerb.getStudentKontaktNr());
        Assertions.assertEquals(1,bewerb.getStudentBenachrichtigung());
        Assertions.assertEquals("Bachelor",bewerb.getStudentHoesterAbschluss());
        Assertions.assertEquals("hassouni",bewerb.getStudentNachname());
        Assertions.assertEquals("salma",bewerb.getStudentVorname());
        Assertions.assertTrue(bewerb.isBewerbungMarkiert());
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

