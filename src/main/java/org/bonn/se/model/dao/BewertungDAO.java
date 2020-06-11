package org.bonn.se.model.dao;

import com.vaadin.ui.UI;
import org.bonn.se.gui.component.Bewerbungen;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.model.objects.entitites.Bewerbung;
import org.bonn.se.model.objects.entitites.Bewertung;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.DeletFile;
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

    public static void bewertung(BewerbungDTO bewerbung) throws DatabaseException, SQLException {


        Student student = (Student) UI.getCurrent().getSession().getAttribute(Roles.Student);

        String sql = "INSERT INTO lacasa.tab_bewertung (datum,anzahl_sterne, firmenname, hauptsitz  ,  student_id) " +
                    "VALUES(?,?,?,?," +
                    "(SELECT lacasa.tab_student.student_id " +
                    "FROM lacasa.tab_student" +
                    " WHERE lacasa.tab_student.email = ?));";

            PreparedStatement statement = getPreparedStatement(sql);

            try{
            assert statement != null;
            statement.setDate(1, Date.valueOf(LocalDate.now()));
            statement.setDouble(2, bewerbung.getRating());
            statement.setString(3, bewerbung.getUnternehmenName());
            statement.setString(4, bewerbung.getUnternehmenHauptsitz());
            statement.setString(5, student.getEmail());
            statement.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
                throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
            } finally {
                JDBCConnection.getInstance().closeConnection();
            }

        }

}






