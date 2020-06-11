package org.bonn.se.model.objects.entitites;

import org.bonn.se.model.dao.ContainerBewerbungDAO;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.services.db.exception.DatabaseException;

import java.util.ArrayList;
import java.util.List;

public class ContainerLetztenBewerbungen {

    private List<BewerbungDTO> liste;

    private static ContainerLetztenBewerbungen instance = new ContainerLetztenBewerbungen();

    public static synchronized ContainerLetztenBewerbungen getInstance() {
        if (instance == null) {
            instance = new ContainerLetztenBewerbungen();
        }
        return instance;
    }

    private ContainerLetztenBewerbungen(){
        liste = new ArrayList<BewerbungDTO>();

    }
    public int getAnzahl(){
        return liste.size();
    }

    public void load(){
        try {
            liste = ContainerBewerbungDAO.load("Alle");
        }
        catch( DatabaseException throwables){
            throwables.getMessage();
        }

    }

    public void load(String str){
        try {
            liste = ContainerBewerbungDAO.load(str);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    public void loadByStellenAnzeigeID(String str, int saID){
        try {
            liste = ContainerBewerbungDAO.loadByStellenAnzeigeID(str,saID);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    public BewerbungDTO getBewerbung(int i){
        return liste.get(i);
    }

    public List<BewerbungDTO> getListe(){
        return liste;
    }

}


