package org.bonn.se.control;

import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.SQLException;
import java.sql.Statement;

public class AnzStatusControl {


        public static void changeStatus (StellenanzeigeDTO stellenanzeige) throws DatabaseException {


            try {
                Statement statement = JDBCConnection.getInstance().getStatement();

                statement.executeUpdate("UPDATE lacasa.tab_stellen_anzeige " +
                        "SET status = \'"+stellenanzeige.getStatus()+"' WHERE s_anzeige_id = \'"+stellenanzeige.getId()+"\'");
            } catch (SQLException | DatabaseException throwables) {
                throwables.printStackTrace();
                throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
            } finally {
                JDBCConnection.getInstance().closeConnection();
            }
        }
}
