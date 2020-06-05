package org.bonn.se.model.objects.entitites;

import java.time.LocalDate;

public class Taetigkeit {

    private String taetigkeit;
    private LocalDate beginn;
    private LocalDate ende;




    public String getTaetigkeitName() { return taetigkeit; }
    public void setTaetigkeitName(String taetigkeit) { this.taetigkeit = taetigkeit; }
    public LocalDate getBeginn() { return beginn; }
    public void setBeginn(LocalDate beginn) { this.beginn = beginn; }
    public LocalDate getEnde() { return ende; }
    public void setEnde(LocalDate ende) { this.ende = ende; }

    public Taetigkeit(){
        setTaetigkeitName(taetigkeit);
        setBeginn(beginn);
        setEnde(ende);
    }





}
