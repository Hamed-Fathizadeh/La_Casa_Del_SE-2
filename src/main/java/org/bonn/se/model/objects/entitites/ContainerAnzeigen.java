package org.bonn.se.model.objects.entitites;

import org.bonn.se.model.dao.ContainerAnzDAO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.services.db.exception.DatabaseException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContainerAnzeigen {
    private List<StellenanzeigeDTO> liste;

    private ContainerAnzDAO containerAnzDAO  = ContainerAnzDAO.getInstance();
    private static ContainerAnzeigen instance;

    public static ContainerAnzeigen getInstance() {
        return instance == null ? instance = new ContainerAnzeigen() : instance;
    }

    private ContainerAnzeigen(){
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

    public void loadSuche(String suchbegriff_id, String standort, String bundesland, String umkreis,String artSuche, String einstellungsart, Date ab_Datum,String branche){
        try {
            liste = containerAnzDAO.loadSuche(suchbegriff_id, standort, bundesland, umkreis, artSuche, einstellungsart, ab_Datum, branche);
        }
        catch( DatabaseException throwables){
            throwables.getMessage();
        }

    }

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
        catch( DatabaseException throwables){
            throwables.getMessage();
        }

    }

    public StellenanzeigeDTO getAnzeige(int i){
        return liste.get(i);
    }


}
