package org.bonn.se.control;

import com.vaadin.data.provider.DataProvider;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;

import java.util.Date;

public interface Suche {

    int getRowsCount();



    DataProvider<StellenanzeigeDTO,Void> einfacheSuche(String suchbegriff, String ort, String bundesland, String umkreis,
                                                  String artSuche, String einstellungsart, Date ab_Datum, String branche);

}
