package org.bonn.se.model.objects.dto;

import com.vaadin.ui.Image;
import org.bonn.se.services.util.ConvertByteToImage;

import java.sql.Date;

public class BewerbungDTO {
    private int bewerbungID;
    private Date datum;
    private String description;
    private byte[] lebenslauf;
    private int status;
    private int studentID;
    private String unternehmenName;
    private String unternehmenHauptsitz;
    private Image unternehmenLogo;
    private String s_titel;
    private double rating;
    private String emailStudent;
    private Date student_g_datum;
    private String student_studiengang;
    private String student_ausbildung;
    private String  student_kontakt_nr;
    private int student_benachrichtigung;
    private Image student_picture;
    private String student_hoester_abschluss;
    private String student_vorname;
    private String student_nachname;
    private boolean bewerbung_markiert;

    public boolean isBewerbung_markiert() {
        return bewerbung_markiert;
    }

    public void setBewerbung_markiert(boolean bewerbung_markiert) {
        this.bewerbung_markiert = bewerbung_markiert;
    }

    public Date getStudent_g_datum() {
        return student_g_datum;
    }

    public void setStudent_g_datum(Date student_g_datum) {
        this.student_g_datum = student_g_datum;
    }

    public String getStudent_studiengang() {
        return student_studiengang;
    }

    public void setStudent_studiengang(String student_studiengang) {
        this.student_studiengang = student_studiengang;
    }

    public String getStudent_ausbildung() {
        return student_ausbildung;
    }

    public void setStudent_ausbildung(String student_ausbildung) {
        this.student_ausbildung = student_ausbildung;
    }

    public String getStudent_kontakt_nr() {
        return student_kontakt_nr;
    }

    public void setStudent_kontakt_nr(String student_kontakt_nr) {
        this.student_kontakt_nr = student_kontakt_nr;
    }

    public int getStudent_benachrichtigung() {
        return student_benachrichtigung;
    }

    public void setStudent_benachrichtigung(int student_benachrichtigung) {
        this.student_benachrichtigung = student_benachrichtigung;
    }

    public Image getStudent_picture() {
        return student_picture;
    }

    public void setStudent_picture(byte[] student_picture) {
    }

    public String getStudent_hoester_abschluss() {
        return student_hoester_abschluss;
    }

    public void setStudent_hoester_abschluss(String student_hester_abschluss) {
        this.student_hoester_abschluss = student_hester_abschluss;
    }

    public String getStudent_vorname() {
        return student_vorname;
    }

    public void setStudent_vorname(String student_vorname) {
        this.student_vorname = student_vorname;
    }

    public String getStudent_nachname() {
        return student_nachname;
    }

    public void setStudent_nachname(String student_nachname) {
        this.student_nachname = student_nachname;
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
        return s_titel;
    }


    public String getUnternehmenName() {
        return unternehmenName;
    }

    public void setUnternehmenName(String unternehmenName) {
    }

    public Image getUnternehmenLogo() {
        return unternehmenLogo;
    }

    public void setUnternehmenLogo(byte[] unternehmenLogo) {
    }

    public BewerbungDTO(){

    }

    public BewerbungDTO(int bewerbungID, Date datum, String description, byte[] lebenslauf, int status, int studentID, int anzeigeID,
                        String unternehmenName,String unternehmenHauptsitz, byte[] unternehmenLogo,String emailStudent, String s_titel,
                        Date student_g_datum,String student_studiengang, String student_ausbildung,String  student_kontakt_nr,int student_benachrichtigung,
                        byte[] student_picture,String student_hoester_abschluss,String student_vorname,String student_nachname,double rating, boolean bewerbung_markiert
                       ) {
        this.bewerbungID = bewerbungID;
        this.datum = datum;
        this.description = description;
        this.lebenslauf = lebenslauf;
        this.status = status;
        this.studentID = studentID;
        this.anzeigeID = anzeigeID;
        this.unternehmenName = unternehmenName;
        this.unternehmenLogo = ConvertByteToImage.getImage(unternehmenLogo);
        this.s_titel = s_titel;
        this.rating = rating;
        this.emailStudent = emailStudent;
        this.unternehmenHauptsitz = unternehmenHauptsitz;
        this.student_g_datum = student_g_datum;
        this. student_studiengang =  student_studiengang;
        this.student_ausbildung = student_ausbildung;
        this.student_kontakt_nr = student_kontakt_nr;
        this.student_benachrichtigung = student_benachrichtigung;
        this.student_picture = ConvertByteToImage.getImage(student_picture);
        this.student_hoester_abschluss = student_hoester_abschluss;
        this.student_vorname = student_vorname;
        this.student_nachname = student_nachname;
        this.bewerbung_markiert = bewerbung_markiert;



    }

    public BewerbungDTO(int bewerbungID, Date datum, String description, byte[] lebenslauf, int status, int studentID, int anzeigeID,
                        String unternehmenName,String unternehmenHauptsitz, byte[] unternehmenLogo,String emailStudent, String s_titel, double rating) {
        this.bewerbungID = bewerbungID;
        this.datum = datum;
        this.description = description;
        this.lebenslauf = lebenslauf;
        this.status = status;
        this.studentID = studentID;
        this.anzeigeID = anzeigeID;
        this.unternehmenName = unternehmenName;
        this.unternehmenLogo = ConvertByteToImage.getImage(unternehmenLogo);
        this.s_titel = s_titel;
        this.rating = rating;
        this.emailStudent = emailStudent;
        this.unternehmenHauptsitz = unternehmenHauptsitz;

    }



    public BewerbungDTO( byte[] unternehmenLogo,String unternehmenName, Date datum, int status ,int bewerbungID, String s_titel) {
        this.datum = datum;
        this.status = status;
        this.unternehmenName = unternehmenName;
        this.unternehmenLogo = ConvertByteToImage.getImage(unternehmenLogo);
        this.bewerbungID = bewerbungID;
        this.s_titel = s_titel;
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


}
