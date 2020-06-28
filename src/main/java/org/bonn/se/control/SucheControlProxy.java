package org.bonn.se.control;

import com.vaadin.data.provider.DataProvider;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;

import java.time.LocalDate;

public class SucheControlProxy implements Suche {

    private  SucheControl implSucheControl;

    @Override
    public int getRowsCount() {
        if(implSucheControl == null) {
            implSucheControl = new SucheControl();
        }
        return SucheControl.rowsCount;
    }

    @Override
    public DataProvider<StellenanzeigeDTO, Void> einfacheSuche(String suchbegriff, String ort, String bundesland, String umkreis, String artSuche, String einstellungsart, LocalDate abDatum, String branche) {
        if(implSucheControl == null) {
            implSucheControl = new SucheControl();
        }
         return implSucheControl.einfacheSuche(suchbegriff, ort, bundesland, umkreis, artSuche, einstellungsart, abDatum,  branche);
    }
}
