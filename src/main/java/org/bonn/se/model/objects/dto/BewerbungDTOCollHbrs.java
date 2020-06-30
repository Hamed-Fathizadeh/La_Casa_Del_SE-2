package org.bonn.se.model.objects.dto;
import com.vaadin.ui.Image;
import org.bonn.se.services.util.ImageConverter;

import java.sql.Date;
import java.util.Arrays;
import java.util.Objects;

public class BewerbungDTOCollHbrs implements BewerbungDTO{
    private int bewerbungID;
    private Date datum;
    private String description;
    private byte[] lebenslauf;
    private int status;
    private int studentID;
    private String unternehmenName;
    private String unternehmenHauptsitz;
    private Image unternehmenLogo;
    private String sTitel;
    private double rating;
    private String emailStudent;
    private Date studentGDatum;
    private String studentStudiengang;
    private String studentAusbildung;
    private String studentKontaktNr;
    private int studentBenachrichtigung;
    private Image studentPicture;
    private String studentHoesterAbschluss;
    private String studentVorname;
    private String studentNachname;
    private boolean bewerbungMarkiert;

    public boolean isBewerbungMarkiert() {
        return bewerbungMarkiert;
    }

    public void setBewerbungMarkiert(boolean bewerbungMarkiert) {
        this.bewerbungMarkiert = bewerbungMarkiert;
    }


    public void setStudentGDatum(Date studentGDatum) {
        this.studentGDatum = studentGDatum;
    }

    public String getStudentStudiengang() {
        return studentStudiengang;
    }

    public void setStudentStudiengang(String studentStudiengang) {
        this.studentStudiengang = studentStudiengang;
    }

    public String getStudentAusbildung() {
        return studentAusbildung;
    }

    public void setStudentAusbildung(String studentAusbildung) {
        this.studentAusbildung = studentAusbildung;
    }

    public String getStudentKontaktNr() {
        return studentKontaktNr;
    }

    public void setStudentKontaktNr(String studentKontaktNr) {
        this.studentKontaktNr = studentKontaktNr;
    }

    public int getStudentBenachrichtigung() {
        return studentBenachrichtigung;
    }

    public void setStudentBenachrichtigung(int studentBenachrichtigung) {
        this.studentBenachrichtigung = studentBenachrichtigung;
    }

    public Image getStudentPicture() {
        return studentPicture;
    }

    public String getStudentHoesterAbschluss() {
        return studentHoesterAbschluss;
    }

    public void setStudentHoesterAbschluss(String studentHoesterAbschluss) {
        this.studentHoesterAbschluss = studentHoesterAbschluss;
    }

    public String getStudentVorname() {
        return studentVorname;
    }

    public void setStudentVorname(String studentVorname) {
        this.studentVorname = studentVorname;
    }

    public String getStudentNachname() {
        return studentNachname;
    }

    public void setStudentNachname(String studentNachname) {
        this.studentNachname = studentNachname;
    }

    public String getUnternehmenHauptsitz() {
        return unternehmenHauptsitz;
    }

    public void setUnternehmenHauptsitz(String unternehmenHauptsitz) {
        this.unternehmenHauptsitz = unternehmenHauptsitz;
    }

    public String getEmailStudent() {
        return emailStudent;
    }

    public void setEmailStudent(String emailStudent) {
        this.emailStudent = emailStudent;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getTitel() {
        return sTitel;
    }


    public String getUnternehmenName() {
        return unternehmenName;
    }

    public void setUnternehmenName(String unternehmenName) {
        this.unternehmenName=unternehmenName;
    }

    public Image getUnternehmenLogo() {
        return unternehmenLogo;
    }

    public BewerbungDTOCollHbrs(){

    }

    public BewerbungDTOCollHbrs(int bewerbungID, Date datum, String description, byte[] lebenslauf, int status, int studentID, int anzeigeID,
                        String unternehmenName, String unternehmenHauptsitz, byte[] unternehmenLogo, String emailStudent, String sTitel,
                        Date studentGDatum, String studentStudiengang, String studentAusbildung, String  studentKontaktNr, int studentBenachrichtigung,
                        byte[] studentPicture, String studentHoesterAbschluss, String studentVorname, String studentNachname, double rating, boolean bewerbungMarkiert
    ) {
        this.bewerbungID = bewerbungID;
        this.datum = datum;
        this.description = description;
        this.lebenslauf = lebenslauf;
        this.status = status;
        this.studentID = studentID;
        this.anzeigeID = anzeigeID;
        this.unternehmenName = unternehmenName;
        this.unternehmenLogo = ImageConverter.convertImagetoMenu(unternehmenLogo);
        this.sTitel = sTitel;
        this.rating = rating;
        this.emailStudent = emailStudent;
        this.unternehmenHauptsitz = unternehmenHauptsitz;
        this.studentGDatum = studentGDatum;
        this.studentStudiengang =  studentStudiengang;
        this.studentAusbildung = studentAusbildung;
        this.studentKontaktNr = studentKontaktNr;
        this.studentBenachrichtigung = studentBenachrichtigung;
        this.studentPicture = ImageConverter.convertImagetoMenu(studentPicture);
        this.studentHoesterAbschluss = studentHoesterAbschluss;
        this.studentVorname = studentVorname;
        this.studentNachname = studentNachname;
        this.bewerbungMarkiert = bewerbungMarkiert;



    }

    public BewerbungDTOCollHbrs(int bewerbungID, Date datum, String description, byte[] lebenslauf, int status, int studentID, int anzeigeID,
                        String unternehmenName,String unternehmenHauptsitz, byte[] unternehmenLogo,String emailStudent, String sTitel, double rating) {
        this.bewerbungID = bewerbungID;
        this.datum = datum;
        this.description = description;
        this.lebenslauf = lebenslauf;
        this.status = status;
        this.studentID = studentID;
        this.anzeigeID = anzeigeID;
        this.unternehmenName = unternehmenName;
        this.unternehmenLogo = ImageConverter.convertImagetoMenu(unternehmenLogo);
        this.sTitel = sTitel;
        this.rating = rating;
        this.emailStudent = emailStudent;
        this.unternehmenHauptsitz = unternehmenHauptsitz;

    }


    private int anzeigeID;

    public int getBewerbungID() {
        return bewerbungID;
    }

    public void setBewerbungID(int bewerbungID) {
        this.bewerbungID = bewerbungID;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getLebenslauf() {
        return lebenslauf;
    }

    public void setLebenslauf(byte[] lebenslauf) {
        this.lebenslauf = lebenslauf;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getAnzeigeID() {
        return anzeigeID;
    }

    public void setAnzeigeID(int anzeigeID) {
        this.anzeigeID = anzeigeID;
    }


    @Override
    public String toString() {
        return "BewerbungDTO{" +
                "bewerbungID=" + bewerbungID +
                ", datum=" + datum +
                ", description='" + description + '\'' +
                ", lebenslauf=" + Arrays.toString(lebenslauf) +
                ", status=" + status +
                ", studentID=" + studentID +
                ", unternehmenName='" + unternehmenName + '\'' +
                ", unternehmenHauptsitz='" + unternehmenHauptsitz + '\'' +
                ", unternehmenLogo=" + unternehmenLogo +
                ", sTitel='" + sTitel + '\'' +
                ", rating=" + rating +
                ", emailStudent='" + emailStudent + '\'' +
                ", studentGDatum=" + studentGDatum +
                ", studentStudiengang='" + studentStudiengang + '\'' +
                ", studentAusbildung='" + studentAusbildung + '\'' +
                ", studentKontaktNr='" + studentKontaktNr + '\'' +
                ", studentBenachrichtigung=" + studentBenachrichtigung +
                ", studentPicture=" + studentPicture +
                ", studentHoesterAbschluss='" + studentHoesterAbschluss + '\'' +
                ", studentVorname='" + studentVorname + '\'' +
                ", studentNachname='" + studentNachname + '\'' +
                ", bewerbungMarkiert=" + bewerbungMarkiert +
                ", anzeigeID=" + anzeigeID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BewerbungDTOCollHbrs)) return false;
        BewerbungDTOCollHbrs that = (BewerbungDTOCollHbrs) o;
        return getBewerbungID() == that.getBewerbungID() &&
                getStatus() == that.getStatus() &&
                getStudentID() == that.getStudentID() &&
                Double.compare(that.getRating(), getRating()) == 0 &&
                getStudentBenachrichtigung() == that.getStudentBenachrichtigung() &&
                isBewerbungMarkiert() == that.isBewerbungMarkiert() &&
                getAnzeigeID() == that.getAnzeigeID() &&
                Objects.equals(getDatum(), that.getDatum()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Arrays.equals(getLebenslauf(), that.getLebenslauf()) &&
                Objects.equals(getUnternehmenName(), that.getUnternehmenName()) &&
                Objects.equals(getUnternehmenHauptsitz(), that.getUnternehmenHauptsitz()) &&
                Objects.equals(getUnternehmenLogo(), that.getUnternehmenLogo()) &&
                Objects.equals(sTitel, that.sTitel) &&
                Objects.equals(getEmailStudent(), that.getEmailStudent()) &&
                Objects.equals(studentGDatum, that.studentGDatum) &&
                Objects.equals(getStudentStudiengang(), that.getStudentStudiengang()) &&
                Objects.equals(getStudentAusbildung(), that.getStudentAusbildung()) &&
                Objects.equals(getStudentKontaktNr(), that.getStudentKontaktNr()) &&
                Objects.equals(getStudentPicture(), that.getStudentPicture()) &&
                Objects.equals(getStudentHoesterAbschluss(), that.getStudentHoesterAbschluss()) &&
                Objects.equals(getStudentVorname(), that.getStudentVorname()) &&
                Objects.equals(getStudentNachname(), that.getStudentNachname());
    }

}
