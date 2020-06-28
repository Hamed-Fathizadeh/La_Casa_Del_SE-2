package org.bonn.se.gui.window;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se.control.BewerbungControl;
import org.bonn.se.gui.component.CustomWindow;
import org.bonn.se.model.dao.ProfilDAO;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.Taetigkeit;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.ImageConverter;
import org.bonn.se.services.util.PdfUploader;
import org.bonn.se.services.util.Roles;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BewerbungWindow extends CustomWindow {

    public BewerbungWindow(StellenanzeigeDTO stellenanzeige, String userType, BewerbungDTO bewerbung )  {

        setUp(stellenanzeige,userType, bewerbung );
    }

    public void setUp(StellenanzeigeDTO stellenanzeige, String userType,BewerbungDTO bewerbung) {

        Panel panelBewerbung = new Panel();
        panelBewerbung.setWidthFull();
        Button back = new Button("Zurück");

        back.addClickListener((Button.ClickListener) event -> {
            BewerbungWindow.this.close();
        });

        GridLayout mainGridLayout = new GridLayout(6, 16);
        mainGridLayout.setSizeFull();
        mainGridLayout.setMargin(true);
        Image profilbild;
        Student student = null;
        if(userType.equals("Student")) {
            student = (Student) UI.getCurrent().getSession().getAttribute(Roles.Student);

        }else {
            try {

                student = ProfilDAO.getInstance().getStudent(bewerbung.getEmailStudent());
            } catch (DatabaseException | SQLException e) {
                Logger.getLogger(BewerbungWindow.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        profilbild = ImageConverter.convertImagetoProfil(student.getPicture());
        if(userType.equals("Student")) {
            Label titel = new Label("<h2><b> Bewerbung auf: " + stellenanzeige.getTitel() + "</font></b></h2>", ContentMode.HTML);

            mainGridLayout.addComponent(titel, 0, 1, 5, 1);
            mainGridLayout.setComponentAlignment(titel, Alignment.TOP_CENTER);
        }
        Label vorNachname = new Label("Vor und Nachname: ");
        Label vorNachnameData = new Label(student.getVorname()+" "+student.getNachname());
        Label gebDatum = new Label("Geb Datum: ");
        Label gebDatumData = new Label(student.getG_datum() == null?"":""+student.getG_datum());
        Label adresse = new Label("Adresse: ");
        Label adresseData = new Label(student.getAdresse().getStrasse());
        Label plzOrt = new Label("PLZ Ort: ");
        Label plzOrtData = new Label(student.getAdresse().getPlz()+" "+student.getAdresse().getOrt());
        Label rufnummer = new Label("Rufnummer: ");
        Label rufnummerData = new Label(student.getKontakt_nr());
        Label email = new Label("Email: ");
        Label emailData = new Label(student.getEmail());
        Label abschluss = new Label("Abschluss: ");
        Label abschlussData = new Label(student.getAbschluss());
        Label studiengang = new Label("Studiengang: ");
        Label studiengangData = new Label(student.getStudiengang());
        Label ausbildung = new Label("Ausbildung: ");
        Label ausbildungData = new Label(student.getAusbildung());

        Label line = new Label("<h1><color: black h1>", ContentMode.HTML);

        GridLayout grid1 = new GridLayout(2, student.getTaetigkeiten().size()+1);
        grid1.setMargin(false);
        grid1.setWidth("300px");
        grid1.addComponent( new Label("<h1>Berufstätigkeiten<h1>", ContentMode.HTML),0,0,1,0);
        int i = 1;
        for(Taetigkeit te: student.getTaetigkeiten()){
            if(te.getTaetigkeitName() != null) {
                grid1.addComponent(new Label(te.getTaetigkeitName() + ":  "), 0, i);
                grid1.addComponent(new Label(" " + te.getBeginn() + " - " + te.getEnde()), 1, i);
            }
            i++;
        }

        GridLayout grid2 = new GridLayout(2, student.getItKenntnisList().size()+1);
        grid2.setMargin(false);
        grid2.setWidth("300px");
        grid2.addComponent( new Label("<h1>IT-Kenntnisse<h1>", ContentMode.HTML),0,0,1,0);
        i=1;
        for(Student.ITKenntnis itK: student.getItKenntnisList()){
            if(itK.getKenntnis() != null) {
                grid2.addComponent(new Label(itK.getKenntnis() + ":  "), 0, i);
                grid2.addComponent(new Label(" " + itK.getNiveau()), 1, i);
            }
            i++;
        }

        GridLayout grid3 = new GridLayout(2, student.getSprachKenntnisList().size()+1);
        grid3.setMargin(false);
        grid3.setWidth("300px");
        grid3.addComponent( new Label("<h1>Sprachkenntnisse<h1>", ContentMode.HTML),0,0,1,0);
        i=1;

        for(Student.SprachKenntnis sp: student.getSprachKenntnisList()){
            if(sp.getKenntnis() != null) {
                grid3.addComponent(new Label(sp.getKenntnis() + ":  "), 0, i);
                grid3.addComponent(new Label(" " + sp.getNiveau()), 1, i);
            }
            i++;
        }


        mainGridLayout.addComponent(back, 5, 0);
          mainGridLayout.addComponent(profilbild, 0, 2,0,7);
        mainGridLayout.addComponent(vorNachname, 1, 2);mainGridLayout.addComponent(vorNachnameData, 2, 2);
           mainGridLayout.addComponent(gebDatum, 1, 3);mainGridLayout.addComponent(gebDatumData, 2, 3);
             mainGridLayout.addComponent(adresse, 1, 4); mainGridLayout.addComponent(adresseData, 2, 4);
              mainGridLayout.addComponent(plzOrt, 1, 5);mainGridLayout.addComponent(plzOrtData, 2, 5);
           mainGridLayout.addComponent(rufnummer, 1, 6);mainGridLayout.addComponent(rufnummerData, 2, 6);
               mainGridLayout.addComponent(email, 1, 7); mainGridLayout.addComponent(emailData, 2, 7);

        mainGridLayout.addComponent(abschluss, 3, 2); mainGridLayout.addComponent(abschlussData, 4, 2);
        mainGridLayout.addComponent(studiengang, 3, 3);  mainGridLayout.addComponent(studiengangData, 4, 3);
        mainGridLayout.addComponent(ausbildung, 3, 4); mainGridLayout.addComponent(ausbildungData, 4, 4);

        mainGridLayout.addComponent(line, 0, 8,4,8);

        mainGridLayout.addComponent(grid1, 0, 9,1,9);
        mainGridLayout.addComponent(grid2, 2, 9,3,9);
        mainGridLayout.addComponent(grid3, 4, 9,5,9);


        mainGridLayout.setComponentAlignment(back, Alignment.TOP_RIGHT);

        mainGridLayout.setComponentAlignment(profilbild, Alignment.MIDDLE_CENTER);

        mainGridLayout.setComponentAlignment(line, Alignment.TOP_CENTER);
        mainGridLayout.setComponentAlignment(grid1, Alignment.TOP_CENTER);
        mainGridLayout.setComponentAlignment(grid2, Alignment.TOP_CENTER);
        mainGridLayout.setComponentAlignment(grid3, Alignment.TOP_CENTER);






        if(userType.equals("Student")) {
            Button bewerben = new Button("Bewerben");
            final RichTextArea richTextArea = new RichTextArea();
            richTextArea.setWidthFull();
            richTextArea.setHeight("600px");
            final Student st = student;
            richTextArea.setValue("<h1>Schreiben Sie hier Ihre Anschreiben!</h1>");


            mainGridLayout.addComponent(new Label("&nbsp", ContentMode.HTML), 0, 12, 0, 12);
            mainGridLayout.addComponent(richTextArea, 0, 13, 5, 13);
            mainGridLayout.addComponent(new Label("&nbsp", ContentMode.HTML), 0, 14, 5, 14);
            mainGridLayout.addComponent(bewerben, 5, 15);

            mainGridLayout.setComponentAlignment(richTextArea, Alignment.BOTTOM_CENTER);
            mainGridLayout.setComponentAlignment(bewerben, Alignment.BOTTOM_RIGHT);

            bewerben.addClickListener((Button.ClickListener) event -> {

                BewerbungDTO bewerbungDTO = new BewerbungDTO();
                bewerbungDTO.setDescription(richTextArea.getValue());
                bewerbungDTO.setLebenslauf(PdfUploader.getByte());
                bewerbungDTO.setStatus(9);
                bewerbungDTO.setStudentID(st.getStudent_id());
                bewerbungDTO.setAnzeigeID(stellenanzeige.getId());
                BewerbungWindow.this.close();
                try {
                    BewerbungControl.bewerben(bewerbungDTO);
                } catch (DatabaseException e) {
                    e.printStackTrace();
                    Notification.show("DB-Fehler", e.getReason(), Notification.Type.ERROR_MESSAGE);
                }
            });

        }else{
            Label titel = new Label("<h2><b> Bewerbung</font></b></h2>", ContentMode.HTML);
            Label lAnschreiben = new Label("<h1>Anschreiben<h1>", ContentMode.HTML);

            Button downloadLebnslauf = new Button("Lebenslauf Herunterladen");
            Button markieren = new Button("Bewerbung Markieren");
            markieren.setSizeFull();

            Button ablehnen = new Button("Bewerbung ablehnen");
            Button zusagen = new Button("Bewerbung zusagen");





            Image picMarkierung;
            if( bewerbung.isBewerbungMarkiert()){
                ThemeResource resource3 = new ThemeResource("img/Anzeigen/makierung.png");
                picMarkierung = new Image(null, resource3);
                mainGridLayout.addComponent(picMarkierung, 5, 2);
                markieren.setCaption("Markierung aufheben");
            }else{
                markieren.setCaption("Bewerbung Markieren");
            }


            RichTextArea beschreibungData = new RichTextArea();
            beschreibungData.setSizeFull();
            beschreibungData.setValue(bewerbung.getDescription());
            beschreibungData.setReadOnly(true);



            mainGridLayout.addComponent(new Label("&nbsp", ContentMode.HTML), 0, 11, 0, 11);
            mainGridLayout.addComponent(lAnschreiben, 0, 12, 0, 12);
            mainGridLayout.addComponent(beschreibungData, 0, 13, 5, 13);
            mainGridLayout.addComponent(new Label("&nbsp", ContentMode.HTML), 0, 14, 5, 14);


            mainGridLayout.addComponent(markieren, 4, 0);
            mainGridLayout.addComponent(downloadLebnslauf, 5, 15);
            mainGridLayout.addComponent(zusagen, 4, 15);
            mainGridLayout.addComponent(ablehnen, 3, 15);
            mainGridLayout.addComponent(titel, 0, 1, 5, 1);
            mainGridLayout.setComponentAlignment(titel, Alignment.TOP_CENTER);
            mainGridLayout.setComponentAlignment(markieren, Alignment.TOP_RIGHT);

            markieren.addClickListener((Button.ClickListener) event -> {
                try {
                   bewerbung.setBewerbungMarkiert(BewerbungControl.markierungAendern(bewerbung.getBewerbungID()));
                } catch (DatabaseException | SQLException e) {
                    e.printStackTrace();
                }
                BewerbungWindow bewerbungWindow = new BewerbungWindow(null,"Unternehmen", bewerbung);
                UI.getCurrent().addWindow(bewerbungWindow);
                BewerbungWindow.this.close();



            });

            zusagen.addClickListener((Button.ClickListener) event -> {

                BewerbungControl.statusAendern(bewerbung.getBewerbungID(),2);
                BewerbungWindow.this.close();
            });

            ablehnen.addClickListener((Button.ClickListener) event -> {
                if(bewerbung.isBewerbungMarkiert()) {
                    try {
                        bewerbung.setBewerbungMarkiert(BewerbungControl.markierungAendern(bewerbung.getBewerbungID()));
                    } catch (DatabaseException | SQLException e) {
                        e.printStackTrace();
                    }
                }

                BewerbungControl.statusAendern(bewerbung.getBewerbungID(),3);

                BewerbungWindow.this.close();
            });




                 if(bewerbung != null) {
                try {

                    StreamResource myResource = BewerbungControl.downloadLebenslauf(bewerbung.getStudentID(),bewerbung.getStudentVorname(),bewerbung.getStudentNachname());
                    FileDownloader fileDownloader = new FileDownloader(myResource);
                    fileDownloader.extend(downloadLebnslauf);
                } catch (DatabaseException | SQLException e) {
                    e.printStackTrace();
                }
            }

            downloadLebnslauf.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {

                }
            });




        }
        panelBewerbung.setContent(mainGridLayout);
        this.setContent(panelBewerbung);





    }


}

