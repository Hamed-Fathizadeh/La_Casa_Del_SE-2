package org.bonn.se.control.exception;

import org.bonn.se.model.dao.BewerbungDAO;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.SQLException;

public class BewerbungControl {

    public static void bewerben (BewerbungDTO bewerbung,String path) throws DatabaseException {
        try {
            BewerbungDAO.bewerben(bewerbung,  path);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new DatabaseException("Die Anzeige wurde gelöscht!.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
