package org.bonn.se.gui.window.wizard;

import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import org.bonn.se.gui.component.StudentDatenView;
import org.bonn.se.model.dao.ProfilDAO;
import org.bonn.se.model.objects.entitites.Adresse;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;
import org.vaadin.teemu.wizards.WizardStep;

public class WizardStudentDatenStep implements WizardStep {


        StudentDatenView studentDatenView;
        @Override
        public String getCaption() {
            return "Daten";
        }

        @Override
        public Component getContent() {
                studentDatenView = new StudentDatenView();
            return studentDatenView;



/*
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

            filename = new Label(lebenslauf.getLastFileName());

            lebenslauf.addValueChangeListener((HasValue.ValueChangeListener<byte[]>) event -> {
                if(lebenslauf.getValue() == null) {
                    form1.removeComponent(filename);
                } else {
                    form1.removeComponent(filename);
                    filename = new Label(lebenslauf.getLastFileName());
                    form1.addComponent(filename, 5);
                }
            });




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


            abschluss = new ComboBox<>("");
            abschluss.setPlaceholder("Wähle den höchsten Abschluss...");
            abschluss.setHeight("56px");
            abschluss.setWidth("300px");
            abschluss.setItems(ComponentControl.getInstance().getAbschluss());

            form2.addComponents(place1, strasse, ort, studiengang, place2, ausbildung, abschluss);




            gridLayout.addComponent(form1, 0, 0, 0, 0);
            gridLayout.addComponent(form2, 1, 0, 1, 0);


            gridLayout.setComponentAlignment(form1, Alignment.TOP_LEFT);
            gridLayout.setComponentAlignment(form2, Alignment.TOP_LEFT);


            return gridLayout;

 */
        }


        @Override
        public boolean onAdvance() {


            Student student = (Student) UI.getCurrent().getSession().getAttribute(Roles.STUDENT);

            if(studentDatenView.getUploadProfil().getValue() != null){
                student.setPicture(studentDatenView.getUploadProfil().getValue());
                studentDatenView.getUploadProfil().clear();
            }
            if(studentDatenView.getLebenslauf().getValue() != null) {
                student.setLebenslauf(studentDatenView.getLebenslauf().getValue());
                studentDatenView.getLebenslauf().clear();
            }
            student.setAbschluss(studentDatenView.getAbschluss().getValue());
            student.setKontaktNr(studentDatenView.getMobilnr().getValue());
            student.setAusbildung(studentDatenView.getAusbildung().getValue());
            student.setStudiengang(studentDatenView.getStudiengang().getValue());
            student.setGDatum(studentDatenView.getgDatum().getValue());
            Adresse adresse = new Adresse();
            if(!( studentDatenView.getOrt().getOrtField().getValue() == null ) || studentDatenView.getOrt().getPlzField().getValue() == null ) {
                adresse.setStrasse(studentDatenView.getStrasse().getValue());
                adresse.setPlz(studentDatenView.getOrt().getPlzField().getValue());
                adresse.setOrt(studentDatenView.getOrt().getOrt());
                adresse.setBundesland(studentDatenView.getOrt().getBunesland());
            }
            student.setAdresse(adresse);

            try {
                ProfilDAO.getInstance().createStudentProfil1(student);
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
            UI.getCurrent().getSession().setAttribute(Roles.STUDENT, student);



            return true;
        }


        @Override
        public boolean onBack() {
            return true;
        }
}

