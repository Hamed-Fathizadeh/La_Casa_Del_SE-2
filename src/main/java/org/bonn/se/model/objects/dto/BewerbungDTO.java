package org.bonn.se.model.objects.dto;

import com.vaadin.ui.Image;
import org.bonn.se.services.util.ImageConverter;

import java.sql.Date;
import java.util.Arrays;
import java.util.Objects;

public interface  BewerbungDTO extends AbstractBewerbungDTO{


    boolean isBewerbungMarkiert();

     void setBewerbungMarkiert(boolean bewerbungMarkiert);


     void setStudentGDatum(Date studentGDatum);

     String getStudentStudiengang();

     void setStudentStudiengang(String studentStudiengang);

     String getStudentAusbildung();

     void setStudentAusbildung(String studentAusbildung);

     String getStudentKontaktNr();

     void setStudentKontaktNr(String studentKontaktNr);

     int getStudentBenachrichtigung();

     void setStudentBenachrichtigung(int studentBenachrichtigung) ;

     Image getStudentPicture();

     String getStudentHoesterAbschluss();

     void setStudentHoesterAbschluss(String studentHoesterAbschluss) ;

     String getStudentVorname();

     void setStudentVorname(String studentVorname);

     String getStudentNachname();

     void setStudentNachname(String studentNachname);

     String getUnternehmenHauptsitz();

     void setUnternehmenHauptsitz(String unternehmenHauptsitz) ;

     String getEmailStudent();

     void setEmailStudent(String emailStudent);

     double getRating();

     void setRating(double rating);

     String getTitel();


     String getUnternehmenName();

     void setUnternehmenName(String unternehmenName);

     Image getUnternehmenLogo();


     int getBewerbungID();

     void setBewerbungID(int bewerbungID);

     Date getDatum();

     void setDatum(Date datum);

     String getDescription();

     void setDescription(String description);

     byte[] getLebenslauf() ;

     void setLebenslauf(byte[] lebenslauf) ;

     int getStatus();

     void setStatus(int status);

     int getStudentID();

     void setStudentID(int studentID);

     int getAnzeigeID();

     void setAnzeigeID(int anzeigeID);


    @Override
     String toString() ;

    @Override
     boolean equals(Object o) ;


}
