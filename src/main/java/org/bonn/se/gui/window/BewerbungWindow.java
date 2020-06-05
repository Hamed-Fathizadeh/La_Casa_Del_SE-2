package org.bonn.se.gui.window;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.Unternehmen;

public class BewerbungWindow extends Window {

    public BewerbungWindow(StellenanzeigeDTO stellenanzeige, Unternehmen unternehmen_data)  {
        setUp(stellenanzeige,unternehmen_data);
    }

    public void setUp(StellenanzeigeDTO stellenanzeige, Unternehmen unternehmen_data) {
        center();

        this.setWidth("80%");
        this.setHeight("90%");
        this.setModal(true);
        this.setResizable(false);
        this.setClosable(false);

        Panel panel = new Panel();
        panel.setWidthFull();


        GridLayout gridLayout = new GridLayout(5, 14);
        gridLayout.setSizeFull();
        gridLayout.setMargin(true);

        Image logo = unternehmen_data.getLogo();
        Label titel = new Label("<h2><b> Bewerbung auf: " + stellenanzeige.getTitel() + "</font></b></h3>", ContentMode.HTML);


        gridLayout.addComponent(logo, 1, 1);
        gridLayout.addComponent(titel, 1, 2, 4, 2);


        Button bewerben = new Button("Bewerben");
        Button back = new Button("Zurück zu Ergebnissen");

        gridLayout.addComponent(bewerben, 4, 13, 4, 13);
        gridLayout.addComponent(back, 4, 0, 4, 0);


        back.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                BewerbungWindow.this.close();
            }
        });

        panel.setContent(gridLayout);
        this.setContent(panel);



    }

}

