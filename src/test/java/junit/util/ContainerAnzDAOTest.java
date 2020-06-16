package junit.util;

import org.bonn.se.model.dao.ContainerAnzDAO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.Adresse;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.IllegalException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

import static org.bonn.se.model.dao.ContainerAnzDAO.*;


public class ContainerAnzDAOTest {
    public void checkload() throws DatabaseException {
        final List<StellenanzeigeDTO> l =getInstance().load();
        StellenanzeigeDTO stelle= new StellenanzeigeDTO();
        Assertions.assertNull(get(1));
    }
}
