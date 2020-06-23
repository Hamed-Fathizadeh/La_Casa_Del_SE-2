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

}
