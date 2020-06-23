package org.bonn.se.gui.views;

import com.vaadin.data.HasValue;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se.control.ProfilControl;
import org.bonn.se.gui.component.*;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.DatenStudentProfil;
import org.bonn.se.services.util.ImageConverter;
import org.bonn.se.services.util.Roles;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.dialogs.DefaultConfirmDialogFactory;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProfilVerwaltenStudentView extends GridLayout implements View {
    static final GridLayout gridAnzeig = null;
    Button cancel;
    Button save;
    Button bearbeitenButton;
    final Button plus = new Button("", VaadinIcons.PLUS);
    final Button minus = new Button("", VaadinIcons.MINUS);
    HorizontalLayout buttonsBar;
    ProfilStudentTaetigkeit taetigkeit;



    public void setUp() throws DatabaseException, SQLException {

        Student student = ((Student) MyUI.getCurrent().getSession().getAttribute(Roles.Student));

        TopPanelUser topPanel = new TopPanelUser();
        topPanel.setMargin(false);

        this.setMargin(false);
        this.setColumns(3);
        this.setRows(13);
        this.setHeightUndefined();
        this.setWidthFull();
        this.addComponent(topPanel, 0, 0, 2, 0);

        FormLayout formLayout = new FormLayout();
        formLayout.setMargin(true);
        topPanel.setHeightUndefined();
        topPanel.addStyleName("toppanel");
        Label label = new Label("<h1>Allgemeine Angaben</h1>",ContentMode.HTML);
        cancel = new Button("Abbrechen");
        save= new Button("Speichern");





        Image profilbild = ImageConverter.convertImagetoProfil(student.getPicture());
        ProfilStudentTextField tfvorname = new ProfilStudentTextField("Vorname", student.getVorname());
        ProfilStudentTextField tfnachname = new ProfilStudentTextField("Nachname", student.getNachname());
        StudentDateField tfgdatum= new StudentDateField("Geburtsdatum");
        tfgdatum.setCaption("Geburtsdatum");
        tfgdatum.setValue(student.getG_datum());
        ProfilStudentTextField tstrasse = new ProfilStudentTextField("Strasse",student.getAdresse().getStrasse());
        OrtPlzTextField ortPlzTextField = new OrtPlzTextField();
        ortPlzTextField.getOrtField().setValue(student.getAdresse().getOrt() + ", " +student.getAdresse().getBundesland());
        ortPlzTextField.getPlzField().setValue(student.getAdresse().getPlz());

        ortPlzTextField.getOrtField().setPlaceholder("Ort");

        ortPlzTextField.setCaption("PLZ & Ort");
        ortPlzTextField.getPlzField().setPlaceholder("PLZ");
        ortPlzTextField.getPlzField().setHeight("37px");
        ortPlzTextField.getOrtField().setHeight("37px");
        ortPlzTextField.getPlzField().setWidth("60px");
        ortPlzTextField.getOrtField().setWidth("180px");
        ortPlzTextField.getPlzField().setReadOnly(true);
        ortPlzTextField.getOrtField().setReadOnly(true);

        ProfilStudentTextField tfemail = new ProfilStudentTextField("Email", student.getEmail());
        ProfilStudentTextField tkontaktnr = new ProfilStudentTextField("Kontaktnr", student.getKontakt_nr());
        ProfilStudentTextField tfausbildung = new ProfilStudentTextField("Ausbildung", student.getAusbildung());
        ProfilStudentTextField tfstudium = new ProfilStudentTextField("Studium", student.getStudiengang());
        ComboBox<String> tfabschluss = new ComboBox<>("", DatenStudentProfil.getCollection());
        tfabschluss.setCaption("Abschluss");
        tfabschluss.setPlaceholder("Abschluss");
        tfabschluss.setHeight("37px");
        tfabschluss.setWidth("250px");
        tfabschluss.setValue(student.getAbschluss());
        tfabschluss.setReadOnly(true);


        this.addComponent(formLayout,0,1,0,11);
        formLayout.addComponents(profilbild,tfvorname,tfnachname,tfgdatum,tstrasse,ortPlzTextField,tfemail,
                tkontaktnr,tfausbildung,tfstudium,tfabschluss);

        bearbeitenButton = new Button("Profil Bearbeiten");
        this.addComponent(bearbeitenButton,2,12,2,12);
        this.setComponentAlignment(bearbeitenButton,Alignment.TOP_CENTER);

        bearbeitenButton.addClickListener((Button.ClickListener) event -> {
            Notification notification = Notification.show("Information","Du kannst nun deine Daten bearbeiten", Notification.Type.HUMANIZED_MESSAGE);
            notification.setDelayMsec(1200);
            this.removeComponent(bearbeitenButton);
            for (int i = 0; i < formLayout.getComponentCount(); i++) {
                if(formLayout.getComponent(i) instanceof ProfilStudentTextField) {
                    ((ProfilStudentTextField) formLayout.getComponent(i)).setReadOnly(false);
                }
            }
            ortPlzTextField.getOrtField().setReadOnly(false);
            ortPlzTextField.getPlzField().setReadOnly(false);
            tfabschluss.setReadOnly(false);


            save.setEnabled(false);

            buttonsBar =  new HorizontalLayout();
            buttonsBar.addComponents(cancel,save);
            buttonsBar.setMargin(true);
            this.addComponent(buttonsBar,2,12,2,12);
            this.setComponentAlignment(buttonsBar,Alignment.TOP_CENTER);
            for (int i = 1; i <=student.getTaetigkeiten().size() ; i++) {
                ((ProfilStudentTaetigkeit)this.getComponent(1,i)).getBeschreibungField().setReadOnly(false);
                ((ProfilStudentTaetigkeit)this.getComponent(1,i)).getBeginnField().setReadOnly(false);
                ((ProfilStudentTaetigkeit)this.getComponent(1,i)).getEndeField().setReadOnly(false);
            }
            this.addComponent(minus,1,student.getTaetigkeiten().size()+1,1,student.getTaetigkeiten().size()+1);
            this.setComponentAlignment(minus,Alignment.MIDDLE_CENTER);

        });

        cancel.addClickListener((Button.ClickListener) event1 -> {
            ConfirmDialog.Factory df = new DefaultConfirmDialogFactory(){
                @Override
                public ConfirmDialog create(String caption, String message, String okCaption, String cancelCaption, String notOkCaption) {
                    return super.create("Abbrechen", message, "Ja", "Nein", notOkCaption);
                }
            } ;
            ConfirmDialog.setFactory(df);
            ConfirmDialog.show(MyUI.getCurrent(), "Möchtest du wirklich abbrechen?",
                    (ConfirmDialog.Listener) dialog -> {
                        if (dialog.isConfirmed()) {
                            this.replaceComponent(buttonsBar,bearbeitenButton);
                        }
                    });
        });




        save.addClickListener((Button.ClickListener) event -> {
            ConfirmDialog.Factory df = new DefaultConfirmDialogFactory(){
                @Override
                public ConfirmDialog create(String caption, String message, String okCaption, String cancelCaption, String notOkCaption) {
                    return super.create("Speichern", message, "Ja", "Nein", notOkCaption);
                }
            } ;
            ConfirmDialog.setFactory(df);
            ConfirmDialog.show(MyUI.getCurrent(), "Möchtest du wirklich speichern?",
                    (ConfirmDialog.Listener) dialog -> {
                        if (dialog.isConfirmed()) {
                            student.setVorname(tfvorname.getValue());
                            student.setNachname(tfnachname.getValue());
                            student.setG_datum(tfgdatum.getValue());
                            student.getAdresse().setStrasse(tstrasse.getValue());
                            student.getAdresse().setOrt(ortPlzTextField.getOrt());
                            student.getAdresse().setPlz(ortPlzTextField.getPlz());
                            // Bundesland eventuell
                            //EMail
                            student.setKontakt_nr(tkontaktnr.getValue());
                            student.setAusbildung(tfausbildung.getValue());
                            student.setStudiengang(tfstudium.getValue());
                            student.setAbschluss(tfabschluss.getValue());
                            ProfilControl.getInstance().updateStudent(student);

                            this.replaceComponent(buttonsBar,bearbeitenButton);
                        }
                    });
        });

        minus.addClickListener((Button.ClickListener) event -> {
            if(student.getTaetigkeiten().size() == 1) {
                this.removeComponent(minus);
                this.addComponent(plus,1,0);
            } else {
                this.replaceComponent(this.getComponent(1, student.getTaetigkeiten().size()), minus);
                this.removeComponent(1, student.getTaetigkeiten().size() + 1);
            }
            student.getTaetigkeiten().remove(student.getTaetigkeiten().size() - 1);
        });

        for (int i = 0; i < formLayout.getComponentCount() ; i++) {
            if(formLayout.getComponent(i) instanceof ProfilStudentTextField) {
                ((ProfilStudentTextField) formLayout.getComponent(i)).addValueChangeListener(
                        (HasValue.ValueChangeListener<String>) event -> save.setEnabled(true)
                );
            }
        }
        ortPlzTextField.getPlzField().addValueChangeListener((HasValue.ValueChangeListener<String>) event -> save.setEnabled(true));
        ortPlzTextField.getOrtField().addValueChangeListener((HasValue.ValueChangeListener<String>) event -> save.setEnabled(true));
        tfabschluss.addValueChangeListener((HasValue.ValueChangeListener<String>) event -> save.setEnabled(true));

        for (int i = 0; i < student.getTaetigkeiten().size(); i++) {
            taetigkeit = new ProfilStudentTaetigkeit();
            taetigkeit.getBeschreibungField().setValue(student.getTaetigkeiten().get(i).getTaetigkeitName());
            taetigkeit.getBeginnField().setValue(student.getTaetigkeiten().get(i).getBeginn());
            taetigkeit.getEndeField().setValue(student.getTaetigkeiten().get(i).getEnde());
            this.addComponent(taetigkeit,1,i+1,1,i+1);
            this.setComponentAlignment(taetigkeit,Alignment.MIDDLE_CENTER);

        }


    }
        @Override
        public void enter (ViewChangeListener.ViewChangeEvent event){
            try {
                setUp();
            } catch (DatabaseException e) {
                Logger.getLogger(ProfilVerwaltenStudentView.class.getName()).log(Level.SEVERE, null, e);
            } catch (SQLException throwables) {
                Logger.getLogger(ProfilVerwaltenStudentView.class.getName()).log(Level.SEVERE, null, throwables);
            }
        }

}


