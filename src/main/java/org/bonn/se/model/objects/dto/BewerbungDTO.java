package org.bonn.se.model.objects.dto;

import com.vaadin.ui.Image;
import org.bonn.se.services.util.ConvertByteToImage;

import java.sql.Date;

public class BewerbungDTO { private int bewerbungID;
    private Date datum;
    private String description;
    private byte[] lebenslauf;
    private int status;
    private int studentID;
    private String unternehmenName;
    private Image unternehmenLogo;
    private String s_titel;
    private double rating;
    private String emailStudent;

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
        unternehmenName = unternehmenName;
    }

    public Image getUnternehmenLogo() {
        return unternehmenLogo;
    }

    public void setUnternehmenLogo(byte[] unternehmenLogo) {
        unternehmenLogo = unternehmenLogo;
    }

    public BewerbungDTO(){

    }

    public BewerbungDTO(int bewerbungID, Date datum, String description, byte[] lebenslauf, int status, int studentID, int anzeigeID,
                        String unternehmenName, byte[] unternehmenLogo,String emailStudent, String s_titel, double rating) {
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
