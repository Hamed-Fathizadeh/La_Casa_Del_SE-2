package org.bonn.se.model.objects.dto;

import com.vaadin.ui.Image;
import org.bonn.se.services.util.ImageConverter;

import java.sql.Date;
import java.util.Arrays;
import java.util.Objects;

public abstract class BewerbungDTO {


 public abstract boolean isBewerbungMarkiert() ;

 public abstract void setBewerbungMarkiert(boolean bewerbungMarkiert);


 public abstract void setStudentGDatum(Date studentGDatum) ;

 public abstract String getStudentStudiengang() ;

 public abstract void setStudentStudiengang(String studentStudiengang) ;

 public abstract String getStudentAusbildung() ;

 public abstract void setStudentAusbildung(String studentAusbildung) ;

 public abstract String getStudentKontaktNr() ;

 public abstract void setStudentKontaktNr(String studentKontaktNr);


 public abstract int getStudentBenachrichtigung() ;

 public abstract void setStudentBenachrichtigung(int studentBenachrichtigung) ;

 public abstract Image getStudentPicture() ;

 public abstract String getStudentHoesterAbschluss() ;

 public abstract void setStudentHoesterAbschluss(String studentHoesterAbschluss) ;

 public abstract String getStudentVorname() ;

 public abstract void setStudentVorname(String studentVorname) ;

 public abstract String getStudentNachname() ;

 public abstract void setStudentNachname(String studentNachname) ;

 public abstract String getUnternehmenHauptsitz() ;

 public abstract void setUnternehmenHauptsitz(String unternehmenHauptsitz) ;

 public abstract String getEmailStudent()  ;

 public abstract void setEmailStudent(String emailStudent)  ;

 public abstract double getRating()  ;

 public abstract void setRating(double rating)  ;

 public abstract String getTitel()  ;


 public abstract String getUnternehmenName()  ;

 public abstract void setUnternehmenName(String unternehmenName)  ;

 public abstract Image getUnternehmenLogo()  ;

 public abstract int getBewerbungID()  ;

 public abstract void setBewerbungID(int bewerbungID)  ;

 public abstract Date getDatum()  ;

 public abstract void setDatum(Date datum)  ;

 public abstract String getDescription()  ;

 public abstract void setDescription(String description)  ;

 public abstract byte[] getLebenslauf()  ;

 public abstract void setLebenslauf(byte[] lebenslauf)  ;

 public abstract int getStatus()  ;

 public abstract void setStatus(int status)  ;

 public abstract int getStudentID()  ;

 public abstract void setStudentID(int studentID)  ;

 public abstract int getAnzeigeID()  ;

 public abstract void setAnzeigeID(int anzeigeID)  ;


 @Override
 public abstract String toString();

 @Override
 public abstract boolean equals(Object o) ;



}
