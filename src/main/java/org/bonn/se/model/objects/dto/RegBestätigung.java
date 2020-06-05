package org.bonn.se.model.objects.dto;

public class RegBestätigung {
    private final boolean accepted;


    public RegBestätigung(RegistrationResult registrierung ,boolean accepted) {
        this.accepted = accepted;
    }
    public static RegBestätigung accepted(RegistrationResult reg){
        if(reg.getResult()){
            return new RegBestätigung(reg,true);
        }
        else
            return new RegBestätigung(reg,false);
    }
    public boolean isAccepted(){
        return accepted;
    }
}
