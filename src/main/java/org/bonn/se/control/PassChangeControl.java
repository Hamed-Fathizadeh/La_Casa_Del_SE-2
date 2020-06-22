package org.bonn.se.control;

import com.vaadin.ui.UI;
import org.bonn.se.gui.window.ConfirmationWindow;
import org.bonn.se.model.dao.PassChangeDAO;
import org.bonn.se.model.objects.dto.PassChangeRequest;

public class PassChangeControl {

    public static PassChangeControl passChangeControl = null;

    public static PassChangeControl getInstance(){
        if(passChangeControl == null){
            passChangeControl = new PassChangeControl();
        }
        return passChangeControl;
    }

    public void changePass(PassChangeRequest request){

        boolean bRes = PassChangeDAO.getInstance().changePass(request);
        if(bRes){
            UI.getCurrent().addWindow(new ConfirmationWindow("Passwort wurde gändert! "));
        }else{
            //Fehlerhandling
            System.out.println("Fehler Pass könnte nicht gändert werden");
        }
    }

}
