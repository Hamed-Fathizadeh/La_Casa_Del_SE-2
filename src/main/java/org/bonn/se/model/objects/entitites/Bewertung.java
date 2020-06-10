package org.bonn.se.model.objects.entitites;

import java.sql.Date;

public class Bewertung {
    private Date datum;
    private int anzahlSterne;
    private String firmenname;
    private String hauptsitz;
    private int studentID;

    public Bewertung(Date datum , int anzahlSterne, String firmenname, String hauptsitz, int studentID){
        this.datum = datum;
        this.anzahlSterne = anzahlSterne;
        this.firmenname = firmenname;
        this.hauptsitz = hauptsitz;
        this.studentID = studentID;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public int getAnzahlSterne() {
        return anzahlSterne;
    }

    public void setAnzahlSterne(int anzahlSterne) {
        this.anzahlSterne = anzahlSterne;
    }

    public String getFirmenname() {
        return firmenname;
    }

    public void setFirmenname(String firmenname) {
        this.firmenname = firmenname;
    }

    public String getHauptsitz() {
        return hauptsitz;
    }

    public void setHauptsitz(String hauptsitz) {
        this.hauptsitz = hauptsitz;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }


}
