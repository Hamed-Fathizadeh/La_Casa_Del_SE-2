package org.bonn.se.model.dao;

import com.vaadin.ui.UI;
import org.bonn.se.gui.window.ConfirmationWindow;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;

import java.sql.*;
import java.time.LocalDate;

public class BewertungDAO extends AbstractDAO {
    public static BewertungDAO dao = null;

    private BewertungDAO() {

    }

    public static BewertungDAO getInstance() {
        if (dao == null) {
            dao = new BewertungDAO();
        }
        return dao;
    }

    public static void bewertung(BewerbungDTO bewerbung) {
        ResultSet set = null;
        Student student = (Student) UI.getCurrent().getSession().getAttribute(Roles.Student);
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
                    throwables.printStackTrace();
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






