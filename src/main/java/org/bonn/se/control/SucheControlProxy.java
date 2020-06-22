package org.bonn.se.control;

import com.vaadin.data.provider.DataProvider;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;

import java.util.Date;

public class SucheControlProxy implements Suche {

    private  SucheControl implSucheControl;
    static int rowsCount;

    @Override
    public int getRowsCount() {
        if(implSucheControl == null) {
            implSucheControl = new SucheControl();
        }
        return implSucheControl.rowsCount;
    }

    @Override
    public DataProvider<StellenanzeigeDTO, Void> einfacheSuche(String suchbegriff, String ort, String bundesland, String umkreis, String artSuche, String einstellungsart, Date ab_Datum, String branche) {
        if(implSucheControl == null) {
            implSucheControl = new SucheControl();
        }
         return implSucheControl.einfacheSuche(suchbegriff, ort, bundesland, umkreis, artSuche, einstellungsart, ab_Datum,  branche);
    }
}
