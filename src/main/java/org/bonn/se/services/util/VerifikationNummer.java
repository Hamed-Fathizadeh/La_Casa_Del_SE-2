package org.bonn.se.services.util;

import java.util.Random;

public class VerifikationNummer {

    public static VerifikationNummer vNummer = null;
    public int randNummer;

    public VerifikationNummer() {
        setRandNummer();
    }

    public static VerifikationNummer getInstance() {
        if (vNummer == null) {
            vNummer = new VerifikationNummer();
        }
        return vNummer;
    }

    public void setRandNummer(){
        Random r = new Random();
        int low =  10000;
        int high = 99999;
        randNummer = r.nextInt(high-low) + low;
    }
    public String getRandNummer(){
        return Integer.toString(randNummer) ;
    }
}
