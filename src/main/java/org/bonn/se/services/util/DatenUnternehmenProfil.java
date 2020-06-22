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
// --Commented out by Inspection START (22.06.20, 23:36):
// --Commented out by Inspection START (22.06.20, 23:38):
// --Commented out by Inspection START (22.06.20, 23:38):
// --Commented out by Inspection START (22.06.20, 23:38):
////////    public static final   Collection<String>  branche2  = new ArrayList<String>( Arrays.asList(
////////            "Administration/Verwaltung", "Automobil", "Banken", "Bau/Architektur", "Beratung/Consulting", "Bildung",
////////            "Chemie","Dienstleistung","Druck/Verpackung","IT","Einkauf","Elektro/Elektronik","Energie","Finanz","Forschung/Entwicklung",
////////            "Gesundheit/Soziales/Pflege","Handel","Handwerk","Immobilien","Industrie","Internet","Kunst/Kultur","Marketing/Werbung/PR",
////////            "Marktforschung","Maschinenbau","Medien","Medizin/Pharma","Medizintechnik","Nahrungsmittel/Landwirtschaft","Personalwesen & -beschaffung"
////////            ) );
//////// --Commented out by Inspection STOP (22.06.20, 23:36)
//////    public static final   Collection<String>  branche3  = new ArrayList<String>( Arrays.asList(
//////            "Administration/Verwaltung", "Automobil", "Banken", "Bau/Architektur", "Beratung/Consulting", "Bildung",
//////            "Chemie","Dienstleistung","Druck/Verpackung","IT","Einkauf","Elektro/Elektronik","Energie","Finanz","Forschung/Entwicklung",
//////            "Gesundheit/Soziales/Pflege","Handel","Handwerk","Immobilien","Industrie","Internet","Kunst/Kultur","Marketing/Werbung/PR",
//////            "Marktforschung","Maschinenbau","Medien","Medizin/Pharma","Medizintechnik","Nahrungsmittel/Landwirtschaft","Personalwesen & -beschaffung"
//////    ) );
////// --Commented out by Inspection STOP (22.06.20, 23:38)
////    public static final   Collection<String>  reichweite  = new ArrayList<String>( Arrays.asList(
////            "Bundesweit",
////            "DACH-Unternehmen",
////            "Deutschlandweit",
////            "Europaweit",
////            "Weltweit"
////           ) );
//// --Commented out by Inspection STOP (22.06.20, 23:38)
//    public static final   Collection<String>  joboffers = new ArrayList<String>( Arrays.asList(
//            "joboffers1",
//            "joboffers2",
//            "joboffers3",
//            "joboffers4",
//            "joboffers5",
//            "joboffers6"
//    ) );
// --Commented out by Inspection STOP (22.06.20, 23:38)

// --Commented out by Inspection START (22.06.20, 23:38):
//    public static final   Collection<String>  city = new ArrayList<String>( Arrays.asList(
//            "city1",
//            "city2",
//            "city3",
//            "city4",
//            "city5",
//            "city6"
//    ) );
// --Commented out by Inspection STOP (22.06.20, 23:38)

// --Commented out by Inspection START (22.06.20, 23:38):
//    public static final   Collection<String>  einstellungsort = new ArrayList<String>( Arrays.asList(
//            "einstellungsort1",
//            "einstellungsort2",
//            "einstellungsort3",
//            "einstellungsort4",
//            "einstellungsort5",
//            "einstellungsort6"
//    ) );
// --Commented out by Inspection STOP (22.06.20, 23:38)
// --Commented out by Inspection START (22.06.20, 23:36):
//    public static final   Collection<String>  sofort = new ArrayList<String>( Arrays.asList(
//            "now",
//            "not now"
//    ) );
// --Commented out by Inspection STOP (22.06.20, 23:36)

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


