package org.bonn.se.control;

import com.vaadin.data.provider.DataProvider;
import org.bonn.se.model.dao.ContainerAnzDAO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SucheControl  implements Suche {


    private static SucheControl instance;
    List<StellenanzeigeDTO> stellenanzeigeDTOS = null;

    public static SucheControl getInstance() {
        if (instance == null){
            instance = new SucheControl();
        }
        return instance ;
    }

    static int rowsCount;


    @Override
    public int getRowsCount() {
        return rowsCount;
    }

    @Override
    public DataProvider<StellenanzeigeDTO,Void> einfacheSuche(String suchbegriff, String ort, String bundesland, String umkreis, String artSuche, String einstellungsart, Date abDatum, String branche) {
        try {
            stellenanzeigeDTOS = ContainerAnzDAO.getInstance().loadSuche(
                    suchbegriff, ort, bundesland, umkreis, artSuche, einstellungsart, abDatum,  branche);
        } catch (DatabaseException | SQLException e) {
            Logger.getLogger(SucheControl.class.getName()).log(Level.SEVERE,null,e);
        }
        rowsCount = stellenanzeigeDTOS.size();

        return DataProvider.fromCallbacks(query -> {
            int offset = query.getOffset();
            int limit = query.getLimit();
            return stellenanzeigeDTOS.stream().skip(offset).limit(limit);
        } ,query ->getRowsCount() );

     }
}
