package junit.util;


import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import org.bonn.se.control.LoginControl;
import org.bonn.se.control.exception.NoSuchUserOrPassword;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MockitoTest {




    public static final String USERNAME = "s@s.de";
    public static final String PASSWORD = "12345678";

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
    public void buttonClick() throws DatabaseException, NoSuchUserOrPassword {
        LoginControl loginControl = Mockito.mock(LoginControl.class);
        Mockito.doCallRealMethod().when(loginControl).checkAuthentication(USERNAME,PASSWORD);
        LoginControl.getInstance().checkAuthentication(USERNAME,PASSWORD);
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
}