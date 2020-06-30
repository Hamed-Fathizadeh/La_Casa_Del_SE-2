package org.bonn.se.services.util;

import org.bonn.se.model.objects.dto.BewerbungDTO;

import java.sql.Date;

public interface DTOFactory {

     BewerbungDTO createBewerbungDTO(int bewerbungID, Date datum, String description, byte[] lebenslauf, int status, int studentID, int anzeigeID,
                                                     String unternehmenName, String unternehmenHauptsitz, byte[] unternehmenLogo, String emailStudent, String sTitel,
                                                     java.sql.Date studentGDatum, String studentStudiengang, String studentAusbildung, String  studentKontaktNr, int studentBenachrichtigung,
                                                     byte[] studentPicture, String studentHoesterAbschluss, String studentVorname, String studentNachname, double rating, boolean bewerbungMarkiert
    );

     BewerbungDTO createBewerbungDTO(int bewerbungID, Date datum, String description, byte[] lebenslauf, int status, int studentID, int anzeigeID,
                                           String unternehmenName, String unternehmenHauptsitz, byte[] unternehmenLogo, String emailStudent, String sTitel, double rating
    );

    BewerbungDTO createBewerbungDTO();


}
