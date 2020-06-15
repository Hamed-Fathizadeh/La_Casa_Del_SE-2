package junit;

import junit.util.RandomString;
import junit.util.UserTestFactory;
import org.bonn.se.model.dao.ProfilDAO;
import org.bonn.se.model.dao.UserDAO;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.model.objects.entitites.User;
import org.bonn.se.services.db.exception.DatabaseException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;


public class TestDAOs {

    String vorname = "Vjunit";
    String nachname = "Njunit";
    String email = "junit@junit.de";
    String password = "11111111";
    String hauptsitz = "Bonn - Nordrhein-Westfalen";
    String cname = "Firma-JUNIT";
    User student;
    User unternehmen;
    RandomString gen = new RandomString(8, ThreadLocalRandom.current());

    UserTestFactory userTestFactory = new UserTestFactory();

    @Test
    public void createStudent() throws DatabaseException {

        for (int i = 0; i < 3; i++) {
            student = new User();
            student.setVorname(vorname+i);
            student.setNachname(nachname+i);
            student.setEmail(i+email);
            student.setPasswort(password);
            student.setType("S");
            UserDAO.getInstance().registerUser(student);
            User student_read = UserDAO.getInstance().getUser(i+email);
            Assertions.assertEquals(vorname+i,student_read.getVorname());
            Assertions.assertEquals(nachname+i,student_read.getNachname());
            Assertions.assertEquals(i+email,student_read.getEmail());
            Assertions.assertEquals(password,student_read.getPasswort());
            Assertions.assertTrue(UserDAO.getInstance().getUserbyEmail(i+email));
            System.out.println(student.toString());

            UserDAO.deleteUser(i+email);
        }
    }
    @Test
    public void createUnternehmen() throws DatabaseException {

        for (int i = 0; i < 3; i++) {
            unternehmen = new User();
            unternehmen.setVorname(vorname+i);
            unternehmen.setNachname(nachname+i);
            unternehmen.setEmail(i+email);
            unternehmen.setPasswort(password);
            unternehmen.setType("C");
            unternehmen.setHauptsitz(hauptsitz);
            unternehmen.setCname(cname);

            UserDAO.getInstance().registerUser(unternehmen);
            User unternehmen_read = UserDAO.getInstance().getUser(i+email);
            Assertions.assertEquals(vorname+i,unternehmen_read.getVorname());
            Assertions.assertEquals(nachname+i,unternehmen_read.getNachname());
            Assertions.assertEquals(i+email,unternehmen_read.getEmail());
            Assertions.assertEquals(password,unternehmen_read.getPasswort());
            Assertions.assertEquals(password,unternehmen_read.getPasswort());
            Assertions.assertEquals(password,unternehmen_read.getPasswort());

            Assertions.assertTrue(UserDAO.getInstance().getUserbyEmail(i+email));

            UserDAO.deleteUser(i+email);
        }

        }

    @Test
    public void registerStudentWithCheck() throws DatabaseException {
        Student student = userTestFactory.registerStudent();
        UserDAO.getInstance().registerUser(student);
        assertEquals("S",UserDAO.getInstance().getUserType(student.getEmail()));
        assertTrue(UserDAO.getInstance().getUserbyEmail(student.getEmail()));
        UserDAO.deleteUser(student.getEmail());
        assertFalse(UserDAO.getInstance().getUserbyEmail(student.getEmail()));
    }

    @Test
    public void checkStudentProfil() throws DatabaseException {
       Student student = userTestFactory.getProfilStudent();
        UserDAO.getInstance().registerUser(student);
        ProfilDAO.getInstance().createStudentProfil1(student);
        UserDAO.deleteUser(student.getEmail());

    }
    @Test
    public void checkStudentProfilWithAdresse() throws DatabaseException {
        Student student = userTestFactory.getProfilStudentWithoutAdress();
        UserDAO.getInstance().registerUser(student);
        ProfilDAO.getInstance().createStudentProfil1(student);
        UserDAO.deleteUser(student.getEmail());

    }

    @Test
    public void registerUnternehmenWithCheck() throws DatabaseException {
        Unternehmen unternehmen = userTestFactory.registerUnternehmen();
        UserDAO.getInstance().registerUser(unternehmen);
        assertEquals("C",UserDAO.getInstance().getUserType(unternehmen.getEmail()));
        assertTrue(UserDAO.getInstance().getUserbyEmail(unternehmen.getEmail()));
        UserDAO.deleteUser(unternehmen.getEmail());
        assertFalse(UserDAO.getInstance().getUserbyEmail(unternehmen.getEmail()));
    }

    @Test
    public void checkUnternehmenProfil() throws DatabaseException {
        Unternehmen unternehmen = userTestFactory.getProfilUnternehmen();
        UserDAO.getInstance().registerUser(unternehmen);
        ProfilDAO.getInstance().createUnternehmenProfil(unternehmen);
        UserDAO.deleteUser(unternehmen.getEmail());

    }

    @Test
    public void checkUnternehmenProfilWithAdresse() throws DatabaseException {
        Unternehmen unternehmen = userTestFactory.getProfilUnternehmenWithoutAdresss();
        UserDAO.getInstance().registerUser(unternehmen);
        ProfilDAO.getInstance().createUnternehmenProfil(unternehmen);
        UserDAO.deleteUser(unternehmen.getEmail());

    }
    /*
    @Test
    public void checkUnternehmenProfilWithoutBranche() throws DatabaseException {
        Unternehmen unternehmen = userTestFactory.getProfilUnternehmenWithoutBranche();
        UserDAO.registerUser(unternehmen);
        ProfilDAO.createUnternehmenProfil(unternehmen);
        UserDAO.deleteUser(unternehmen.getEmail());
    }
    */

    @Test
    public void checkUnternehmenProfilWithDescription() throws DatabaseException {
        Unternehmen unternehmen = userTestFactory.getProfilUnternehmen();
        UserDAO.getInstance().registerUser(unternehmen);
        ProfilDAO.getInstance().createUnternehmenProfil(unternehmen);
        UserDAO.deleteUser(unternehmen.getEmail());


    }

    @Test
    public void checkandGetUnternehmenProfilWithDescription() throws DatabaseException {
        Unternehmen unternehmen = userTestFactory.getProfilUnternehmen();
        UserDAO.getInstance().registerUser(unternehmen);
        ProfilDAO.getInstance().createUnternehmenProfil(unternehmen);
        Unternehmen actual = ProfilDAO.getInstance().getUnternehmenProfil(unternehmen);
        assertEquals(unternehmen.getVorname(),actual.getVorname());
        assertEquals(unternehmen.getNachname(),actual.getNachname());
        assertEquals(unternehmen.getEmail(),actual.getEmail());
        assertEquals(unternehmen.getCname(),actual.getCname());
        assertEquals(unternehmen.getHauptsitz(),actual.getHauptsitz());
        assertEquals(unternehmen.getBundesland(),actual.getBundesland());
        assertEquals(unternehmen.getKontaktnummer(),actual.getKontaktnummer());
        assertEquals(unternehmen.getBranche(),actual.getBranche());
        assertEquals(unternehmen.getAdresse().getOrt(),unternehmen.getAdresse().getOrt());
        assertEquals(unternehmen.getAdresse().getPlz(),unternehmen.getAdresse().getPlz());
        assertEquals(unternehmen.getAdresse().getStrasse(),unternehmen.getAdresse().getStrasse());
        assertTrue(actual.getLogo() == null);
        UserDAO.deleteUser(unternehmen.getEmail());

    }
    @Test
    public void checkStudentProfil2() throws DatabaseException {
        Student student = userTestFactory.getProfilStudent();
        UserDAO.getInstance().registerUser(student);
        ProfilDAO.getInstance().createStudentProfil1(student);
        ProfilDAO.getInstance().createStudentProfil2(student);
        Student actual = ProfilDAO.getInstance().getStudent(student.getEmail());

        assertEquals(student.getTaetigkeit().getTaetigkeitName(),actual.getTaetigkeit().getTaetigkeitName());
        assertEquals(student.getTaetigkeit().getBeginn(),actual.getTaetigkeit().getBeginn());
        assertEquals(student.getTaetigkeit().getEnde(),actual.getTaetigkeit().getEnde());
        assertEquals(student.getTaetigkeiten().size(),actual.getTaetigkeiten().size());
        for (int i = 0; i < student.getTaetigkeiten().size(); i++) {
            assertEquals(student.getTaetigkeiten().get(i).getTaetigkeitName(),actual.getTaetigkeiten().get(i).getTaetigkeitName());
        }

        UserDAO.deleteUser(student.getEmail());

    }

    @Test
    public void checkStudentWithKenntnisse() throws DatabaseException, IOException {
        Student student = userTestFactory.registerStudent();
        UserDAO.getInstance().registerUser(student);

        for (int i = 0; i < 4; i++) {
            Student.SprachKenntnis sprachKenntnis = new Student.SprachKenntnis();
            sprachKenntnis.setKenntnis(gen.nextString());
            sprachKenntnis.setNiveau("A1 - Grundkenntnisse");
            student.setSprachKenntnis(sprachKenntnis);
        }
        for (int i = 0; i < 4; i++) {
            Student.ITKenntnis itKenntnis = new Student.ITKenntnis();
            String kenntnis = gen.nextString();
            String niveau = "Gut";
            itKenntnis.setKenntnis(kenntnis);
            itKenntnis.setNiveau(niveau);

            Assert.assertEquals(kenntnis,itKenntnis.getKenntnis());
            Assert.assertEquals(niveau,itKenntnis.getNiveau());

            student.setITKenntnis(itKenntnis);
        }
        ProfilDAO.getInstance().createStudentProfil3(student);
        Student actual = ProfilDAO.getInstance().getStudent(student.getEmail());
        assertTrue(actual.getItKenntnisList() != null);
        assertTrue(actual.getSprachKenntnisList() != null);

        assertEquals(student.getSprachKenntnisList().size(),actual.getSprachKenntnisList().size());
        assertEquals(student.getSprachKenntnis().getKenntnis(),actual.getSprachKenntnis().getKenntnis());
        assertEquals(student.getSprachKenntnis().getNiveau(),actual.getSprachKenntnis().getNiveau());

        assertEquals(student.getItKenntnisList().size(),actual.getItKenntnisList().size());
        assertEquals(student.getITKenntnis().getKenntnis(),actual.getITKenntnis().getKenntnis());
        assertEquals(student.getITKenntnis().getNiveau(),actual.getITKenntnis().getNiveau());


    }

/*
    @Test
    public void checkRegisterStudentWithBuilder() throws DatabaseException {
      Student student = new StudentBuilder(gen.nextEMail()).withVorname(gen.nextString()).withNachname(gen.nextString())
              .withPasswort("12345678").createStudent();
      UserDAO.registerUser(student);
      UserDAO.deleteUser(student.getEmail());
    }

    @Test
    public void checkStudentKenntnisse() throws DatabaseException {
        Student.ITKenntnis itKenntnis = new Student.ITKenntnis();
        itKenntnis.setKenntnis(gen.nextString());
        itKenntnis.setNiveau("Sehr Gut");

      Student student = new StudentBuilder(gen.nextEMail()).withVorname(gen.nextString()).withNachname(gen.nextString())
              .withPasswort("12345678").withItKenntnis(itKenntnis).withItKenntnis(itKenntnis).createStudent();
      UserDAO.registerUser(student);
      ProfilDAO.createStudentProfil3(student);
      UserDAO.deleteUser(student.getEmail());

    }
 */








}
