package org.bonn.se.model.dao;

import com.vaadin.ui.UI;
import org.bonn.se.gui.window.ConfirmationWindow;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BewertungDAO extends AbstractDAO {


    private static BewertungDAO instance;

    public static BewertungDAO getInstance() {
        return instance == null ? instance = new BewertungDAO() : instance;
    }

    public void bewertung(BewerbungDTO bewerbung) {
        ResultSet set = null;


        try {
            Statement statement = JDBCConnection.getInstance().getStatement();

            set = statement.executeQuery("SELECT * \n" +
                    "  FROM lacasa.tab_bewertung\n" +
                    " where student_id = "+bewerbung.getStudentID()+" \n" +
                    "   and firmenname = '"+bewerbung.getUnternehmenName()+"' and hauptsitz = '"+bewerbung.getUnternehmenHauptsitz()+"'");
        } catch (SQLException | DatabaseException throwables) {
            throwables.printStackTrace();
          }
        try {
            while (!set.next()) {
                String sql = "INSERT INTO lacasa.tab_bewertung (datum, anzahl_sterne, firmenname, hauptsitz, student_id) " +
                        "VALUES(?,?,?,?,?);";
                PreparedStatement statement = getPreparedStatement(sql);
                try {
                    assert statement != null;
                    statement.setDate(1, Date.valueOf(LocalDate.now()));
                    statement.setDouble(2, bewerbung.getRating());
                    statement.setString(3, bewerbung.getUnternehmenName());
                    statement.setString(4, bewerbung.getUnternehmenHauptsitz());
                    statement.setInt(5, bewerbung.getStudentID());
                    statement.executeUpdate();

                } catch (SQLException throwables) {
                    Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
                    throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
                } finally {
                    JDBCConnection.getInstance().closeConnection();
                }
                UI.getCurrent().addWindow(new ConfirmationWindow("Vielen Dank für Ihre bewertung!"));
                return;
            }

        } catch (DatabaseException | SQLException e) {
            e.printStackTrace();
        }
        UI.getCurrent().addWindow(new ConfirmationWindow("Sie haben schon für "+bewerbung.getUnternehmenName()+" bewertet!"));

    }
}






