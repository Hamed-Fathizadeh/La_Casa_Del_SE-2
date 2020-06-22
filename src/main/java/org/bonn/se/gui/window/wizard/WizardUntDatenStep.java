package org.bonn.se.gui.window.wizard;

import com.vaadin.data.HasValue;
import com.vaadin.ui.*;
import org.bonn.se.gui.component.OrtPlzTextField;
import org.bonn.se.gui.component.PlaceHolderField;
import org.bonn.se.gui.component.PopUpTextField;
import org.bonn.se.model.dao.ProfilDAO;
import org.bonn.se.model.objects.entitites.Adresse;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.DatenUnternehmenProfil;
import org.bonn.se.services.util.ImageConverter;
import org.bonn.se.services.util.Roles;
import org.vaadin.easyuploads.UploadField;
import org.vaadin.teemu.wizards.WizardStep;

public class WizardUntDatenStep implements WizardStep {

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


            branche = new ComboBox<>("", DatenUnternehmenProfil.getBranche1());
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


            ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).setLogo(uploadField.getValue());
            uploadField.clear();
            ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).setKontaktnummer(kontaktnummer.getValue());
            ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).setBranche(branche.getValue());

            Adresse adresse = new Adresse();

            if(!( ort.getOrt() == null ) || ort.getPlz() == null ) {
                adresse.setStrasse(strasse.getValue());
                adresse.setPlz(ort.getPlz());
                adresse.setOrt(ort.getOrt());
                adresse.setBundesland(ort.getBunesland());
            }
            ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).setAdresse(adresse);

            try {
                ProfilDAO.getInstance().createUnternehmenProfil( ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen)));
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
