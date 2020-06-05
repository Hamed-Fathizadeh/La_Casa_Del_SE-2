package org.bonn.se.model.objects.entitites;
import com.vaadin.ui.Image;
import org.bonn.se.services.util.IllegalException;

import java.util.ArrayList;

public class Unternehmen extends User {
       private int unt_id;
       private String name;
       private String ansprechpartner;
       private String hauptsitz;
       private final ArrayList<Stellenanzeige> stellenanzeigen;
       private Image logo;
       private int mitarbeiteranzahl;
       private int gruendungsjahr;
       private String description;
       private String bundesland;
       private String reichweite;
       private String branche;
       private String kontaktnummer;
       private Adresse adresse;

    public int getGruendungsjahr() {
        return gruendungsjahr;
    }


    public String getBundesland() {
        return bundesland;
    }

    public void setBundesland(String bundesland) {
        this.bundesland = bundesland;
    }


    public void setGruendungsjahr(int gruendungsjahr) {
        this.gruendungsjahr = gruendungsjahr;
    }

    public int getMitarbeiteranzahl() {
        return mitarbeiteranzahl;
    }

    public void setMitarbeiteranzahl(int mitarbeiteranzahl) {
        this.mitarbeiteranzahl = mitarbeiteranzahl;
    }

    public ArrayList<Stellenanzeige> getStellenanzeigen() {
        return stellenanzeigen;
    }

    public Image getLogo() {
        return logo;
    }

    public void setLogo(Image logo) {
        this.logo = logo;
    }



       public Unternehmen() {
           super();
           setUnt_id(unt_id);
           setName(name);
           setAnsprechpartner(ansprechpartner);
           setBundesland(bundesland);
           setDescription(description);
           setMitarbeiteranzahl(mitarbeiteranzahl);
           setHauptsitz(hauptsitz);
           setGruendungsjahr(gruendungsjahr);
           setReichweite(reichweite);
           setLogo(logo);
           super.setType("C");
           stellenanzeigen = new ArrayList<>();
       }
       public int getUnt_id(){
           return unt_id;
       }
       public void setUnt_id(int unt_id){
           int length = String.valueOf(unt_id).length();
           if(length<=0) {
               throw new IllegalException("unt_id field is leer");
           }
           if(length>5){
               throw  new IllegalException("Illegale unt_id");
           }
           else
               this.unt_id=unt_id;
       }
    public Adresse getAdresse(){
        return adresse;
    }
    public void setAdresse(Adresse adresse){
        this.adresse=adresse;
    }
    public String getReichweite(){
        return reichweite;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public String getDescription(){
        return description;
    }
    public void setReichweite(String reichweite){
        this.reichweite=reichweite;
    }

    public String getKontaktnummer(){
        return kontaktnummer;
    }
    public void setKontaktnummer(String kontaktnummer){
        this.kontaktnummer=kontaktnummer;
    }

    public String getBranche(){
        return branche;
    }
    public void setBranche(String branche){
        this.branche=branche;
    }
    public String getName(){
           return name;
       }

    public void setName(String name){
           this.name=name;
       }
    public String getAnsprechpartner(){
           return ansprechpartner;
       }
       public void setAnsprechpartner(String name){
           this.ansprechpartner=ansprechpartner;
       }


       public void setEmail(String email){
           super.setEmail(email);
       }
       public String getHauptsitz() {   return hauptsitz; }
       public void setHauptsitz(String hauptsitz) { this.hauptsitz = hauptsitz; }
       public Stellenanzeige getStellenanzeige() { return stellenanzeigen.get(stellenanzeigen.size()-1); }
       public void setStellenanzeige(Stellenanzeige stellenanzeige) { stellenanzeigen.add(stellenanzeige);}
   }
