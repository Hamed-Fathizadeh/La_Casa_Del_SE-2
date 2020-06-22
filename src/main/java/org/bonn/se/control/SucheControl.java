package org.bonn.se.control;

import com.vaadin.data.provider.DataProvider;
import org.bonn.se.model.dao.ContainerAnzDAO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.services.db.exception.DatabaseException;

import java.util.Date;
import java.util.List;

public class SucheControl  implements Suche {


    private static SucheControl instance;
    List<StellenanzeigeDTO> stellenanzeigeDTOS = null;

    public static SucheControl getInstance() {
        return instance == null ? instance = new SucheControl() : instance;
    }

    static int rowsCount;


    @Override
    public int getRowsCount() {
        return rowsCount;
    }

    @Override
    public DataProvider<StellenanzeigeDTO,Void> einfacheSuche(String suchbegriff, String ort, String bundesland, String umkreis, String artSuche, String einstellungsart, Date ab_Datum, String branche) {
        try {
            stellenanzeigeDTOS = ContainerAnzDAO.getInstance().loadSuche(
                    suchbegriff, ort, bundesland, umkreis, artSuche, einstellungsart, ab_Datum,  branche);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        rowsCount = stellenanzeigeDTOS.size();

        return DataProvider.fromCallbacks(query -> {
            int offset = query.getOffset();
            int limit = query.getLimit();
            return stellenanzeigeDTOS.stream().skip(offset).limit(limit);
        } ,query ->getRowsCount() );

     }
}
