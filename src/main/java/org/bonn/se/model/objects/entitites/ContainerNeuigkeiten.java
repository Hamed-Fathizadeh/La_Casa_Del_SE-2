package org.bonn.se.model.objects.entitites;

import org.bonn.se.model.dao.ContainerAnzDAO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.services.db.exception.DatabaseException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class ContainerNeuigkeiten {

    private List<StellenanzeigeDTO> liste;
    private ArrayList<StellenanzeigeDTO> liste2;
    private ContainerAnzDAO containerAnzDAO = ContainerAnzDAO.getInstance();

    private static ContainerNeuigkeiten instance;

    public static ContainerNeuigkeiten getInstance() {
        return instance == null ? instance = new ContainerNeuigkeiten() : instance;
    }
    private ContainerNeuigkeiten(){
        liste = new ArrayList<StellenanzeigeDTO>();

    }
    public int getAnzahl(){
        return liste.size();
    }

    public void load(){
        try {
            liste = containerAnzDAO.load();
        }
        catch( DatabaseException throwables){
            throwables.getMessage();
        }

    }

    public void loadNeuigkeiten(String str){
        try {
            liste = containerAnzDAO.loadNeuigkeiten(str);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    public void loadUnternehmenAnzeigen(String email){
        try {
            liste = containerAnzDAO.loadUnternehmenAnzeigen(email);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    public void loadNeuBewerbungen(Unternehmen unternehmen){
        try {
            liste = containerAnzDAO.loadNeuBewerbungen(unternehmen);
        } catch (DatabaseException e) {
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
        catch( DatabaseException throwables){
            throwables.getMessage();
        }

    }


    public Stream<StellenanzeigeDTO> fetchNachwas(StellenanzeigeDTO filter, int offset, int limit) {
        return getListe().stream()
                .filter(begrif -> filter == null || begrif.getSuchbegriff()
                        .toLowerCase().startsWith(filter.getSuchbegriff().toLowerCase())
                )
                .skip(offset).limit(limit);
    }
    public Stream<StellenanzeigeDTO> fetchOrtBund(StellenanzeigeDTO filter, int offset, int limit) {
        return getListe().stream()
                .filter(begrif -> filter == null || begrif.getStandort()
                        .toLowerCase().startsWith(filter.getStandort().toLowerCase())
                )
                .skip(offset).limit(limit);
    }

}
