package org.bonn.se.control;

import org.bonn.se.model.objects.dto.StellenanzeigeDTO;

import java.util.List;

public interface SucheServce {

    List<StellenanzeigeDTO> fetchStellenanzeigen(int offset, int limit);
    int getStellenanzeigenCount();

}
