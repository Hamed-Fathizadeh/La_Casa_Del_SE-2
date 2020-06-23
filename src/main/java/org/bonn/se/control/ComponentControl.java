package org.bonn.se.control;

import org.bonn.se.model.dao.BrancheDAO;
import org.bonn.se.model.dao.OrtDAO;
import org.bonn.se.model.dao.SuchbegriffDAO;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.SQLException;
import java.util.List;

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
        return OrtDAO.getInstance().getOrt();
    }

    public List<String> getBund () {
        return OrtDAO.getInstance().getBund();
    }

}
