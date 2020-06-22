package org.bonn.se.model.objects.entitites;

import org.bonn.se.model.dao.ContainerBewerbungDAO;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.services.db.exception.DatabaseException;

import java.util.ArrayList;
import java.util.List;

public class ContainerLetztenBewerbungen {

    private List<BewerbungDTO> liste;

    private static ContainerLetztenBewerbungen instance;

    public static ContainerLetztenBewerbungen getInstance() {
        return instance == null ? instance = new ContainerLetztenBewerbungen() : instance;
    }

    private ContainerLetztenBewerbungen(){
        liste = new ArrayList<BewerbungDTO>();

    }
    public int getAnzahl(){
        return liste.size();
    }

    public void load(String email){
        try {
            liste = ContainerBewerbungDAO.load("Alle", email);
        }
        catch( DatabaseException throwables){
            throwables.getMessage();
        }

    }

    public void load(String str, String email){
        try {
            liste = ContainerBewerbungDAO.load(str, email);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    public void loadNeueBewerbungen(){
        try {
            liste = ContainerBewerbungDAO.loadNeueBewerbungen();
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
    public void printAll(){
        for(BewerbungDTO b :liste){
            System.out.println(b.toString());
        }
    }

   // public static void main(String [] args){
        // ContainerLetztenBewerbungen ins= ContainerLetztenBewerbungen.getInstance();
         //ins.load("s@s.de");
         //ins.loadNeueBewerbungen();
        // ins.load("Alle","test1@test.de");
         //ins.printAll();




   // }
}


