package org.bonn.se.model.objects.entitites;

import java.sql.Date;

public class Bewerbung {
    private int bewerbungID;
    private Date datum;
    private String description;
    private byte[] lebenslauf;
    private int status;
    private int studentID;
    private int anzeigeID;


    public Bewerbung(int bewerbungID, Date datum, String description, byte[] lebenslauf, int status, int studentID, int anzeigeID) {
        this.bewerbungID = bewerbungID;
        this.datum = datum;
        this.description = description;
        this.lebenslauf = lebenslauf;
        this.status = status;
        this.studentID = studentID;
        this.anzeigeID = anzeigeID;
    }
}
