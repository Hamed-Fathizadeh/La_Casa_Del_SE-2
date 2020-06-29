package org.bonn.se.control;

import org.bonn.se.model.dao.BewerbungDAO;
import org.bonn.se.model.dao.BewertungDAO;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.SQLException;

public class BewertungControl {

    public BewertungControl(){

    }

    public static double bewertungByID(String hauptsitz, String firmenname) {
        Double res = 0.0;
        try {
            res = BewertungDAO.getInstance().bewertungByID(hauptsitz,firmenname );
        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }
}
