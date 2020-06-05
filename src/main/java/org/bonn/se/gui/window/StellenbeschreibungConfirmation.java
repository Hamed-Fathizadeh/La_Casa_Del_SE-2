package org.bonn.se.gui.window;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.model.objects.entitites.Stellenanzeige;
import org.bonn.se.services.util.Views;

public class StellenbeschreibungConfirmation extends Window {


    public StellenbeschreibungConfirmation(Stellenanzeige stellenanzeige){
        setWindow(stellenanzeige);
    }

    public void setWindow(Stellenanzeige stellenanzeige) {
        center();

        this.setHeight("60%");
        this.setWidth("60%");
        this.setDraggable(false);
        this.setResizable(false);
        GridLayout gridLayout = new GridLayout(1, 3);
        gridLayout.setHeight("100%");
        gridLayout.setWidth("100%");
        Label head = null;
        Label message = null;
        if(stellenanzeige.getStatus() == 1) {
             head = new Label("<h1><p><font color=\"blue\">Herzlichen Glückwunsch!</font></p></h1>", ContentMode.HTML);
             message = new Label("<h2><p><font color=\"blue\">Ihre Stellenbeschreibung ist online!</font></p></21>", ContentMode.HTML);
        } else {
            head = new Label("<h1><p><font color=\"blue\">Sehr gut!</font></p></h1>", ContentMode.HTML);
            message = new Label("<h2><p><font color=\"blue\">Ihre Stellenbeschreibung ist aktuell offline!</font></p></21>", ContentMode.HTML);
        }

        Button fertig = new Button("Fertig");

        gridLayout.addComponent(head,0,0);
        gridLayout.addComponent(message,0,1);
        gridLayout.addComponent(fertig,0,2);

        gridLayout.setComponentAlignment(head, Alignment.TOP_CENTER);
        gridLayout.setComponentAlignment(message,Alignment.MIDDLE_CENTER);
        gridLayout.setComponentAlignment(fertig,Alignment.MIDDLE_CENTER);

        fertig.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                StellenbeschreibungConfirmation.this.close();
                MyUI.getCurrent().getNavigator().navigateTo(Views.UnternehmenHomeView);
            }
        });

        this.addCloseListener(new CloseListener() {
            @Override
            public void windowClose(CloseEvent e) {
                UI.getCurrent().getNavigator().navigateTo(Views.UnternehmenHomeView);
            }
        });

        setContent(gridLayout);
    }
}
