package org.bonn.se.model.objects.entitites;

import org.bonn.se.model.dao.ContainerAnzDAO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.services.db.exception.DatabaseException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContainerAnzeigen {
    private List<StellenanzeigeDTO> liste;

    private static ContainerAnzeigen instance = new ContainerAnzeigen();

    public static synchronized ContainerAnzeigen getInstance() {
        if (instance == null) {
            instance = new ContainerAnzeigen();
        }
        return instance;
    }

    private ContainerAnzeigen(){
        liste = new ArrayList<StellenanzeigeDTO>();

    }
    public int getAnzahl(){
        return liste.size();
    }

    public void load(){
        try {
            liste = ContainerAnzDAO.load();
        }
        catch( DatabaseException throwables){
            throwables.getMessage();
        }

    }

    public void loadSuche(String suchbegriff_id, String standort, String bundesland, String umkreis,String artSuche, String einstellungsart, Date ab_Datum,String branche){
        try {
            liste = ContainerAnzDAO.loadSuche(suchbegriff_id, standort, bundesland, umkreis, artSuche, einstellungsart, ab_Datum, branche);
        }
        catch( DatabaseException throwables){
            throwables.getMessage();
        }

    }

    public StellenanzeigeDTO getAnzeige(int i){
        return liste.get(i);
    }


}
