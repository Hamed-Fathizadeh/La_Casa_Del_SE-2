package Mockito;
import org.bonn.se.gui.views.RegisterStudentView;
import org.bonn.se.model.objects.entitites.User;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TestRegisterStudent1 {
    @Test
    public void testRegistration() throws Exception {
        User student = Mockito.mock(User.class);
        Mockito.when(student.getVorname()).thenReturn("Jan");
        Mockito.when(student.getNachname()).thenReturn("Müller");
        Mockito.when(student.getEmail()).thenReturn("jan@mueller.de");
        Mockito.when(student.getPasswort()).thenReturn("12345678");

        assertTrue(student.getVorname() == "Jan");
        assertTrue(student.getNachname() == "Müller");
        assertTrue(student.getEmail() == "jan@mueller.de");
        assertTrue(student.getPasswort() == "12345678");
        assertEquals(student,student);
        RegisterStudentView test1 = Mockito.mock(RegisterStudentView.class);
        RegisterStudentView test2 = Mockito.mock(RegisterStudentView.class);
       // assertEquals(,test2.setColumns(10));

    }
}
