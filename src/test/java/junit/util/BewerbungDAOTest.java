
package junit.util;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.services.db.exception.DatabaseException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.bonn.se.model.dao.BewerbungDAO;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class BewerbungDAOTest {
    @Test
    public void testall() throws DatabaseException, SQLException {
        BewerbungDAO bewerb = BewerbungDAO.getInstance();
         if(bewerb.markierungAendern(2)==false){
             System.out.println(bewerb.markierungAendern(2));
             Assertions.assertTrue(bewerb.markierungAendern(2));
         }
         else
             Assertions.assertFalse(bewerb.markierungAendern(2));
        Assertions.assertNull(bewerb.downloadLebenslauf(16));
    }

}