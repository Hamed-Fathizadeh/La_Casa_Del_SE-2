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
            BewerbungDTO bewerbungDTO = new BewerbungDTO( bewerbungID,  datum,  description,  lebenslauf,  status,  studentID,  anzeigeID,
                                                          unternehmenName, unternehmenHauptsitz,  unternehmenLogo, emailStudent,  s_titel,
                                                          student_g_datum, student_studiengang,  student_ausbildung,  student_kontakt_nr, student_benachrichtigung,
                                                          student_picture, student_hoester_abschluss, student_vorname, student_nachname, rating,  bewerbung_markiert
                                                        );
            return bewerbungDTO;
    }

    public static BewerbungDTO createBewerbungDTO(int bewerbungID, Date datum, String description, byte[] lebenslauf, int status, int studentID, int anzeigeID,
                                           String unternehmenName,String unternehmenHauptsitz, byte[] unternehmenLogo,String emailStudent, String s_titel, double rating
                                          ){
        BewerbungDTO bewerbungDTO = new BewerbungDTO(bewerbungID, datum, description, lebenslauf, status, studentID, anzeigeID, unternehmenName,
                                                    unternehmenHauptsitz,  unternehmenLogo, emailStudent,  s_titel,  rating
                                                    );
        return bewerbungDTO;
    }


}
