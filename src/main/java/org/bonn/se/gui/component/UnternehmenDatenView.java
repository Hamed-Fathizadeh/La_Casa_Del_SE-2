package org.bonn.se.gui.component;

import com.vaadin.data.HasValue;
import com.vaadin.ui.*;
import org.bonn.se.control.ComponentControl;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.util.ImageConverter;
import org.bonn.se.services.util.Roles;
import org.vaadin.easyuploads.UploadField;

public class UnternehmenDatenView extends GridLayout {


        NumeralField kontaktnummer;

    public NumeralField getKontaktnummer() {
        return kontaktnummer;
    }

    public UnternehmenDatenView setKontaktnummer(NumeralField kontaktnummer) {
        this.kontaktnummer = kontaktnummer;
        return this;
    }

    public PopUpTextField getStrasse() {
        return strasse;
    }

    public UnternehmenDatenView setStrasse(PopUpTextField strasse) {
        this.strasse = strasse;
        return this;
    }

    public OrtPlzTextField getOrt() {
        return ort;
    }

    public UnternehmenDatenView setOrt(OrtPlzTextField ort) {
        this.ort = ort;
        return this;
    }

    public ComboBox<String> getBranche() {
        return branche;
    }

    public UnternehmenDatenView setBranche(ComboBox<String> branche) {
        this.branche = branche;
        return this;
    }

    public UploadField getUploadLogo() {
        return uploadLogo;
    }

    public UnternehmenDatenView setUploadLogo(UploadField uploadLogo) {
        this.uploadLogo = uploadLogo;
        return this;
    }

    public Image getImage() {
        return image;
    }

    public UnternehmenDatenView setImage(Image image) {
        this.image = image;
        return this;
    }

    PopUpTextField strasse;
    OrtPlzTextField ort;
     ComboBox<String> branche;
        UploadField uploadLogo;
        Image image = ImageConverter.getUnknownProfilImage();

    public UnternehmenDatenView() {


        this.setColumns(2);
        this.setRows(3);
        this.setHeightUndefined();
        this.setWidth("100%");

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
            form1.addComponent(image, 0);
        });


        kontaktnummer = new NumeralField("Kontaktnummer");
        form1.addComponents(image, uploadLogo, kontaktnummer);
        form1.setComponentAlignment(image, Alignment.TOP_LEFT);

        FormLayout form2 = new FormLayout();
        form2.setWidth("300px");
        form2.setMargin(true);
        PlaceHolderField place1 = new PlaceHolderField();
        strasse = new PopUpTextField("Straße & Nr.");
        ort = new OrtPlzTextField();
        PlaceHolderField place2 = new PlaceHolderField();
        branche = new ComboBox<>(null, ComponentControl.getInstance().getBranche());
        branche.setPlaceholder("Branche");
        branche.setHeight("56px");
        branche.setWidth("300px");

        form2.addComponents(place1, strasse, ort, place2, branche);
        this.addComponent(form1, 0, 1, 0, 1);
        this.addComponent(form2, 1, 1, 1, 1);
        this.setComponentAlignment(form1, Alignment.TOP_LEFT);
        this.setComponentAlignment(form2, Alignment.TOP_LEFT);
    }

    public void setReadOnly(boolean status) {
        kontaktnummer.setReadOnly(status);
        strasse.setReadOnly(status);
        ort.getPlzField().setReadOnly(status);
        ort.getOrtField().setReadOnly(status);
        branche.setReadOnly(status);
        /*
        if (status) {
            branche.setValue("Branche");
            ort.getOrtField().setValue("Ort");
        } else {
            branche.setValue(branche.getValue());
            ort.getOrtField().setValue(ort.getOrtField().getValue());
        }
         */
        uploadLogo.setVisible(!status);
    }

    public void setUnternehmenValue(){
        Unternehmen unternehmen = (Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen);
        uploadLogo.setValue(unternehmen.getLogo());

        if(unternehmen.getKontaktnummer() != null) kontaktnummer.setValue(unternehmen.getKontaktnummer());
        if(unternehmen.getAdresse().getStrasse() != null) strasse.setValue(unternehmen.getAdresse().getStrasse());
        if(unternehmen.getAdresse().getOrt() != null )  ort.getOrtField().setValue(unternehmen.getAdresse().getOrt() + ", " +
                unternehmen.getBundesland());
        if(unternehmen.getAdresse().getPlz() != null)  ort.getPlzField().setValue(unternehmen.getAdresse().getPlz());
        if(unternehmen.getBranche() != null) branche.setValue(unternehmen.getBranche());
    }

}
