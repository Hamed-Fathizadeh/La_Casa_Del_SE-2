package org.bonn.se.model.objects.entitites;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class Student extends User {
    private int studentId;
    private String vorname;
    private String nachname;
    private LocalDate gDatum;
    private String studiengang;
    private byte[] picture = null;
    private byte[] lebenslauf = null;
    private String abschluss;
    private String kontaktNr;
    private int benachrichtigung;
    private  ArrayList<Taetigkeit> taetigkeiten = new ArrayList<>();
    private  ArrayList<SprachKenntnis> sprachKenntnisList = new ArrayList<>();
    private  ArrayList<ITKenntnis> itKenntnisList = new ArrayList<>();
    private String ausbildung;
    private boolean hasLebenslauf;



    Adresse adresse;


    public Student() {
        super();
        setVorname(vorname);
        setNachname(nachname);
        setGDatum(gDatum);
        setStudiengang(studiengang);
        super.setType("S");
        setAdresse(adresse);
        setBenachrichtigung(benachrichtigung);
        setTaetigkeitenListe(taetigkeiten);
        setItKenntnisList(itKenntnisList);
        setSprachKenntnisList(sprachKenntnisList);
        setLebenslauf(lebenslauf);
    }

    public byte[] getLebenslauf() {
        return lebenslauf;
    }

    public void setLebenslauf(byte[] lebenslauf) {
        this.lebenslauf = lebenslauf;
    }
    public Student(String email,int studentId, String vorname, String nachname, LocalDate gDatum, String studiengang, byte[] picture, byte[] lebenslauf, String abschluss, String kontaktNr, int benachrichtigung, ArrayList<Taetigkeit> taetigkeiten, ArrayList<SprachKenntnis> sprachKenntnisList, ArrayList<ITKenntnis> itKenntnisList, String ausbildung, Adresse adresse,String passwort, ITKenntnis itKenntnis, SprachKenntnis sprachKenntnis) {
        super.setEmail(email);
        this.studentId = studentId;
        this.vorname = vorname;
        this.nachname = nachname;
        this.gDatum = gDatum;
        this.studiengang = studiengang;
        this.picture = picture;
        this.lebenslauf = lebenslauf;
        this.abschluss = abschluss;
        this.kontaktNr = kontaktNr;
        this.benachrichtigung = benachrichtigung;
        this.taetigkeiten = taetigkeiten;
        this.sprachKenntnisList = sprachKenntnisList;
        this.itKenntnisList = itKenntnisList;
        this.ausbildung = ausbildung;
        this.adresse = adresse;
        setITKenntnis(itKenntnis);
        setSprachKenntnis(sprachKenntnis);
        super.setPasswort(passwort);
        setType("S");
    }

    public boolean hasLebenslauf(){
        return hasLebenslauf;
    }

    public void setHasLebenslauf(boolean hasLebenslauf){
        this.hasLebenslauf = hasLebenslauf;
    }
    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public ArrayList<Taetigkeit> getTaetigkeiten() { return taetigkeiten; }
    public void setTaetigkeiten(ArrayList<Taetigkeit> taetigkeiten) { this.taetigkeiten = taetigkeiten; }

    public ArrayList<SprachKenntnis> getSprachKenntnisList() { return sprachKenntnisList; }
    public void setSprachKenntnisList(ArrayList<SprachKenntnis> sprachKenntnisList) { this.sprachKenntnisList = sprachKenntnisList; }

    public ArrayList<ITKenntnis> getItKenntnisList() { return itKenntnisList; }
    public void setItKenntnisList(ArrayList<ITKenntnis> itKenntnisList) { this.itKenntnisList = itKenntnisList; }

    public String getAusbildung() {
        return ausbildung;
    }
    public void setAusbildung(String ausbildung) {
        this.ausbildung = ausbildung;
    }

    public void setBenachrichtigung(int benachrichtigung) {
        this.benachrichtigung = benachrichtigung;
    }

    public byte[] getPicture() { return picture; }
    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getAbschluss() { return abschluss; }
    public void setAbschluss(String abschluss) { this.abschluss = abschluss; }

    public String getKontaktNr() {
        return kontaktNr;
    }
    public void setKontaktNr(String kontaktNr) {
        this.kontaktNr = kontaktNr;
    }

    public Adresse getAdresse() { return adresse; }
    public void setAdresse(Adresse adresse) { this.adresse = adresse; }

    public String getVorname(){
        return vorname;
    }
    public void setVorname(String vorname){
        this.vorname=vorname;
    }

    public String getNachname(){
        return nachname;
    }
    public void setNachname(String nachname){
        this.nachname=nachname;
    }

    public LocalDate getGDatum(){
        return gDatum;
    }
    public void setGDatum(LocalDate gDatum){
        this.gDatum=gDatum;
    }

    public String getStudiengang(){
        return studiengang;
    }
    public void setStudiengang(String studiengang){
        this.studiengang=studiengang;
    }

    public void setEmail(String email){
        super.setEmail(email);
    }
    public void setPasswort(String passwort){ super.setPasswort(passwort);}

    public void setTaetigkeitenListe(ArrayList<Taetigkeit> taetigkeiten) { this.taetigkeiten = taetigkeiten; }

    public Taetigkeit getTaetigkeit() { return taetigkeiten.get(taetigkeiten.size()-1); }
    public void setTaetigkeit(Taetigkeit taetigkeit) {
        if(taetigkeit == null ){return;}
        if(taetigkeiten == null) {
            taetigkeiten = new ArrayList<>();
        }
        taetigkeiten.add(taetigkeit);
    }
    public SprachKenntnis getSprachKenntnis() { return sprachKenntnisList.get(sprachKenntnisList.size()-1); }
    public void setSprachKenntnis(SprachKenntnis sprachKenntnis) {
        if(sprachKenntnis == null){return;}
        if(!(sprachKenntnis.getKenntnis() == null)){
            if(sprachKenntnisList == null) {
                sprachKenntnisList = new ArrayList<>();
            }
            sprachKenntnisList.add(sprachKenntnis);
        }
    }

    public ITKenntnis getITKenntnis() { return itKenntnisList.get(itKenntnisList.size()-1); }
    public void setITKenntnis(ITKenntnis itKenntnis) {
        if(itKenntnis == null){return;}
        if(!(itKenntnis.getKenntnis() == null)) {
            if(itKenntnisList == null) {
                itKenntnisList = new ArrayList<>();
            }
            itKenntnisList.add(itKenntnis);
        }
    }


    public static class ITKenntnis {
        private String kenntnis;
        private String niveau;

        public ITKenntnis() {
            setKenntnis(kenntnis);
            setNiveau(niveau);
        }

        public ITKenntnis(String kenntnis, String niveau) {
            this.kenntnis=kenntnis;
            this.niveau=niveau;
        }


        public String getKenntnis() { return kenntnis; }
        public void setKenntnis(String kenntnis) { this.kenntnis = kenntnis; }
        public String getNiveau() { return this.niveau; }
        public void setNiveau(String niveau) { this.niveau = niveau; }
    }


    public static class SprachKenntnis {
        private String kenntnis;
        private String niveau;


        public SprachKenntnis() {
            setKenntnis(kenntnis);
            setNiveau(niveau);
        }

        public String getKenntnis() { return kenntnis; }
        public void setKenntnis(String kenntnis) { this.kenntnis = kenntnis; }
        public String getNiveau() { return this.niveau; }
        public void setNiveau(String niveau) { this.niveau = niveau; }
    }




    @Override
    public String toString() {
        return "Student{" +
                "student_id=" + studentId +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", g_datum=" + gDatum +
                ", studiengang='" + studiengang + '\'' +
                ", picture=" + Arrays.toString(picture) +
                ", lebenslauf=" + Arrays.toString(lebenslauf) +
                ", abschluss='" + abschluss + '\'' +
                ", kontakt_nr='" + kontaktNr + '\'' +
                ", benachrichtigung=" + benachrichtigung +
                ", taetigkeiten=" + taetigkeiten +
                ", sprachKenntnisList=" + sprachKenntnisList +
                ", itKenntnisList=" + itKenntnisList +
                ", ausbildung='" + ausbildung + '\'' +
                ", adresse=" + adresse +
                '}';
    }
}
