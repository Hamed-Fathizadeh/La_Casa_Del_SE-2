package junit.util;

import com.vaadin.ui.GridLayout;
import org.bonn.se.gui.component.TopPanel;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TopPanelTest {

    TopPanel tp = new TopPanel("Unternehmen");


   @Test
    public void getTest(){
       Assert.assertTrue(tp instanceof GridLayout);

   }


}