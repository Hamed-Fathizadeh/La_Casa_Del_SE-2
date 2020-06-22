package org.bonn.se.control;

import com.vaadin.data.provider.CallbackDataProvider;
import com.vaadin.data.provider.DataProvider;
import org.bonn.se.model.dao.ContainerAnzDAO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.services.db.exception.DatabaseException;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SucheControl  implements Suche<Serializable, Number>,SucheServce {


    private static SucheControl instance;

    public static SucheControl getInstance() {
        return instance == null ? instance = new SucheControl() : instance;
    }

    static int rowsCount;

    public static int getRowsCount() {
        return rowsCount;
    }

    public DataProvider<StellenanzeigeDTO,Void> getData(String suchbegriff, String ort, String bundesland, String umkreis, String artSuche, String einstellungsart, Date ab_Datum, String branche) {

        List<StellenanzeigeDTO> stellenanzeigeDTOS = SucheControl.getInstance().fetchStellenanzeigen1(0,5,
                suchbegriff, ort, bundesland, umkreis, artSuche, einstellungsart, ab_Datum,  branche);

        rowsCount = stellenanzeigeDTOS.size();
        CallbackDataProvider<StellenanzeigeDTO,Void> dataProvider = DataProvider.fromCallbacks(query -> {
            int offset = query.getOffset();
            int limit = query.getLimit();

            return stellenanzeigeDTOS.stream().skip(offset).limit(limit);

        } ,query ->rowsCount );

        return dataProvider;
    }


    public List<StellenanzeigeDTO> einfacheSuche1(int offset, int limit, String suchbegriff, String ort, String bundesland, String umkreis, String artSuche, String einstellungsart, Date ab_Datum, String branche) {
        try {
            return ContainerAnzDAO.getInstance().loadSuche1(offset,limit,
                    suchbegriff, ort, bundesland, umkreis, artSuche, einstellungsart, ab_Datum,  branche
            );
        } catch (DatabaseException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public List<StellenanzeigeDTO> einfacheSuche(String suchbegriff, String ort, String bundesland, String umkreis, String artSuche, String einstellungsart, Date ab_Datum, String branche) {
        return null;
    }

    @Override
    public void erweiterteSuche() {

    }



    public List<StellenanzeigeDTO> fetchStellenanzeigen1(int offset, int limit, String suchbegriff, String ort, String bundesland, String umkreis, String artSuche, String einstellungsart, java.util.Date ab_Datum, String branche) {

            return  einfacheSuche1(offset, limit,suchbegriff, ort, bundesland, umkreis, artSuche, einstellungsart, ab_Datum,  branche);


    }

    @Override
    public List<StellenanzeigeDTO> fetchStellenanzeigen(int offset, int limit) {
        return null;
    }

    @Override
    public int getStellenanzeigenCount() {
        return 19;
    }
}
