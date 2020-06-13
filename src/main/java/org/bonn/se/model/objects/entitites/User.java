package org.bonn.se.model.objects.entitites;

import java.io.Serializable;

public class User implements Serializable {
    private String vorname ;
    private String nachname ;
    private String email ;
    private String passwort ;
    private String cname;
    private String hauptsitz;
    private String type;
    private String bundesland;

    public String getBundesland() {
        return bundesland;
    }

    public void setBundesland(String bundesland) {
        this.bundesland = bundesland;
    }

    public String getHauptsitz() {
        return hauptsitz;
    }

    public void setHauptsitz(String hauptsitz) {
            this.hauptsitz = hauptsitz;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User(){
        setVorname(null);
        setNachname(this.nachname);
        setEmail(this.email);
        setPasswort(this.passwort);
        setCname(this.cname);
        setType(this.type);
        setHauptsitz(this.hauptsitz);
        setBundesland(bundesland);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswort(){
        return passwort;
    }
    public void setPasswort(String passwort){
    /*      Wirft noch Exception
    String regex="[a-z][A-Z][0-9][?*')(#]";
		if(!passwort.matches(regex)) {
            throw new IllegalException(" passwort ist nicht stark!bitte versuchen sie noch mal mit dieser Form [aZ0?*#]");
    }
		else
            this.passwort=passwort;
   }
   */
        this.passwort = passwort;
    }


    public String getVorname() { return vorname; }

    public void setVorname(String vorname) { this.vorname = vorname; }

    public String getCname() { return cname; }

    public void setCname(String cname) { this.cname = cname; }

    public String getNachname() { return nachname; }

    public void setNachname(String nachname) { this.nachname = nachname; }


}
