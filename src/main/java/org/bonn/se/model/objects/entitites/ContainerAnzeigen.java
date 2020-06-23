package org.bonn.se.model.objects.entitites;

import org.bonn.se.model.dao.ContainerAnzDAO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContainerAnzeigen {
   private final List<StellenanzeigeDTO> liste;

    final private ContainerAnzDAO containerAnzDAO  = ContainerAnzDAO.getInstance();
    private static ContainerAnzeigen instance;

    public static ContainerAnzeigen getInstance() {
        return instance == null ? instance = new ContainerAnzeigen() : instance;
    }

    private ContainerAnzeigen(){
        liste = new ArrayList<>();

    }
// --Commented out by Inspection START (22.06.20, 23:29):
//    public int getAnzahl(){
//        return liste.size();
//    }
// --Commented out by Inspection STOP (22.06.20, 23:29)

// --Commented out by Inspection START (22.06.20, 23:30):
//    public void load(){
//        try {
//            liste = containerAnzDAO.load();
//        }
//        catch( DatabaseException throwables){
//            throwables.getMessage();
//        }
//
//    }
// --Commented out by Inspection STOP (22.06.20, 23:30)

// --Commented out by Inspection START (22.06.20, 23:30):
//    public void loadSuche(String suchbegriff_id, String standort, String bundesland, String umkreis,String artSuche, String einstellungsart, Date ab_Datum,String branche){
//        try {
//            liste = containerAnzDAO.loadSuche(suchbegriff_id, standort, bundesland, umkreis, artSuche, einstellungsart, ab_Datum, branche);
//        }
//        catch( DatabaseException throwables){
//            throwables.getMessage();
//        }
//
//    }
// --Commented out by Inspection STOP (22.06.20, 23:30)

    public void setAnzeige(Unternehmen user){
        try {
            containerAnzDAO.setAnzeige(user);
        }
        catch( DatabaseException throwables){
            throwables.getMessage();
        }
    }

    public void updateAnzeige(StellenanzeigeDTO stellenanzeigeDTO){
        try {
            containerAnzDAO.updateAnzeige(stellenanzeigeDTO);
        }
        catch( DatabaseException throwables){
            throwables.getMessage();
        }

    }

    public void deleteAnzeige(StellenanzeigeDTO stellenanzeigeDTO){
        try {
            ContainerAnzDAO.deleteAnzeige(stellenanzeigeDTO);
        }
        catch(DatabaseException | SQLException throwables){
            throwables.getMessage();
        }

    }

// --Commented out by Inspection START (22.06.20, 23:29):
//    public StellenanzeigeDTO getAnzeige(int i){
//        return liste.get(i);
//    }
// --Commented out by Inspection STOP (22.06.20, 23:29)


}
