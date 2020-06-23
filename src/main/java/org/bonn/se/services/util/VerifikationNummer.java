package org.bonn.se.services.util;

import java.util.Random;

public class VerifikationNummer {


    private static VerifikationNummer instance;
    private Random r = new Random();

    public static VerifikationNummer getInstance() {
        return instance == null ? instance = new VerifikationNummer() : instance;
    }
    private int randNummer;

    public VerifikationNummer() {
        setRandNummer();
    }


    public void setRandNummer(){
        //Random r = new Random();
        int low =  10000;
        int high = 99999;
        randNummer = r.nextInt(high-low) + low;
    }
    public String getRandNummer(){
        return Integer.toString(randNummer) ;
    }
}
