package junit;

import junit.util.StudentBuilder;
import org.bonn.se.model.dao.UserDAO;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.User;
import org.bonn.se.services.db.exception.DatabaseException;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;


public class RoundTripTest {

    Student student = new StudentBuilder()
            .withVorname("Tobias")
            .withNachname("Fellechner")
            .withPasswort("12345678")
            .withEmail("abc@abc.de")
            .createStudent();
    //RoundTripTest mit Builder
    //C-R-Ass-D
    @Test
    public void createReadDeleteStudent() throws DatabaseException, SQLException {
        UserDAO.getInstance().registerUser(student);
        Assert.assertEquals("S",UserDAO.getInstance().getUserType(student.getEmail()));
        Assert.assertTrue(UserDAO.getInstance().getUserbyEmail(student.getEmail()));


        System.out.println("Create erfolgreich");

        User actual = UserDAO.getInstance().getUser(student.getEmail());
        Assert.assertEquals(student.getVorname(),actual.getVorname());
        Assert.assertEquals(student.getNachname(),actual.getNachname());
        Assert.assertEquals(student.getPasswort(),actual.getPasswort());
        Assert.assertEquals(student.getEmail(),actual.getEmail());
        System.out.println("Read erfolgreich");

        UserDAO.deleteUser(student.getEmail());

        System.out.println("Delete erfolgreich");
    }

}
