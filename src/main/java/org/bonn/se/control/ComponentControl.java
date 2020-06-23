package org.bonn.se.control;

import org.bonn.se.model.dao.BrancheDAO;
import org.bonn.se.model.dao.OrtDAO;
import org.bonn.se.model.dao.SuchbegriffDAO;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ComponentControl {

    private static ComponentControl instance;

    public static ComponentControl getInstance() {
        return instance == null ? instance = new ComponentControl() : instance;
    }

    public List<String> getSuchbegriffe () {

        return SuchbegriffDAO.getInstance().getSuchbegriffe();
    }

    public List<String> getBranche () throws DatabaseException, SQLException {
        return BrancheDAO.getInstance().getBranche();
    }

    public List<String> getOrt () {

        try {
            return OrtDAO.getInstance().getOrt();
        } catch (DatabaseException e) {
            Logger.getLogger(ComponentControl.class.getName()).log(Level.SEVERE, null, e);
        } catch (SQLException throwables) {
            Logger.getLogger(ComponentControl.class.getName()).log(Level.SEVERE, null, throwables);
        }
        return null;
    }

    public List<String> getBund ()  {
        try {
            return OrtDAO.getInstance().getBund();
        } catch (DatabaseException e) {
            Logger.getLogger(ComponentControl.class.getName()).log(Level.SEVERE, null, e);
        } catch (SQLException throwables) {
            Logger.getLogger(ComponentControl.class.getName()).log(Level.SEVERE, null, throwables);
        }
        return null;
    }

}
