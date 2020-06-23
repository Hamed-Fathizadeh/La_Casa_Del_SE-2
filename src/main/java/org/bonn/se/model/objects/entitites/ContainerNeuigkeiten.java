package org.bonn.se.model.objects.entitites;

import org.bonn.se.model.dao.ContainerAnzDAO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContainerNeuigkeiten {

    private List<StellenanzeigeDTO> liste;
    // --Commented out by Inspection (22.06.20, 23:32):private ArrayList<StellenanzeigeDTO> liste2;
    final private ContainerAnzDAO containerAnzDAO = ContainerAnzDAO.getInstance();

    private static ContainerNeuigkeiten instance;

    public static ContainerNeuigkeiten getInstance() {
        return instance == null ? instance = new ContainerNeuigkeiten() : instance;
    }
    private ContainerNeuigkeiten(){
        liste = new ArrayList<>();

    }
    public int getAnzahl(){
        return liste.size();
    }

    public void load(){
        try {
            liste = containerAnzDAO.load();
        }
        catch(DatabaseException | SQLException throwables){
            throwables.getMessage();
        }

    }

    public void loadNeuigkeiten(String str){
        try {
            liste = containerAnzDAO.loadNeuigkeiten(str);
        } catch (DatabaseException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadUnternehmenAnzeigen(String email){
        try {
            liste = containerAnzDAO.loadUnternehmenAnzeigen(email);
        } catch (DatabaseException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadNeuBewerbungen(Unternehmen unternehmen){
        try {
            liste = containerAnzDAO.loadNeuBewerbungen(unternehmen);
        } catch (DatabaseException | SQLException e) {
            e.printStackTrace();
        }
    }

    public StellenanzeigeDTO getAnzeige(int i){
        return liste.get(i);
    }

    public List<StellenanzeigeDTO> getListe(){
        return liste;
    }


    public void loadSuche(String suchbegriff_id, String standort, String bundesland, String umkreis, String artSuche, String einstellungsart, Date ab_Datum, String branche){
        try {
            liste = containerAnzDAO.loadSuche(suchbegriff_id, standort, bundesland, umkreis, artSuche, einstellungsart, ab_Datum, branche);
        }
        catch(DatabaseException | SQLException throwables){
            throwables.getMessage();
        }

    }


// --Commented out by Inspection START (22.06.20, 23:32):
// --Commented out by Inspection START (22.06.20, 23:32):
////    public Stream<StellenanzeigeDTO> fetchNachwas(StellenanzeigeDTO filter, int offset, int limit) {
////        return getListe().stream()
////                .filter(begrif -> filter == null || begrif.getSuchbegriff()
////                        .toLowerCase().startsWith(filter.getSuchbegriff().toLowerCase())
////                )
////                .skip(offset).limit(limit);
////    }
//// --Commented out by Inspection STOP (22.06.20, 23:32)
//    public Stream<StellenanzeigeDTO> fetchOrtBund(StellenanzeigeDTO filter, int offset, int limit) {
//        return getListe().stream()
//                .filter(begrif -> filter == null || begrif.getStandort()
//                        .toLowerCase().startsWith(filter.getStandort().toLowerCase())
//                )
//                .skip(offset).limit(limit);
//    }
// --Commented out by Inspection STOP (22.06.20, 23:32)

}
