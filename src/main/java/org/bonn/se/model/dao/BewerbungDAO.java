package org.bonn.se.model.dao;

import com.vaadin.server.StreamResource;
import com.vaadin.ui.UI;
import org.bonn.se.gui.window.ConfirmationWindow;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Views;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BewerbungDAO extends AbstractDAO{


    private static BewerbungDAO instance;

    public static BewerbungDAO getInstance() {
        return instance == null ? instance = new BewerbungDAO() : instance;
    }

    public static void statusAendern(int bew_id, int status) throws DatabaseException {
        String sql = "update lacasa.tab_bewerbung set status ="+ status + " where bewerbung_id = "+bew_id;
        PreparedStatement statement = getPreparedStatement(sql);

        try {
            statement.executeUpdate();
        } catch (SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        }finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }
    public static void bewerbungLoeschen(BewerbungDTO bewerbung) throws SQLException, DatabaseException {
        String sql = "DELETE FROM lacasa.tab_bewerbung WHERE bewerbung_id = "+bewerbung.getBewerbungID();

        PreparedStatement statement = getPreparedStatement(sql);
        try {
            statement.executeUpdate();
        }catch(NullPointerException | SQLException e){
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, e);
        }finally {
            JDBCConnection.getInstance().closeConnection();
        }

    }

    public static void bewerben(BewerbungDTO bewerbung) throws DatabaseException {
        String sql = "INSERT INTO lacasa.tab_bewerbung (datum, description, lebenslauf, status, student_id, s_anzeige_id)"+
                "select ?,?,?,?,?,? "+
                " WHERE NOT EXISTS( SELECT bewerbung_id from lacasa.tab_bewerbung where student_id = ? and s_anzeige_id = ? and ( status = 1 or status = 9) ) LIMIT 1";

        PreparedStatement statement = getPreparedStatement(sql);
        try {

            statement.setDate(1, Date.valueOf(LocalDate.now()));
            statement.setString(2, bewerbung.getDescription());
            statement.setBytes(3, bewerbung.getLebenslauf());
            statement.setInt(4, bewerbung.getStatus());
            statement.setInt(5, bewerbung.getStudentID());
            statement.setInt(6, bewerbung.getAnzeigeID());
            statement.setInt(7, bewerbung.getStudentID());
            statement.setInt(8, bewerbung.getAnzeigeID());

           if( statement.executeUpdate() == 0){
               ConfirmationWindow confWindow =  new ConfirmationWindow("Ihre Bewerbung ist noch bei der Bearbeitung!");
               UI.getCurrent().addWindow(confWindow);
           }else{
               UI.getCurrent().addWindow(new ConfirmationWindow("Sie haben sich erfolgreich beworben!"));
               UI.getCurrent().getNavigator().navigateTo(Views.StudentHomeView);
           }
        } catch (SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }


    }

    public static boolean markierungAendern(int bew_id) throws DatabaseException{
        ResultSet set;
        boolean bMarkierung= false;

        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("select markiert from lacasa.tab_bewerbung where bewerbung_id = "+bew_id);
            System.out.println("bewDAO"+set.toString());
        } catch (SQLException | DatabaseException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        }

        try {
            while(set.next()){

            bMarkierung =  set.getBoolean(1);
            }

        } catch (SQLException  throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        }

        System.out.println("bewDAO"+bMarkierung );
        String sql = "update lacasa.tab_bewerbung set markiert ="+ !bMarkierung + " where bewerbung_id = "+bew_id;
        PreparedStatement statement = getPreparedStatement(sql);

        try {
            statement.executeUpdate();
        } catch (SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        }finally {
            JDBCConnection.getInstance().closeConnection();
        }

        return !bMarkierung;

    }

    public static void statusNeuBewAendern(int bew_id) throws DatabaseException{

        String sql = "update lacasa.tab_bewerbung set status = 1 where bewerbung_id = "+bew_id +" and status = 9";
        PreparedStatement statement = getPreparedStatement(sql);

        try {
            statement.executeUpdate();
        } catch (SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        }finally {
            JDBCConnection.getInstance().closeConnection();
        }

    }


    public static StreamResource downloadLebenslauf(int student_id) throws DatabaseException {

        ResultSet set;

        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("select lebenslauf from lacasa.tab_student where student_id = " + student_id);
        } catch (SQLException | DatabaseException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        }
        //InputStream targetStream = null;
        try {
            while (set.next()) {
                InputStream   targetStream = new ByteArrayInputStream(set.getBytes(1));

                return  new StreamResource(new StreamResource.StreamSource() {
                    @Override
                    public InputStream getStream() {

                        return targetStream;
                    }
                }, student_id+" Lebenslauf.pdf");
            }
        } catch (SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
        return null;




    }

}
