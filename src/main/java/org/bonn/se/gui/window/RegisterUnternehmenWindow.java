package org.bonn.se.gui.window;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se.gui.component.OrtPlzTextField;
import org.bonn.se.gui.component.PlaceHolderField;
import org.bonn.se.gui.component.PopUpTextField;
import org.bonn.se.gui.component.RegistrationTextField;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.model.dao.ProfilDAO;
import org.bonn.se.model.objects.entitites.Adresse;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.DatenUnternehmenProfil;
import org.bonn.se.services.util.ImageUploader;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;
import org.vaadin.teemu.wizards.Wizard;
import org.vaadin.teemu.wizards.WizardStep;
import org.vaadin.teemu.wizards.event.*;

import java.io.File;

public class RegisterUnternehmenWindow extends Window implements WizardProgressListener {

    private Wizard wizard;
    private final Image image = new Image();
    private Image image1;

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
       // wizard.addStep(new AllgemeinStep(), "Allgemeine Daten");
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

    }

    @Override
    public void wizardCancelled(WizardCancelledEvent wizardCancelledEvent) {

    }


    private class DatenStep implements WizardStep {

        PopUpTextField kontaktnummer;
        PopUpTextField strasse;
        OrtPlzTextField ort;
        ComboBox<String> branche;
        Unternehmen unternehmen = (Unternehmen) MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen);

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

            ImageUploader receiver = new ImageUploader();
            // Create the upload with a caption and set receiver later
            Upload upload = new Upload("", receiver);
            upload.addSucceededListener(receiver);
            upload.setButtonCaption("Profilbild hochladen");

            image.setSource(new FileResource(new File("src/main/webapp/VAADIN/themes/demo/images/Unknown.png")));

            upload.addStartedListener(new Upload.StartedListener() {
                @Override
                public void uploadStarted(Upload.StartedEvent event) {
                    form1.removeComponent(image);
                    Image image = ImageUploader.getImage();
                    image.setHeight(150,Unit.PIXELS);
                    image.setWidth(150,Unit.PIXELS);
                    form1.addComponent(image,0);


                }
            });
            form1.setWidth("300px");
            form1.setMargin(true);


            kontaktnummer = new PopUpTextField("Kontaktnummer");

            form1.addComponents( image,upload,kontaktnummer);
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
           // Unternehmen unternehmen =(Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen);
            Unternehmen unternehmen = new Unternehmen();
            unternehmen.setEmail("test@c.de");
            unternehmen.setCname("Firma");
            unternehmen.setHauptsitz("Bonn");
            try {
                ProfilDAO.createUnternehmenProfil1(unternehmen.getEmail(),ImageUploader.getFile(),kontaktnummer.getValue(),
                        strasse.getValue(),ort.getPlz().getValue(),sOrt[0],sOrt[1],branche.getValue(),unternehmen.getCname(),unternehmen.getHauptsitz());
            } catch (DatabaseException e) {
                e.printStackTrace();
            }

            unternehmen.setLogo(image);
            if(image != null) {
                unternehmen.setLogo(image);
            }else{
                ThemeResource resource = new ThemeResource("img/RegisterStudent/Unknown.png");
                Image UnknowenPic = new Image(null,resource);
                unternehmen.setLogo(UnknowenPic);
            }
            unternehmen.setKontaktnummer(kontaktnummer.getValue());
            unternehmen.setBranche(branche.getValue());

            Adresse adresse = new Adresse();

            if(!(strasse.getValue().equals("") || ort.getPlz().getValue().equals("") || ort.equals("") )) {
                adresse.setStrasse(strasse.getValue());
                adresse.setPlz(ort.getPlz().getValue());
                adresse.setOrt(sOrt[0]);
                unternehmen.setAdresse(adresse);
            }
            UI.getCurrent().getSession().setAttribute(Roles.Unternehmen,unternehmen);

            return true;
        }

        @Override
        public boolean onBack() {
            return false;
        }
    }
    /*
    private class AllgemeinStep implements WizardStep {

        RegistrationTextField mitarbeiteranzahl;
        RegistrationTextField gruendungsjahr;
        ComboBox<String> reichweite;

        @Override
        public String getCaption() {
            return "Allgemeine Angaben";
        }

        @Override
        public Component getContent() {

            GridLayout gridLayout = new GridLayout(3, 3);
            gridLayout.setHeight("100%");
            gridLayout.setWidth("100%");

            FormLayout form2 = new FormLayout();
            form2.setWidth("300px");
            form2.setMargin(true);
            mitarbeiteranzahl = new RegistrationTextField("Mitarbeiteranzahl");
            gruendungsjahr = new RegistrationTextField("Gründungsjah");

            reichweite = new ComboBox<>("", DatenUnternehmenProfil.reichweite);
            reichweite.setPlaceholder("Reichweite");
            reichweite.setHeight("56px");
            reichweite.setWidth("300px");

            form2.addComponents(mitarbeiteranzahl,gruendungsjahr,reichweite);


            gridLayout.addComponent(form2, 0, 1, 0, 1);

            gridLayout.setComponentAlignment(form2, Alignment.TOP_CENTER);



            return gridLayout;
        }

        @Override
        public boolean onAdvance() {

            Unternehmen unternehmen = (Unternehmen)UI.getCurrent().getSession().getAttribute(Roles.Unternehmen);
            if(mitarbeiteranzahl.getValue() != null) {
                unternehmen.setMitarbeiteranzahl(Integer.valueOf(String.valueOf(mitarbeiteranzahl.getValue())));
            } else if ( gruendungsjahr.getValue() != null ) {
                unternehmen.setGruendungsjahr(Integer.parseInt(String.valueOf(gruendungsjahr.getValue())));
            }

            unternehmen.setReichweite(reichweite.getValue());
            UI.getCurrent().getSession().setAttribute(Roles.Unternehmen,unternehmen);

            try {
                ProfilDAO.createUnternehmenProfil2((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen));
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

    */

    private class BeschreibungStep implements WizardStep {


        @Override
        public String getCaption() {
            return null;
        }

        @Override
        public Component getContent() {

            GridLayout gridLayout = new GridLayout(3, 3);
            gridLayout.setHeight("100%");
            gridLayout.setWidth("100%");


            Label head = new Label("<h1><p><font color=\"blue\"> Schreiben Sie etwas zu Ihrem Unternehmen !!\n</font></p></h1>", ContentMode.HTML);
            head.setHeight("70px");
            FormLayout form1= new FormLayout();
            form1.setWidth("300px");
            form1.setMargin(true);
            form1.addComponent(head);

            FormLayout form2= new FormLayout();
            final RichTextArea sample = new RichTextArea();
            ((Unternehmen) MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).setDescription(sample.getValue());
            sample.setValue("The quick brown fox jumps over the lazy dog.");
            sample.setSizeFull();

            sample.addValueChangeListener(event -> Notification.show("Value changed:",
                    String.valueOf(event.getValue()), Notification.Type.TRAY_NOTIFICATION));

            form2.addComponent(sample);


            gridLayout.addComponent(head,0,0,0,0);
            gridLayout.addComponent(form2,0,1,0,1);


            gridLayout.setComponentAlignment(head,Alignment.TOP_CENTER);
            gridLayout.setComponentAlignment(form2,Alignment.MIDDLE_CENTER);



            return gridLayout;
        }

        @Override
        public boolean onAdvance() {

            try {
                ProfilDAO.createUnternehmenProfil3((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen));
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
