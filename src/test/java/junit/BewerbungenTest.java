package junit;

import junit.util.ImageConverter;
import org.bonn.se.gui.component.Bewerbungen;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.model.objects.entitites.Bewerbung;
import org.bonn.se.model.objects.entitites.ContainerLetztenBewerbungen;
import org.bonn.se.services.db.exception.DatabaseException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import java.sql.Date;
import java.util.List;

public class BewerbungenTest<T extends BewerbungDTO> {

        List<T> data;
        BewerbungDTO bewerbungDTO;

        File file = new File("src/main/resources/Unknown.png");
        private byte[] image = ImageConverter.getImage(file);

        String unternehmenName = "Bargo Bank";
        Date datum = Date.valueOf(LocalDate.now());;
        int status = 2;
        int bewerbungID = 10;
        String s_titel = "Bewerbung";

        ContainerLetztenBewerbungen container = ContainerLetztenBewerbungen.getInstance();

        public BewerbungenTest() throws IOException {

        }

        //kann nicht getestet werden, weil beim aufruf des konstrutors wird UI.getCurrent aufgerufen
/*
        @Test
        public void getBewerbungDTOTest(){
            BewerbungDTO bwdto = new BewerbungDTO(image, unternehmenName, datum, status, bewerbungID, s_titel);

            Bewerbungen bw = new Bewerbungen(container, "AlleBewerbungenView");

            bw.setBewerbungDTO(bwdto);

            Assertions.assertEquals(bw.getBewerbungDTO(), bwdto);


        }


 */


        //methode setUP und setUpBewertung haben auch UI.getCurrent

       /* @Test

        public void TestBewerbungDTO(){


               // ob ne Exception geworfen wird, kann nicht getest werden weil die Methode UI.getCurrent aufruft

               Assertions.assertDoesNotThrow(
                        ()-> new Bewerbungen(container, "AlleBewerbungenView")
               );


        }

        */
}
