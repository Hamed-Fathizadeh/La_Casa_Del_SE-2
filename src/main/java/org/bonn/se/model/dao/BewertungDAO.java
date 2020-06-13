package org.bonn.se.model.dao;

import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
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

    public static void bewertung(BewerbungDTO bewerbung) throws DatabaseException, SQLException {
        ResultSet set = null;
        Student student = (Student) UI.getCurrent().getSession().getAttribute(Roles.Student);
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT * "
                    + "FROM lacasa.tab_bewertung "
                    + "WHERE EXISTS (SELECT student_id, firmenname, hauptsitz FROM lacasa.tab_student, lacasa.tab_unternehmen"
                    + "WHERE lacasa.tab_student.student_id = lacasa.tab_bewertung.student.id"
                    + "AND lacasa.tab_unternehmen.firmenname = lacasa.tab_bewertung.firmenname"
                    + "AND lacasa.tab_unternehmen.hauptsitz = lacasa.tab.bewertung.hauptsitz"
                    + "AND upper(lacasa.tab_unternehmen.firmenname) = '" + bewerbung.getUnternehmenName().toUpperCase() + "'"
                    + "AND upper(lacasa.tab_unternehmen.hauptsitz) = '" + bewerbung.getUnternehmenHauptsitz().toUpperCase() + "'"
                    + "AND lacasa.tab_student.student_id =  '" + student.getStudent_id() + "' ) ");
        } catch (SQLException | DatabaseException throwables) {
            throwables.printStackTrace();
            //throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");

        }
        try {
            if (set == null) {
                String sql = "INSERT INTO lacasa.tab_bewertung (datum,anzahl_sterne, firmenname, hauptsitz  ,  student_id) " +
                        "VALUES(?,?,?,?," +
                        "(SELECT lacasa.tab_student.student_id " +
                        "FROM lacasa.tab_student" +
                        " WHERE lacasa.tab_student.email = ?));";

                PreparedStatement statement = getPreparedStatement(sql);

                try {
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
            } //else{
             //   Window subWindow2 = new Window("Bewertung");
              //  VerticalLayout subContent = new VerticalLayout();
             //   subWindow2.setContent(subContent);
             //   subContent.addComponent(new Label("Sie haben bereits eine Bewertung abgegeben! Eine neue Bewertung ist nicht möglich!"));
              //  subWindow2.center();
              //  UI.getCurrent().addWindow(subWindow2);
              //  }



        } catch (DatabaseException e) {
            e.printStackTrace();
        }

    }
}






