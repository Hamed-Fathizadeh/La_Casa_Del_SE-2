package org.bonn.se.model.dao;

import org.bonn.se.control.LoginControl;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.model.objects.entitites.User;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO  extends AbstractDAO {

    private static UserDAO instance;

    public static UserDAO getInstance() {
        return instance == null ? instance = new UserDAO() : instance;
    }


    public User getUser(String email) throws DatabaseException, SQLException {

        ResultSet set = null;
        Statement statement = JDBCConnection.getInstance().getStatement();

        try {
            set = statement.executeQuery("SELECT * "
                    + "FROM lacasa.tab_user "
                    + "WHERE upper(lacasa.tab_user.email) = '" + email.toUpperCase() + "'");

        User user = null;

            while (set.next()) {

                user = new User();
                user.setEmail(set.getString(1));
                user.setPasswort(set.getString(2));
                user.setVorname(set.getString(3));
                user.setNachname(set.getString(4));
                user.setType(set.getString(5));
            }

            return user;
        } catch (SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            assert set != null;
            set.close();
            JDBCConnection.getInstance().closeConnection();
        }
        return null;
    }

    public boolean getUserbyEmail(String email) throws DatabaseException, SQLException {

        ResultSet set = null;
        Statement statement = JDBCConnection.getInstance().getStatement();

        try {
            set = statement.executeQuery("SELECT * "
                    + "FROM lacasa.tab_user "
                    + "WHERE upper(lacasa.tab_user.email) = '" + email.toUpperCase() + "'");

            while (set.next()) {
                return set.getString(1).equalsIgnoreCase(email);
            }
        } catch (SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            assert set != null;
            set.close();
            JDBCConnection.getInstance().closeConnection();
        }
        return false;
    }

    public void registerUser(User user) throws DatabaseException {
        String sql;

        if (user.getType().equals("S")) {
             sql = "INSERT INTO lacasa.tab_user VALUES(?,?,?,?,?); INSERT INTO lacasa.tab_student (email) VALUES(?);";
        } else{
            sql = "INSERT INTO lacasa.tab_user VALUES(?,?,?,?,?); INSERT INTO lacasa.tab_unternehmen (firmenname,hauptsitz,bundesland,email) VALUES(?,?,?,?);";
        }
        PreparedStatement statement = AbstractDAO.getPreparedStatement(sql);


        try {
            assert statement != null;
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPasswort());
            statement.setString(3, user.getVorname());
            statement.setString(4, user.getNachname());
            statement.setString(5, user.getType());
            if(user.getType().equals("C")) {

                statement.setString(6, user.getCname());
                statement.setString(7, user.getHauptsitz());
                statement.setString(8, user.getBundesland());
                statement.setString(9, user.getEmail());
            } else {
                statement.setString(6, user.getEmail());
            }
            statement.executeUpdate();
        } catch (SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }
    public String getUserType(String email) throws DatabaseException, SQLException {
        ResultSet set = null;
        Statement statement = JDBCConnection.getInstance().getStatement();

        try {
            set = statement.executeQuery("SELECT * "
                    + "FROM lacasa.tab_user "
                    + "WHERE lacasa.tab_user.email = '" + email + "'");

            if( set.next()){
                return set.getString("benutzertyp");
            }
        } catch (SQLException throwables) {

            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
            throw new DatabaseException("UserTyp nicht vorhanden");
        } finally {
            assert set != null;
            set.close();
            JDBCConnection.getInstance().closeConnection();
        }
     return null;
    }
    public static void deleteUser(User user) throws DatabaseException, SQLException {
        String sql;
        if(UserDAO.getInstance().getUserType(user.getEmail()).equals("S")) {
            sql = "DELETE FROM lacasa.tab_user WHERE email = '" + user.getEmail() + "'";
        } else {
            sql = "UPDATE lacasa.tab_bewerbung SET s_anzeige_id = '-1'\n" +
                    " WHERE lacasa.tab_bewerbung.s_anzeige_id in(\n" +
                    "select s_anzeige_id\n" +
                    "  from lacasa.tab_stellen_anzeige \n" +
                    " where firmenname = '" + user.getCname() + "' and hauptsitz = '" + user.getHauptsitz()+ "');  DELETE FROM lacasa.tab_user WHERE email = '" + user.getEmail() + "'";
        }
        PreparedStatement statement = AbstractDAO.getPreparedStatement(sql);


        try {
            assert statement != null;
            statement.executeUpdate();

        } catch (SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }

    }

    public static Unternehmen getUnternehmenByStellAnz(StellenanzeigeDTO stellenanzeige) throws DatabaseException, SQLException {
        ResultSet set = null;
        Statement statement = JDBCConnection.getInstance().getStatement();

        try {
            set = statement.executeQuery("SELECT * FROM lacasa.tab_unternehmen " +
                    "JOIN lacasa.tab_user " +
                    "USING(email)" +
                    "WHERE firmenname = '" + stellenanzeige.getFirmenname() + "'");

            if( set.next()){
                Unternehmen unternehmen = new Unternehmen();
                unternehmen.setCname(set.getString("firmenname"));
                unternehmen.setHauptsitz(set.getString("hauptsitz"));
                unternehmen.setLogo(set.getBytes("logo"));
                unternehmen.setDescription(set.getString("description"));
                unternehmen.setBundesland(set.getString("bundesland"));
                unternehmen.setEmail(set.getString("email"));
                unternehmen.setVorname(set.getString("vorname"));
                unternehmen.setNachname(set.getString("nachname"));
                unternehmen.setKontaktnummer(set.getString("kontakt_nr"));
                unternehmen.setBranche(set.getString("branch_name"));

                return unternehmen;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            assert set != null;
            set.close();
            JDBCConnection.getInstance().closeConnection();
        }
        return null;
    }


}


