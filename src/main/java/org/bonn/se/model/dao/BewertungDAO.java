package org.bonn.se.model.dao;

import com.vaadin.ui.UI;
import org.bonn.se.gui.component.Bewerbungen;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.model.objects.entitites.Bewerbung;
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

    public static void bewertung(Bewerbungen bewerbung) throws DatabaseException, SQLException {
        ResultSet set;
        Student student = (Student) UI.getCurrent().getSession().getAttribute(Roles.Student);

        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT * "
                    + "FROM lacasa.tab_bewertung"
                    + "WHERE lacasa.tab_bewertung.firmenname NOT EXISTS"
                    + "  ( SELECT *  "
                    + "FROM laca.tab.bewertung"
                    + "WHERE lacasa.tab.hauptsitz IS NOT NULL "
                    + " AND lacasa.tab.bewertung.student_id = '" + student.getStudent_id() + "')");
        } catch (SQLException | DatabaseException throwables) {
            throwables.printStackTrace();
        }
        //if(set.next()){

        String sql = "INSERT INTO lacasa.tab_bewertung (datum,anzahl_sterne, firmenname, hauptsitz  ,  student_id) " +
                "VALUES(?,?,?,?," +
                "(SELECT lacasa.tab_student.student_id " +
                "FROM lacasa.tab_student" +
                " WHERE lacasa.tab_student.email = ?));";

        PreparedStatement statement = getPreparedStatement(sql);


        assert statement != null;
         statement.setDate(1, Date.valueOf(LocalDate.now()));
        //statement.setDouble(2, bewerbung.rating.getValue() );
        // statement.setString(3, container.getListe().);
        // statement.setString(4, );
         statement.setString(5, student.getEmail());
        statement.executeUpdate();


    }



    }


