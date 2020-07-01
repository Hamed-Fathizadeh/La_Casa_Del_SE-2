package org.bonn.se.services.util;

import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.model.objects.dto.BewerbungDTOStepstone2;

import java.sql.Date;

public class ConcreteFactoryStepston2 extends DTOFactory {

    @Override
    public  BewerbungDTOStepstone2 createBewerbungDTO(int bewerbungID, Date datum, String description, byte[] lebenslauf, int status, int studentID, int anzeigeID,
                                                        String unternehmenName, String unternehmenHauptsitz, byte[] unternehmenLogo, String emailStudent, String sTitel,
                                                        java.sql.Date studentGDatum, String studentStudiengang, String studentAusbildung, String  studentKontaktNr, int studentBenachrichtigung,
                                                        byte[] studentPicture, String studentHoesterAbschluss, String studentVorname, String studentNachname, double rating, boolean bewerbungMarkiert
    ) {


        BewerbungDTOStepstone2 bewerbungDTO = new BewerbungDTOStepstone2( bewerbungID,  datum,  description,  lebenslauf,  status,  studentID,  anzeigeID,
                unternehmenName, unternehmenHauptsitz,  unternehmenLogo, emailStudent,  sTitel,
                studentGDatum, studentStudiengang,  studentAusbildung,  studentKontaktNr, studentBenachrichtigung,
                studentPicture, studentHoesterAbschluss, studentVorname, studentNachname, rating,  bewerbungMarkiert
        );
        return bewerbungDTO;
    }


    @Override
    public  BewerbungDTOStepstone2 createBewerbungDTO(int bewerbungID, Date datum, String description, byte[] lebenslauf, int status, int studentID, int anzeigeID,
                                                        String unternehmenName,String unternehmenHauptsitz, byte[] unternehmenLogo,String emailStudent, String sTitel, double rating
    ){
        BewerbungDTOStepstone2 bewerbungDTO = new BewerbungDTOStepstone2(bewerbungID, datum, description, lebenslauf, status, studentID, anzeigeID, unternehmenName,
                unternehmenHauptsitz,  unternehmenLogo, emailStudent,  sTitel,  rating
        );
        return bewerbungDTO;
    }

    @Override
    public BewerbungDTO createBewerbungDTO() {
        return new BewerbungDTOStepstone2();
    }
}


