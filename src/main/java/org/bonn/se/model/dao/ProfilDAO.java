package org.bonn.se.model.dao;

import com.vaadin.server.StreamResource;
import com.vaadin.ui.Image;
import org.bonn.se.model.objects.entitites.Adresse;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.Taetigkeit;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;

public class ProfilDAO extends AbstractDAO{

    public static ProfilDAO dao = null;

    private ProfilDAO() {

    }

    public static ProfilDAO getInstance() {
        if (dao == null) {
            dao = new ProfilDAO();
        }
        return dao;
    }
    public static void createStudentProfil1(Student student) throws DatabaseException {

        String sql = "UPDATE lacasa.tab_student SET g_datum = ?,"
                + "studiengang = ?,"
                + "ausbildung = ?,"
                + "kontakt_nr = ?,"
                + "picture = ?,"
                + "hoester_abschluss = ?"
                + "WHERE email = ?;" +
                "INSERT INTO lacasa.tab_adresse VALUES(DEFAULT,?,?,?,?,?);";
        PreparedStatement statement = getPreparedStatement(sql);
        try {

            statement.setBytes(5,student.getPicture());

            if (String.valueOf(student.getG_datum()).equals("null")) {
                assert statement != null;
                statement.setDate(1,null);

            } else{
                Date geburtsdatum = Date.valueOf(student.getG_datum());
                assert statement != null;
                statement.setDate(1,geburtsdatum);
            }

            statement.setString(2,student.getStudiengang());
            statement.setString(3,student.getAusbildung());
            statement.setString(4,student.getKontakt_nr());
            statement.setString(6,student.getAbschluss());
            statement.setString(7,student.getEmail());
            statement.setString(8,student.getAdresse().getStrasse());
            if(!(student.getAdresse().getPlz() == null)) {
                statement.setInt(9, Integer.parseInt(student.getAdresse().getPlz()));
            } else {
                statement.setBigDecimal(9,null);
            }
            if(!(student.getAdresse().getOrt() == null) || !(student.getAdresse().getBundesland() == null)) {
                statement.setString(10, student.getAdresse().getOrt());
                statement.setString(11,student.getAdresse().getBundesland());
            } else {
                statement.setNull(10, Types.VARCHAR);
                statement.setNull(11, Types.VARCHAR);
            }
            statement.setString(12,student.getEmail());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");

        } finally {
            JDBCConnection.getInstance().closeConnection();

        }
    }


    public static void createStudentProfil2(Student student) throws DatabaseException {
        try {

            for (Taetigkeit taetigkeit: student.getTaetigkeitenListe()) {


                String sql = "INSERT INTO lacasa.tab_taetigkeiten VALUES(DEFAULT,?,?,?,(Select tab_student.student_id FROM lacasa.tab_student WHERE email = \'"+ student.getEmail() +"\'));";

                Date begin = null;
                Date ende = null;
                //String sql = "INSERT INTO lacasa.tab_student VALUES(DEFAULT,?,?,?,DEFAULT,DEFAULT,?,?) WHERE email = \'" + email + "\';         INSERT INTO lacasa.tab_adresse VALUES(DEFAULT,?,?,?,?);"  ;
                PreparedStatement statement = getPreparedStatement(sql);
                if(taetigkeit.getBeginn() != null || taetigkeit.getEnde() != null ) {
                    begin = Date.valueOf(taetigkeit.getBeginn());
                    ende = Date.valueOf(taetigkeit.getEnde());
                }
                assert statement != null;
                statement.setString(1, taetigkeit.getTaetigkeitName());
                statement.setDate(2, begin);
                statement.setDate(3, ende);

                statement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }

    }

    public static void createStudentProfil3(Student student) throws DatabaseException {
        try {
            if(student.getItKenntnisList() != null) {
                for (Student.ITKenntnis itKenntnis : student.getItKenntnisList()) {

                    String sql = "INSERT INTO lacasa.tab_it_kenntnisse (kompetenz_name,niveau_it,student_id) " +
                            "VALUES(?,?," +
                            "(SELECT lacasa.tab_student.student_id " +
                            "FROM lacasa.tab_student" +
                            " WHERE lacasa.tab_student.email = ?));";

                    //String sql = "INSERT INTO lacasa.tab_student VALUES(DEFAULT,?,?,?,DEFAULT,DEFAULT,?,?) WHERE email = \'" + email + "\';         INSERT INTO lacasa.tab_adresse VALUES(DEFAULT,?,?,?,?);"  ;
                    PreparedStatement statement = getPreparedStatement(sql);


                    assert statement != null;
                    statement.setString(1, itKenntnis.getKenntnis());
                    statement.setString(2, itKenntnis.getNiveau());
                    statement.setString(3, student.getEmail());


                    statement.executeUpdate();
                }
            }else if(student.getSprachKenntnisList() != null){
            for (Student.SprachKenntnis sprachKenntnis: student.getSprachKenntnisList() ) {

                String sql = "INSERT INTO lacasa.tab_sprachen (sprache, niveau_sprache,student_id) " +
                        "VALUES(?,?" +
                        ",(SELECT lacasa.tab_student.student_id FROM lacasa.tab_student WHERE lacasa.tab_student.email = ?))";
                PreparedStatement statement = getPreparedStatement(sql);

                assert statement != null;
                statement.setString(1, sprachKenntnis.getKenntnis());
                statement.setString(2, sprachKenntnis.getNiveau());
                statement.setString(3, student.getEmail());
                statement.executeUpdate();
            }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }

    }

    public static void createUnternehmenProfil(Unternehmen unternehmen) throws DatabaseException {
        String sql = "INSERT INTO lacasa.tab_adresse (strasse,plz,ort,bundesland,email) VALUES(?,?,?,?,?);" +
                "UPDATE lacasa.tab_unternehmen SET logo = ?, kontakt_nr = ?," +
                "description = ?, branch_name = ? WHERE lacasa.tab_unternehmen.email = ?;";
        PreparedStatement statement = getPreparedStatement(sql);

        try {
            statement.setString(1, unternehmen.getAdresse().getStrasse());
            if (!(unternehmen.getAdresse().getPlz() == null)) {
                statement.setInt(2, Integer.parseInt(unternehmen.getAdresse().getPlz()));
            } else {
                statement.setBigDecimal(2, null);
            }
            if(!(unternehmen.getAdresse().getOrt() == null) || !(unternehmen.getAdresse().getBundesland() == null) ) {
                statement.setString(3, unternehmen.getAdresse().getOrt());
                statement.setString(4,unternehmen.getAdresse().getBundesland());
            } else {
                statement.setNull(3, Types.VARCHAR);
                statement.setNull(4, Types.VARCHAR);
            }
            statement.setString(5, unternehmen.getEmail());
            statement.setBytes(6,unternehmen.getLogo());
            statement.setString(7, unternehmen.getKontaktnummer());
            statement.setString(8, unternehmen.getDescription());

            statement.setString(9,unternehmen.getBranche());

            statement.setString(10, unternehmen.getEmail());


            statement.executeUpdate();

        }catch (SQLException ex) {
            ex.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen");
        }finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }



    public static Student getStudentProfil(Student student) throws DatabaseException {
        ResultSet set;
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery(" Select * FROM lacasa.tab_student WHERE email = \'"+student.getEmail()+"\'");
        } catch (SQLException | DatabaseException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        }
        try {

            while (set.next()) {
                LocalDate localDate = set.getDate("g_datum") == null? null: set.getDate("g_datum").toLocalDate() ;

                student.setG_datum(localDate);
                student.setStudiengang(set.getString("studiengang"));//getInt, get
                student.setAusbildung(set.getString("ausbildung"));
                student.setKontakt_nr(set.getString("kontakt_nr"));
                student.setAbschluss("hoechster_abschluss");
                byte[] bild = set.getBytes("picture"); //Bild nehmen bisschen kompliziert an Tobi hammed

                StreamResource.StreamSource streamSource = new StreamResource.StreamSource() {
                    public InputStream getStream()
                    {
                        return (bild == null) ? null : new ByteArrayInputStream(
                                bild);
                    }
                };

                Image profilbild = new Image(
                        null, new StreamResource(
                        streamSource, "streamedSourceFromByteArray"));
               // student.setPicture(profilbild);


                return student;
            }


        } catch (SQLException  throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
        return null;
    }
    public static Unternehmen getUnternehmenProfil(Unternehmen unternehmen) throws DatabaseException {
        ResultSet set;
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery(" SELECT u.email, u.firmenname, u.hauptsitz, u.logo, u.description ,u.kontakt_nr, " +
                    "u.branch_name, a.strasse,a.plz,a.ort, a.bundesland AS a_bundesland, u.bundesland AS u_bundesland   " +
                    "FROM lacasa.tab_unternehmen AS u JOIN lacasa.tab_adresse AS a" +
                    " ON u.email = a.email\n" +
                    "WHERE u.email =\'"+unternehmen.getEmail()+"\'");
        } catch (SQLException | DatabaseException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        }
        try {

            while (set.next()) {

                unternehmen.setCname(set.getString("firmenname"));
                unternehmen.setHauptsitz(set.getString("hauptsitz"));
                unternehmen.setDescription(set.getString("description"));
                unternehmen.setBundesland(set.getString("u_bundesland"));
                unternehmen.setKontaktnummer(String.valueOf(set.getInt("kontakt_nr")));
                unternehmen.setLogo(set.getBytes("logo"));
                Adresse adresse = new Adresse();
                unternehmen.setAdresse(adresse);
                unternehmen.getAdresse().setOrt(set.getString("ort"));
                unternehmen.getAdresse().setStrasse(set.getString("strasse"));
                unternehmen.getAdresse().setPlz(set.getString("plz"));
                unternehmen.getAdresse().setBundesland(set.getString("a_bundesland"));





                return unternehmen;
            }


        } catch (SQLException  throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
        return null;
    }
    public static Student getStudent(String email) throws DatabaseException {
        ResultSet set;
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT * FROM lacasa.tab_student " +
                    " FULL OUTER JOIN lacasa.tab_user ON lacasa.tab_student.email = lacasa.tab_user.email " +
                    " FULL OUTER JOIN lacasa.tab_taetigkeiten ON lacasa.tab_student.student_id = lacasa.tab_taetigkeiten.student_id" +
                    " FULL OUTER JOIN lacasa.tab_bewerbung ON lacasa.tab_student.student_id = lacasa.tab_bewerbung.student_id" +
                    " FULL OUTER JOIN lacasa.tab_adresse ON lacasa.tab_student.email = lacasa.tab_adresse.email" +
                    " FULL OUTER JOIN lacasa.tab_it_kenntnisse ON lacasa.tab_student.student_id = lacasa.tab_it_kenntnisse.student_id " +
                    " FULL OUTER JOIN lacasa.tab_sprachen ON lacasa.tab_student.student_id = lacasa.tab_sprachen.student_id" +
                    " WHERE lacasa.tab_student.email = \'"+ email + "\'");
        } catch (SQLException | DatabaseException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        }

        Student student = new Student();
        try {

            while (set.next()) {
                student.setVorname(set.getString("vorname"));
                student.setNachname(set.getString("nachname"));
                student.setEmail(set.getString("email"));
                student.setKontakt_nr(set.getString("kontakt_nr"));
                student.setStudiengang(set.getString("studiengang"));
                LocalDate localDate = set.getDate("g_datum") == null? null: set.getDate("g_datum").toLocalDate() ;
                student.setG_datum(localDate);
                student.setAusbildung(set.getString("ausbildung"));
                student.setAbschluss(set.getString("hoester_abschluss"));
                student.setType(set.getString("benutzertyp"));
                Taetigkeit taetigkeit = new Taetigkeit();
                taetigkeit.setTaetigkeitName(set.getString("art"));
                LocalDate beginn = set.getDate("beginn_datum") == null? null: set.getDate("beginn_datum").toLocalDate() ;
                LocalDate ende = set.getDate("end_datum") == null? null: set.getDate("end_datum").toLocalDate() ;

                taetigkeit.setBeginn(beginn);
                taetigkeit.setEnde(ende);
                student.setTaetigkeit(taetigkeit);
                Adresse adresse = new Adresse(set.getString("strasse"),String.valueOf(set.getInt("plz")),set.getString("ort"));
                student.setAdresse(adresse);
                Student.ITKenntnis itKenntnis = new Student.ITKenntnis();
                itKenntnis.setKenntnis(set.getString("kompetenz_name"));
                itKenntnis.setNiveau(set.getString("niveau_it"));
                if(itKenntnis.getKenntnis() != null){
                student.setITKenntnis(itKenntnis);}
                Student.SprachKenntnis sprachKenntnis = new Student.SprachKenntnis();
                sprachKenntnis.setKenntnis(set.getString("sprache"));
                sprachKenntnis.setNiveau(set.getString("niveau_sprache"));
                student.setSprachKenntnis(sprachKenntnis);
                /*
                byte[] bild = set.getBytes("picture");
                Image picture = null;
                if (set.getBytes("picture") == null) {
                    ThemeResource unknownPic = new ThemeResource("images/Unknown.png");
                    picture = new Image("", unknownPic);
                } else {
                    StreamResource.StreamSource streamSource = new StreamResource.StreamSource() {
                        public InputStream getStream() {
                            return (bild == null) ? null : new ByteArrayInputStream(
                                    bild);
                        }
                    };

                    picture = new Image(
                            null, new StreamResource(
                            streamSource, "streamedSourceFromByteArray"));
                }
                student.setPicture(picture);

                 */
             //   student.setPicture(UserDAO.getInstance().getImage(email));

                return student;
            }


        } catch (SQLException  throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
        return null;
    }


    public static Student getStudent2(String email) throws DatabaseException {
        ResultSet set;
        ResultSet set2;
        ResultSet set3;
        ResultSet set4;
        System.out.println("profDAO hier2");
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT s.*, a.strasse, a.plz, a.ort, a.bundesland, u.vorname, u.nachname, u.benutzertyp\n" +
                    "  FROM lacasa.tab_student s\n" +
                    "  join lacasa.tab_user u\n" +
                    "    on  s.email = u.email\n" +
                    "  left outer join lacasa.tab_adresse a\n" +
                    "    on s.email = a.email WHERE s.email = \'"+ email + "\'");
        } catch (SQLException | DatabaseException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        }
        Student student = new Student();
        try {
            System.out.println("profDAO hier2.2");
            while (set.next()) {
                student.setStudent_id(set.getInt("student_id"));
                student.setVorname(set.getString("vorname"));
                student.setNachname(set.getString("nachname"));
                student.setEmail(set.getString("email"));
                student.setKontakt_nr(set.getString("kontakt_nr"));
                student.setStudiengang(set.getString("studiengang"));
                LocalDate localDate = set.getDate("g_datum") == null ? null : set.getDate("g_datum").toLocalDate();
                student.setG_datum(localDate);
                student.setAusbildung(set.getString("ausbildung"));
                student.setAbschluss(set.getString("hoester_abschluss"));
                student.setBenachrichtigung(set.getInt("benachrichtigung"));
                student.setType(set.getString("benutzertyp"));
                Adresse adresse = new Adresse(set.getString("strasse"), String.valueOf(set.getInt("plz")), set.getString("ort"));
                student.setAdresse(adresse);

                student.setPicture(set.getBytes("picture"));

            }
        } catch (SQLException  throwables) {
            throwables.printStackTrace();
        }
        System.out.println("profDAO hier3.3");
        try {
            System.out.println("profDAO hier3");
            Statement statement = JDBCConnection.getInstance().getStatement();
            set2 = statement.executeQuery("SELECT t.art, t.beginn_datum, t.end_datum\n" +
                    "  FROM lacasa.tab_taetigkeiten t\n" +
                    "  join lacasa.tab_student s\n" +
                    "    on s.student_id = t.student_id\n" +
                    "WHERE s.email  = \'"+ email + "\'");

        } catch (SQLException | DatabaseException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        }
        try{

            while (set2.next()) {
                Taetigkeit taetigkeit = new Taetigkeit();
                taetigkeit.setTaetigkeitName(set2.getString("art"));
                LocalDate beginn = set2.getDate("beginn_datum") == null ? null : set2.getDate("beginn_datum").toLocalDate();
                LocalDate ende = set2.getDate("end_datum") == null ? null : set2.getDate("end_datum").toLocalDate();
                System.out.println("profDAO "+taetigkeit.getTaetigkeitName());
                taetigkeit.setBeginn(beginn);
                taetigkeit.setEnde(ende);
                student.setTaetigkeit(taetigkeit);
            }
        } catch (SQLException  throwables) {
            throwables.printStackTrace();
        }
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set3 = statement.executeQuery("SELECT k.kompetenz_name, k.niveau_it \n" +
                    "  FROM lacasa.tab_it_kenntnisse k\n" +
                    "  join lacasa.tab_student s\n" +
                    "    on s.student_id = k.student_id\n" +
                    "WHERE s.email  = \'"+ email + "\'");

        } catch (SQLException | DatabaseException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        }

        try{
            while (set3.next()) {

                Student.ITKenntnis itKenntnis = new Student.ITKenntnis();
                itKenntnis.setKenntnis(set3.getString("kompetenz_name"));
                itKenntnis.setNiveau(set3.getString("niveau_it"));
                if (itKenntnis.getKenntnis() != null) {
                    student.setITKenntnis(itKenntnis);
                }
            }
        } catch (SQLException  throwables) {
            throwables.printStackTrace();
        }
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set4 = statement.executeQuery("SELECT sp.sprache, sp.niveau_sprache\n" +
                    "  FROM lacasa.tab_sprachen sp\n" +
                    "  join lacasa.tab_student s\n" +
                    "    on s.student_id = sp.student_id\n" +
                    "WHERE s.email  = \'"+ email + "\'");
        } catch (SQLException | DatabaseException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        }
        try{
            while (set4.next()) {
                Student.SprachKenntnis sprachKenntnis = new Student.SprachKenntnis();
                sprachKenntnis.setKenntnis(set4.getString("sprache"));
                sprachKenntnis.setNiveau(set4.getString("niveau_sprache"));
                student.setSprachKenntnis(sprachKenntnis);
            }
            return student;
        } catch (SQLException  throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
        return student;
    }

}
