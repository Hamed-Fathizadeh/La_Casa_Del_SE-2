package junit;

import org.bonn.se.services.util.*;
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
        DTOFactory.createBewerbungDTO(1,null,"Test",null ,1,1,1,"name","bonn"
                ,null,"email","titel",null,"studium","ausbildung","konakt",1,null,"abschluss","vorname","nachname",0.0,true);
    }
    @Test
    public void utilPDFUploader(){
        PdfUploader.getByte();
    }
    @Test
    public void utilSuchbegriffService(){
        SuchbegrifService suchbegrifService = new SuchbegrifService();
        suchbegrifService.getSuchbegriffe();
        suchbegrifService.count("");
        suchbegrifService.fetch("",0,5);
        suchbegrifService.count(null);
        suchbegrifService.fetch(null,0,5);
    }

    @Test
    public void utilVerificationNummer(){
        VerifikationNummer verifikationNummer =new VerifikationNummer();

        VerifikationNummer.getInstance().setRandNummer();;
        VerifikationNummer.getInstance().getRandNummer();
    }

    @Test
    public void utilClasses(){
        Roles roles = new Roles();
        Views views = new Views();
        Password password = new Password();
        IllegalException illegalException = new IllegalException();

    }
}
