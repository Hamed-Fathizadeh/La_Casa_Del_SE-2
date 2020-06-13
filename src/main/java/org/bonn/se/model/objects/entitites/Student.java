package org.bonn.se.model.objects.entitites;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class Student extends User {
    private int student_id;
    private String vorname;
    private String nachname;
    private LocalDate g_datum;
    private String studiengang;
    private byte[] picture = null;
    private byte[] lebenslauf = null;
    private String abschluss;
    private String kontakt_nr;
    private int benachrichtigung;
    private  ArrayList<Taetigkeit> taetigkeiten = new  ArrayList<Taetigkeit>();
    private  ArrayList<SprachKenntnis> sprachKenntnisList =  new  ArrayList<SprachKenntnis>();
    private  ArrayList<ITKenntnis> itKenntnisList = new  ArrayList<ITKenntnis>();
    private String ausbildung;
    Adresse adresse;

    public byte[] getLebenslauf() {
        return lebenslauf;
    }

    public void setLebenslauf(byte[] lebenslauf) {
        this.lebenslauf = lebenslauf;
    }

    public Student() {
        super();
        setVorname(vorname);
        setNachname(nachname);
        setG_datum(g_datum);
        setStudiengang(studiengang);
        super.setType("S");
        setAdresse(adresse);
        setBenachrichtigung(benachrichtigung);
        setTaetigkeitenListe(taetigkeiten);
        setItKenntnisList(itKenntnisList);
        setSprachKenntnisList(sprachKenntnisList);
    }


    public int getStudent_id() {
        return student_id;
    }
    public void setStudent_id(int student_id) {
        this.student_id = student_id;
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

    public int getBenachrichtigung() {
        return benachrichtigung;
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

    public String getKontakt_nr() {
        return kontakt_nr;
    }
    public void setKontakt_nr(String kontakt_nr) {
        this.kontakt_nr = kontakt_nr;
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

    public LocalDate getG_datum(){
        return g_datum;
    }
    public void setG_datum(LocalDate g_datum){
        this.g_datum=g_datum;
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
        if(taetigkeiten == null) {
            taetigkeiten = new ArrayList<>();
            taetigkeiten.add(taetigkeit);
        } else {
            taetigkeiten.add(taetigkeit);
        }
    }
    public SprachKenntnis getSprachKenntnis() { return sprachKenntnisList.get(sprachKenntnisList.size()-1); }
    public void setSprachKenntnis(SprachKenntnis sprachKenntnis) {
        if(!(sprachKenntnis.getKenntnis() == null)){
            if(sprachKenntnisList == null) {
                sprachKenntnisList = new ArrayList<>();
            }
            sprachKenntnisList.add(sprachKenntnis);
        }
    }

    public ITKenntnis getITKenntnis() { return itKenntnisList.get(itKenntnisList.size()-1); }
    public void setITKenntnis(ITKenntnis itKenntnis) {
        if(!(itKenntnis.getKenntnis().equals(null))) {
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

        public SprachKenntnis(String kenntnis, String niveau) {
            this.kenntnis=kenntnis;
            this.niveau=niveau;
        }
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
                "student_id=" + student_id +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", g_datum=" + g_datum +
                ", studiengang='" + studiengang + '\'' +
                ", email='" + super.getEmail() + '\'' +
                ", image=" + Arrays.toString(picture) +
                ", abschluss='" + abschluss + '\'' +
                ", mobil_nr='" + kontakt_nr + '\'' +
                ", taetigkeiten=" + taetigkeiten +
                ", sprachKenntnisList=" + sprachKenntnisList +
                ", itKenntnisList=" + itKenntnisList +
                ", ausbildung='" + ausbildung + '\'' +
                ", adresse=" + adresse +
                '}';
    }




}
