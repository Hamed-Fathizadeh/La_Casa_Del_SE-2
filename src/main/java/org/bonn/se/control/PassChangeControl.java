package org.bonn.se.control;

import com.vaadin.ui.UI;
import org.bonn.se.gui.window.ConfirmationWindow;
import org.bonn.se.model.dao.PassChangeDAO;
import org.bonn.se.model.objects.dto.PassChangeRequest;
import org.bonn.se.services.db.exception.DatabaseException;

public class PassChangeControl {

    private static PassChangeControl instance;

    public static PassChangeControl getInstance() {
        return instance == null ? instance = new PassChangeControl() : instance;
    }

    public void changePass(PassChangeRequest request) throws DatabaseException {

        boolean bRes = PassChangeDAO.getInstance().changePass(request);
        if(bRes){
            UI.getCurrent().addWindow(new ConfirmationWindow("Passwort wurde gändert! "));
        }else{
            //Fehlerhandling
            System.out.println("Fehler Pass könnte nicht gändert werden");
        }
    }

}
