package org.bonn.se.control;

import org.bonn.se.model.dao.OrtDAO;
import org.bonn.se.model.dao.SuchbegriffDAO;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ComponentControl {

    private static ComponentControl instance;

    public static ComponentControl getInstance() {
        if (instance == null){
            instance = new ComponentControl();
        }
        return instance ;
    }

    public List<String> getSuchbegriffe () {

        return SuchbegriffDAO.getInstance().getSuchbegriffe();
    }

    public List<String> getBranche ()  {
        try {
            return SuchbegriffDAO.getInstance().getBranche();
        } catch (SQLException throwables) {
            Logger.getLogger(ComponentControl.class.getName()).log(Level.SEVERE, null, throwables);
        } catch (DatabaseException e) {
            Logger.getLogger(ComponentControl.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
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

    public Collection<String> getAbschluss ()  {
        try {
            return SuchbegriffDAO.getInstance().getAbschluss();
        } catch (DatabaseException e) {
            Logger.getLogger(ComponentControl.class.getName()).log(Level.SEVERE, null, e);
        } catch (SQLException throwables) {
            Logger.getLogger(ComponentControl.class.getName()).log(Level.SEVERE, null, throwables);
        }
        return null;
    }
    public Collection<String> getSpracheNiveau ()  {
        try {
            return SuchbegriffDAO.getInstance().getSpracheNiveau();
        } catch (DatabaseException e) {
            Logger.getLogger(ComponentControl.class.getName()).log(Level.SEVERE, null, e);
        } catch (SQLException throwables) {
            Logger.getLogger(ComponentControl.class.getName()).log(Level.SEVERE, null, throwables);
        }
        return null;
    }

    public Collection<String> getITNiveau ()  {
        try {
            return SuchbegriffDAO.getInstance().getITNiveau();
        } catch (DatabaseException e) {
            Logger.getLogger(ComponentControl.class.getName()).log(Level.SEVERE, null, e);
        } catch (SQLException throwables) {
            Logger.getLogger(ComponentControl.class.getName()).log(Level.SEVERE, null, throwables);
        }
        return null;
    }


    public Collection<String> getEinstellungsArt()  {
        try {
            return SuchbegriffDAO.getInstance().getEinstellungsart();
        } catch (DatabaseException e) {
            Logger.getLogger(ComponentControl.class.getName()).log(Level.SEVERE, null, e);
        } catch (SQLException throwables) {
            Logger.getLogger(ComponentControl.class.getName()).log(Level.SEVERE, null, throwables);
        }
        return null;
    }

}
