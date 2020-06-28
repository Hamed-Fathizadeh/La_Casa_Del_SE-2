package org.bonn.se.gui.component;

import com.vaadin.data.HasValue;
import com.vaadin.ui.*;
import org.bonn.se.control.ComponentControl;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.services.util.ImageConverter;
import org.bonn.se.services.util.Roles;
import org.vaadin.easyuploads.UploadField;

public class StudentDatenView extends GridLayout {

    private OrtPlzTextField ort;
    private PopUpTextField strasse;
    private ComboBox<String> abschluss;
    private DateField gDatum;
    private PopUpTextField studiengang;
    private PopUpTextField ausbildung;
    private NumeralField mobilnr;
    private UploadField uploadProfil;
    private Image image = ImageConverter.getUnknownProfilImage();
    private UploadField lebenslauf;
    private Label filename;
    private FormLayout form1;
    private Link link = new Link();


    public OrtPlzTextField getOrt() {
        return ort;
    }

    public StudentDatenView setOrt(OrtPlzTextField ort) {
        this.ort = ort;
        return this;
    }

    public PopUpTextField getStrasse() {
        return strasse;
    }

    public StudentDatenView setStrasse(PopUpTextField strasse) {
        this.strasse = strasse;
        return this;
    }

    public ComboBox<String> getAbschluss() {
        return abschluss;
    }



    public DateField getgDatum() {
        return gDatum;
    }



    public PopUpTextField getStudiengang() {
        return studiengang;
    }


    public PopUpTextField getAusbildung() {
        return ausbildung;
    }

    public NumeralField getMobilnr() {
        return mobilnr;
    }


    public UploadField getUploadProfil() {
        return uploadProfil;
    }



    public Image getImage() {
        return image;
    }

    public StudentDatenView setImage(Image image) {
        this.image = image;
        return this;
    }

    public UploadField getLebenslauf() {
        return lebenslauf;
    }

    public StudentDatenView setLebenslauf(UploadField lebenslauf) {
        this.lebenslauf = lebenslauf;
        return this;
    }


    public StudentDatenView() {

        this.setColumns(2);
        this.setRows(3);
        this.setHeightUndefined();
        this.setWidth("100%");



        form1 = new FormLayout();
        form1.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        uploadProfil =new UploadField();
        uploadProfil.setDisplayUpload(false);
        uploadProfil.setButtonCaption("Profilbild hochladen");
        uploadProfil.setClearButtonVisible(true);
        uploadProfil.setAcceptFilter("image/*");
        uploadProfil.addValueChangeListener((HasValue.ValueChangeEvent<byte[]> event) -> {
            form1.removeComponent(image);

            byte[] bild = uploadProfil.getValue();

            image = ImageConverter.convertImagetoProfil(bild);

            form1.addComponent(image,0);
            form1.setComponentAlignment(image,Alignment.MIDDLE_CENTER);
        });



        form1.setWidth("300px");
        form1.setMargin(true);
        gDatum =new DateField();
        gDatum.setHeight("56px");
        gDatum.setWidth("300px");
        gDatum.setPlaceholder("Geburtsdatum dd.mm.yyyy");
        mobilnr =new NumeralField("Kontaktnummer");

        image.setHeight("170px");
        image.setWidth("150px");
        lebenslauf =new UploadField();
        lebenslauf.setButtonCaption("Lebenslauf hochladen");
        lebenslauf.setDisplayUpload(false);

    lebenslauf.addValueChangeListener((HasValue.ValueChangeListener<byte[]>)event -> {
        if (lebenslauf.getValue() == null) {
            form1.removeComponent(link);
        } else {
            form1.removeComponent(link);
            link = new Link(lebenslauf.getLastFileName(),ImageConverter.getLebenslaufasPDF(lebenslauf.getValue(),lebenslauf.getLastFileName()));
            link.setTargetName("_blank");
            link.addStyleName("color3");
            form1.addComponent(link, 5);
        }
    });



            form1.addComponents(image,uploadProfil, gDatum,mobilnr,lebenslauf,link);


    FormLayout form2 = new FormLayout();
            form2.setWidth("300px");
            form2.setMargin(true);
    PlaceHolderField place1 = new PlaceHolderField();
    strasse =new PopUpTextField("Straße & Nr.");

    ort =new OrtPlzTextField();


    PlaceHolderField place2 = new PlaceHolderField();
    studiengang =new PopUpTextField("Studiengang");

    ausbildung =new PopUpTextField("Ausbildung (optional)");


    abschluss =new ComboBox<>("");
            abschluss.setPlaceholder("Wähle den höchsten Abschluss...");
            abschluss.setHeight("56px");
            abschluss.setWidth("300px");
            abschluss.setItems(ComponentControl.getInstance().getAbschluss());

            form2.addComponents(place1,strasse,ort,studiengang,place2,ausbildung,abschluss);
            form1.setComponentAlignment(image,Alignment.MIDDLE_CENTER);
            this.addComponent(form1,0,0,0,0);
            this.addComponent(form2,1,0,1,0);
            this.setComponentAlignment(form1,Alignment.TOP_LEFT);
            this.setComponentAlignment(form2,Alignment.TOP_LEFT);
    }


    public void setStudentValue(){
        Student student = (Student) UI.getCurrent().getSession().getAttribute(Roles.Student);
        ort.getOrtField().setValue(student.getAdresse().getOrt() == null ? "" : student.getAdresse().getOrt() + ", " + student.getAdresse().getBundesland()) ;
        strasse.setValue(student.getAdresse().getStrasse() == null ? "" : student.getAdresse().getStrasse());
        ort.getPlzField().setValue(student.getAdresse().getPlz().equals("0") ? "" : student.getAdresse().getPlz());
        abschluss.setValue(student.getAbschluss());
        gDatum.setValue(student.getG_datum());
        studiengang.setValue(student.getStudiengang());
        ausbildung.setValue(student.getAusbildung());
        mobilnr.setValue(student.getKontakt_nr());
        uploadProfil.setValue(student.getPicture());
        lebenslauf.setLastFilename("Lebenslauf_" + student.getVorname()+ " " + student.getNachname());
        if(student.hasLebenslauf()) {
            link.setCaption(lebenslauf.getLastFileName());
            link.setResource(ImageConverter.getLebenslaufasPDF(student.getLebenslauf(),lebenslauf.getLastFileName()));
            link.setTargetName("_blank");
        }


    }

    public void setReadOnly(boolean status) {
        strasse.setCaption("Anschrift");
        studiengang.setCaption("Studiengang");
        abschluss.setCaption("Abschluss");
        ausbildung.setCaption("Ausbildung");
        mobilnr.setCaption("Kontaktnr.");
        gDatum.setCaption("Geburtsdatum");
        ort.getPlzField().setReadOnly(status);
        ort.getOrtField().setReadOnly(status);
        strasse.setReadOnly(status);
        abschluss.setReadOnly(status);
        gDatum.setReadOnly(status);
        studiengang.setReadOnly(status);
        ausbildung.setReadOnly(status);
        mobilnr.setReadOnly(status);
        uploadProfil.setVisible(!status);
        lebenslauf.setVisible(!status);
        link.setVisible(status);
    }

}
