package org.bonn.se.model.objects.dto;

import com.vaadin.ui.Image;
import org.bonn.se.services.util.ConvertByteToImage;

import java.sql.Date;

public class StellenanzeigeDTO {

    private int id;
    private Date datum;
    private Date zeitstempel;
    private String titel;
    private String beschreibung;
    private int status;
    private String standort;
    private String bundesland;
    private String firmenname;
    private String hauptsitz;
    private Image unternehmenLogo;
    private double bewertung;

    public StellenanzeigeDTO(int id, Date datum, Date zeitstempel, String titel, String beschreibung,
                             int status, String standort, String bundesland, String firmenname, String hauptsitz,
                             String suchbegriff, String art, byte[] unternehmenLogo) {
        this.id = id;
        this.datum = datum;
        this.zeitstempel = zeitstempel;
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.status = status;
        this.standort = standort;
        this.bundesland = bundesland;
        this.firmenname = firmenname;
        this.hauptsitz = hauptsitz;
        this.unternehmenLogo =  ConvertByteToImage.getImage(unternehmenLogo);;
        this.suchbegriff = suchbegriff;
        this.art = art;
    }

    public StellenanzeigeDTO(int id, Date datum, Date zeitstempel, String titel, String beschreibung,
                             int status, String standort, String bundesland, String firmenname, String hauptsitz,
                             String suchbegriff, String art) {
        this.id = id;
        this.datum = datum;
        this.zeitstempel = zeitstempel;
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.status = status;
        this.standort = standort;
        this.bundesland = bundesland;
        this.firmenname = firmenname;
        this.hauptsitz = hauptsitz;
        this.suchbegriff = suchbegriff;
        this.art = art;
    }

    public Image getUnternehmenLogo() {
        return unternehmenLogo;
    }

    public void setUnternehmenLogo(byte[] unternehmenLogo) {
        this.unternehmenLogo = ConvertByteToImage.getImage(unternehmenLogo);
    }

    public double getBewertung() {
        return bewertung;
    }

    public void setBewertung(double bewertung) {
        this.bewertung = bewertung;
    }

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




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Date getZeitstempel() {
        return zeitstempel;
    }

    public void setZeitstempel(Date zeitstempel) {
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
