package org.bonn.se.model.dao;

import org.bonn.se.control.LoginControl;
import org.bonn.se.model.objects.dto.StudentDTO;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProfilVerwaltenDAO extends AbstractDAO {

    public static ProfilVerwaltenDAO dao = null;

    public static ProfilVerwaltenDAO getInstance(){
        if(dao == null){
            dao = new ProfilVerwaltenDAO();
        }
        return dao;
    }

    //get daten
    public boolean changeVorname(StudentDTO dto) {
        Statement statement = this.getStatement();
        return true;
    }

    public static String getVorname(String email){
        ResultSet set;
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT * "
                    + "FROM lacasa.tab_user "
                    + "WHERE lacasa.tab_user.vorname = '" + email + "'");


                return set.getString(1);

        } catch (SQLException | DatabaseException ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String getNachname(String email){
        ResultSet set;
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT * "
                    + "FROM lacasa.tab_user "
                    + "WHERE lacasa.tab_user.nachname = '" + email + "'");


            return set.getString(1);

        } catch (SQLException | DatabaseException ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String getEmail (String studentid){
        ResultSet set;
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT * "
                    + "FROM lacasa.tab_student "
                    + "WHERE lacasa.tab_student.email = '" + studentid + "'");


            return set.getString(1);

        } catch (SQLException | DatabaseException ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    public static String getTelnr (String studentid){
        ResultSet set;
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT * "
                    + "FROM lacasa.tab_student "
                    + "WHERE lacasa.tab_student.kontakt_nr = '" + studentid + "'");


            return set.getString(1);

        } catch (SQLException | DatabaseException ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String getAusbildung (String studentid){
        ResultSet set;
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT * "
                    + "FROM lacasa.tab_student "
                    + "WHERE lacasa.tab_student.ausbildung = '" + studentid + "'");


            return set.getString(1);

        } catch (SQLException | DatabaseException ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String getAbschluss (String studentid){
        ResultSet set;
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT * "
                    + "FROM lacasa.tab_student "
                    + "WHERE lacasa.tab_student.hoester_abschluss = '" + studentid + "'");


            return set.getString(1);

        } catch (SQLException | DatabaseException ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String getStudium (String studentid){
        ResultSet set;
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT * "
                    + "FROM lacasa.tab_student "
                    + "WHERE lacasa.tab_student.studiengang = '" + studentid + "'");


            return set.getString(1);

        } catch (SQLException | DatabaseException ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }



}

