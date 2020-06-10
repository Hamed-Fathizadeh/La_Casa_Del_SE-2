import org.bonn.se.model.dao.UserDAO;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.User;
import org.bonn.se.services.db.exception.DatabaseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class TestDAOs {

    String vorname = "Vjunit";
    String nachname = "Njunit";
    String email = "junit@junit.de";
    String password = "11111111";
    User student;

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
            System.out.println(student.toString());

            UserDAO.deleteUser(i+email);

        }
    }
}
