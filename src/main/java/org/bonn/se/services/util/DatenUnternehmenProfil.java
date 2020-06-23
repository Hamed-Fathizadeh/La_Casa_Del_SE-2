package org.bonn.se.services.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class DatenUnternehmenProfil {

    private static final Collection<String> branche1  = new ArrayList<>(Arrays.asList(
            "Administration/Verwaltung", "Automobil", "Banken", "Bau/Architektur", "Beratung/Consulting", "Bildung",
            "Chemie", "Dienstleistung", "Druck/Verpackung", "IT", "Einkauf", "Elektro/Elektronik", "Energie", "Finanz", "Forschung/Entwicklung",
            "Gesundheit/Soziales/Pflege", "Handel", "Handwerk", "Immobilien", "Industrie", "Internet", "Kunst/Kultur", "Marketing/Werbung/PR",
            "Marktforschung", "Maschinenbau", "Medien", "Medizin/Pharma", "Medizintechnik", "Nahrungsmittel/Landwirtschaft", "Personalwesen & -beschaffung"
    ));

    public static Collection<String> getBranche1() {
        return branche1;
    }

    public static Collection<String> getStatus() {
        return status;
    }

    public static Collection<String> getEinstellungsart() {
        return einstellungsart;
    }

    private static final   Collection<String>  status = new ArrayList<>(Arrays.asList(
            "status1",
            "status2"
    ));
    private static final   Collection<String>  einstellungsart  = new ArrayList<>(Arrays.asList(
            "Feste Anstellung",
            "Befristeter Vertrag",
            "Praktikum",
            "Werkstudent",
            "Praktikum/Werkstudent",
            "Trainee",
            "Ausbildung/Studium",
            "Bachelor-/Master-/Diplom-Arbeiten",
            "Promotion/Habilitation",
            "Freie Mitarbeit/Projektmitarbeit"
    ));












}


