package junit.Mockito;


import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import junit.util.StudentBuilder;
import org.bonn.se.control.LoginControl;
import org.bonn.se.control.exception.NoSuchUserOrPassword;
import org.bonn.se.gui.component.Anzeigen;
import org.bonn.se.gui.component.StudentDatenView;
import org.bonn.se.gui.views.StudentHomeView;
import org.bonn.se.gui.views.UnternehmenHomeView;
import org.bonn.se.gui.window.BewerbungWindow;
import org.bonn.se.gui.window.StellenanzeigeWindow;
import org.bonn.se.model.dao.UserDAO;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.Adresse;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MockitoTest {


    Student student = new StudentBuilder()
                .withVorname("Tobias")
                .withNachname("Fellechner")
                .withPasswort("12345678")
                .withEmail("abc@abc.de")
                .createStudent();
    Unternehmen unternehmen;


    @Mock
    private UI ui;

    @Mock
    private VaadinSession vaadinSession;

    @Mock
    Navigator navigator;

    @Before
    public void init() throws DatabaseException {
        UI.setCurrent(ui);
        when(ui.getSession()).thenReturn(vaadinSession);
        when(ui.getNavigator()).thenReturn(navigator);

        UserDAO.getInstance().registerUser(student);
    }
    @After
    public void ende() throws DatabaseException, SQLException {
       UserDAO.deleteUser(student.getEmail());
    }


    @Test
    public void testLogin() throws DatabaseException, NoSuchUserOrPassword, SQLException {
        LoginControl loginControl = Mockito.mock(LoginControl.class);
        Mockito.doCallRealMethod().when(loginControl).checkAuthentication(student.getEmail(),student.getPasswort());
        LoginControl.getInstance().checkAuthentication(student.getEmail(),student.getPasswort());
        when(vaadinSession.getAttribute(Roles.Student)).thenReturn(true);
        //verify
    }

    @Test
    public void testStudentHomeView() throws DatabaseException, SQLException {

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




    //Putriana Test
    List<StellenanzeigeDTO> dataInput;
    Anzeigen anz;
    Anzeigen anz1;

    @Test
    public void getTest() {

        when(vaadinSession.getAttribute(Roles.Student)).thenReturn(student);
        // Assert.assertTrue( instanceof  );
        dataInput = new ArrayList<StellenanzeigeDTO>();
        for (int i = 0; i < 10; i++) {
            StellenanzeigeDTO san = new StellenanzeigeDTO();
            san.setId(i);
            san.setDatum(LocalDate.now());
            san.setZeitstempel(Date.valueOf(LocalDate.now()));
            san.setTitel("titel"+i);
            san.setBeschreibung("beschreibung"+i);
            san.setStatus(i);
            san.setStandort("stand"+i);
            san.setBundesland("bundes"+i);
            san.setFirmenname("firmen"+i);
            san.setHauptsitz("haupt"+i);
            san.setSuchbegriff("such"+i);
            san.setArt("art"+i);
            san.setHatNeuBewerbung(i);
            san.setBranche("branche"+i);

            dataInput.add(san);
        }
    }

    @Test
    public void testStudentView() {
        StudentDatenView studentDatenView = new StudentDatenView();
        studentDatenView.setStudentValue();

    }


    @Test
    public void testStellenanzeigeStudent() {

        StellenanzeigeDTO stellenanzeigeDTO = new StellenanzeigeDTO();
        stellenanzeigeDTO.setId(102);
        stellenanzeigeDTO.setFirmenname("BargoBank");
        stellenanzeigeDTO.setBeschreibung("Test");
        StellenanzeigeWindow stellenanzeigeWindow = new StellenanzeigeWindow(stellenanzeigeDTO);
    }

    @Test
    public void testStellenanzeigeUnternehmen() {
        unternehmen = new Unternehmen();
        unternehmen.setEmail("abc.de");
        unternehmen.setCname("Test");
        unternehmen.setPasswort("11111111");
        unternehmen.setVorname("vorname");
        unternehmen.setNachname("nachname");
        unternehmen.setHauptsitz("Bonn");
        unternehmen.setBundesland("Nordrhein-Westfalen");
        when(vaadinSession.getAttribute(Roles.Unternehmen)).thenReturn(unternehmen);
        StellenanzeigeDTO stellenanzeigeDTO = new StellenanzeigeDTO();
        stellenanzeigeDTO.setId(102);
        stellenanzeigeDTO.setFirmenname("BargoBank");
        stellenanzeigeDTO.setBeschreibung("Test");
        StellenanzeigeWindow stellenanzeigeWindow = new StellenanzeigeWindow(stellenanzeigeDTO);
    }



    @Test
    public void testBewerbungUnternehmen() {

        Adresse adresse = new Adresse();
        student.setAdresse(adresse);
        when(vaadinSession.getAttribute(Roles.Student)).thenReturn(student);
        StellenanzeigeDTO stellenanzeigeDTO = new StellenanzeigeDTO();
        stellenanzeigeDTO.setId(102);
        stellenanzeigeDTO.setFirmenname("BargoBank");
        stellenanzeigeDTO.setBeschreibung("Test");
        BewerbungDTO bewerbungDTO = new BewerbungDTO();
        BewerbungWindow bewerbungWindow = new BewerbungWindow(stellenanzeigeDTO,"Student",bewerbungDTO);
    }

}