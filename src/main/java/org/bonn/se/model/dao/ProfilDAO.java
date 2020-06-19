package org.bonn.se.model.dao;

import org.bonn.se.model.objects.entitites.*;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;

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
    public void createStudentProfil1(Student student) throws DatabaseException {

        String sql = "UPDATE lacasa.tab_student SET g_datum = ?,"
                + "studiengang = ?,"
                + "ausbildung = ?,"
                + "kontakt_nr = ?,"
                + "picture = ?,"
                + "lebenslauf = ? ,"
                + "hoester_abschluss = ?"
                + "WHERE email = ?;" +
                "INSERT INTO lacasa.tab_adresse VALUES(DEFAULT,?,?,?,?,?);";
        PreparedStatement statement = getPreparedStatement(sql);
        try {

            statement.setBytes(5,student.getPicture());
            statement.setBytes(6,student.getLebenslauf());
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
            statement.setString(7,student.getAbschluss());
            statement.setString(8,student.getEmail());
            statement.setString(9,student.getAdresse().getStrasse());
            if(!(student.getAdresse().getPlz() == null)) {
                statement.setInt(10, Integer.parseInt(student.getAdresse().getPlz()));
            } else {
                statement.setBigDecimal(10,null);
            }
            if(!(student.getAdresse().getOrt() == null) || !(student.getAdresse().getBundesland() == null)) {
                statement.setString(11, student.getAdresse().getOrt());
                statement.setString(12,student.getAdresse().getBundesland());
            } else {
                statement.setNull(11, Types.VARCHAR);
                statement.setNull(12, Types.VARCHAR);
            }
            statement.setString(13,student.getEmail());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");

        } finally {
            JDBCConnection.getInstance().closeConnection();

        }
    }


    public void createStudentProfil2(Student student) throws DatabaseException {
        try {

            for (Taetigkeit taetigkeit: student.getTaetigkeiten()) {


                String sql = "INSERT INTO lacasa.tab_taetigkeiten VALUES(DEFAULT,?,?,?,(Select tab_student.student_id FROM lacasa.tab_student WHERE email = '" + student.getEmail() + "'));";

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

    public void createStudentProfil3(Student student) throws DatabaseException {
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
            }
            if(student.getSprachKenntnisList() != null){
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

    public void createUnternehmenProfil(Unternehmen unternehmen) throws DatabaseException {
        String sql = "INSERT INTO lacasa.tab_adresse (strasse,plz,ort,bundesland,email) VALUES(?,?,?,?,?);" +
                "UPDATE lacasa.tab_unternehmen SET logo = ?, kontakt_nr = ?, branch_name = ? , " +
                " description = ? WHERE lacasa.tab_unternehmen.email = ?;";

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
            statement.setString(8,unternehmen.getBranche());

            statement.setString(9, unternehmen.getDescription());


            statement.setString(10, unternehmen.getEmail());


            statement.executeUpdate();

        }catch (SQLException ex) {
            ex.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen");
        }finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }




    public Unternehmen getUnternehmenProfil(Unternehmen unternehmen) throws DatabaseException {
        ResultSet set;
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery(" SELECT u.email, u.firmenname, u.hauptsitz, u.logo, u.description ,u.kontakt_nr, " +
                    "u.branch_name, a.strasse,a.plz,a.ort, a.bundesland AS a_bundesland, u.bundesland AS u_bundesland   " +
                    "FROM lacasa.tab_unternehmen AS u JOIN lacasa.tab_adresse AS a" +
                    " ON u.email = a.email\n" +
                    "WHERE u.email ='" +unternehmen.getEmail()+ "'");
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
                unternehmen.setKontaktnummer(set.getString("kontakt_nr"));
                unternehmen.setLogo(set.getBytes("logo"));
                Adresse adresse = new Adresse();
                unternehmen.setAdresse(adresse);

                return unternehmen;
            }


        } catch (SQLException  throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
        return null;
    }

    public Student getStudent(String email) throws DatabaseException {
        ResultSet set;
        ResultSet set2;
        ResultSet set3;
        ResultSet set4;
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT s.*, a.strasse, a.plz, a.ort, a.bundesland, u.vorname, u.nachname, u.benutzertyp\n" +
                    "  FROM lacasa.tab_student s\n" +
                    "  join lacasa.tab_user u\n" +
                    "    on  s.email = u.email\n" +
                    "  left outer join lacasa.tab_adresse a\n" +
                    "    on s.email = a.email WHERE s.email = '" + email + "'");
        } catch (SQLException | DatabaseException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        }
        Student student = new Student();
        try {
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
                Adresse adresse = new Adresse(set.getString("strasse"), String.valueOf(set.getInt("plz")), set.getString("ort"),set.getString("bundesland"));
                student.setAdresse(adresse);
                student.setPicture(set.getBytes("picture"));
                //nur um zu checken ob der student einen lebenslauf hochgeladen hat
                student.setHasLebenslauf(set.getBytes("lebenslauf") != null);

            }
        } catch (SQLException  throwables) {
            throwables.printStackTrace();
        }
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set2 = statement.executeQuery("SELECT t.art, t.beginn_datum, t.end_datum\n" +
                    "  FROM lacasa.tab_taetigkeiten t\n" +
                    "  join lacasa.tab_student s\n" +
                    "    on s.student_id = t.student_id\n" +
                    "WHERE s.email  = '" + email + "'");

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
                    "WHERE s.email  = '" + email + "'");

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
                    "WHERE s.email  = '" + email + "'");
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


    public void updateStudent(Student student) throws DatabaseException {
        String sql = "UPDATE lacasa.tab_user " +
                "SET vorname = ?, nachname = ? " +
                "WHERE email = ?; " +
                "UPDATE lacasa.tab_student " +
                "SET   g_datum = ?, studiengang = ?, ausbildung =?, " +
                "kontakt_nr = ?, picture = ?, lebenslauf = ?, hoester_abschluss = ? WHERE email = ?; " +
                "UPDATE lacasa.tab_adresse SET strasse = ?, plz = ?, ort = ?, bundesland = ? WHERE email = ?;";

        PreparedStatement statement = getPreparedStatement(sql);

        try {

            statement.setString(1,student.getVorname());
            statement.setString(2,student.getNachname());
            statement.setString(3,student.getEmail());
            statement.setDate(4,student.getG_datum() == null ? null : Date.valueOf(student.getG_datum()));
            statement.setString(5,student.getStudiengang());
            statement.setString(6,student.getAusbildung());
            statement.setString(7,student.getKontakt_nr());
            statement.setBytes(8,student.getPicture());
            statement.setBytes(9,student.getLebenslauf());
            statement.setString(10,student.getAbschluss());
            statement.setString(11,student.getEmail());
            statement.setString(12,student.getAdresse().getStrasse());
            statement.setInt(13,student.getAdresse().getPlz() == null ? null : Integer.parseInt(student.getAdresse().getPlz()));

            if(!(student.getAdresse().getOrt() == null) || !(student.getAdresse().getBundesland() == null)) {
                statement.setString(14, student.getAdresse().getOrt());
                statement.setString(15,student.getAdresse().getBundesland());
            } else {
                statement.setNull(14, Types.VARCHAR);
                statement.setNull(15, Types.VARCHAR);
            }

            statement.setString(16,student.getEmail());

            statement.executeUpdate();

        }catch (SQLException ex) {
            ex.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen");
        }finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }



}
