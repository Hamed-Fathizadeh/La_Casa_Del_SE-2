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
    private DateField g_datum;
    private PopUpTextField studiengang;
    private PopUpTextField ausbildung;
    private NumeralField mobilnr;
    private UploadField uploadProfil;
    private Image image = ImageConverter.getUnknownProfilImage();
    private UploadField lebenslauf;
    private Label filename;
    private FormLayout form1;
    private Link link = new Link();

    public FormLayout getForm1() {
        return form1;
    }

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

    public StudentDatenView setAbschluss(ComboBox<String> abschluss) {
        this.abschluss = abschluss;
        return this;
    }

    public DateField getG_datum() {
        return g_datum;
    }

    public StudentDatenView setG_datum(DateField g_datum) {
        this.g_datum = g_datum;
        return this;
    }

    public PopUpTextField getStudiengang() {
        return studiengang;
    }

    public StudentDatenView setStudiengang(PopUpTextField studiengang) {
        this.studiengang = studiengang;
        return this;
    }

    public PopUpTextField getAusbildung() {
        return ausbildung;
    }

    public StudentDatenView setAusbildung(PopUpTextField ausbildung) {
        this.ausbildung = ausbildung;
        return this;
    }

    public NumeralField getMobilnr() {
        return mobilnr;
    }

    public StudentDatenView setMobilnr(NumeralField mobilnr) {
        this.mobilnr = mobilnr;
        return this;
    }

    public UploadField getUploadProfil() {
        return uploadProfil;
    }

    public StudentDatenView setUploadProfil(UploadField uploadProfil) {
        this.uploadProfil = uploadProfil;
        return this;
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

    public Label getFilename() {
        return filename;
    }

    public StudentDatenView setFilename(Label filename) {
        this.filename = filename;
        return this;
    }

    public Link getLink() {
        return link;
    }

    public StudentDatenView setLink(Link link) {
        this.link = link;
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
        g_datum =new DateField();
        g_datum.setHeight("56px");
        g_datum.setWidth("300px");
        g_datum.setPlaceholder("Geburtsdatum dd.mm.yyyy");
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



            form1.addComponents(image,uploadProfil,g_datum,mobilnr,lebenslauf,link);


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
            abschluss.setItems(ComponentControl.getInstance().

    getAbschluss());

            form2.addComponents(place1,strasse,ort,studiengang,place2,ausbildung,abschluss);
            form1.setComponentAlignment(image,Alignment.MIDDLE_CENTER);
            this.addComponent(form1,0,0,0,0);
            this.addComponent(form2,1,0,1,0);
            this.setComponentAlignment(form1,Alignment.TOP_LEFT);
            this.setComponentAlignment(form2,Alignment.TOP_LEFT);
    }


    public void setStudentValue(){
        Student student = (Student) UI.getCurrent().getSession().getAttribute(Roles.Student);
        ort.getOrtField().setValue(student.getAdresse().getOrt() + ", " + student.getAdresse().getBundesland()) ;
        strasse.setValue(student.getAdresse().getStrasse());
        ort.getPlzField().setValue(student.getAdresse().getPlz());
        abschluss.setValue(student.getAbschluss());
        g_datum.setValue(student.getG_datum());
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
        ort.getPlzField().setReadOnly(status);
        strasse.setReadOnly(status);
        abschluss.setReadOnly(status);
        g_datum.setReadOnly(status);
        studiengang.setReadOnly(status);
        ausbildung.setReadOnly(status);
        mobilnr.setReadOnly(status);
        uploadProfil.setVisible(!status);
        lebenslauf.setVisible(!status);
        link.setVisible(status);
    }

}
