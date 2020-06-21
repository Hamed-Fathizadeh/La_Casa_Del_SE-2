package junit;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import org.bonn.se.gui.component.TopPanel;
import org.bonn.se.gui.component.TopPanelUser;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.util.Roles;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TopPanelTest {

    @Mock
    private UI ui;

    @Mock
    private VaadinSession vaadinSession;

    @Mock
    Navigator navigator;

    Unternehmen unternehmen = null;
    Student student = null;


    //when(vaadinSession.getAttribute(Roles.Student)).thenReturn(student);
    //when(vaadinSession.getAttribute(Roles.Unternehmen)).thenReturn(unternehmen);
    @Before
    public void setUp() throws Exception {
        UI.setCurrent(ui);
        when(ui.getSession()).thenReturn(vaadinSession);
        when(ui.getNavigator()).thenReturn(navigator);


    }

    @Test
    public void testStudent() throws Exception {

        TopPanel topPanel = new TopPanel("Student");
        TopPanel topPanel1 = new TopPanel("Unternehmen");
        TopPanel topPanel2 = new TopPanel("test");
    }

    @Test
    public void tearDown1() throws Exception {
        student = new Student();
        student.setEmail("abc.de");
        student.setVorname("Test");
        when(vaadinSession.getAttribute(Roles.Student)).thenReturn(student);

        TopPanelUser topPanelUser = new TopPanelUser();
    }

    @Test
    public void testUnternehmen() throws Exception {
        unternehmen = new Unternehmen();
        unternehmen.setEmail("abc.de");
        unternehmen.setCname("Test");
        when(vaadinSession.getAttribute(Roles.Unternehmen)).thenReturn(unternehmen);

        TopPanelUser topPanelUser = new TopPanelUser();
    }
}