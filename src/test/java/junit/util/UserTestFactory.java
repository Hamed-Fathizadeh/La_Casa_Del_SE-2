package junit.util;

import org.bonn.se.model.objects.entitites.Adresse;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.Taetigkeit;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.exception.DatabaseException;

import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

public class UserTestFactory {
    Student student;
    Unternehmen unternehmen;
    RandomString randomString = new RandomString(8, ThreadLocalRandom.current());
    RandomString randomSecureString = new RandomString(8, new SecureRandom());

    public UserTestFactory() {
    }

    public Student registerStudent(){
        student = new Student();
        student.setVorname(randomString.nextString());
        student.setNachname(randomString.nextString());
        student.setEmail(randomString.nextEMail());
        student.setPasswort(randomSecureString.nextString());


        System.out.println(student.getVorname());
        System.out.println(student.getNachname());
        System.out.println(student.getPasswort());
        System.out.println(student.getEmail());

        return  student;
    }

    public Student getProfilStudent() throws DatabaseException {
        student = registerStudent();
        student.setG_datum(randomString.nextDate());
        student.setKontakt_nr(randomString.nextString());

        //Adresse
        Adresse adresse = new Adresse();
        adresse.setStrasse(randomString.nextString());
        adresse.setPlz(String.valueOf(randomString.nextInt()));
        adresse.setOrt("Bonn, Nordrhein-Westfalen");
        student.setAdresse(adresse);

        student.setAbschluss("Diplom");
        student.setStudiengang(randomString.nextString());
        for (int i = 0; i < 4; i++) {
            Taetigkeit taetigkeit = new Taetigkeit();
            taetigkeit.setTaetigkeitName(randomString.nextString());
            taetigkeit.setBeginn(randomString.nextDate());
            taetigkeit.setEnde(randomString.nextDate());
            student.setTaetigkeit(taetigkeit);
        }
        System.out.println(student.toString());



        return student;
    }

    public Student getProfilStudentWithoutAdress() throws DatabaseException {
        student = registerStudent();
        student.setG_datum(randomString.nextDate());
        student.setKontakt_nr(randomString.nextString());
        Adresse adresse = new Adresse();
        student.setAdresse(adresse);
        student.setAbschluss("Diplom");
        student.setStudiengang(randomString.nextString());

        System.out.println(student.toString());

        return student;
    }

    public Unternehmen registerUnternehmen(){
        unternehmen = new Unternehmen();
        unternehmen.setCname(randomString.nextString());
        unternehmen.setHauptsitz("Bonn, Nordrhein-Westfalen");
        unternehmen.setVorname(randomString.nextString());
        unternehmen.setNachname(randomString.nextString());
        unternehmen.setEmail(randomString.nextEMail());
        unternehmen.setPasswort("11111111");
        return  unternehmen;
    }

    public Unternehmen getProfilUnternehmen() throws DatabaseException {
        unternehmen = registerUnternehmen();
        unternehmen.setKontaktnummer(String.valueOf(randomString.nextInt()));
//      Adresse
        Adresse adresse = new Adresse();
        adresse.setStrasse(randomString.nextString());
        adresse.setPlz(String.valueOf(randomString.nextInt()));
        adresse.setOrt("Bonn");
        adresse.setBundesland("Nordrhein-Westfalen");
        unternehmen.setAdresse(adresse);
        unternehmen.setBranche("Banken");
        unternehmen.setDescription("Das ist eine Beschreibung");
        unternehmen.setKontaktnummer(String.valueOf(randomString.nextInt()));
        return unternehmen;
    }

    public Unternehmen getProfilUnternehmenWithoutAdresss() throws DatabaseException {
        unternehmen = registerUnternehmen();
        unternehmen.setKontaktnummer(String.valueOf(randomString.nextInt()));

//      Adresse
        Adresse adresse = new Adresse();

        unternehmen.setAdresse(adresse);
        unternehmen.setBranche("Banken");
        return unternehmen;
    }










}
