package org.bonn.se.model.objects.entitites;

import java.sql.Date;

public class Bewerbung {
    private int bewerbungID;
    private Date datum;
    private String description;
    private byte[] lebenslauf;
    private int status;
    private int studentID;

    public Bewerbung(int bewerbungID, Date datum, String description, byte[] lebenslauf, int status, int studentID, int anzeigeID) {
        this.bewerbungID = bewerbungID;
        this.datum = datum;
        this.description = description;
        this.lebenslauf = lebenslauf;
        this.status = status;
        this.studentID = studentID;
        this.anzeigeID = anzeigeID;
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
