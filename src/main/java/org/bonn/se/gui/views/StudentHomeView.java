package org.bonn.se.gui.views;


import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import org.bonn.se.control.BewerbungControl;
import org.bonn.se.gui.component.Anzeigen;
import org.bonn.se.gui.component.Bewerbungen;
import org.bonn.se.gui.component.OrtField;
import org.bonn.se.gui.component.TopPanelUser;
import org.bonn.se.gui.window.ErweiterteSuche;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.*;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.SuchbegrifService;
import org.bonn.se.services.util.Views;

import java.sql.SQLException;
import java.util.Date;

public class StudentHomeView extends VerticalLayout implements View {

    static Grid GridAnzeig = null;
    static GridLayout Maingrid = new GridLayout(2, 5);

    public static Grid getGridAnzeig() {
        return GridAnzeig;
    }

    public static void setGridAnzeig(Grid gridAnzeig) {
        GridAnzeig = gridAnzeig;
    }

    public static GridLayout getMaingrid() {
        return Maingrid;
    }

    public static void setMaingrid(GridLayout maingrid) {
        Maingrid = maingrid;
    }



    public void setUp() throws DatabaseException, SQLException {

        Maingrid = new GridLayout(2, 5);


        Maingrid.setSizeFull();
        TopPanelUser topPanel = new TopPanelUser();

        ///HorizontalLayout hLayoutSearch = new HorizontalLayout();
        //hLayoutSearch.setSizeFull();
        //hLayoutSearch.setMargin(true);
// spruch oben
        String ls3 = "<p class=MsoNormal><b><span style='font-size:20.0pt;line-height:107%;\n" +
                "font-family:\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:\n" +
                "minor-bidi;mso-bidi-theme-font:minor-bidi;color:#2F5597;mso-themecolor:accent1;\n" +
                "mso-themeshade:191;mso-style-textfill-fill-color:#2F5597;mso-style-textfill-fill-themecolor:\n" +
                "accent1;mso-style-textfill-fill-alpha:100.0%;mso-style-textfill-fill-colortransforms:\n" +
                "lumm=75000'>Finde JETZT deinen Traumjob!<o:p></o:p></span></b></p>";

        Label lSpruch = new Label(ls3, ContentMode.HTML);

// add search bottom
        GridLayout searchGrid = new GridLayout(7, 3);
        searchGrid.setMargin(true);
        searchGrid.setSizeFull();


// add combobox
        ComboBox<String> comboNachWas = new ComboBox<>();
        comboNachWas.setPlaceholder("Nach was suchen Sie?");
        comboNachWas.setWidth(300.0f, Unit.PIXELS);

        SuchbegrifService Sservice = new SuchbegrifService();
        comboNachWas.setDataProvider(Sservice::fetch, Sservice::count);

// add combobox ortBund
        OrtField comboOrtBund = new OrtField("Ort");
        comboOrtBund.setWidth(300.0f, Unit.PIXELS);


// add combobox
        ComboBox<String> comboUmkreis = new ComboBox<>();
        comboUmkreis.setWidth(200.0f, Unit.PIXELS);
        comboUmkreis.setItems("Ganzer Ort", "+10 km", "+25 km", "+50 km", "+75 km","+100 km");
        comboUmkreis.setValue("Ganzer Ort");

        Button buttonSearch = new Button("Job finden!", VaadinIcons.SEARCH);

        buttonSearch.addClickListener(
                event -> {
                    Maingrid.removeComponent(GridAnzeig);

                    stellenSuchen( comboNachWas.getValue(), comboOrtBund.getOrt(), comboOrtBund.getBundesland(),comboUmkreis.getValue(), "Normal", null, null, null);
                    Maingrid.addComponent(GridAnzeig, 0, 2, 1, 2);
                    Maingrid.setComponentAlignment(GridAnzeig, Alignment.MIDDLE_CENTER);
                });



//job erwiterte suche button
        Button buttonErwitertSuche= new Button("Erweiterte Suche");
        buttonErwitertSuche.addStyleName("buttonErwitertSuche");

// add components in searchGrid

             searchGrid.addComponent(lSpruch,0,0,6,0);
        searchGrid.addComponent(comboNachWas,2,1,2,1);
        searchGrid.addComponent(comboOrtBund,3,1,3,1);
        searchGrid.addComponent(comboUmkreis,5,1,5,1);
        searchGrid.addComponent(buttonSearch,6,1,6,1);
 searchGrid.addComponent(buttonErwitertSuche,2,2,2,2);


        searchGrid.setComponentAlignment(comboNachWas, Alignment.BOTTOM_LEFT);
        searchGrid.setComponentAlignment(comboNachWas, Alignment.BOTTOM_LEFT);
        searchGrid.setComponentAlignment(comboOrtBund, Alignment.BOTTOM_CENTER);
        searchGrid.setComponentAlignment(buttonSearch, Alignment.BOTTOM_CENTER);
        searchGrid.setComponentAlignment(buttonErwitertSuche, Alignment.BOTTOM_LEFT);
        searchGrid.setComponentAlignment(lSpruch, Alignment.TOP_CENTER);

        buttonErwitertSuche.addClickListener(
                event -> {
                            Maingrid.removeComponent(GridAnzeig);
                            comboNachWas.clear();comboOrtBund.clear(); comboUmkreis.clear();
                            ErweiterteSuche window = new ErweiterteSuche();
                            UI.getCurrent().addWindow(window);
                });

        ContainerNeuigkeiten containerNeuigkeiten = ContainerNeuigkeiten.getInstance();
        containerNeuigkeiten.loadNeuigkeiten("Top 5");

        Anzeigen<StellenanzeigeDTO> gAnzeigen = new  Anzeigen<StellenanzeigeDTO>("Student",containerNeuigkeiten);
        gAnzeigen.setHeightMode(HeightMode.UNDEFINED);
        gAnzeigen.setWidth("705px");

        ContainerLetztenBewerbungen containerBewerbungen  = ContainerLetztenBewerbungen.getInstance();
        containerBewerbungen.load("Top 5");
        Bewerbungen<BewerbungDTO> gBewerbungen = new Bewerbungen<BewerbungDTO>(containerBewerbungen,"Student");
        gBewerbungen.setHeightMode(HeightMode.UNDEFINED);
        gBewerbungen.setWidth("705px");

//bottom grid für bewerbung und Neugkeiten
        String ls1 = "<p class=MsoNormal><b><span style='font-size:18.0pt;line-height:107%;\n" +
                "font-family:\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:\n" +
                "minor-bidi;mso-bidi-theme-font:minor-bidi;color:white;mso-themecolor:background1'>Deine\n" +
                "letzten Bewerbungsprozesse<o:p></o:p></span></b></p>";

        String ls2 = "<p class=MsoNormal><b><span style='font-size:18.0pt;line-height:107%;\n" +
                "font-family:\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:\n" +
                "minor-bidi;mso-bidi-theme-font:minor-bidi;color:white;mso-themecolor:background1'>\n" +
                "Neuigkeiten<o:p></o:p></span></b></p>";

        Label lBewerbung = new Label(ls1, ContentMode.HTML);
        Label lNeuigkeit = new Label(ls2, ContentMode.HTML);

        // button für bottomGridBewNeu
        Button alleBewerbungen = new Button("Alle Bewerbungen", VaadinIcons.SEARCH);

        alleBewerbungen.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().getNavigator().navigateTo(Views.AlleBewerbungenView);
                System.out.println("stuhomeview hier 1");
            }
        });

        Button alleNeuigkeiten = new Button("Alle Neuigkeiten", VaadinIcons.SEARCH);

        Button meineAbos = new Button("Meine Abos", VaadinIcons.SEARCH);

        Label lPatzhalter = new Label("&nbsp", ContentMode.HTML);

        GridLayout bottomGridBewNeu = new GridLayout(4, 4);
        bottomGridBewNeu.setSizeFull();
        bottomGridBewNeu.addStyleName("bottomGridBewNeu");
        bottomGridBewNeu.setMargin(true);



                bottomGridBewNeu.addComponent(lBewerbung,0,0,1,0);
                bottomGridBewNeu.addComponent(lNeuigkeit,2,0,3,0);
        bottomGridBewNeu.addComponent(gBewerbungen,0,1,1,1);
                bottomGridBewNeu.addComponent(gAnzeigen,2,1,3,1);
               bottomGridBewNeu.addComponent(lPatzhalter,0,2,3,2);
           bottomGridBewNeu.addComponent(alleBewerbungen,0,3,0,3);
           bottomGridBewNeu.addComponent(alleNeuigkeiten,2,3,2,3);
                 bottomGridBewNeu.addComponent(meineAbos,3,3,3,3);


        bottomGridBewNeu.setComponentAlignment(lBewerbung,Alignment.TOP_CENTER);
        bottomGridBewNeu.setComponentAlignment(lNeuigkeit,Alignment.TOP_CENTER);
        bottomGridBewNeu.setComponentAlignment(gBewerbungen,Alignment.TOP_CENTER);
        bottomGridBewNeu.setComponentAlignment(gAnzeigen,Alignment.TOP_CENTER);
        bottomGridBewNeu.setComponentAlignment(alleBewerbungen,Alignment.BOTTOM_CENTER);
        bottomGridBewNeu.setComponentAlignment(alleNeuigkeiten,Alignment.BOTTOM_CENTER);
        bottomGridBewNeu.setComponentAlignment(meineAbos,Alignment.BOTTOM_CENTER);

             Maingrid.addComponent(topPanel, 0, 0, 1, 0);
           Maingrid.addComponent(searchGrid, 0, 1, 1, 1);
     Maingrid.addComponent(bottomGridBewNeu, 0, 3, 1, 3);

        Maingrid.setComponentAlignment(topPanel, Alignment.TOP_CENTER);
        Maingrid.setComponentAlignment(searchGrid, Alignment.TOP_CENTER);
        Maingrid.setComponentAlignment(bottomGridBewNeu, Alignment.TOP_CENTER);


        this.addComponent(Maingrid);
        this.setComponentAlignment(Maingrid, Alignment.TOP_CENTER);
        this.setMargin(false);
        this.addStyleName("grid");

        loadProfil();

    }

    public void loadProfil() throws DatabaseException, SQLException {
        BewerbungControl.checkDeletedAnzeige();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {


        if (UI.getCurrent().getSession().getAttribute(Roles.Student) != null) {
            try {
                this.setUp();
            } catch (DatabaseException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (UI.getCurrent().getSession().getAttribute(Roles.Unternehmen) != null) {
            UI.getCurrent().getNavigator().getCurrentNavigationState();
        } else {
            UI.getCurrent().getNavigator().navigateTo(Views.MainView);
        }
    }

    public static void stellenSuchen( String fachgebiet, String standort, String bundesland, String umkreis,
                                      String artSuche, String einstellungsart, Date ab_Datum, String branche) {

        ContainerNeuigkeiten container = ContainerNeuigkeiten.getInstance();
        container.loadSuche(fachgebiet, standort, bundesland, umkreis, artSuche, einstellungsart, ab_Datum, branche);

        Anzeigen<StellenanzeigeDTO> gAnzeigen = new  Anzeigen<StellenanzeigeDTO>("Student",container);
        gAnzeigen.setHeightMode(HeightMode.UNDEFINED);
        gAnzeigen.setWidth("1000px");
        GridAnzeig = gAnzeigen;

    }

}
