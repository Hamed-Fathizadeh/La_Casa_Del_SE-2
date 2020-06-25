package org.bonn.se.gui.window.wizard;

import com.vaadin.data.HasValue;
import com.vaadin.ui.*;
import org.bonn.se.gui.component.NumeralField;
import org.bonn.se.gui.component.OrtPlzTextField;
import org.bonn.se.gui.component.PlaceHolderField;
import org.bonn.se.gui.component.PopUpTextField;
import org.bonn.se.model.dao.ProfilDAO;
import org.bonn.se.model.objects.entitites.Adresse;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.DatenStudentProfil;
import org.bonn.se.services.util.ImageConverter;
import org.bonn.se.services.util.Roles;
import org.vaadin.easyuploads.UploadField;
import org.vaadin.teemu.wizards.WizardStep;

public class WizardStudentDatenStep implements WizardStep {

        OrtPlzTextField ort;
        PopUpTextField strasse;
        ComboBox<String> abschluss;
        DateField g_datum;
        PopUpTextField studiengang;
        PopUpTextField ausbildung;
        NumeralField mobilnr;
        UploadField uploadProfil;
        Image image = ImageConverter.getUnknownProfilImage();
        UploadField lebenslauf;


        @Override
        public String getCaption() {
            return "Daten";
        }

        @Override
        public Component getContent() {

            GridLayout gridLayout = new GridLayout(2, 3);
            gridLayout.setHeightUndefined();
            gridLayout.setWidth("100%");


            FormLayout form1 = new FormLayout();


            uploadProfil = new UploadField();
            uploadProfil.setDisplayUpload(false);

            uploadProfil.setButtonCaption("Profilbild hochladen");
            uploadProfil.setClearButtonVisible(true);

            uploadProfil.setAcceptFilter("image/*");


            uploadProfil.addValueChangeListener((HasValue.ValueChangeEvent<byte[]> event) -> {
                form1.removeComponent(image);

                byte[] bild = uploadProfil.getValue();

                image = ImageConverter.convertImagetoProfil(bild);

                form1.addComponent(image,0);
            });









            form1.setWidth("300px");
            form1.setMargin(true);

            g_datum = new DateField();
            g_datum.setHeight("56px");
            g_datum.setWidth("300px");
            g_datum.setPlaceholder("Geburtsdatum dd.mm.yyyy");

            mobilnr = new NumeralField("Kontaktnummer");


            image.setHeight("170px");
            image.setWidth("150px");
            lebenslauf = new UploadField();
            lebenslauf.setButtonCaption("Lebenslauf hochladen");
            lebenslauf.setDisplayUpload(false);
            Label filename = new Label(lebenslauf.getLastFileName());
            lebenslauf.addValueChangeListener((HasValue.ValueChangeListener<byte[]>) event -> {
                form1.removeComponent(filename);
                Label filename1 = new Label(lebenslauf.getLastFileName());
                form1.addComponent(filename1,5);
            }
            );




            form1.addComponents(image, uploadProfil, g_datum, mobilnr,lebenslauf,filename);


            FormLayout form2 = new FormLayout();
            form2.setWidth("300px");
            form2.setMargin(true);
            PlaceHolderField place1 = new PlaceHolderField();
            strasse = new PopUpTextField("Strasse");

                ort = new OrtPlzTextField();


            PlaceHolderField place2 = new PlaceHolderField();
            studiengang = new PopUpTextField("Studiengang");
            ausbildung = new PopUpTextField("Ausbildung (optional)");


            abschluss = new ComboBox<>("", DatenStudentProfil.getCollection());
            abschluss.setPlaceholder("Wähle den höchsten Abschluss...");
            abschluss.setHeight("56px");
            abschluss.setWidth("300px");

            form2.addComponents(place1, strasse, ort, studiengang, place2, ausbildung, abschluss);




            gridLayout.addComponent(form1, 0, 0, 0, 0);
            gridLayout.addComponent(form2, 1, 0, 1, 0);


            gridLayout.setComponentAlignment(form1, Alignment.TOP_LEFT);
            gridLayout.setComponentAlignment(form2, Alignment.TOP_LEFT);


            return gridLayout;
        }


        @Override
        public boolean onAdvance() {


            Student student = (Student) UI.getCurrent().getSession().getAttribute(Roles.Student);
            if(uploadProfil.getValue() != null){
                student.setPicture(uploadProfil.getValue());
                uploadProfil.clear();
            }
            if(lebenslauf.getValue() != null) {
                student.setLebenslauf(lebenslauf.getValue());
                lebenslauf.clear();
            }
            student.setAbschluss(abschluss.getValue());
            student.setKontakt_nr(mobilnr.getValue());
            student.setAusbildung(ausbildung.getValue());
            student.setStudiengang(studiengang.getValue());
            student.setG_datum(g_datum.getValue());
            Adresse adresse = new Adresse();
            if(!( ort.getOrt() == null ) || ort.getPlz() == null ) {
                adresse.setStrasse(strasse.getValue());
                adresse.setPlz(ort.getPlz());
                adresse.setOrt(ort.getOrt());
                adresse.setBundesland(ort.getBunesland());
            }
            student.setAdresse(adresse);

            try {
                ProfilDAO.getInstance().createStudentProfil1(student);
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
            UI.getCurrent().getSession().setAttribute(Roles.Student, student);



            return true;
        }


        @Override
        public boolean onBack() {
            return true;
        }
}

