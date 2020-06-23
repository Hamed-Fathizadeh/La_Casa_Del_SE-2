package org.bonn.se.gui.window;


import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ConfirmationWindow extends Window {


    // --Commented out by Inspection (22.06.20, 23:29):private boolean confirmationStatus;
    public ConfirmationWindow(String text){
        super("Confirmation");// Set windows caption
        center();

        VerticalLayout content = new VerticalLayout();
        Label label = new Label(text);
        content.addComponent(label);
        content.setComponentAlignment(label,Alignment.MIDDLE_CENTER);

        content.addComponent(label);

        content.setMargin(true);
        setContent(content);



    }


}
