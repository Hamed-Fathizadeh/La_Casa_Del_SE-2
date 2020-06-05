package org.bonn.se.model.objects.entitites;


import java.time.LocalDate;

public class Stellenanzeige {

    private int id;
    private LocalDate datum;
    private LocalDate zeitstempel;
    private String titel;
    private String beschreibung;
    private int status;
    private String standort;
    private String bundesland;
    private String firmenname;
    private String hauptsitz;

    public String getSuchbegriff() {
        return suchbegriff;
    }

    public void setSuchbegriff(String suchbegriff) {
        this.suchbegriff = suchbegriff;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    private String suchbegriff;
    private String art;

    public Stellenanzeige() {
        setId(id);
        setDatum(datum);
        setZeitstempel(zeitstempel);
        setTitel(titel);
        setBeschreibung(beschreibung);
        setStatus(status);
        setStandort(standort);
        setBundesland(bundesland);
        setFirmenname(firmenname);
        setHauptsitz(hauptsitz);
        setSuchbegriff(suchbegriff);
        setArt(art);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public LocalDate getZeitstempel() {
        return zeitstempel;
    }

    public void setZeitstempel(LocalDate zeitstempel) {
        this.zeitstempel = zeitstempel;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStandort() {
        return standort;
    }

    public void setStandort(String standort) {
        this.standort = standort;
    }

    public String getBundesland() {
        return bundesland;
    }

    public void setBundesland(String bundesland) {
        this.bundesland = bundesland;
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





}
