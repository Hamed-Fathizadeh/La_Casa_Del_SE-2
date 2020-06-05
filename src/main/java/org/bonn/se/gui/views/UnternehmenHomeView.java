package org.bonn.se.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import org.bonn.se.gui.component.Anzeigen;
import org.bonn.se.gui.component.TopPanelUser;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.ContainerNeuigkeiten;
import org.bonn.se.model.objects.entitites.Stellenanzeige;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;


public class UnternehmenHomeView extends VerticalLayout implements View {
    private  Stellenanzeige selected = null;
    private  int anzahl = 0;
    public void setUp() {

        TopPanelUser topPanel = new TopPanelUser();

        GridLayout Maingrid = new GridLayout(3, 3);
        Maingrid.setSizeFull();

        Button buttonAnzeigeErstellen= new Button("Anzeige erstellen");
        buttonAnzeigeErstellen.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo(Views.AnzeigeErstellen);
            }
        });
        VerticalLayout vlayoutButton = new VerticalLayout();
        vlayoutButton.setMargin(true);
        vlayoutButton.addComponent(buttonAnzeigeErstellen);
        vlayoutButton.setComponentAlignment(buttonAnzeigeErstellen,Alignment.BOTTOM_CENTER);

//grid anzeige
        ContainerNeuigkeiten containerMeinAnzeigen = ContainerNeuigkeiten.getInstance();
        String email = ((Unternehmen) MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).getEmail();

        containerMeinAnzeigen.loadUnternehmenAnzeigen(email);
        Anzeigen<StellenanzeigeDTO> gAnzeigen = new  Anzeigen<StellenanzeigeDTO>("Alle",containerMeinAnzeigen);
        gAnzeigen.setHeightMode(HeightMode.UNDEFINED);

 //grid anzeige end

        GridLayout bottomGridBewNeu = new GridLayout(1, 2);
        bottomGridBewNeu.setSizeFull();
        bottomGridBewNeu.addStyleName("bottomGridBewNeu");
        bottomGridBewNeu.setMargin(true);

        String ls = "<p class=MsoNormal><b><span style='font-size:18.0pt;line-height:107%;\n" +
                "font-family:\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:\n" +
                "minor-bidi;mso-bidi-theme-font:minor-bidi;color:white;mso-themecolor:background1'>\n" +
                "Ihre Anzeigen<o:p></o:p></span></b></p>";

        Label lBewerbung = new Label(ls, ContentMode.HTML);


        bottomGridBewNeu.addComponent(lBewerbung,0,0,0,0);
          bottomGridBewNeu.addComponent(gAnzeigen,0,1,0,1);

        bottomGridBewNeu.setComponentAlignment(lBewerbung, Alignment.BOTTOM_CENTER);
        bottomGridBewNeu.setComponentAlignment(gAnzeigen, Alignment.BOTTOM_CENTER);
        bottomGridBewNeu.setSizeFull();
       // bottomGridBewNeu.addStyleName("AnzeigeUnternehmen");



                      Maingrid.addComponent(topPanel, 0, 0, 2, 0);
        Maingrid.addComponent(vlayoutButton, 1, 1, 1, 1);
              Maingrid.addComponent(bottomGridBewNeu, 0, 2, 2, 2);


       Maingrid.setComponentAlignment(topPanel, Alignment.TOP_CENTER);
        Maingrid.setComponentAlignment(vlayoutButton, Alignment.MIDDLE_CENTER);
        Maingrid.setComponentAlignment(bottomGridBewNeu, Alignment.BOTTOM_CENTER);


        this.addComponent(Maingrid);
        this.setComponentAlignment(Maingrid, Alignment.MIDDLE_CENTER);
        this.setMargin(false);
        this.addStyleName("grid");





    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        Unternehmen unternehmen = (Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen);
        if( unternehmen == null) {
            UI.getCurrent().getNavigator().getCurrentNavigationState();
        } else {
            this.setUp();
        }
    }

}
