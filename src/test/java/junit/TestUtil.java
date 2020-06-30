package junit;

import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.services.util.BrancheService;
import org.bonn.se.services.util.ConcreteFactoryCollHbrs;
import org.bonn.se.services.util.DTOFactory;
import org.junit.Test;

public class TestUtil {


    @Test
    public void utilBrancheService() {
        BrancheService brancheService = new BrancheService();
        brancheService.getSuchbegriffe();
        brancheService.count("");
        brancheService.fetch("",0,5);
        brancheService.count(null);
        brancheService.fetch(null,0,5);
    }
    @Test
    public void utilDTOFactory() {
                DTOFactory bewerbung = new ConcreteFactoryCollHbrs();
                bewerbung.createBewerbungDTO(1,null,"Test",null ,1,1,1,"name","bonn"
                ,null,"email","titel",null,"studium","ausbildung","konakt",1,null,"abschluss","vorname","nachname",0.0,true);
    }
}
