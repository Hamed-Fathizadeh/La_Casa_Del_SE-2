package org.bonn.se.model.objects.entitites;

import org.bonn.se.model.dao.ContainerAnzDAO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContainerNeuigkeiten {

    private List<StellenanzeigeDTO> liste;
    private final ContainerAnzDAO containerAnzDAO = ContainerAnzDAO.getInstance();

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


    public void loadSuche(String suchbegriffId, String standort, String bundesland, String umkreis, String artSuche, String einstellungsart, LocalDate abDatum, String branche){
        try {
            liste = containerAnzDAO.loadSuche(suchbegriffId, standort, bundesland, umkreis, artSuche, einstellungsart, abDatum, branche);
        }
        catch(DatabaseException | SQLException throwables){
            throwables.getMessage();
        }

    }
}
