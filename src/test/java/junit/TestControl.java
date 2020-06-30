package junit;

import org.bonn.se.control.*;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.services.db.exception.DatabaseException;
import org.junit.Test;

import java.sql.SQLException;

public class TestControl {

    @Test
    public void controlSuche() {
        SucheControl sucheControl = new SucheControl();
        sucheControl.getRowsCount();
        sucheControl.einfacheSuche("Physiker","Bonn","Nordrhein-Westfalen","Ganzer Ort","Erweitert",null,null,null);

    }

    @Test
    public void controlComponent() {
        ComponentControl.getInstance().getAbschluss();
        ComponentControl.getInstance().getBranche();
        ComponentControl.getInstance().getBund();
        ComponentControl.getInstance().getOrt();
        ComponentControl.getInstance().getEinstellungsArt();
        ComponentControl.getInstance().getITNiveau();
        ComponentControl.getInstance().getSpracheNiveau();
        ComponentControl.getInstance().getSuchbegriffe();
    }

    @Test
    public void controlUnternehmenDescription() throws DatabaseException, SQLException {
        JobTitelControl.getJobTitelList();
        AnzStatusControl.changeStatus(new StellenanzeigeDTO());
        UserSearchControl.getInstance().existUser("tobias.fellechner@365h-brs.de");
    }


}
