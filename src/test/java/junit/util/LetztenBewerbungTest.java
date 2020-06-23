package junit.util;

import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.model.objects.entitites.ContainerLetztenBewerbungen;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class LetztenBewerbungTest {
    @Test
    public void letztenbewerbung() {
        ContainerLetztenBewerbungen ins = ContainerLetztenBewerbungen.getInstance();
        BewerbungDTO bewerb = new BewerbungDTO();
        ins.load("s@s.de");
        Assertions.assertEquals(5, ins.getAnzahl());
        Assertions.assertNotNull(ins.getListe());
        Assertions.assertEquals(47, ins.getBewerbung(2).getBewerbungID());
    }
}
