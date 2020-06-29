package org.bonn.se.control;

import org.bonn.se.model.dao.BewertungDAO;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        }
        return res;
    }
}
