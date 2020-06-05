package org.bonn.se.control;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import org.bonn.se.control.exception.NoSuchUserOrPassword;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.model.dao.ProfilDAO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.Stellenanzeige;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.model.objects.entitites.User;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AnzStatusControl {


        public static void changeStatus (StellenanzeigeDTO stellenanzeige) throws DatabaseException {

            ResultSet set;

            try {
                Statement statement = JDBCConnection.getInstance().getStatement();

                set = statement.executeQuery("UPDATE lacasa.tab_stellen_anzeige " +
                        "SET status = \'"+stellenanzeige.getStatus()+"' WHERE s_anzeige_id = \'"+stellenanzeige.getId()+"\'");
            } catch (SQLException | DatabaseException throwables) {
                throwables.printStackTrace();
                throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
            } finally {
                JDBCConnection.getInstance().closeConnection();
            }
        }
}
