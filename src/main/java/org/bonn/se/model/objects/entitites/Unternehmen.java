package org.bonn.se.model.objects.entitites;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.services.util.IllegalException;

import java.util.ArrayList;

public class Unternehmen extends User {
       private int unt_id;
       private String hauptsitz;
       private ArrayList<StellenanzeigeDTO> stellenanzeigenDTOliste;
       private byte[] logo;
       private String description;
       private String bundesland;
       private String branche;
       private String kontaktnummer;
       private Adresse adresse;
       private StellenanzeigeDTO stellenanzeigeDTO;


    public StellenanzeigeDTO getStellenanzeigeDTO() { return stellenanzeigenDTOliste.get(stellenanzeigenDTOliste.size()-1); }

    public void setStellenanzeigeDTO(StellenanzeigeDTO stellenanzeigeDTO) { 
        this.stellenanzeigeDTO = stellenanzeigeDTO;
    }

    public String getBundesland() {
        return bundesland;
    }

    public void setBundesland(String bundesland) {
        this.bundesland = bundesland;
    }

    public ArrayList<StellenanzeigeDTO> getStellenanzeigenDTO() {
        return stellenanzeigenDTOliste;
    }

    public byte[] getLogo() {

        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }


    public void setStellenanzeigenDTOliste(ArrayList<StellenanzeigeDTO> stellenanzeigenDTOliste) {
        this.stellenanzeigenDTOliste = stellenanzeigenDTOliste;
    }

    public Unternehmen() {
           super();
           setUnt_id(unt_id);
           getAnsprechpartner();
           setBundesland(bundesland);
           setDescription(description);
           setHauptsitz(hauptsitz);
           setLogo(logo);
           super.setType("C");
           setStellenanzeigenDTOliste(stellenanzeigenDTOliste);
       }

       public void setUnt_id(int unt_id){
           int length = String.valueOf(unt_id).length();
           if(length<=0) {
               throw new IllegalException();
           }
           if(length>5){
               throw  new IllegalException();
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
    public void setDescription(String description){
        this.description=description;
    }
    public String getDescription(){
        return description;
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

    public String getAnsprechpartner(){
        return super.getVorname() + " " + super.getNachname();
       }


       public void setEmail(String email){
           super.setEmail(email);
       }
       public String getHauptsitz() {   return hauptsitz; }
       public void setHauptsitz(String hauptsitz) { this.hauptsitz = hauptsitz; }
       public void setStellenanzeige(StellenanzeigeDTO stellenanzeigeDTO) {
        if (stellenanzeigenDTOliste == null ) {
            stellenanzeigenDTOliste = new ArrayList<>();
        }
           stellenanzeigenDTOliste.add(stellenanzeigeDTO);
       }
}
