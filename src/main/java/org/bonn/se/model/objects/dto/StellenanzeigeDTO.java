package org.bonn.se.model.objects.dto;

import com.vaadin.ui.Image;
import org.bonn.se.services.util.ImageConverter;

import java.sql.Date;
import java.time.LocalDate;

public class StellenanzeigeDTO {

    private int id;
    private LocalDate datum;
    private Date zeitstempel;
    private String titel;
    private String beschreibung;
    private int status;
    private String standort;
    private String bundesland;
    private String standortBundesland;
    private String firmenname;
    private String hauptsitz;
    private Image unternehmenLogo;
    private double bewertung;
    private int    anzahlNeuBewerbung;
    private String branche;
    static private int gesamtNeuBewerbungen;

    public static int getGesamtNeuBewerbungen() {
        return gesamtNeuBewerbungen;
    }

    public static void setGesamtNeuBewerbungen(int gesamtNeuBewerbungen) {
        StellenanzeigeDTO.gesamtNeuBewerbungen = gesamtNeuBewerbungen;
    }

    public String getBranche() {
        return branche;
    }

    public void setBranche(String branche) {
        this.branche = branche;
    }

    public String getStandortBundesland() {
        return standort+", "+bundesland;
    }

    public int getanzahlNeuBewerbung() {
        return anzahlNeuBewerbung;
    }

    public void setHatNeuBewerbung(int anzahlNeuBewerbung) {
        this.anzahlNeuBewerbung = anzahlNeuBewerbung;
    }

    public StellenanzeigeDTO() {
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
        setHatNeuBewerbung(anzahlNeuBewerbung);
        setBranche(branche);
    }

    public StellenanzeigeDTO(int id, LocalDate datum, Date zeitstempel, String titel, String beschreibung,
                             int status, String standort, String bundesland, String firmenname, String hauptsitz,
                             String suchbegriff, String art, byte[] unternehmenLogo, double bewertung, String branche) {
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
        this.unternehmenLogo = ImageConverter.convertImagetoMenu(unternehmenLogo);
        this.suchbegriff = suchbegriff;
        this.art = art;
        this.bewertung = bewertung;
        this.branche = branche;
    }

    public StellenanzeigeDTO(int id, LocalDate datum, Date zeitstempel, String titel, String beschreibung,
                             int status, String standort, String bundesland, String firmenname, String hauptsitz,
                             String suchbegriff, String art, int anzahlNeuBewerbung) {
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
        this.anzahlNeuBewerbung = anzahlNeuBewerbung ;
    }

    public Image getUnternehmenLogo() {
        return unternehmenLogo;
    }

    public void setUnternehmenLogo(byte[] unternehmenLogo) {
        this.unternehmenLogo = ImageConverter.convertImagetoMenu(unternehmenLogo);
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

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
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
