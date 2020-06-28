package junit;

import junit.util.ImageConverter;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.model.objects.entitites.ContainerLetztenBewerbungen;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
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
        String sTitel = "Bewerbung";

        ContainerLetztenBewerbungen container = ContainerLetztenBewerbungen.getInstance();

        public BewerbungenTest() throws IOException {

        }


}
