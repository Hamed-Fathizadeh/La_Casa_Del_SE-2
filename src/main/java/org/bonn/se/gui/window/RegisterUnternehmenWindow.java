package org.bonn.se.gui.window;

import com.vaadin.data.HasValue;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import org.bonn.se.control.UnternehmenDescriptionControl;
import org.bonn.se.gui.component.OrtPlzTextField;
import org.bonn.se.gui.component.PlaceHolderField;
import org.bonn.se.gui.component.PopUpTextField;
import org.bonn.se.model.dao.ProfilDAO;
import org.bonn.se.model.objects.entitites.Adresse;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.*;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.dialogs.DefaultConfirmDialogFactory;
import org.vaadin.easyuploads.UploadField;
import org.vaadin.teemu.wizards.Wizard;
import org.vaadin.teemu.wizards.WizardStep;
import org.vaadin.teemu.wizards.event.*;

public class RegisterUnternehmenWindow extends Window implements WizardProgressListener {

    private Wizard wizard;

    public RegisterUnternehmenWindow() {
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
        wizard.addStep(new BeschreibungStep(), "Beschreibung");
        wizard.addStep(new WizardStepFertig(),"Fertig");
        wizard.getBackButton().setVisible(false);


        setContent(wizard);
    }

    @Override
    public void activeStepChanged(WizardStepActivationEvent wizardStepActivationEvent) {

    }

    @Override
    public void stepSetChanged(WizardStepSetChangedEvent wizardStepSetChangedEvent) {

    }

    @Override
    public void wizardCompleted(WizardCompletedEvent wizardCompletedEvent) {
        wizard.setVisible(false);
        this.close();
        UI.getCurrent().getSession().setAttribute(Roles.Unternehmen,null);
        UI.getCurrent().getNavigator().navigateTo(Views.MainView);
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
        ConfirmDialog.show(UI.getCurrent(), "Profilvervollständigung wirklich abbrechen und zum Login?",
                new ConfirmDialog.Listener() {

                    public void onClose(ConfirmDialog dialog) {
                        if (dialog.isConfirmed()) {
                            wizard.setVisible(false);
                            RegisterUnternehmenWindow.this.close();
                            UI.getCurrent().getSession().setAttribute(Roles.Unternehmen,null);
                            UI.getCurrent().getNavigator().navigateTo(Views.MainView);
                        }
                    }
                });
    }


    private class DatenStep implements WizardStep {

        PopUpTextField kontaktnummer;
        PopUpTextField strasse;
        OrtPlzTextField ort;
        ComboBox<String> branche;
        UploadField uploadField;
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
            form1.setWidth("300px");
            form1.setMargin(true);

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



            kontaktnummer = new PopUpTextField("Kontaktnummer");

            form1.addComponents( image,uploadField,kontaktnummer);
            form1.setComponentAlignment(image, Alignment.TOP_LEFT);

            FormLayout form2 = new FormLayout();
            form2.setWidth("300px");
            form2.setMargin(true);
            PlaceHolderField place1 = new PlaceHolderField();
            strasse = new PopUpTextField("Strasse");
            ort = new OrtPlzTextField();
            PlaceHolderField place2 = new PlaceHolderField();


            branche = new ComboBox<>("", DatenUnternehmenProfil.branche1);
            branche.setPlaceholder("Branche");
            branche.setHeight("56px");
            branche.setWidth("300px");



            form2.addComponents(place1, strasse, ort, place2, branche);


            gridLayout.addComponent(form1, 0, 1, 0, 1);
            gridLayout.addComponent(form2, 1, 1, 1, 1);
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

            ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).setLogo(uploadField.getValue());
            uploadField.clear();
            ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).setKontaktnummer(kontaktnummer.getValue());
            ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).setBranche(branche.getValue());

            Adresse adresse = new Adresse();

            if(!(strasse.getValue().equals("") || ort.getPlz().getValue().equals("") || ort.equals("") )) {
                adresse.setStrasse(strasse.getValue());
                adresse.setPlz(ort.getPlz().getValue());
                adresse.setOrt(sOrt[0]);
            }
            ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).setAdresse(adresse);

            try {
                ProfilDAO.createUnternehmenProfil( ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen)));
            } catch (DatabaseException e) {
                e.printStackTrace();
            }


            return true;
        }

        @Override
        public boolean onBack() {
            return false;
        }
    }


    private static class BeschreibungStep implements WizardStep {
        private RichTextArea richTextArea;

        @Override
        public String getCaption() {
            return "Unternehmensbeschreibung";
        }

        @Override
        public Component getContent() {

            VerticalLayout verticalLayout = new VerticalLayout();
            verticalLayout.setSizeFull();
            verticalLayout.setMargin(false);

            richTextArea = new RichTextArea();
            richTextArea.setWidthFull();
            richTextArea.setValue("Schreiben Sie etwas hier über Ihr Unternehmen");

            richTextArea.setSizeFull();
            verticalLayout.addComponent(richTextArea);
            verticalLayout.setComponentAlignment(richTextArea,Alignment.MIDDLE_CENTER);



            return verticalLayout;
        }

        @Override
        public boolean onAdvance() {

            UnternehmenDescriptionControl unternehmenDescriptionControl = UnternehmenDescriptionControl.getInstance();
            try {
                ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).setDescription(richTextArea.getValue());
                unternehmenDescriptionControl.setDescription();
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        public boolean onBack() {
            return false;
        }
    }

}
