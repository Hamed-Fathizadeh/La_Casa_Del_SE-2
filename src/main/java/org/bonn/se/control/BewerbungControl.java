package org.bonn.se.control;

import com.vaadin.server.StreamResource;
import org.bonn.se.model.dao.BewerbungDAO;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.SQLException;

public class BewerbungControl {

    public static void bewerben (BewerbungDTO bewerbung) throws DatabaseException {
        try {
            BewerbungDAO.bewerben(bewerbung);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new DatabaseException("Die Anzeige wurde gelöscht!.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static boolean statusaendern(int bew_id) throws DatabaseException {
       return BewerbungDAO.statusaendern( bew_id);
    }


    public static StreamResource downloadLebenslauf(int student_id) throws DatabaseException {
        return BewerbungDAO.downloadLebenslauf( student_id);
    }
}
