package org.bonn.se.model.objects.entitites;

import org.bonn.se.model.dao.ContainerBewerbungDAO;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContainerLetztenBewerbungen {

    private List<BewerbungDTO> liste;

    private static ContainerLetztenBewerbungen instance;

    public static ContainerLetztenBewerbungen getInstance() {
        return instance == null ? instance = new ContainerLetztenBewerbungen() : instance;
    }

    private ContainerLetztenBewerbungen(){
        liste = new ArrayList<>();

    }
    public int getAnzahl(){
        return liste.size();
    }

    public void load(String email){
        try {
            liste = ContainerBewerbungDAO.getInstance().load("Alle", email);
        }
        catch(DatabaseException | SQLException throwables){
            Logger.getLogger(ContainerLetztenBewerbungen.class.getName()).log(Level.SEVERE, null, throwables);
        }

    }

    public void load(String str, String email){
        try {
            liste = ContainerBewerbungDAO.getInstance().load(str, email);
        } catch (DatabaseException | SQLException e) {
            Logger.getLogger(ContainerLetztenBewerbungen.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void loadByStellenAnzeigeID(String str, int saID){
        try {
            liste = ContainerBewerbungDAO.getInstance().loadByStellenAnzeigeID(str,saID);
        } catch (DatabaseException | SQLException e) {
            Logger.getLogger(ContainerLetztenBewerbungen.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public BewerbungDTO getBewerbung(int i){
        return liste.get(i);
    }

    public List<BewerbungDTO> getListe(){
        return liste;
    }
}


