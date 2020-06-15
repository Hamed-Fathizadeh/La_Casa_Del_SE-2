package org.bonn.se.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.bonn.se.gui.component.Anzeigen;
import org.bonn.se.gui.component.Bewerbungen;
import org.bonn.se.gui.component.TopPanelUser;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.ContainerLetztenBewerbungen;
import org.bonn.se.model.objects.entitites.ContainerNeuigkeiten;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;

import java.util.stream.Collectors;


public class UnternehmenHomeView extends VerticalLayout implements View {
    private final StellenanzeigeDTO selected = null;
    private final int anzahl = 0;
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

        Unternehmen unternehmen = ((Unternehmen) MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen));

        ContainerNeuigkeiten containerMeinAnzeigen = ContainerNeuigkeiten.getInstance();
        containerMeinAnzeigen.loadUnternehmenAnzeigen(unternehmen.getEmail());
        Anzeigen<StellenanzeigeDTO> gAnzeigen = new  Anzeigen<StellenanzeigeDTO>("Alle",containerMeinAnzeigen);
        gAnzeigen.setHeightMode(HeightMode.UNDEFINED);
        gAnzeigen.setSizeFull();

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


        //add TabSheet
        TabSheet tabSheet = new TabSheet();
        tabSheet.setWidthFull();
        tabSheet.setHeight("1000px");
        tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
        tabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        gAnzeigen.removeColumn("Anzahl neue Bewerbungen");
        tabSheet.addTab(gAnzeigen,"Alle "+gAnzeigen.getAnzahlRow());


        Anzeigen<StellenanzeigeDTO> gAnzeigenOnline = new  Anzeigen<StellenanzeigeDTO>("Alle",containerMeinAnzeigen);
        gAnzeigenOnline.setData(gAnzeigen.getData().stream().filter(c -> c.getStatus() ==1).collect(Collectors.toList()));
        gAnzeigenOnline.setSizeFull();
        gAnzeigenOnline.removeColumn("Anzahl neue Bewerbungen");
        tabSheet.addTab(gAnzeigenOnline,"Online "+gAnzeigenOnline.getAnzahlRow());

        Anzeigen<StellenanzeigeDTO> gAnzeigenOffline = new  Anzeigen<StellenanzeigeDTO>("Alle",containerMeinAnzeigen);
        gAnzeigenOffline.setData(gAnzeigen.getData().stream().filter(c -> c.getStatus() ==2).collect(Collectors.toList()));
        gAnzeigenOffline.setSizeFull();
        gAnzeigenOffline.removeColumn("Anzahl neue Bewerbungen");
        tabSheet.addTab(gAnzeigenOffline,"Offline "+gAnzeigenOffline.getAnzahlRow());

        Anzeigen<StellenanzeigeDTO> gAnzeigenEntwurf = new  Anzeigen<StellenanzeigeDTO>("Alle",containerMeinAnzeigen);
        gAnzeigenEntwurf.setData(gAnzeigen.getData().stream().filter(c -> c.getStatus() ==3).collect(Collectors.toList()));
        gAnzeigenEntwurf.setSizeFull();
        gAnzeigenEntwurf.removeColumn("Anzahl neue Bewerbungen");
        tabSheet.addTab(gAnzeigenEntwurf,"Entwurf "+gAnzeigenEntwurf.getAnzahlRow());


         containerMeinAnzeigen.loadNeuBewerbungen(unternehmen);
         Anzeigen<StellenanzeigeDTO> gAnzeigenNeuBewerbungen = new  Anzeigen<StellenanzeigeDTO>("Alle",containerMeinAnzeigen);
         gAnzeigenNeuBewerbungen.setSizeFull();
         gAnzeigenNeuBewerbungen.removeColumn("Status");

         if(gAnzeigenNeuBewerbungen.getAnzahlNeuBewerbungen()>0){
             ThemeResource resource = new ThemeResource("img/Anzeigen/rot_klein.png");
             tabSheet.addTab(gAnzeigenNeuBewerbungen, "Neue Bewerbungen ",resource);
         }else {
             tabSheet.addTab(gAnzeigenNeuBewerbungen, "Neue Bewerbungen ");
         }

        bottomGridBewNeu.addComponent(lBewerbung,0,0,0,0);
        bottomGridBewNeu.addComponent(tabSheet,0,1,0,1);

        bottomGridBewNeu.setComponentAlignment(lBewerbung, Alignment.BOTTOM_CENTER);
        bottomGridBewNeu.setComponentAlignment(tabSheet, Alignment.BOTTOM_CENTER);
        bottomGridBewNeu.setSizeFull();
        bottomGridBewNeu.setMargin(true);

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

        if (UI.getCurrent().getSession().getAttribute(Roles.Unternehmen) != null) {
            this.setUp();
        } else if (UI.getCurrent().getSession().getAttribute(Roles.Student) != null) {
            UI.getCurrent().getNavigator().getCurrentNavigationState();
        } else {
            UI.getCurrent().getNavigator().navigateTo(Views.MainView);
        }
    }
}
