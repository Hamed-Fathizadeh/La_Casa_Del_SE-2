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

import java.lang.reflect.Field;
import java.util.concurrent.ThreadLocalRandom;


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
            UserDAO.registerUser(student);
            User student_read = UserDAO.getUser(i+email);
            Assertions.assertEquals(vorname+i,student_read.getVorname());
            Assertions.assertEquals(nachname+i,student_read.getNachname());
            Assertions.assertEquals(i+email,student_read.getEmail());
            Assertions.assertEquals(password,student_read.getPasswort());
            Assertions.assertTrue(UserDAO.getUserbyEmail(i+email));
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

            UserDAO.registerUser(unternehmen);
            User unternehmen_read = UserDAO.getUser(i+email);
            Assertions.assertEquals(vorname+i,unternehmen_read.getVorname());
            Assertions.assertEquals(nachname+i,unternehmen_read.getNachname());
            Assertions.assertEquals(i+email,unternehmen_read.getEmail());
            Assertions.assertEquals(password,unternehmen_read.getPasswort());
            Assertions.assertEquals(password,unternehmen_read.getPasswort());
            Assertions.assertEquals(password,unternehmen_read.getPasswort());

            Assertions.assertTrue(UserDAO.getUserbyEmail(i+email));

            UserDAO.deleteUser(i+email);
        }

        }

    @Test
    public void registerStudentWithCheck() throws DatabaseException {
        Student student = userTestFactory.registerStudent();
        UserDAO.registerUser(student);
        Assert.assertEquals("S",UserDAO.getUserType(student.getEmail()));
        Assert.assertTrue(UserDAO.getUserbyEmail(student.getEmail()));
        UserDAO.deleteUser(student.getEmail());
        Assert.assertFalse(UserDAO.getUserbyEmail(student.getEmail()));
    }

    @Test
    public void checkStudentProfil() throws DatabaseException {
       Student student = userTestFactory.getProfilStudent();
        UserDAO.registerUser(student);
        ProfilDAO.createStudentProfil1New(student);
    }
    @Test
    public void checkStudentProfilWithAdresse() throws DatabaseException {
        Student student = userTestFactory.getProfilStudentWithoutAdress();
        UserDAO.registerUser(student);
        ProfilDAO.createStudentProfil1New(student);
    }

    @Test
    public void registerUnternehmenWithCheck() throws DatabaseException {
        Unternehmen unternehmen = userTestFactory.registerUnternehmen();
        UserDAO.registerUser(unternehmen);
        Assert.assertEquals("C",UserDAO.getUserType(unternehmen.getEmail()));
        Assert.assertTrue(UserDAO.getUserbyEmail(unternehmen.getEmail()));
        UserDAO.deleteUser(unternehmen.getEmail());
        Assert.assertFalse(UserDAO.getUserbyEmail(unternehmen.getEmail()));
    }
    @Test
    public void checkUnternehmenProfil() throws DatabaseException {
        Unternehmen unternehmen = userTestFactory.getProfilUnternehmen();
        UserDAO.registerUser(unternehmen);
        ProfilDAO.createUnternehmenProfil1_New(unternehmen);
    }

    @Test
    public void checkUnternehmenProfilWithAdresse() throws DatabaseException {
        Unternehmen unternehmen = userTestFactory.getProfilUnternehmenWithoutAdresss();
        UserDAO.registerUser(unternehmen);
        ProfilDAO.createUnternehmenProfil1_New(unternehmen);
    }
}
