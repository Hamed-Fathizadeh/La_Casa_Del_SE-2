package org.bonn.se.model.dao;

import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.model.objects.entitites.User;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class BewerbungDAO extends AbstractDAO{
    public static BewerbungDAO dao = null;

    private BewerbungDAO() {

    }

    public static BewerbungDAO getInstance() {
        if (dao == null) {
            dao = new BewerbungDAO();
        }
        return dao;
    }

    public static void bewerben(BewerbungDTO bewerbung) throws DatabaseException, SQLException {
        String sql = "INSERT INTO lacasa.tab_bewerbung (datum, description, lebenslauf, status, student_id, s_anzeige_id)"+
                "VALUES(?,?,?,?,?,?)";

        PreparedStatement statement = getPreparedStatement(sql);
        try {
            System.out.println("BewerbDAO hier1"+ bewerbung.getLebenslauf());
            statement.setDate(1, Date.valueOf(LocalDate.now()));
            statement.setString(2, bewerbung.getDescription());
            statement.setBytes(3, bewerbung.getLebenslauf());
            statement.setInt(4, bewerbung.getStatus());
            statement.setInt(5, bewerbung.getStudentID());
            statement.setInt(6, bewerbung.getAnzeigeID());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
        System.out.println("BewerbDAO gespeichert");



    }
}
