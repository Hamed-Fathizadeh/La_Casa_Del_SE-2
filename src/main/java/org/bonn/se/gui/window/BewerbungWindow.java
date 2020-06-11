package org.bonn.se.gui.window;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se.control.exception.BewerbungControl;
import org.bonn.se.gui.views.StudentHomeView;
import org.bonn.se.model.dao.ProfilDAO;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.ContainerNeuigkeiten;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.Taetigkeit;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.*;

import java.io.OutputStream;

public class BewerbungWindow extends Window {

    public BewerbungWindow(StellenanzeigeDTO stellenanzeige, Unternehmen unternehmen_data)  {
        setUp(stellenanzeige,unternehmen_data);
    }

    public void setUp(StellenanzeigeDTO stellenanzeige, Unternehmen unternehmen_data) {
        this.center();
        this.setWidth("80%");
        this.setHeight("90%");
        this.setModal(true);
        this.setResizable(false);
        this.setClosable(false);

        Panel panel = new Panel();
        panel.setWidthFull();
        Button back = new Button("Zurück");

        back.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                BewerbungWindow.this.close();

            }
        });

        GridLayout mainGridLayout = new GridLayout(6, 16);
        mainGridLayout.setSizeFull();
        mainGridLayout.setMargin(true);
        Student student = (Student) UI.getCurrent().getSession().getAttribute(Roles.Student);


        Image profilbild = ImageConverter.convertImagetoProfil(student.getPicture());
        Label titel = new Label("<h2><b> Bewerbung auf: " + stellenanzeige.getTitel() + "</font></b></h2>", ContentMode.HTML);
        Label vor_nachname = new Label("Vor und Nachname: ");
        Label vor_nachname_data = new Label(student.getVorname()+" "+student.getNachname());
        Label geb_datum = new Label("Geb Datum: ");
        Label geb_datum_data = new Label(""+student.getG_datum());
        Label adresse = new Label("Adresse: ");
        Label adresse_data = new Label(student.getAdresse().getStrasse());
        Label plzOrt = new Label("PLZ Ort: ");
        Label plzOrt_data = new Label(student.getAdresse().getPlz()+" "+student.getAdresse().getOrt());
        Label rufnummer = new Label("Rufnummer: ");
        Label rufnummer_data = new Label(student.getKontakt_nr());
        Label email = new Label("Email: ");
        Label email_data = new Label(student.getEmail());
        Label abschluss = new Label("Abschluss: ");
        Label abschluss_data = new Label(student.getAbschluss());
        Label studiengang = new Label("Studiengang: ");
        Label studiengang_data = new Label(student.getStudiengang());
        Label ausbildung = new Label("Ausbildung: ");
        Label ausbildung_data = new Label(student.getAusbildung());

        Label line = new Label("<h1><color: black h1>", ContentMode.HTML);

        GridLayout grid1 = new GridLayout(2, student.getTaetigkeiten().size()+1);
        grid1.setMargin(false);
        grid1.addComponent( new Label("<h1>Berufstätigkeiten<h1>", ContentMode.HTML),0,0,1,0);
        int i = 1;
        for(Taetigkeit te: student.getTaetigkeiten()){
            grid1.addComponent(new Label(te.getTaetigkeitName()+":  "),0,i);
            grid1.addComponent(new Label(" "+te.getBeginn()+" - " +te.getEnde()),1,i);
            i++;
        }

        GridLayout grid2 = new GridLayout(2, student.getItKenntnisList().size()+1);
        grid2.setMargin(false);
        grid2.addComponent( new Label("<h1>IT-Kenntnisse<h1>", ContentMode.HTML),0,0,1,0);
        i=1;
        for(Student.ITKenntnis itK: student.getItKenntnisList()){
            grid2.addComponent(new Label(itK.getKenntnis()+":  "),0,i);
            grid2.addComponent(new Label(" "+itK.getNiveau()),1,i);
            i++;
        }

        GridLayout grid3 = new GridLayout(2, student.getSprachKenntnisList().size()+1);
        grid3.setMargin(false);
        grid3.addComponent( new Label("<h1>IT-Sprachkenntnisse<h1>", ContentMode.HTML),0,0,1,0);
        i=1;

        for(Student.SprachKenntnis sp: student.getSprachKenntnisList()){
            grid3.addComponent(new Label(sp.getKenntnis()+":  "),0,i);
            grid3.addComponent(new Label(" "+sp.getNiveau()),1,i);
            i++;
        }


        mainGridLayout.addComponent(back, 5, 0);
        mainGridLayout.addComponent(titel, 0, 1, 5, 1);
          mainGridLayout.addComponent(profilbild, 0, 2,0,7);
        mainGridLayout.addComponent(vor_nachname, 1, 2);mainGridLayout.addComponent(vor_nachname_data, 2, 2);
           mainGridLayout.addComponent(geb_datum, 1, 3);mainGridLayout.addComponent(geb_datum_data, 2, 3);
             mainGridLayout.addComponent(adresse, 1, 4); mainGridLayout.addComponent(adresse_data, 2, 4);
              mainGridLayout.addComponent(plzOrt, 1, 5);mainGridLayout.addComponent(plzOrt_data, 2, 5);
           mainGridLayout.addComponent(rufnummer, 1, 6);mainGridLayout.addComponent(rufnummer_data, 2, 6);
               mainGridLayout.addComponent(email, 1, 7); mainGridLayout.addComponent(email_data, 2, 7);

        mainGridLayout.addComponent(abschluss, 3, 2); mainGridLayout.addComponent(abschluss_data, 4, 2);
        mainGridLayout.addComponent(studiengang, 3, 3);  mainGridLayout.addComponent(studiengang_data, 4, 3);
        mainGridLayout.addComponent(ausbildung, 3, 4); mainGridLayout.addComponent(ausbildung_data, 4, 4);

        mainGridLayout.addComponent(line, 0, 8,4,8);

        mainGridLayout.addComponent(grid1, 0, 9,1,9);
        mainGridLayout.addComponent(grid2, 2, 9,3,9);
        mainGridLayout.addComponent(grid3, 4, 9,5,9);


        mainGridLayout.setComponentAlignment(back, Alignment.TOP_RIGHT);
        mainGridLayout.setComponentAlignment(titel, Alignment.TOP_CENTER);
        mainGridLayout.setComponentAlignment(profilbild, Alignment.MIDDLE_CENTER);

        mainGridLayout.setComponentAlignment(line, Alignment.TOP_CENTER);
        mainGridLayout.setComponentAlignment(grid1, Alignment.TOP_CENTER);
        mainGridLayout.setComponentAlignment(grid2, Alignment.TOP_CENTER);
        mainGridLayout.setComponentAlignment(grid3, Alignment.TOP_CENTER);

        Button bewerben = new Button("Bewerben");





        final RichTextArea richTextArea = new RichTextArea();
        richTextArea.setWidthFull();
        richTextArea.setHeight("600px");

        richTextArea.setValue("<h1>Schreiben Sie hier Ihre Anschreiben!</h1>");


        mainGridLayout.addComponent(new Label("&nbsp", ContentMode.HTML), 0, 12,0,12);
        mainGridLayout.addComponent(richTextArea, 0, 13,5,13);
        mainGridLayout.addComponent(new Label("&nbsp", ContentMode.HTML), 0, 14,5,14);
        mainGridLayout.addComponent(bewerben, 5, 15);

        mainGridLayout.setComponentAlignment(richTextArea, Alignment.BOTTOM_CENTER);
        mainGridLayout.setComponentAlignment(bewerben, Alignment.BOTTOM_RIGHT);

        panel.setContent(mainGridLayout);
        this.setContent(panel);



        bewerben.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {

                BewerbungDTO bewerbungDTO = new BewerbungDTO();
                bewerbungDTO.setDescription(richTextArea.getValue());
                bewerbungDTO.setLebenslauf(PdfUploader.getByte());
                bewerbungDTO.setStatus(1);
                System.out.println("bewWindow "+student.getStudent_id());
                bewerbungDTO.setStudentID(student.getStudent_id());
                bewerbungDTO.setAnzeigeID(stellenanzeige.getId());
                BewerbungWindow.this.close();
                    try {
                        BewerbungControl.bewerben(bewerbungDTO);
                    } catch (DatabaseException e) {
                        e.printStackTrace();
                        Notification.show("DB-Fehler", e.getReason(), Notification.Type.ERROR_MESSAGE);
                    }
                UI.getCurrent().addWindow(new ConfirmationWindow("Sie haben sich erfolgreich beworben!"));
                UI.getCurrent().getNavigator().navigateTo(Views.StudentHomeView);
            }
        });

    }


}

