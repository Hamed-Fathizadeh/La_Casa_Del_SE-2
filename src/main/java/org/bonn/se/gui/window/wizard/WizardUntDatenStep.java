package org.bonn.se.gui.window.wizard;

import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import org.bonn.se.gui.component.UnternehmenDatenView;
import org.bonn.se.model.dao.ProfilDAO;
import org.bonn.se.model.objects.entitites.Adresse;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;
import org.vaadin.teemu.wizards.WizardStep;

public class WizardUntDatenStep implements WizardStep {

        UnternehmenDatenView unternehmenDatenView;


    @Override
        public String getCaption() {
            return "Daten";
        }

        @Override
        public Component getContent() {

            unternehmenDatenView = new UnternehmenDatenView();
            unternehmenDatenView.setRatingInviable();
            return unternehmenDatenView;
/*
            GridLayout gridLayout = new GridLayout(2, 3);
            gridLayout.setHeightUndefined();
            gridLayout.setWidth("100%");

            FormLayout form1 = new FormLayout();
            form1.setWidth("300px");
            form1.setMargin(true);

            uploadLogo = new UploadField();
            uploadLogo.setDisplayUpload(false);
            uploadLogo.setButtonCaption("Firmenlogo hochladen");
            uploadLogo.setClearButtonVisible(true);
            uploadLogo.setAcceptFilter("image/*");


            uploadLogo.addValueChangeListener((HasValue.ValueChangeEvent<byte[]> event) -> {
                form1.removeComponent(image);
                byte[] bild = uploadLogo.getValue();
                image = ImageConverter.convertImagetoProfil(bild);
                form1.addComponent(image,0);
            });



            kontaktnummer = new NumeralField("Kontaktnummer");

            form1.addComponents( image, uploadLogo,kontaktnummer);
            form1.setComponentAlignment(image, Alignment.TOP_LEFT);

            FormLayout form2 = new FormLayout();
            form2.setWidth("300px");
            form2.setMargin(true);
            PlaceHolderField place1 = new PlaceHolderField();
            strasse = new PopUpTextField("Straße & Nr.");

            ort = new OrtPlzTextField();

            PlaceHolderField place2 = new PlaceHolderField();


            branche = new ComboBox<>("", ComponentControl.getInstance().getBranche());
            branche.setPlaceholder("Branche");
            branche.setHeight("56px");
            branche.setWidth("300px");



            form2.addComponents(place1, strasse, ort, place2, branche);


            gridLayout.addComponent(form1, 0, 1, 0, 1);
            gridLayout.addComponent(form2, 1, 1, 1, 1);
            gridLayout.setComponentAlignment(form1, Alignment.TOP_LEFT);
            gridLayout.setComponentAlignment(form2, Alignment.TOP_LEFT);


            return gridLayout;

 */
        }

        @Override
        public boolean onAdvance() {


            ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.UNTERNEHMEN))
                    .setLogo(unternehmenDatenView.getUploadLogo().getValue());
            unternehmenDatenView.getUploadLogo().clear();
            ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.UNTERNEHMEN))
                    .setKontaktnummer(unternehmenDatenView.getKontaktnummer().getValue());
            ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.UNTERNEHMEN))
                    .setBranche(unternehmenDatenView.getBranche().getValue());

            Adresse adresse = new Adresse();

            if(!( unternehmenDatenView.getOrt().getOrtField().getValue() == null )
                    || unternehmenDatenView.getOrt().getPlzField().getValue() == null ) {
                adresse.setStrasse(unternehmenDatenView.getStrasse().getValue());
                adresse.setPlz(unternehmenDatenView.getOrt().getPlzField().getValue());
                adresse.setOrt(unternehmenDatenView.getOrt().getOrt());
                adresse.setBundesland(unternehmenDatenView.getOrt().getBunesland());
            }
            ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.UNTERNEHMEN)).setAdresse(adresse);

            try {
                ProfilDAO.getInstance().createUnternehmenProfil( ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.UNTERNEHMEN)));
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
