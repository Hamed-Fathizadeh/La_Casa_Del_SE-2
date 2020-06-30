package junit;

import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.util.*;
import org.junit.Test;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.HashMap;
import java.util.Properties;

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
    @Test
    public void utilPDFUploader(){
        PdfUploader.getByte();
        PdfUploader.setPath("");
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

    @Test
    public void utilMail() throws Exception {
        String myAccountEmail = "lacolsco.webpage@gmail.com";
        Properties generalProperties = new Properties();

        Session session = Session.getInstance(generalProperties, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(myAccountEmail,Password.MAIL);
            }
        });

       JavaMailUtil.prepareMessage(session,myAccountEmail,"","","");
       JavaMailUtil.sendMailToStudents(new Unternehmen(),new HashMap<String,String>());
       JavaMailUtil.prepareMessageForStudents(session,myAccountEmail,"","",new Unternehmen());
    }


}
