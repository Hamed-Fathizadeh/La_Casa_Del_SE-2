package junit;

import com.vaadin.ui.Image;
import org.bonn.se.gui.component.*;
import org.bonn.se.services.util.ImageConverter;
import org.junit.Test;
import org.vaadin.easyuploads.UploadField;

public class TestViewComponenet {



    @Test
    public void testProfilErstellen(){
        StudentDatenView studentDatenView = new StudentDatenView();
        studentDatenView.setReadOnly(false);
        studentDatenView.getOrt();
        studentDatenView.getStrasse();
        studentDatenView.getStudiengang();
        studentDatenView.getG_datum();
        studentDatenView.getMobilnr();
        studentDatenView.getAbschluss();
        studentDatenView.getUploadProfil();
        studentDatenView.getLebenslauf();
        studentDatenView.getAusbildung();
        studentDatenView.getImage();
        OrtPlzTextField ort = new OrtPlzTextField();
        studentDatenView.setOrt(ort);
        PopUpTextField strasse = new PopUpTextField("Test");
        studentDatenView.setStrasse(strasse);
        UploadField uploadField = new UploadField();
        studentDatenView.setLebenslauf(uploadField);
        Image image = ImageConverter.getUnknownMenuImage();
        studentDatenView.setImage(image);


        UnternehmenBeschreibungView unternehmenBeschreibungView = new UnternehmenBeschreibungView();
        unternehmenBeschreibungView.setReadOnly(true);
        unternehmenBeschreibungView.getRichTextArea();
        UnternehmenDatenView unternehmenDatenView = new UnternehmenDatenView();
        unternehmenDatenView.getOrt();
        unternehmenDatenView.getStrasse();
        unternehmenDatenView.getBranche();
        unternehmenDatenView.getUploadLogo();
        unternehmenDatenView.getImage();
        unternehmenDatenView.setImage(image);
        unternehmenDatenView.setOrt(ort);
        unternehmenDatenView.setStrasse(strasse);
        unternehmenDatenView.getKontaktnummer();
        unternehmenDatenView.setReadOnly(true);

    }
}
