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
import java.util.logging.Level;
import java.util.logging.Logger;

public class BewerbungControl {

    private BewerbungControl(){

    }

    public static void bewerbungLoeschen(BewerbungDTO bewerbung) throws DatabaseException {
            BewerbungDAO.getInstance().bewerbungLoeschen(bewerbung);
    }

    public static void bewerben(BewerbungDTO bewerbung) throws DatabaseException {
        try {
            BewerbungDAO.getInstance().bewerben(bewerbung);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new DatabaseException("Die Anzeige wurde gelöscht!.");
        }
    }

    public static void statusAendern(int bewId, int status){
        try{
            BewerbungDAO.getInstance().statusAendern( bewId, status);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    public static boolean markierungAendern(int bewId) throws DatabaseException {
        return BewerbungDAO.getInstance().markierungAendern(bewId);
    }

    public static void statusNeuBewAendern(int bewId)  {
           try{
               BewerbungDAO.getInstance().statusNeuBewAendern( bewId);
            } catch (DatabaseException e) {
                e.printStackTrace();
            }

    }

    public static StreamResource downloadLebenslauf(int studentId) throws DatabaseException {
        return BewerbungDAO.getInstance().downloadLebenslauf(studentId);
    }

    public static void checkDeletedAnzeige() throws DatabaseException {

        Statement statement = JDBCConnection.getInstance().getStatement();

        try {
            ResultSet set =  statement.executeQuery(
                    "SELECT datum " +
                    " FROM lacasa.tab_bewerbung" +
                    " WHERE (student_id = (SELECT student_id FROM lacasa.tab_student WHERE email = '" +
                    ((Student) UI.getCurrent().getSession().getAttribute(Roles.Student)).getEmail() + "')" +
                    " AND s_anzeige_id = '-1')");

            if (!set.next()) {
            } else {
                StringBuilder msg = new StringBuilder("<p>Die Stellenanzeige/n deiner Bewerbung/en vom <br>");

                do {
                    msg.append("<b>").append(set.getDate("datum")).append("</b><br>");
            } while (set.next());
                  msg.append(" wurde gelöscht. Somit wurde deine Bewerbung/en mitgelöscht.</p>");

                deleteBewerbungAfterAnzeige();

                Notification notification = new Notification("Information", msg.toString(), Notification.Type.WARNING_MESSAGE);
                notification.setHtmlContentAllowed(true);
                notification.setDelayMsec(Notification.DELAY_FOREVER);
                notification.setHtmlContentAllowed(true);
                notification.addCloseListener(e -> notification.close());
                notification.show(Page.getCurrent());
            }
        } catch (SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }

    }

    public static void deleteBewerbungAfterAnzeige() throws DatabaseException {

        Statement statement = JDBCConnection.getInstance().getStatement();

        try {
            statement.execute("DELETE FROM lacasa.tab_bewerbung " +
                    " WHERE s_anzeige_id = '-1' AND student_id  = " +
                    " (SELECT student_id FROM lacasa.tab_student WHERE email = '"
                    + ((Student) UI.getCurrent().getSession().getAttribute(Roles.Student)).getEmail() + "')");
        } catch (SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }
}
