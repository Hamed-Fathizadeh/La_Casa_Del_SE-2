package junit.util;


import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import org.bonn.se.control.LoginControl;
import org.bonn.se.control.exception.NoSuchUserOrPassword;
import org.bonn.se.gui.views.StudentHomeView;
import org.bonn.se.gui.views.UnternehmenHomeView;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.SQLException;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MockitoTest {



    Student student;
    Unternehmen unternehmen;
    public static final String USERNAME = "s@s.de";
    public static final String login = "12345678";

    @Mock
    private UI ui;

    @Mock
    private VaadinSession vaadinSession;

    @Mock
    Navigator navigator;

    @Before
    public void init() {
        UI.setCurrent(ui);
        when(ui.getSession()).thenReturn(vaadinSession);
        when(ui.getNavigator()).thenReturn(navigator);
    }

    @Test
    public void buttonClick() throws DatabaseException, NoSuchUserOrPassword, SQLException {
        LoginControl loginControl = Mockito.mock(LoginControl.class);
        Mockito.doCallRealMethod().when(loginControl).checkAuthentication(USERNAME,login);
        LoginControl.getInstance().checkAuthentication(USERNAME,login);
        when(vaadinSession.getAttribute(Roles.Student)).thenReturn(true);
//        Mockito.verify(ui.getSession()).getAttribute(Roles.Student);

/*
        vaadinSession.setAttribute("User",user);
        registerStudent.setUp();
        Button.ClickEvent clickEvent = null;
        RegisterStudent registerStudent = new RegisterStudent();
        registerStudent.registerButtonClick(clickEvent);

 */

    }

    @Test
    public void testStudentHomeView() throws DatabaseException, SQLException {
        student = new Student();
        student.setEmail("abc.de");
        student.setVorname("Test");
        when(vaadinSession.getAttribute(Roles.Student)).thenReturn(student);

        StudentHomeView studentHomeView = new StudentHomeView();
        studentHomeView.setUp();
    }

    @Test
    public void testUnternehmenHomeView() throws DatabaseException, SQLException {
        unternehmen = new Unternehmen();
        unternehmen.setEmail("abc.de");
        unternehmen.setCname("Test");
        when(vaadinSession.getAttribute(Roles.Unternehmen)).thenReturn(unternehmen);

        UnternehmenHomeView unternehmenHomeView = new UnternehmenHomeView();
        unternehmenHomeView.setUp();
    }
}