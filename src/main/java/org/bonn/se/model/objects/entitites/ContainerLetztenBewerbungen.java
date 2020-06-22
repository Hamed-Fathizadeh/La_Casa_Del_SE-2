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
        liste = new ArrayList<>();

    }
    public int getAnzahl(){
        return liste.size();
    }

    public void load(String email){
        try {
            liste = ContainerBewerbungDAO.getInstance().load("Alle", email);
        }
        catch( DatabaseException throwables){
            throwables.getMessage();
        }

    }

    public void load(String str, String email){
        try {
            liste = ContainerBewerbungDAO.getInstance().load(str, email);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

// --Commented out by Inspection START (22.06.20, 23:35):
//    public void loadNeueBewerbungen(){
//        try {
//            liste = ContainerBewerbungDAO.getInstance().loadNeueBewerbungen();
//        } catch (DatabaseException e) {
//            e.printStackTrace();
//        }
//    }
// --Commented out by Inspection STOP (22.06.20, 23:35)


    public void loadByStellenAnzeigeID(String str, int saID){
        try {
            liste = ContainerBewerbungDAO.getInstance().loadByStellenAnzeigeID(str,saID);
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
// --Commented out by Inspection START (22.06.20, 23:35):
//    public void printAll(){
//        for(BewerbungDTO b :liste){
//            System.out.println(b.toString());
//        }
//    }
// --Commented out by Inspection STOP (22.06.20, 23:35)

   // public static void main(String [] args){
        // ContainerLetztenBewerbungen ins= ContainerLetztenBewerbungen.getInstance();
         //ins.load("s@s.de");
         //ins.loadNeueBewerbungen();
        // ins.load("Alle","test1@test.de");
         //ins.printAll();




   // }
}


