package junit;

import org.bonn.se.control.SucheControl;
import org.junit.Test;

public class TestControl {

    @Test
    public void controlSuche() {
        SucheControl sucheControl = new SucheControl();
        sucheControl.getRowsCount();
        sucheControl.einfacheSuche("Physiker","Bonn","Nordrhein-Westfalen","Ganzer Ort","Erweitert",null,null,null);

    }
}
