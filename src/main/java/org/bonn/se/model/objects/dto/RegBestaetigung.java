package org.bonn.se.model.objects.dto;

public class RegBestaetigung {
    private final boolean accepted;


    public RegBestaetigung(RegistrationResult registrierung ,boolean accepted) {
        this.accepted = accepted;
    }
    public static RegBestaetigung accepted(RegistrationResult reg){
        if(reg.getResult()){
            return new RegBestaetigung(reg,true);
        }
        else
            return new RegBestaetigung(reg,false);
    }
    public boolean isAccepted(){
        return accepted;
    }
}
