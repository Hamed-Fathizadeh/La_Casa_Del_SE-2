package org.bonn.se.model.objects.entitites;

import org.bonn.se.services.util.IllegalException;

public class Adresse {
    private String strasse ;
    private String plz;
    private String ort;
    private String email;
    private String bundesland;

    public String getBundesland() {
        return bundesland;
    }

    public void setBundesland(String bundesland) {
        this.bundesland = bundesland;
    }

    public Adresse(){
        setStrasse(strasse);
        setPlz(plz);
        setOrt(ort);
        setBundesland(bundesland);

    }

    public Adresse(String strasse, String plz, String ort) {
        this.strasse = strasse;
        this.plz = plz;
        this.ort = ort;
    }

    public Adresse(String strasse, String plz, String ort, String bundesland) {
        this.strasse = strasse;
        this.plz = plz;
        this.ort = ort;
        this.bundesland = bundesland;
    }


    public String getStrasse(){
        return strasse;
    }
    public void setStrasse(String strasse){
        this.strasse=strasse;
    }

    public String getPlz(){
        return plz;
    }

    public void setPlz(String plz) {
        int length = String.valueOf(plz).length();
        if (length > 5) {
            throw new IllegalException("Illegale  plz");
        } else
            this.plz=plz;
    }
    public String getOrt(){
        return ort;
    }
    public void setOrt(String ort){
        this.ort=ort;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }




}
