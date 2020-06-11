package org.bonn.se.gui.window;

import com.github.appreciated.material.MaterialTheme;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.ValidationException;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se.gui.component.*;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.model.dao.ProfilDAO;
import org.bonn.se.model.objects.entitites.Adresse;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.Taetigkeit;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.*;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.dialogs.DefaultConfirmDialogFactory;
import org.vaadin.easyuploads.ImagePreviewField;
import org.vaadin.easyuploads.UploadField;
import org.vaadin.teemu.wizards.Wizard;
import org.vaadin.teemu.wizards.WizardStep;
import org.vaadin.teemu.wizards.event.*;

import javax.imageio.ImageIO;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RegisterStudentWindow extends Window implements WizardProgressListener {

    private Wizard wizard;


    public RegisterStudentWindow() {
        setUp();
    }

    public void setUp() {
        this.center();
        this.setDraggable(false);
        this.setResizable(false);
        this.setClosable(false);
        this.setModal(true);
        this.setHeight("80%");
        this.setWidth("80%");
        wizard = new Wizard();

        wizard.setUriFragmentEnabled(true);
        wizard.getBackButton().setCaption("Zurück");
        wizard.getFinishButton().setCaption("Fertig");
        wizard.getNextButton().setCaption("Weiter");
        wizard.getCancelButton().setCaption("Abbrechen");
        wizard.addListener(this);
        wizard.addStep(new WizardStepRegisterSuccess(), "Erfolgreich");
        wizard.addStep(new DatenStep(), "Daten");
        wizard.addStep(new TaetigkeitStep(), "Tätigkeiten");
        wizard.addStep(new KenntnisseStep(), "Kenntnisse");
        wizard.addStep(new WizardStepFertig(),"Fertig");
        wizard.getBackButton().setVisible(false);




        setContent(wizard);

    }

    @Override
    public void activeStepChanged(WizardStepActivationEvent event) {
        Page.getCurrent().setTitle(event.getActivatedStep().getCaption());


    }

    @Override
    public void stepSetChanged(WizardStepSetChangedEvent wizardStepSetChangedEvent) {

    }

    @Override
    public void wizardCompleted(WizardCompletedEvent wizardCompletedEvent) {
        wizard.setVisible(false);
        this.close();

        MyUI.getCurrent().getNavigator().navigateTo(Views.StudentHomeView);
    }

    @Override
    public void wizardCancelled(WizardCancelledEvent wizardCancelledEvent) {
        ConfirmDialog.Factory df = new DefaultConfirmDialogFactory(){

            @Override
            public ConfirmDialog create(String caption, String message, String okCaption, String cancelCaption, String notOkCaption) {
                return super.create("Beenden", message, "Ja", "Nein", notOkCaption);
            }
        } ;

        ConfirmDialog.setFactory(df);
        ConfirmDialog.show(MyUI.getCurrent(), "Profilvervollständigung wirklich abbrechen und zum Login?",
                new ConfirmDialog.Listener() {

                    public void onClose(ConfirmDialog dialog) {
                        if (dialog.isConfirmed()) {
                            wizard.setVisible(false);
                            RegisterStudentWindow.this.close();
                            MyUI.getCurrent().getSession().setAttribute(Roles.Student,null);
                            MyUI.getCurrent().getNavigator().navigateTo(Views.MainView);
                        }
                    }
                });

    }


    private class DatenStep implements WizardStep {

        OrtPlzTextField ort;
        PopUpTextField strasse;
        ComboBox<String> abschluss;
        DateField g_datum;
        PopUpTextField studiengang;
        PopUpTextField ausbildung;
        NumeralField mobilnr;
        Student user = (Student) MyUI.getCurrent().getSession().getAttribute(Roles.Student);
        UploadField  uploadField;
        Image image = ImageConverter.getUnknownProfilImage();


        @Override
        public String getCaption() {
            return "Daten";
        }

        @Override
        public Component getContent() {

            GridLayout gridLayout = new GridLayout(2, 3);
            gridLayout.setHeight("100%");
            gridLayout.setWidth("100%");


            FormLayout form1 = new FormLayout();


            uploadField = new UploadField();
            uploadField.setDisplayUpload(false);
            uploadField.setButtonCaption("Profilbild hochladen");
            uploadField.setClearButtonVisible(false);
            uploadField.setAcceptFilter("image/*");


            uploadField.addValueChangeListener((HasValue.ValueChangeEvent<byte[]> event) -> {
                    form1.removeComponent(image);
                    byte[] bild = uploadField.getValue();
                    image = ImageConverter.convertImagetoProfil(bild);
                    form1.addComponent(image,0);
            });









            form1.setWidth("300px");
            form1.setMargin(true);

            //Geburtsdatum
            g_datum = new DateField();
            g_datum.setHeight("56px");
            g_datum.setWidth("300px");
            g_datum.setPlaceholder("Geburtsdatum dd.mm.yyyy");

            mobilnr = new NumeralField("Kontaktnummer");


            image.setHeight("170px");
            image.setWidth("150px");

            form1.addComponents(image,uploadField, g_datum, mobilnr);
            //form1.setComponentAlignment(image, Alignment.MIDDLE_CENTER);


            FormLayout form2 = new FormLayout();
            form2.setWidth("300px");
            form2.setMargin(true);
            PlaceHolderField place1 = new PlaceHolderField();
            strasse = new PopUpTextField("Strasse");
            ort = new OrtPlzTextField();


            PlaceHolderField place2 = new PlaceHolderField();
            studiengang = new PopUpTextField("Studiengang");
            ausbildung = new PopUpTextField("Ausbildung (optional)");


            abschluss = new ComboBox<>("", DatenStudentProfil.collection);
            abschluss.setPlaceholder("Wähle den höchsten Abschluss...");
            abschluss.setHeight("56px");
            abschluss.setWidth("300px");

            form2.addComponents(place1, strasse, ort, studiengang, place2, ausbildung, abschluss);



            MyUI.getCurrent().getSession().setAttribute(Roles.Student, user);


            gridLayout.addComponent(form1, 0, 0, 0, 0);
            gridLayout.addComponent(form2, 1, 0, 1, 0);


            gridLayout.setComponentAlignment(form1, Alignment.TOP_LEFT);
            gridLayout.setComponentAlignment(form2, Alignment.TOP_LEFT);


            return gridLayout;
        }


        @Override
        public boolean onAdvance() {
                String[] sOrt = {"",""};

                if(ort.getBundesland().getValue() != null) {
                    sOrt = ort.getBundesland().getValue().toString().split(" - ");
                }
                Student student = (Student) MyUI.getCurrent().getSession().getAttribute(Roles.Student);
                student.setPicture(uploadField.getValue());
                uploadField.clear();
                student.setAbschluss(abschluss.getValue());
                student.setKontakt_nr(mobilnr.getValue());
                student.setAusbildung(ausbildung.getValue());
                student.setStudiengang(studiengang.getValue());
                student.setG_datum(g_datum.getValue());
                Adresse adresse = new Adresse();
                if(!(strasse.getValue().equals("") || ort.getPlz().getValue().equals("") || ort.equals("") )) {
                    adresse.setStrasse(strasse.getValue());
                    adresse.setPlz(ort.getPlz().getValue());
                    adresse.setOrt(sOrt[0]);
                }
                student.setAdresse(adresse);

            try {
                ProfilDAO.createStudentProfil1New(student);
            } catch (DatabaseException e) {
                e.printStackTrace();
            }

                student.setVorname("Test");
                UI.getCurrent().getSession().setAttribute(Roles.Student, student);



            return true;
        }


        @Override
        public boolean onBack() {
            return true;
        }
    }

    private class TaetigkeitStep implements WizardStep {

        ArrayList<Taetigkeit> taetigkeitArrayList = new ArrayList<>();
        Binder<Taetigkeit> binder = new Binder<>(Taetigkeit.class);
        Button plus;

        @Override
        public String getCaption() {
            return "Tätigkeiten";
        }

        @Override
        public Component getContent() {
            GridLayout gridLayout = new GridLayout(3, 10);
            gridLayout.setHeight("100%");
            gridLayout.setWidth("100%");



            RegistrationTextField taetigkeit1 = new RegistrationTextField("Tätigkeit (Optional)");
            StudentDateField t1_beginn = new StudentDateField("Beginn");
            StudentDateField t1_ende = new StudentDateField("Ende");

            plus = new Button(VaadinIcons.PLUS);
            plus.addStyleName(MaterialTheme.BUTTON_FLOATING_ACTION);

            Button minus = new Button(VaadinIcons.MINUS);
            minus.addStyleNames(MaterialTheme.BUTTON_DANGER,MaterialTheme.BUTTON_FLOATING_ACTION);


            binder.forField(taetigkeit1)
                    .asRequired("Bitte ausfüllen")
                    .bind(Taetigkeit::getTaetigkeitName,Taetigkeit::setTaetigkeitName);

            binder.forField(t1_beginn)
                    .asRequired("Bitte ausfüllen")
                    .bind(Taetigkeit::getBeginn,Taetigkeit::setBeginn);

            binder.forField(t1_ende)
                    .asRequired("Bitte ausfüllen")
                    .bind(Taetigkeit::getEnde,Taetigkeit::setEnde);

            gridLayout.addComponent(taetigkeit1,0,1);
            gridLayout.addComponent(t1_beginn,1,1);
            gridLayout.addComponent(t1_ende,2,1);
            gridLayout.setComponentAlignment(taetigkeit1,Alignment.MIDDLE_CENTER);
            gridLayout.setComponentAlignment(t1_beginn,Alignment.MIDDLE_CENTER);
            gridLayout.setComponentAlignment(t1_ende,Alignment.MIDDLE_CENTER);
            gridLayout.addComponent(plus,0,2);
            gridLayout.setComponentAlignment(plus,Alignment.MIDDLE_CENTER);
            taetigkeit1.selectAll();


            final int[] i_c = {0,1,2};
            final int[] i_r = {2};
            plus.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {

                    if (binder.isValid()) {
                        Taetigkeit taetigkeit = new Taetigkeit();
                        try {
                            binder.writeBean(taetigkeit);
                        } catch (ValidationException e) {
                            e.printStackTrace();
                        }
                        taetigkeitArrayList.add(taetigkeit);

                        gridLayout.removeComponent(plus);
                        gridLayout.removeComponent(minus);

                        gridLayout.addComponent(new RegistrationTextField("Tätigkeit (Optional)"), i_c[0], i_r[0]);
                        gridLayout.addComponent(new StudentDateField("Beginn"), i_c[1], i_r[0]);
                        gridLayout.addComponent(new StudentDateField("Ende"), i_c[2], i_r[0]);
                        gridLayout.setComponentAlignment(gridLayout.getComponent(i_c[0], i_r[0]), Alignment.MIDDLE_CENTER);
                        gridLayout.setComponentAlignment(gridLayout.getComponent(i_c[1], i_r[0]), Alignment.MIDDLE_CENTER);
                        gridLayout.setComponentAlignment(gridLayout.getComponent(i_c[2], i_r[0]), Alignment.MIDDLE_CENTER);
                        ((RegistrationTextField) gridLayout.getComponent(i_c[0], i_r[0])).selectAll();

                        binder.forField((RegistrationTextField) gridLayout.getComponent(i_c[0], i_r[0]))
                                .asRequired("Bitte ausfüllen")
                                .bind(Taetigkeit::getTaetigkeitName, Taetigkeit::setTaetigkeitName);

                        binder.forField((StudentDateField) gridLayout.getComponent(i_c[1], i_r[0]))
                                .asRequired("Bitte ausfüllen")
                                .bind(Taetigkeit::getBeginn, Taetigkeit::setBeginn);

                        binder.forField((StudentDateField) gridLayout.getComponent(i_c[2] , i_r[0]))
                                .asRequired("Bitte ausfüllen")
                                .bind(Taetigkeit::getEnde, Taetigkeit::setEnde);

                        if (i_r[0] <= 3) {
                            gridLayout.addComponent(plus, i_c[0], i_r[0] + 1);
                            gridLayout.setComponentAlignment(plus, Alignment.MIDDLE_CENTER);
                        }
                        i_r[0]++;

                        gridLayout.addComponent(minus,i_c[1],i_r[0]);
                        gridLayout.setComponentAlignment(minus, Alignment.MIDDLE_CENTER);

                    } else {
                        binder.validate().getFieldValidationErrors();
                    }
                }

            });


            minus.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {

                    gridLayout.removeComponent(plus);
                    gridLayout.removeComponent(minus);
                    i_r[0]--;
                    binder.removeBinding( ((StudentDateField)gridLayout.getComponent(2,i_r[0])));
                    binder.removeBinding( ((StudentDateField)gridLayout.getComponent(1,i_r[0])));
                    binder.removeBinding( ((RegistrationTextField)gridLayout.getComponent(0,i_r[0])));

                    for (int i = 0; i < gridLayout.getColumns() ; i++) {
                        gridLayout.removeComponent(i,i_r[0]);

                    }



                    gridLayout.addComponent(plus,i_c[0],i_r[0]);
                    gridLayout.addComponent(minus,i_c[1],i_r[0]);
                    gridLayout.setComponentAlignment(plus,Alignment.MIDDLE_CENTER);
                    gridLayout.setComponentAlignment(minus,Alignment.MIDDLE_CENTER);
                    if(binder.isValid()) {
                        taetigkeitArrayList.remove(taetigkeitArrayList.size() - 1);
                    }




                }
            });


            binder.addStatusChangeListener(event -> wizard.getNextButton().setEnabled(binder.isValid()));




            return gridLayout;
        }

        @Override
        public boolean onAdvance() {

                    Taetigkeit taetigkeit = new Taetigkeit();
                    binder.writeBeanIfValid(taetigkeit);
                    taetigkeitArrayList.add(taetigkeit);

                    ((Student)UI.getCurrent().getSession().getAttribute(Roles.Student)).setTaetigkeitenListe(taetigkeitArrayList);

                    try {
                        ProfilDAO.createStudentProfil2((Student) UI.getCurrent().getSession().getAttribute(Roles.Student));
                    } catch (DatabaseException e) {
                        e.printStackTrace();
                    }


            return true;
        }

        @Override
        public boolean onBack() {
            return true;
        }
    }

    private class KenntnisseStep implements WizardStep {

        ArrayList<Student.SprachKenntnis> sprachKenntnisArrayList;
        Binder<Student.SprachKenntnis> binder1;
        Binder<Student.ITKenntnis> binder;
        ArrayList<Student.ITKenntnis> itKenntnisArrayList;

        @Override
        public String getCaption() {
            return "Kenntnisse";
        }

        @Override
        public Component getContent() {
            GridLayout gridLayout = new GridLayout(5, 10);
            gridLayout.setHeight("100%");
            gridLayout.setWidth("100%");




            Label label2 = new Label("<h3><p><font color=\"blue\">Kenntnisse</font></p></h3>",ContentMode.HTML);
            label2.setHeight("45px");
            gridLayout.addComponent(label2,0,1,1,1);
            gridLayout.setComponentAlignment(label2,Alignment.MIDDLE_CENTER);

            itKenntnisArrayList = new ArrayList<>();
            PopUpTextField itKenntnis1 = new PopUpTextField("Bsp. MS-Office");
            itKenntnis1.setWidth("200px");
            ComboBoxNiveau niveau1 = new ComboBoxNiveau("",DatenStudentProfil.collection3);



            Button plus = new Button(VaadinIcons.PLUS);
            plus.addStyleName(MaterialTheme.BUTTON_FLOATING_ACTION);

            Button minus = new Button(VaadinIcons.MINUS);
            minus.addStyleNames(MaterialTheme.BUTTON_DANGER,MaterialTheme.BUTTON_FLOATING_ACTION);

            binder = new Binder<>(Student.ITKenntnis.class);


            binder.forField(itKenntnis1)
                    .asRequired("Bitte Feld ausfüllen!")
                    .bind(Student.ITKenntnis::getKenntnis, Student.ITKenntnis::setKenntnis);
            binder.forField(niveau1)
                    .asRequired("Bitte Niveau ausfüllen")
                    .bind(Student.ITKenntnis::getNiveau, Student.ITKenntnis::setNiveau);

            gridLayout.addComponent(itKenntnis1,0,2);
            gridLayout.addComponent(niveau1,1,2);
            gridLayout.addComponent(plus,0,3);
            gridLayout.setComponentAlignment(itKenntnis1,Alignment.MIDDLE_CENTER);
            gridLayout.setComponentAlignment(niveau1,Alignment.MIDDLE_CENTER);
            gridLayout.setComponentAlignment(plus,Alignment.MIDDLE_CENTER);


            final int[] i_c = {0,1,2,3,4};
            final int[] i_r = {3};
            plus.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {

                    if (binder.isValid()) {
                        Student.ITKenntnis itKenntnis = new Student.ITKenntnis();
                        try {
                            binder.writeBean(itKenntnis);
                        } catch (ValidationException e) {
                            e.printStackTrace();
                        }
                        itKenntnisArrayList.add(itKenntnis);

                        gridLayout.removeComponent(plus);
                        gridLayout.removeComponent(minus);

                        gridLayout.addComponent(new PopUpTextField("Bsp. MS-Office"), i_c[0], i_r[0]);
                        gridLayout.getComponent(i_c[0], i_r[0]).setWidth("200px");
                        gridLayout.addComponent(new ComboBoxNiveau("",DatenStudentProfil.collection3), i_c[1], i_r[0]);
                        gridLayout.setComponentAlignment(gridLayout.getComponent(i_c[0], i_r[0]), Alignment.MIDDLE_CENTER);
                        gridLayout.setComponentAlignment(gridLayout.getComponent(i_c[1], i_r[0]), Alignment.MIDDLE_CENTER);
                        ((PopUpTextField) gridLayout.getComponent(i_c[0], i_r[0])).selectAll();

                        binder.forField((PopUpTextField) gridLayout.getComponent(i_c[0], i_r[0]))
                                .asRequired("Bitte Feld ausfüllen!")
                                .bind(Student.ITKenntnis::getKenntnis, Student.ITKenntnis::setKenntnis);
                        binder.forField((ComboBoxNiveau) gridLayout.getComponent(i_c[1], i_r[0]))
                                .asRequired("Bitte Niveau ausfüllen")
                                .bind(Student.ITKenntnis::getNiveau, Student.ITKenntnis::setNiveau);


                        if (i_r[0] <= 4) {
                            gridLayout.addComponent(plus, i_c[0], i_r[0] + 1);
                            gridLayout.setComponentAlignment(plus, Alignment.MIDDLE_CENTER);
                        }
                        i_r[0]++;
                        gridLayout.addComponent(minus, i_c[1], i_r[0]);
                        gridLayout.setComponentAlignment(minus, Alignment.MIDDLE_CENTER);


                    } else {
                        binder.validate().getFieldValidationErrors();
                    }
                }
            });



            minus.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {

                    gridLayout.removeComponent(plus);
                    gridLayout.removeComponent(minus);
                    i_r[0]--;
                    binder.removeBinding( (PopUpTextField) gridLayout.getComponent(i_c[0], i_r[0]));
                    binder.removeBinding( ((ComboBoxNiveau) gridLayout.getComponent(i_c[1], i_r[0])));

                    for (int i = 0; i <= 1 ; i++) {
                        gridLayout.removeComponent(i,i_r[0]);

                    }



                    gridLayout.addComponent(plus,i_c[0],i_r[0]);
                    if(i_r[0] != 3) {
                        gridLayout.addComponent(minus, i_c[1], i_r[0]);
                    }
                    gridLayout.setComponentAlignment(plus,Alignment.MIDDLE_CENTER);
                    gridLayout.setComponentAlignment(minus,Alignment.MIDDLE_CENTER);
                    if(binder.isValid()) {
                        itKenntnisArrayList.remove(itKenntnisArrayList.size() - 1);
                    }
                }
            });



            Label label3 = new Label("<h3><p><font color=\"blue\">Sprachen</font></p></h3>",ContentMode.HTML);
            label3.setHeight("45px");
            gridLayout.addComponent(label3,3,1,4,1);
            gridLayout.setComponentAlignment(label3,Alignment.MIDDLE_CENTER);

            PopUpTextField sprachKenntnis1 = new PopUpTextField("Bsp. Englisch");
            sprachKenntnis1.setWidth("200px");

            sprachKenntnisArrayList = new ArrayList<>();

            ComboBoxNiveau niveau21 = new ComboBoxNiveau("",DatenStudentProfil.collection2);




            Button plus1 = new Button(VaadinIcons.PLUS);
            plus1.addStyleName(MaterialTheme.BUTTON_FLOATING_ACTION);

            Button minus1 = new Button(VaadinIcons.MINUS);
            minus1.addStyleNames(MaterialTheme.BUTTON_DANGER,MaterialTheme.BUTTON_FLOATING_ACTION);

            binder1 = new Binder<>(Student.SprachKenntnis.class);


            binder1.forField(sprachKenntnis1)
                    .asRequired("Bitte Feld ausfüllen!")
                    .bind(Student.SprachKenntnis::getKenntnis, Student.SprachKenntnis::setKenntnis);
            binder1.forField(niveau21)
                    .asRequired("Bitte Niveau ausfüllen")
                    .bind(Student.SprachKenntnis::getNiveau, Student.SprachKenntnis::setNiveau);

            gridLayout.addComponent(sprachKenntnis1,3,2);
            gridLayout.addComponent(niveau21,4,2);
            gridLayout.addComponent(plus1,3,3);
            gridLayout.setComponentAlignment(plus1, Alignment.MIDDLE_CENTER);

            gridLayout.setComponentAlignment(sprachKenntnis1,Alignment.MIDDLE_CENTER);
            gridLayout.setComponentAlignment(niveau21,Alignment.MIDDLE_CENTER);

            final int[] j_c = {0,1,2,3,4};
            final int[] j_r = {3};
            plus1.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {

                    if (binder1.isValid()) {
                        Student.SprachKenntnis sprachKenntnis = new Student.SprachKenntnis();
                        try {
                            binder1.writeBean(sprachKenntnis);
                        } catch (ValidationException e) {
                            e.printStackTrace();
                        }
                        sprachKenntnisArrayList.add(sprachKenntnis);

                        gridLayout.removeComponent(plus1);
                        gridLayout.removeComponent(minus1);

                        gridLayout.addComponent(new PopUpTextField("Bsp. Englisch"), j_c[3], j_r[0]);
                        gridLayout.getComponent(j_c[3], j_r[0]).setWidth("200px");
                        gridLayout.addComponent(new ComboBoxNiveau("",DatenStudentProfil.collection2), j_c[4], j_r[0]);
                        gridLayout.setComponentAlignment(gridLayout.getComponent(j_c[3], j_r[0]), Alignment.MIDDLE_CENTER);
                        gridLayout.setComponentAlignment(gridLayout.getComponent(j_c[4], j_r[0]), Alignment.MIDDLE_CENTER);
                        ((PopUpTextField) gridLayout.getComponent(j_c[3], j_r[0])).selectAll();

                        binder1.forField((PopUpTextField) gridLayout.getComponent(j_c[3], j_r[0]))
                                .asRequired("Bitte Feld ausfüllen!")
                                .bind(Student.SprachKenntnis::getKenntnis, Student.SprachKenntnis::setKenntnis);
                        binder1.forField((ComboBoxNiveau) gridLayout.getComponent(j_c[4], j_r[0]))
                                .asRequired("Bitte Niveau ausfüllen")
                                .bind(Student.SprachKenntnis::getNiveau, Student.SprachKenntnis::setNiveau);


                        if (j_r[0] <= 4) {
                            gridLayout.addComponent(plus1, j_c[3], j_r[0] + 1);
                            gridLayout.setComponentAlignment(plus1, Alignment.MIDDLE_CENTER);
                        }
                        j_r[0]++;

                        gridLayout.addComponent(minus1,j_c[4],j_r[0]);
                        gridLayout.setComponentAlignment(minus1, Alignment.MIDDLE_CENTER);

                    } else {
                        binder1.validate().getFieldValidationErrors();
                    }
                }
            });
            minus1.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {

                    gridLayout.removeComponent(plus1);
                    gridLayout.removeComponent(minus1);
                    j_r[0]--;
                    binder.removeBinding( (PopUpTextField) gridLayout.getComponent(j_c[0], j_r[0]));
                    binder.removeBinding( ((ComboBoxNiveau) gridLayout.getComponent(j_c[1], j_r[0])));

                    for (int i = 0; i <= 1 ; i++) {
                        gridLayout.removeComponent(i,j_r[0]);

                    }



                    gridLayout.addComponent(plus1,j_c[0],j_r[0]);
                    if(j_r[0] != 3) {
                        gridLayout.addComponent(minus1, j_c[1], j_r[0]);
                    }
                    gridLayout.setComponentAlignment(plus1,Alignment.MIDDLE_CENTER);
                    gridLayout.setComponentAlignment(minus1,Alignment.MIDDLE_CENTER);
                    if(binder1.isValid()) {
                        sprachKenntnisArrayList.remove(sprachKenntnisArrayList.size() - 1);
                    }
                }
            });


            wizard.getNextButton().setEnabled(false);


       //     binder.addStatusChangeListener(event -> wizard.getNextButton().setEnabled(binder.isValid()));
        //    binder1.addStatusChangeListener(event -> wizard.getNextButton().setEnabled(binder.isValid()));


            return gridLayout;
        }

        @Override
        public boolean onAdvance() {
                Student.ITKenntnis itKenntnis = new Student.ITKenntnis();
                Student.SprachKenntnis sprachKenntnis = new Student.SprachKenntnis();
                try {
                    if(binder.isValid()) {
                        binder.writeBean(itKenntnis);
                        itKenntnisArrayList.add(itKenntnis);
                        ((Student)UI.getCurrent().getSession().getAttribute(Roles.Student)).setItKenntnisList(itKenntnisArrayList);

                    }
                    if (binder1.isValid()) {
                        binder1.writeBean(sprachKenntnis);
                        sprachKenntnisArrayList.add(sprachKenntnis);
                        ((Student)UI.getCurrent().getSession().getAttribute(Roles.Student)).setSprachKenntnisList(sprachKenntnisArrayList);

                    }
                } catch (ValidationException e) {
                    e.printStackTrace();
                }

                try {
                    ProfilDAO.createStudentProfil3((Student) UI.getCurrent().getSession().getAttribute(Roles.Student));
                } catch (DatabaseException e) {
                    e.printStackTrace();
                }

            return true;
        }

        @Override
        public boolean onBack() {
            return true;
        }
    }

}