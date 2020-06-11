package junit;

import org.bonn.se.model.dao.ProfilDAO;
import org.bonn.se.model.dao.UserDAO;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.User;
import org.bonn.se.services.db.exception.DatabaseException;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Field;


public class TestDAOs {

    String vorname = "Vjunit";
    String nachname = "Njunit";
    String email = "junit@junit.de";
    String password = "11111111";
    String hauptsitz = "Bonn - Nordrhein-Westfalen";
    String cname = "Firma-JUNIT";
    User student;
    User unternehmen;

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
            System.out.println(unternehmen.toString());

            UserDAO.deleteUser(i+email);
        }

        }

    @Test
    public void getEmptyStudent() throws DatabaseException, IllegalAccessException {
        student = new User();
        student.setEmail(email);

        Student student = ProfilDAO.getStudent2(email);
        Field[] fields = student.getClass().getDeclaredFields();
        for(int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            Object value = fields[i].get(student);
            if(value != null) {
                Assertions.assertTrue(((Integer) value == 0));
            }
        }
    }
}
