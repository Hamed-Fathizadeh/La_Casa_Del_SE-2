package junit.util;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import org.bonn.se.gui.component.Anzeigen;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.util.Roles;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)




public class AnzeigenTest {
    //public Anzeigen(String str, List<StellenanzeigeDTO> dataInput){
      //  super();

    @Mock
    private UI ui;

    @Mock
    private VaadinSession vaadinSession;

    @Mock
    Navigator navigator;

    Unternehmen unternehmen = null;
    Student student = null;

    List<StellenanzeigeDTO> dataInput;
    Anzeigen anz;
    Anzeigen anz1;


/*
        setId(id);
        setDatum(datum);
        setZeitstempel(zeitstempel);
        setTitel(titel);
        setBeschreibung(beschreibung);
        setStatus(status);
        setStandort(standort);
        setBundesland(bundesland);
        setFirmenname(firmenname);
        setHauptsitz(hauptsitz);
        setSuchbegriff(suchbegriff);
        setArt(art);
        setHatNeuBewerbung(anzahlNeuBewerbung);
        setBranche(branche);
 */

    @Before
    public void setUp() throws Exception {
        UI.setCurrent(ui);
        when(ui.getSession()).thenReturn(vaadinSession);
        when(ui.getNavigator()).thenReturn(navigator);
    }

    @Test
    public void getTest() {
        student = new Student();
        student.setEmail("abc.de");
        student.setVorname("Test");
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
        //try {
            anz = new Anzeigen("str", dataInput);
            Assert.assertEquals(dataInput, anz.getData());
            Assert.assertEquals(10,anz.setGesamtNeuBewerbungen(10));
            Assert.assertEquals(10, anz.getGesamtNeuBewerbungen());

             anz1 = new Anzeigen("Student", dataInput);
            Assert.assertEquals(dataInput, anz1.getData());
            Assert.assertEquals(10,anz1.setGesamtNeuBewerbungen(10));
            Assert.assertEquals(20, anz1.getGesamtNeuBewerbungen());
            //System.out.println(anz.setGesamtNeuBewerbungen(10));
        /*}catch (NullPointerException e){
            e.printStackTrace();
            System.out.println("ini catch");
        }*/




    }



}