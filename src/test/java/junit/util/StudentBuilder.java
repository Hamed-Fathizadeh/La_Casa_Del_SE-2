package junit.util;

import org.bonn.se.model.objects.entitites.Adresse;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.Taetigkeit;

import java.time.LocalDate;
import java.util.ArrayList;

public class StudentBuilder {
    private String email;
    private int student_id;
    private String vorname;
    private String nachname;
    private LocalDate g_datum;
    private String studiengang;
    private byte[] picture;
    private byte[] lebenslauf;
    private String abschluss;
    private String kontakt_nr;
    private int benachrichtigung;
    private ArrayList<Taetigkeit> taetigkeiten;
    private ArrayList<Student.SprachKenntnis> sprachKenntnisList;
    private ArrayList<Student.ITKenntnis> itKenntnisList;
    private String ausbildung;
    private Adresse adresse;
    private String passwort;
    private Student.ITKenntnis itKenntnis;
    private Student.SprachKenntnis sprachKenntnis;

    public StudentBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public StudentBuilder withStudent_id(int student_id) {
        this.student_id = student_id;
        return this;
    }

    public StudentBuilder withVorname(String vorname) {
        this.vorname = vorname;
        return this;
    }

    public StudentBuilder withNachname(String nachname) {
        this.nachname = nachname;
        return this;
    }

    public StudentBuilder withG_datum(LocalDate g_datum) {
        this.g_datum = g_datum;
        return this;
    }

    public StudentBuilder withStudiengang(String studiengang) {
        this.studiengang = studiengang;
        return this;
    }

    public StudentBuilder withPicture(byte[] picture) {
        this.picture = picture;
        return this;
    }

    public StudentBuilder withLebenslauf(byte[] lebenslauf) {
        this.lebenslauf = lebenslauf;
        return this;
    }

    public StudentBuilder withAbschluss(String abschluss) {
        this.abschluss = abschluss;
        return this;
    }

    public StudentBuilder withKontakt_nr(String kontakt_nr) {
        this.kontakt_nr = kontakt_nr;
        return this;
    }

    public StudentBuilder withBenachrichtigung(int benachrichtigung) {
        this.benachrichtigung = benachrichtigung;
        return this;
    }

    public StudentBuilder withTaetigkeiten(ArrayList<Taetigkeit> taetigkeiten) {
        this.taetigkeiten = taetigkeiten;
        return this;
    }

    public StudentBuilder withSprachKenntnisList(ArrayList<Student.SprachKenntnis> sprachKenntnisList) {
        this.sprachKenntnisList = sprachKenntnisList;
        return this;
    }

    public StudentBuilder withItKenntnisList(ArrayList<Student.ITKenntnis> itKenntnisList) {
        this.itKenntnisList = itKenntnisList;
        return this;
    }

    public StudentBuilder withAusbildung(String ausbildung) {
        this.ausbildung = ausbildung;
        return this;
    }

    public StudentBuilder withAdresse(Adresse adresse) {
        this.adresse = adresse;
        return this;
    }

    public StudentBuilder withPasswort(String passwort) {
        this.passwort = passwort;
        return this;
    }

    public StudentBuilder withItKenntnis(Student.ITKenntnis itKenntnis) {
        this.itKenntnis = itKenntnis;
        return this;
    }

    public StudentBuilder withSprachKenntnis(Student.SprachKenntnis sprachKenntnis) {
        this.sprachKenntnis = sprachKenntnis;
        return this;
    }

    public Student createStudent() {
        return new Student(email, student_id, vorname, nachname, g_datum, studiengang, picture, lebenslauf, abschluss, kontakt_nr, benachrichtigung, taetigkeiten, sprachKenntnisList, itKenntnisList, ausbildung, adresse, passwort, itKenntnis, sprachKenntnis);
    }
}