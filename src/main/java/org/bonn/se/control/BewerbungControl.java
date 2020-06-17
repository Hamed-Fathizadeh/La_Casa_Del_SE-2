package org.bonn.se.control;

import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import org.bonn.se.model.dao.BewerbungDAO;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BewerbungControl {

    public static void bewerbungLoeschen(BewerbungDTO bewerbung) throws DatabaseException {
        try {
            BewerbungDAO.bewerbungLoeschen(bewerbung);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Die Bewerbung ist nicht vorhanden!");
        }

    }

    public static void bewerben(BewerbungDTO bewerbung) throws DatabaseException {
        try {
            BewerbungDAO.bewerben(bewerbung);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new DatabaseException("Die Anzeige wurde gelöscht!.");
        }
    }

    public static void statusAendern(int bew_id, int status){
        try{
            BewerbungDAO.statusAendern( bew_id, status);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    public static boolean markierungAendern(int bew_id) throws DatabaseException {
        return BewerbungDAO.markierungAendern(bew_id);
    }

    public static void statusNeuBewAendern(int bew_id)  {
           try{
               BewerbungDAO.statusNeuBewAendern( bew_id);
            } catch (DatabaseException e) {
                e.printStackTrace();
            }

    }

    public static StreamResource downloadLebenslauf(int student_id) throws DatabaseException {
        return BewerbungDAO.downloadLebenslauf(student_id);
    }

    public static void checkDeletedAnzeige() throws DatabaseException, SQLException {

        Statement statement = JDBCConnection.getInstance().getStatement();

        try {
            ResultSet set =  statement.executeQuery(
                    "SELECT datum " +
                    " FROM lacasa.tab_bewerbung" +
                    " WHERE (student_id = (SELECT student_id FROM lacasa.tab_student WHERE email = '" +
                    ((Student) UI.getCurrent().getSession().getAttribute(Roles.Student)).getEmail() + "')" +
                    " AND s_anzeige_id = '-1')");

            if (set.next() == false) {
                return;
            } else {
                String msg = "<p>Die Stellenanzeige/n deiner Bewerbung/en vom <br>";

                do {
                    msg = msg + "<b>" + set.getDate("datum") + "</b><br>";
            } while (set.next());
                  msg = msg + " wurde gelöscht. Somit wurde deine Bewerbung/en mitgelöscht.</p>";

                deleteBewerbungAfterAnzeige();

                Notification notification = new Notification("Information", msg, Notification.TYPE_WARNING_MESSAGE);
                notification.setHtmlContentAllowed(true);
                notification.setDelayMsec(Notification.DELAY_FOREVER);
                notification.setHtmlContentAllowed(true);
                notification.addCloseListener(e -> notification.close());
                notification.show(Page.getCurrent());
                return;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }

    }

    public static void deleteBewerbungAfterAnzeige() throws DatabaseException, SQLException {

        Statement statement = JDBCConnection.getInstance().getStatement();

        try {
            statement.execute("DELETE FROM lacasa.tab_bewerbung " +
                    " WHERE s_anzeige_id = '-1' AND student_id  = " +
                    " (SELECT student_id FROM lacasa.tab_student WHERE email = '"
                    + ((Student) UI.getCurrent().getSession().getAttribute(Roles.Student)).getEmail() + "')");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }
}
