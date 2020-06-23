package org.bonn.se.control;

import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnzStatusControl {
  private  AnzStatusControl(){

  }

        public static void changeStatus (StellenanzeigeDTO stellenanzeige)  {


            try {
                Statement statement = JDBCConnection.getInstance().getStatement();

                statement.executeUpdate("UPDATE lacasa.tab_stellen_anzeige " +
                        "SET status = '" +stellenanzeige.getStatus()+ "' WHERE s_anzeige_id = '" +stellenanzeige.getId()+ "'");
                } catch (DatabaseException e) {
                    Logger.getLogger(AnzStatusControl.class.getName()).log(Level.SEVERE, null, e);
                } catch (SQLException throwables) {
                 Logger.getLogger(AnzStatusControl.class.getName()).log(Level.SEVERE, null, throwables);
            } finally {
                try {
                    JDBCConnection.getInstance().closeConnection();
                } catch (DatabaseException e) {
                    Logger.getLogger(AnzStatusControl.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
}
