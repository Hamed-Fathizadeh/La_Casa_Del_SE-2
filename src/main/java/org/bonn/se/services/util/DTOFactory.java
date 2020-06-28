package org.bonn.se.services.util;

import org.bonn.se.model.objects.dto.BewerbungDTO;

import java.sql.Date;

public class DTOFactory {

    public DTOFactory(){

    }

    public static BewerbungDTO createBewerbungDTO(int bewerbungID, Date datum, String description, byte[] lebenslauf, int status, int studentID, int anzeigeID,
                                           String unternehmenName,String unternehmenHauptsitz, byte[] unternehmenLogo,String emailStudent, String s_titel,
                                           java.sql.Date student_g_datum,String student_studiengang, String student_ausbildung,String  student_kontakt_nr,int student_benachrichtigung,
                                           byte[] student_picture,String student_hoester_abschluss,String student_vorname,String student_nachname,double rating, boolean bewerbung_markiert
                                           ) {
            BewerbungDTO bewerbungDTO = new BewerbungDTO( bewerbungID, datum, description, lebenslauf, status, studentID, anzeigeID, unternehmenName,
                    unternehmenLogo,  s_titel,  rating, emailStudent, unternehmenHauptsitz,student_picture
                                                        );
            bewerbungDTO.setStudent_g_datum(student_g_datum);
            bewerbungDTO.setStudent_g_datum(student_g_datum);
            bewerbungDTO.setStudent_studiengang(student_studiengang);
            bewerbungDTO.setStudent_ausbildung(student_ausbildung);
            bewerbungDTO.setStudent_kontakt_nr(student_kontakt_nr);
            bewerbungDTO.setStudent_benachrichtigung(student_benachrichtigung);
            bewerbungDTO.setStudent_hoester_abschluss(student_hoester_abschluss);
            bewerbungDTO.setStudent_vorname(student_vorname);
            bewerbungDTO.setStudent_nachname(student_nachname);
            bewerbungDTO.setBewerbung_markiert(bewerbung_markiert);
            return bewerbungDTO;
    }

    public static BewerbungDTO createBewerbungDTO(int bewerbungID, Date datum, String description, byte[] lebenslauf, int status, int studentID, int anzeigeID,
                                           String unternehmenName,String unternehmenHauptsitz, byte[] unternehmenLogo,String emailStudent, String s_titel, double rating
                                          ){
        BewerbungDTO bewerbungDTO = new BewerbungDTO(bewerbungID, datum, description, lebenslauf, status, studentID, anzeigeID, unternehmenName,
                                                    unternehmenLogo,  s_titel,  rating, emailStudent, unternehmenHauptsitz,null);


        return bewerbungDTO;
    }


}
