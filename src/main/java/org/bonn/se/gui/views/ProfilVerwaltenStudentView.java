package org.bonn.se.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se.gui.component.OrtPlzTextField;
import org.bonn.se.gui.component.ProfilStudentTextField;
import org.bonn.se.gui.component.StudentDateField;
import org.bonn.se.gui.component.TopPanelUser;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.User;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;

import java.io.File;
import java.sql.SQLException;

public class ProfilVerwaltenStudentView extends GridLayout implements View {
    static GridLayout GridAnzeig = null;

    public static GridLayout getGridAnzeig() {
        return GridAnzeig;
    }

    public static void setGridAnzeig(GridLayout gridAnzeig) {
        GridAnzeig = gridAnzeig;
    }


    public void setUp() throws DatabaseException, SQLException {
        User user = new User();

        //Für Test
        user.setEmail("test1@test.de");
        user.setType("S");
     //   Student student1 = ProfilVerwaltenDAO.getStudent("test1@test.de");
    //    MyUI.getCurrent().getSession().setAttribute(Roles.Student,student1);

        //Student per Session nehmen
        Student student = ((Student) MyUI.getCurrent().getSession().getAttribute(Roles.Student));

        /*
        //Student Daten als Value
        String vorname = student.getVorname();
        String nachname = student.getNachname();
        //String gdatum = student.getG_datum();
        //Adresse adresse = student.getAdresse();
        String email =student.getEmail();
        String mobilnr = student.getMobil_nr();
        String ausbildung = student.getAusbildung();
        String studium= student.getStudiengang();
        String abschluss = student.getAbschluss();
        */

        /*
        Column 1:
        It Tätigkeit

        Anhänge
         */

        /*
        column 2:
        It Kenntnisse

        Sprach Kenntnisse
        */

        //Grid Einstellungen und Top Panel
        TopPanelUser topPanel = new TopPanelUser();
        topPanel.setMargin(false);
       // HashMap<String, String> hasmap = new HashMap<>();
       // hasmap.put("Vorname",student.getVorname());
       // String[] s  ={"Vorname,Nachname,"}
       // Maingrid.addComponent(new ProfilStudentTextField(),0,i);

        this.setMargin(false);
        this.setColumns(10);
        this.setRows(3);
        this.setHeightUndefined();
        this.setWidthFull();

        GridLayout gridLayout = new GridLayout(3,13);
        gridLayout.setHeightUndefined();
        gridLayout.setWidthFull();

        this.addComponent(topPanel, 0, 0, 9, 0);
        topPanel.setHeightUndefined();
        topPanel.addStyleName("toppanel");
        Label label = new Label("<h1>Allgemeine Angaben</h1>",ContentMode.HTML);



        //
        /*
        //Column0
        ProfilStudentTextField tfvorname = new ProfilStudentTextField("Vorname", "vorname");
        gridLayout.addComponent(tfvorname, 0,1);
        gridLayout.setComponentAlignment(tfvorname, Alignment.MIDDLE_CENTER);

        TextField plz = new TextField("PLZ");
        OrtPlzTextField ortPlzTextField = new OrtPlzTextField();
        ortPlzTextField.getPlz().setCaption("PLZ");
        ortPlzTextField.getBundesland().setCaption("Ort");
        ortPlzTextField.getPlz().setHeight("37px");
        ortPlzTextField.getBundesland().setHeight("37px");
        gridLayout.addComponent(ortPlzTextField, 0,2);

        //Column2
        ProfilStudentTextField tfberufs = new ProfilStudentTextField("Berufstätigkeiten", "Muster");
        gridLayout.addComponent(tfberufs, 1,1);
        */



        //Erzeugung von TextField: mit Student Daten ausfüllen
        //Column 0
        Image profilbild = new Image();
        profilbild.setSource(new FileResource(new File("src/main/webapp/VAADIN/themes/demo/images/Unknown.png")));
        ProfilStudentTextField tfvorname = new ProfilStudentTextField("Vorname", "vorname");
        ProfilStudentTextField tfnachname = new ProfilStudentTextField("Nachname", "nachname");
        ProfilStudentTextField tfgdatum= new ProfilStudentTextField("Geburtsdatum", "Muster");

        TextField plz = new TextField("PLZ");
        OrtPlzTextField ortPlzTextField = new OrtPlzTextField();
        /*
        ortPlzTextField.getPlz().setCaption("PLZ");
        ortPlzTextField.getBundesland().setCaption("Ort");
        ortPlzTextField.getPlz().setHeight("37px");
        ortPlzTextField.getBundesland().setHeight("37px");


         */

        ProfilStudentTextField tfemail = new ProfilStudentTextField("Email", "vorname");
        ProfilStudentTextField tfmobilnr = new ProfilStudentTextField("Mobil. Nr", "mobilnr");
        ProfilStudentTextField tfausbildung = new ProfilStudentTextField("Ausbildung", "ausbildung");
        ProfilStudentTextField tfstudium = new ProfilStudentTextField("Studium", "studium");
        ProfilStudentTextField tfabschluss = new ProfilStudentTextField("Abschluss", "abschluss");

        //Erzeugung von TextField: mit Student Daten ausfüllen
        //Column 1
        ProfilStudentTextField tfberufs = new ProfilStudentTextField("Berufstätigkeiten", "Muster");
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        StudentDateField beginn = new StudentDateField("Beginn");
        StudentDateField ende = new StudentDateField("Ende");
        horizontalLayout.addComponents(beginn,ende);
        beginn.setReadOnly(true);
        ende.setReadOnly(true);
        ProfilStudentTextField tfanhänge = new ProfilStudentTextField("Anhänge", "Muster");
            //Niveau

        //Erzeugung von TextField: mit Student Daten ausfüllen
        //Column 2
        ProfilStudentTextField tfitkenntnis = new ProfilStudentTextField("IT-Kentnisse", "Muster");
        ProfilStudentTextField tfsprache= new ProfilStudentTextField("Sprachkenntnisse", "Muster");
            //Niveau



        //Erzeugung von Button: Mit Button Caption
        Button bearbeitenButton = new Button("Profil Bearbeiten");
        Button abbrechenButton = new Button("Abbrechen");
        Button fertigButton = new Button("Fertig");




        //Anordnung von TextField
        //Column 0
        gridLayout.addComponent(profilbild,0,0);
        gridLayout.addComponent(tfvorname, 0,1);
        gridLayout.addComponent(tfnachname, 0,2);
        gridLayout.addComponent(tfgdatum, 0, 3);
        gridLayout.addComponent(ortPlzTextField, 0,4);
        gridLayout.addComponent(tfemail, 0, 5);
        gridLayout.addComponent(tfmobilnr, 0, 6);
        gridLayout.addComponent(tfausbildung, 0, 7);
        gridLayout.addComponent(tfstudium, 0, 8);
        gridLayout.addComponent(tfabschluss, 0, 9);

        //Anordnung von TextField
        //Column 1-->2
        gridLayout.addComponent(tfberufs, 1,0);
        gridLayout.addComponent(tfanhänge, 1,6);
        gridLayout.addComponent(horizontalLayout,1,1);


        //Anordnung von TextField
        //Column 2
        gridLayout.addComponent(tfitkenntnis, 2,0);
        gridLayout.addComponent(tfsprache, 2,2);

        //Anordnung von Button
        this.addComponent(abbrechenButton,7,2,7,2);
        this.addComponent(bearbeitenButton,8,2,8,2);
        this.addComponent(fertigButton,9,2,9,2);

        //student.getTaetigkeitenListe().size();

        for (int i = 0; i <= 9; i++) {
            gridLayout.setComponentAlignment(gridLayout.getComponent(0,i),Alignment.MIDDLE_CENTER);
        }


        //Anordnung von Button
        this.addComponent(abbrechenButton,7,2,7,2);
        this.addComponent(bearbeitenButton,8,2,8,2);
        this.addComponent(fertigButton,9,2,9,2);


        this.setComponentAlignment(topPanel, Alignment.TOP_CENTER);
        this.setMargin(false);
        this.addComponent(gridLayout,0,1,9,1);

        bearbeitenButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
               for (int i = 0; i < 10 ; i++) {
                    if (gridLayout.getComponent(0,i) instanceof TextField ) {
                        ((TextField) gridLayout.getComponent(0, i)).setReadOnly(false);
                    }
                }
                beginn.setReadOnly(false);
                ende.setReadOnly(false);
                /*
                tfnachname.setReadOnly(false);

                email.setReadOnly(true);
                telnr.setReadOnly(false);
                ausbildung.setReadOnly(false);
                studium.setReadOnly(false);
                abschluss.setReadOnly(false);
                */
            }

        });

//abschluss.addValueChangeListener() farbe zu basteln
        abbrechenButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().getNavigator().navigateTo(Views.RegisterStudent);
            }
        });
    }
        @Override
        public void enter (ViewChangeListener.ViewChangeEvent event){
            try {
                setUp();
            } catch (DatabaseException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

}


