package org.bonn.se.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.bonn.se.control.FeatureToggleControl;
import org.bonn.se.gui.component.Anzeigen;
import org.bonn.se.gui.component.TopPanelUser;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.ContainerNeuigkeiten;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class UnternehmenHomeView extends VerticalLayout implements View {


    private TabSheet.Tab bewerbung;
    public void setUp() throws DatabaseException, SQLException {


        TopPanelUser topPanel = new TopPanelUser();

        GridLayout mainGrid = new GridLayout(3, 3);
        mainGrid.setSizeFull();

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
        ((Unternehmen)MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).setStellenanzeigenDTOliste((ArrayList<StellenanzeigeDTO>) containerMeinAnzeigen.getListe());

        Anzeigen<StellenanzeigeDTO> gAnzeigen = new  Anzeigen<StellenanzeigeDTO>("Alle",containerMeinAnzeigen.getListe());
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
        tabSheet.addTab(gAnzeigen,"Alle "+gAnzeigen.getAnzahlRow());


        Anzeigen<StellenanzeigeDTO> gAnzeigenOnline = new  Anzeigen<StellenanzeigeDTO>("Alle",containerMeinAnzeigen.getListe());
        gAnzeigenOnline.setData(gAnzeigen.getData().stream().filter(c -> c.getStatus() ==1).collect(Collectors.toList()));
        gAnzeigenOnline.setSizeFull();
        tabSheet.addTab(gAnzeigenOnline,"Online "+gAnzeigenOnline.getAnzahlRow());

        Anzeigen<StellenanzeigeDTO> gAnzeigenOffline = new  Anzeigen<StellenanzeigeDTO>("Alle",containerMeinAnzeigen.getListe());
        gAnzeigenOffline.setData(gAnzeigen.getData().stream().filter(c -> c.getStatus() ==2).collect(Collectors.toList()));
        gAnzeigenOffline.setSizeFull();
        tabSheet.addTab(gAnzeigenOffline,"Offline "+gAnzeigenOffline.getAnzahlRow());

        Anzeigen<StellenanzeigeDTO> gAnzeigenEntwurf = new  Anzeigen<StellenanzeigeDTO>("Alle",containerMeinAnzeigen.getListe());
        gAnzeigenEntwurf.setData(gAnzeigen.getData().stream().filter(c -> c.getStatus() ==3).collect(Collectors.toList()));
        gAnzeigenEntwurf.setSizeFull();
        tabSheet.addTab(gAnzeigenEntwurf,"Entwurf "+gAnzeigenEntwurf.getAnzahlRow());




        bottomGridBewNeu.addComponent(lBewerbung,0,0,0,0);
        bottomGridBewNeu.addComponent(tabSheet,0,1,0,1);

        bottomGridBewNeu.setComponentAlignment(lBewerbung, Alignment.BOTTOM_CENTER);
        bottomGridBewNeu.setComponentAlignment(tabSheet, Alignment.BOTTOM_CENTER);
        bottomGridBewNeu.setSizeFull();
        bottomGridBewNeu.setMargin(true);



        mainGrid.addComponent(topPanel, 0, 0, 2, 0);
        mainGrid.addComponent(vlayoutButton, 1, 1, 1, 1);
        mainGrid.addComponent(bottomGridBewNeu, 0, 2, 2, 2);


        mainGrid.setComponentAlignment(topPanel, Alignment.TOP_CENTER);
        mainGrid.setComponentAlignment(vlayoutButton, Alignment.MIDDLE_CENTER);
        mainGrid.setComponentAlignment(bottomGridBewNeu, Alignment.BOTTOM_CENTER);


        this.addComponent(mainGrid);
        this.setComponentAlignment(mainGrid, Alignment.MIDDLE_CENTER);
        this.setMargin(false);
        this.addStyleName("grid");

        String anzahlNeueBewerbungen = "Anzahl neue Bewerbungen";
        gAnzeigen.removeColumn(anzahlNeueBewerbungen);
        gAnzeigenOnline.removeColumn(anzahlNeueBewerbungen);
        gAnzeigenOffline.removeColumn(anzahlNeueBewerbungen);
        gAnzeigenEntwurf.removeColumn(anzahlNeueBewerbungen);

        if(FeatureToggleControl.getInstance().featureIsEnabled("BEWERBUNGEN")) {

            UI.getCurrent().access(() -> {

                containerMeinAnzeigen.loadNeuBewerbungen(unternehmen);
                ((Unternehmen) MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).setStellenanzeigenDTOliste((ArrayList<StellenanzeigeDTO>) containerMeinAnzeigen.getListe());
                Anzeigen<StellenanzeigeDTO> gAnzeigenNeuBewerbungen = new Anzeigen<StellenanzeigeDTO>("Alle", containerMeinAnzeigen.getListe());
                gAnzeigenNeuBewerbungen.setSizeFull();
                gAnzeigenNeuBewerbungen.removeColumn("Status");


                if (gAnzeigenNeuBewerbungen.getData().size() > 0) {
                    List<StellenanzeigeDTO> data = gAnzeigenNeuBewerbungen.getData().stream().filter(c -> c.getStatus() == 1).collect(Collectors.toList());
                    int gesamtAnzahl = 0;
                    for (StellenanzeigeDTO sa : data) {
                        gesamtAnzahl += sa.getanzahlNeuBewerbung();
                    }

                    ThemeResource resource = new ThemeResource("img/Anzeigen/rot_klein.png");
                    bewerbung = tabSheet.addTab(gAnzeigenNeuBewerbungen, "Neue Bewerbungen " + gesamtAnzahl, resource);
                } else {
                    bewerbung = tabSheet.addTab(gAnzeigenNeuBewerbungen, "Neue Bewerbungen 0");
                }
            });
            }
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        if (UI.getCurrent().getSession().getAttribute(Roles.Unternehmen) != null) {
            try {
                this.setUp();
            } catch (DatabaseException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
            }
        } else if (UI.getCurrent().getSession().getAttribute(Roles.Student) != null) {
            UI.getCurrent().getNavigator().getCurrentNavigationState();
        } else {
            UI.getCurrent().getNavigator().navigateTo(Views.LoginView);
        }
    }
}
