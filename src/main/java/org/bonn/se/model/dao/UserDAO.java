package org.bonn.se.model.dao;

import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Image;
import org.bonn.se.control.LoginControl;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.model.objects.entitites.User;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO  extends AbstractDAO {

    public static UserDAO dao = null;

    private UserDAO() {

    }

    public static UserDAO getInstance() {
        if (dao == null) {
            dao = new UserDAO();
        }
        return dao;
    }



    public static User getUser(String email) throws DatabaseException {

        ResultSet set = null;

        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT * "
                    + "FROM lacasa.tab_user "
                    + "WHERE upper(lacasa.tab_user.email) = '" + email.toUpperCase() + "'");
        } catch (SQLException | DatabaseException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        }
        boolean exist;
        User user = null;
        try {

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
            throwables.printStackTrace();
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
        return null;
    }

    public static boolean getUserbyEmail(String email) throws DatabaseException {

        ResultSet set = null;

        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT * "
                    + "FROM lacasa.tab_user "
                    + "WHERE upper(lacasa.tab_user.email) = '" + email.toUpperCase() + "'");
        } catch (SQLException | DatabaseException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        }
        boolean exist;
        try {
            while (set.next()) {
                if (set.getString(1).equalsIgnoreCase(email)) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
        return false;
    }

    public static void registerUser(User user) throws DatabaseException {
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
                String[] sOrt = user.getHauptsitz().toString().split(" - ");

                statement.setString(6, user.getCname());
                statement.setString(7, sOrt[0]);
                statement.setString(8, sOrt[1]);
                statement.setString(9, user.getEmail());
            } else {
                statement.setString(6, user.getEmail());
            }
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }
    public static String getUserType(String email)  {
        ResultSet set;
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT * "
                    + "FROM lacasa.tab_user "
                    + "WHERE lacasa.tab_user.email = '" + email + "'");

            if( set.next()){
                return set.getString(6);
            }
        } catch (SQLException | DatabaseException ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
        }
     return null;
    }
    public static void deleteUser(String email) throws DatabaseException {
        String sql;
        if(Objects.requireNonNull(UserDAO.getUserType(email)).equals("S")) {
            sql = "DELETE FROM lacasa.tab_student WHERE email = '" + email + "'; DELETE FROM lacasa.tab_user WHERE email = '" + email + "'";
        } else {
            sql = "DELETE FROM lacasa.tab_unternehmen WHERE email = '" + email + "'; DELETE FROM lacasa.tab_user WHERE email = '" + email + "'";
        }
        PreparedStatement statement = AbstractDAO.getPreparedStatement(sql);


        try {
            assert statement != null;
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }

    }


    public  Image getImage(String email)  {
        ResultSet set = null;

        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT picture "
                    + "FROM lacasa.tab_student "
                    + "WHERE email = '" + email + "'");
        } catch (SQLException | DatabaseException throwables) {
            throwables.printStackTrace();

        }
        try {
            while (true) {
                assert set != null;
                if (!set.next()) break;
                if(set.getBytes(1) == null) {
                    ThemeResource unknownPic = new ThemeResource("images/Unknown.png");
                    return  new Image("",unknownPic);
                }
                    byte[] bild = set.getBytes(1);



                StreamResource.StreamSource streamSource = new StreamResource.StreamSource() {
                    public InputStream getStream()
                    {
                        return (bild == null) ? null : new ByteArrayInputStream(
                                bild);
                    }
                };
                    return new Image(
                            null, new StreamResource(
                            streamSource, "streamedSourceFromByteArray"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                JDBCConnection.getInstance().closeConnection();
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
        }
        ThemeResource resource = new ThemeResource("img/RegisterStudent/Unknown.png");
        return new Image(null,resource);
    }


    public static Unternehmen getUnternehmenByStellAnz(StellenanzeigeDTO stellenanzeige) throws DatabaseException {
        ResultSet set;
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT * FROM lacasa.tab_unternehmen WHERE firmenname = \'" + stellenanzeige.getFirmenname() + "\'");

            if( set.next()){
                Unternehmen unternehmen = new Unternehmen();
                unternehmen.setCname(set.getString("firmenname"));
                unternehmen.setHauptsitz(set.getString("hauptsitz"));

                byte[] bild = set.getBytes("logo");
                StreamResource.StreamSource streamSource = new StreamResource.StreamSource() {
                    public InputStream getStream()
                    {
                        return (bild == null) ? null : new ByteArrayInputStream(
                                bild);
                    }
                };
                unternehmen.setLogo(new Image(
                        null, new StreamResource(
                        streamSource, "streamedSourceFromByteArray")));
                unternehmen.setMitarbeiteranzahl(set.getInt("mitarbeiterzahl"));
                unternehmen.setGruendungsjahr(set.getInt("gruendungsjahr"));
                unternehmen.setDescription(set.getString("description"));
                unternehmen.setBundesland(set.getString("bundesland"));
                unternehmen.setEmail(set.getString("email"));
                //REICHWEITE FEHLT HIER NOCH


                return unternehmen;
            }
        } catch (SQLException | DatabaseException ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
        return null;
    }


}


