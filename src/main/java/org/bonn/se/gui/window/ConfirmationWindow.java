package org.bonn.se.gui.window;


import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ConfirmationWindow extends Window {


    public ConfirmationWindow(String text){
        super("Confirmation");
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
