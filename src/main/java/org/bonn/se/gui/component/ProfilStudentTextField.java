package org.bonn.se.gui.component;

import com.vaadin.ui.TextField;

public class ProfilStudentTextField extends TextField {

    //eingabe wert
    private String caption;
    private String value;

    //TextField Direkt nehmen, value set erst in Klasse ProfilVerwalten
    public ProfilStudentTextField(String caption, String value){
        this.setHeight("37px");
        this.setWidth("250px");
        this.setCaption(caption);
        this.setValue(value);
        this.setReadOnly(true);
    }




    /*
    //
    public ProfilStudentTextField (String value){

    }

    //TextField, die benötigt sind

    private TextField vorname;
    private TextField nachname;
    private OrtPlzTextField strasse;
    private OrtPlzTextField plz;
    private TextField telnr;
    private TextField studiengang;
    private TextField abschluss;


    Student nehmen schön hier

    Student student = ((Student) MyUI.getCurrent().getSession().getAttribute(Roles.Student));
Norname = student.get

    public TextField getVorname() {
        return vorname;
    }

    public void setVorname() {
        vorname.setHeight("37px");
        vorname.setWidth("408px");
        vorname.setCaption("Vorname");
        vorname.setValue(vorname);
    }

    public TextField getNachname() {
        return nachname;
    }

    public void setNachname(TextField nachname) {
        this.nachname = nachname;
    }

    public OrtPlzTextField getStrasse() {
        return strasse;
    }

    public void setStrasse(OrtPlzTextField strasse) {
        this.strasse = strasse;
    }

    public OrtPlzTextField getPlz() {
        return plz;
    }

    public void setPlz(OrtPlzTextField plz) {
        this.plz = plz;
    }

    public TextField getTelnr() {
        return telnr;
    }

    public void setTelnr(TextField telnr) {
        this.telnr = telnr;
    }

    public TextField getStudiengang() {
        return studiengang;
    }

    public void setStudiengang(TextField studiengang) {
        this.studiengang = studiengang;
    }

    public TextField getAbschluss() {
        return abschluss;
    }

    public void setAbschluss(TextField abschluss) {
        this.abschluss = abschluss;
    }


    public TextField setTfVorname (){
        return this.vorname;
    }

    */
}
