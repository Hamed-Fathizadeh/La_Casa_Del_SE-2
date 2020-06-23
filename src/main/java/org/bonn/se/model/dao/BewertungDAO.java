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

    public void bewertung(BewerbungDTO bewerbung) throws DatabaseException, SQLException {
        ResultSet set = null;


        try {
            Statement statement = JDBCConnection.getInstance().getStatement();

            set = statement.executeQuery("SELECT * \n" +
                    "  FROM lacasa.tab_bewertung\n" +
                    " where student_id = "+bewerbung.getStudentID()+" \n" +
                    "   and firmenname = '"+bewerbung.getUnternehmenName()+"' and hauptsitz = '"+bewerbung.getUnternehmenHauptsitz()+"'");

            while (!set.next()) {
                String sql = "INSERT INTO lacasa.tab_bewertung (datum, anzahl_sterne, firmenname, hauptsitz, student_id) " +
                        "VALUES(?,?,?,?,?);";
                PreparedStatement preparedStatement = getPreparedStatement(sql);

                assert statement != null;
                preparedStatement.setDate(1, Date.valueOf(LocalDate.now()));
                preparedStatement.setDouble(2, bewerbung.getRating());
                preparedStatement.setString(3, bewerbung.getUnternehmenName());
                preparedStatement.setString(4, bewerbung.getUnternehmenHauptsitz());
                preparedStatement.setInt(5, bewerbung.getStudentID());
                preparedStatement.executeUpdate();
            }
                } catch (SQLException throwables) {
                    Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
                    throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
                } finally {
                    set.close();
                    JDBCConnection.getInstance().closeConnection();
                }
                UI.getCurrent().addWindow(new ConfirmationWindow("Vielen Dank für Ihre bewertung!"));
                  UI.getCurrent().addWindow(new ConfirmationWindow("Sie haben schon für "+bewerbung.getUnternehmenName()+" bewertet!"));

                     return;
            }


 }







