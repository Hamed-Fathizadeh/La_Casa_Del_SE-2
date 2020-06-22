package org.bonn.se.control;

import org.bonn.se.model.objects.dto.StellenanzeigeDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface Suche<D extends Serializable, I extends Number> {

    List<StellenanzeigeDTO> einfacheSuche(String suchbegriff, String ort, String bundesland, String umkreis,
                                          String artSuche, String einstellungsart, Date ab_Datum, String branche);
    void erweiterteSuche();


}
