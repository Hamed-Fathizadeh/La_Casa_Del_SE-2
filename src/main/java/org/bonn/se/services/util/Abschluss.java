package org.bonn.se.services.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Abschluss {
    public static final   Collection<String>  collection  = new ArrayList<String>( Arrays.asList(
            "Staatsexamen",
            "Diplom",
            "Magister",
            "Master",
            "Bachelor",
            "Abitur (allgemeine Hochschulreife)",
            "Fachhochschulreife (allgemeine Fachhochschulreife)") );

    public static final   Collection<String>  reichweite  = new ArrayList<String>( Arrays.asList(
            "r1",
            "r2",
            "r3"
            ) );

    public static final   Collection<String>  bundesland  = new ArrayList<String>( Arrays.asList(
            "Nordrhein-Westfalen","Niedersachsen","Bayern","Rheinland-Pfalz","Hessen",
            "Saarland","Berlin","Brandenburg","Schleswig-Holstein","Mecklenburg-Vorpommern",
            "Thüringen","Sachsen","Sachsen-Anhalt","Bremen","Baden-Württemberg","Hamburg"));

    public static final   Collection<String>  einstellungsart  = new ArrayList<String>( Arrays.asList(
            "Vollzeiz",
            "Teilzeit",
            "Werkstudent",
            "Minijob"
    ) );
}