
package org.bonn.se.gui.views;

import com.vaadin.data.HasValue;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import org.bonn.se.control.BewerbungControl;
import org.bonn.se.control.FeatureToggleControl;
import org.bonn.se.control.Suche;
import org.bonn.se.control.SucheControlProxy;
import org.bonn.se.gui.component.Anzeigen;
import org.bonn.se.gui.component.Bewerbungen;
import org.bonn.se.gui.component.OrtField;
import org.bonn.se.gui.component.TopPanelUser;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.gui.window.StellenanzeigeWindow;
import org.bonn.se.model.dao.UserDAO;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.ContainerLetztenBewerbungen;
import org.bonn.se.model.objects.entitites.ContainerNeuigkeiten;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.BrancheService;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.SuchbegrifService;
import org.bonn.se.services.util.Views;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class StudentHomeView extends VerticalLayout implements View {

    static Grid gridAnzeige = null;
    static GridLayout mainGrid = new GridLayout(2, 5);

    public static Grid getGridAnzeige() {
        return gridAnzeige;
    }


    public static GridLayout getMaingrid() {
        return mainGrid;
    }


    static GridLayout bottomGridBewNeu;
    private String suchArt = "Einfache";
    ///Test
    final Suche suche = new SucheControlProxy();


    public void setUp() throws DatabaseException, SQLException {

        mainGrid = new GridLayout(2, 7);
        mainGrid.setSizeFull();
        TopPanelUser topPanel = new TopPanelUser();



// spruch oben
        String ls3 = "<p class=MsoNormal><b><span style='font-size:20.0pt;line-height:107%;\n" +
                "font-family:\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:\n" +
                "minor-bidi;mso-bidi-theme-font:minor-bidi;color:#2F5597;mso-themecolor:accent1;\n" +
                "mso-themeshade:191;mso-style-textfill-fill-color:#2F5597;mso-style-textfill-fill-themecolor:\n" +
                "accent1;mso-style-textfill-fill-alpha:100.0%;mso-style-textfill-fill-colortransforms:\n" +
                "lumm=75000'>Finde JETZT deinen Traumjob!<o:p></o:p></span></b></p>";

        Label lSpruch = new Label(ls3, ContentMode.HTML);

        GridLayout searchGrid = new GridLayout(7, 4);
        searchGrid.setMargin(true);
        searchGrid.setSizeFull();

//--------------------------------------------SUCHE------------------------------------------------------------
        String ganzerOrt = "Ganzer Ort";
        //Titel
        ComboBox<String> comboNachWas = new ComboBox<>();
        comboNachWas.setPlaceholder("Nach was suchen Sie?");
        comboNachWas.setWidth(300.0f, Unit.PIXELS);
        SuchbegrifService Sservice = new SuchbegrifService();
        comboNachWas.setDataProvider(Sservice::fetch, Sservice::count);

        //Ort
        OrtField comboOrtBund = new OrtField("Ort");
        comboOrtBund.setWidth(300.0f, Unit.PIXELS);

        //Umkreis
        ComboBox<String> comboUmkreis = new ComboBox<>();
        comboUmkreis.setWidth(200.0f, Unit.PIXELS);
        comboUmkreis.setPlaceholder("Umkreis");
        comboUmkreis.setItems(ganzerOrt, "+10 km", "+25 km", "+50 km", "+75 km","+100 km");
        comboUmkreis.setValue(ganzerOrt);

        //Erweiterte Suche
        CheckBox erwSuche = new CheckBox("Erweiterte Suche");
        ComboBox<String> comboEinstellungsart = new ComboBox<>();
        ComboBox<String> ComboBranche = new ComboBox<>();
        DateField wann_datum = new DateField();
        comboEinstellungsart.setVisible(false);
        ComboBranche.setVisible(false);
        wann_datum.setVisible(false);

        //GridLayout Suche
        searchGrid.addComponent(lSpruch,0,0,6,0);
        searchGrid.addComponent(comboNachWas,2,1);
        searchGrid.addComponent(comboOrtBund,3,1);
        searchGrid.addComponent(comboUmkreis,4,1);
        searchGrid.addComponent(erwSuche,6,3);
        searchGrid.addComponent(comboEinstellungsart,2,2);
        searchGrid.addComponent(ComboBranche,3,2);
        searchGrid.addComponent(wann_datum,4,2);

        searchGrid.setComponentAlignment(comboNachWas, Alignment.BOTTOM_LEFT);
        searchGrid.setComponentAlignment(comboOrtBund, Alignment.BOTTOM_LEFT);
        searchGrid.setComponentAlignment(comboUmkreis, Alignment.BOTTOM_LEFT);
        searchGrid.setComponentAlignment(lSpruch, Alignment.TOP_CENTER);

        //Ergebnis Tabelle
        Grid<StellenanzeigeDTO> grid1 = new Grid<>();

        //Erweiterte Suche An/Aus
        erwSuche.addValueChangeListener((HasValue.ValueChangeListener<Boolean>) event -> {
            if(erwSuche.getValue()) {
                suchArt = "Erweitert";

                //Art
                comboEinstellungsart.setItems("Feste Anstellung","Befristeter Vertrag","Praktikum","Werkstudent",
                        "Praktikum/Werkstudent","Trainee","Ausbildung/Studium",
                        "Bachelor-/Master-/Diplom-Arbeiten","Promotion/Habilitation","Freie Mitarbeit/Projektmitarbeit");
                comboEinstellungsart.setPlaceholder("Einstellungsart");
                comboEinstellungsart.setHeight("56px");
                comboEinstellungsart.setWidth("300px");

                //Branche
                ComboBranche.setPlaceholder("Branche");
                ComboBranche.setHeight("56px");
                ComboBranche.setWidth("300px");
                BrancheService SserviceBranche = null;
                try {
                    SserviceBranche = new BrancheService();
                } catch (DatabaseException e) {
                    Logger.getLogger(StudentHomeView.class.getName()).log(Level.SEVERE,null,e);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                ComboBranche.setDataProvider(SserviceBranche::fetch, SserviceBranche::count);

                //Datum
                wann_datum.setHeight("56px");
                wann_datum.setWidth("200px");
                wann_datum.setPlaceholder("ab Wann? dd.mm.yyyy");
                LocalDate emptyDate = LocalDate.parse("0001-01-01");


                //EVENTUELL NOCH FOR-SCHLEIFE HIER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                comboEinstellungsart.addValueChangeListener((HasValue.ValueChangeListener<String>) event1 -> {
                    DataProvider<StellenanzeigeDTO,Void> dataProvider = suche.einfacheSuche(comboNachWas.getValue(),comboOrtBund.getOrt(),comboOrtBund.getBundesland(),comboUmkreis.getValue(),suchArt,comboEinstellungsart.getValue(),null,ComboBranche.getValue());
                    grid1.setDataProvider(dataProvider);
                });

                ComboBranche.addValueChangeListener((HasValue.ValueChangeListener<String>) event1 -> {

                });
                wann_datum.addValueChangeListener((HasValue.ValueChangeListener<LocalDate>) event2 -> {
                    DataProvider<StellenanzeigeDTO,Void> dataProvider = suche.einfacheSuche(comboNachWas.getValue(),comboOrtBund.getOrt(),comboOrtBund.getBundesland(),comboUmkreis.getValue(),suchArt,comboEinstellungsart.getValue(),null,ComboBranche.getValue());

                    grid1.setDataProvider(dataProvider);
                });
                //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


                comboEinstellungsart.setVisible(true);
                ComboBranche.setVisible(true);
                wann_datum.setVisible(true);
            } else {
                comboEinstellungsart.setVisible(false);
                ComboBranche.setVisible(false);
                wann_datum.setVisible(false);
                comboEinstellungsart.clear();
                ComboBranche.clear();
                wann_datum.clear();
                suchArt = "Einfache";
            }
        });

        //Layout für Margin
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidthFull();
        verticalLayout.setMargin(true);
        mainGrid.addComponent(verticalLayout,0,2,1,2);
        verticalLayout.addComponent(grid1);

        //Spalten hinzufügen
        grid1.setWidthFull();
        grid1.setVisible(false);
        grid1.addComponentColumn(StellenanzeigeDTO::getUnternehmenLogo).setCaption("Logo").setWidth(80.0);
        grid1.addColumn(StellenanzeigeDTO::getFirmenname).setCaption("Firmenname");
        grid1.addColumn(StellenanzeigeDTO::getTitel).setCaption("Titel");
        grid1.addColumn(StellenanzeigeDTO::getArt).setCaption("Art");
        grid1.addColumn(StellenanzeigeDTO::getSuchbegriff).setCaption("Berufsbezeichnung");
        grid1.addColumn(StellenanzeigeDTO::getStandort).setCaption("Standort");
        grid1.addColumn(StellenanzeigeDTO::getDatum).setCaption("Einstellungsdatum").setWidth(150.0);

        //ValueChangeListener für Suche
        for (int i = 0; i < 3; i++) {
            ((ComboBox)searchGrid.getComponent(i+2,1)).addValueChangeListener((HasValue.ValueChangeListener) event -> {

                //Datenabfrage

                DataProvider<StellenanzeigeDTO,Void> dataProvider = suche.einfacheSuche(comboNachWas.getValue(),comboOrtBund.getOrt(),comboOrtBund.getBundesland(),comboUmkreis.getValue(),suchArt,comboEinstellungsart.getValue(),null,ComboBranche.getValue());
                grid1.setDataProvider(dataProvider);
                grid1.setCaption("Anzahl der Ergebisse: " + suche.getRowsCount());
                grid1.setVisible(true);

            });
        }
        SingleSelect<StellenanzeigeDTO> selection = grid1.asSingleSelect();

        //Selektieren der Anzeige
        grid1.addSelectionListener((SelectionListener<StellenanzeigeDTO>) event -> {
           StellenanzeigeDTO temp = selection.getValue();
            Unternehmen unternehmen = null;
            try {
                unternehmen =  UserDAO.getUnternehmenByStellAnz(temp);
            } catch (DatabaseException e) {
                e.printStackTrace();
            }

          UI.getCurrent().addWindow(new StellenanzeigeWindow(temp,unternehmen));

        });

//---------------SUCHE ENDE ------------------------------------------------------------------------------------------


        ContainerNeuigkeiten containerNeuigkeiten = ContainerNeuigkeiten.getInstance();
        containerNeuigkeiten.loadNeuigkeiten("Alle");
        List<StellenanzeigeDTO> dataTop5 = containerNeuigkeiten.getListe().stream().limit(5)
                .collect(Collectors.toList());

        Anzeigen<StellenanzeigeDTO> gAnzeigen = new Anzeigen<>("Student", dataTop5);
        gAnzeigen.setHeightMode(HeightMode.UNDEFINED);
        gAnzeigen.setWidth("705px");



//bottom grid für bewerbung und Neugkeiten
        String ls1 = "<p class=MsoNormal><b><span style='font-size:18.0pt;line-height:107%;\n" +
                "font-family:\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:\n" +
                "minor-bidi;mso-bidi-theme-font:minor-bidi;color:white;mso-themecolor:background1'>Deine\n" +
                "letzten Bewerbungsprozesse<o:p></o:p></span></b></p>";

        String ls2 = "<p class=MsoNormal><b><span style='font-size:18.0pt;line-height:107%;\n" +
                "font-family:\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:\n" +
                "minor-bidi;mso-bidi-theme-font:minor-bidi;color:white;mso-themecolor:background1'>\n" +
                "Neuigkeiten<o:p></o:p></span></b></p>";

        Label lNeuigkeit = new Label(ls2, ContentMode.HTML);

        // button für bottomGridBewNeu



        Button alleNeuigkeiten = new Button("Alle Neuigkeiten", VaadinIcons.SEARCH);

        Button meineAbos = new Button("Meine Abos", VaadinIcons.SEARCH);

        Label lPatzhalter = new Label("&nbsp", ContentMode.HTML);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthFull();
        bottomGridBewNeu = new GridLayout(2, 4);
        horizontalLayout.addComponents(bottomGridBewNeu);
        horizontalLayout.setComponentAlignment(bottomGridBewNeu,Alignment.MIDDLE_CENTER);
        bottomGridBewNeu.setSizeFull();
        horizontalLayout.addStyleName("bottomGridBewNeu");
        horizontalLayout.setMargin(true);
        bottomGridBewNeu.setMargin(true);



        bottomGridBewNeu.addComponent(lNeuigkeit,0,0,1,0);
        bottomGridBewNeu.addComponent(gAnzeigen,0,1,1,1);
        bottomGridBewNeu.addComponent(lPatzhalter,0,2,1,2);
        bottomGridBewNeu.addComponent(alleNeuigkeiten,0,3);
        bottomGridBewNeu.addComponent(meineAbos,1,3);


        bottomGridBewNeu.setComponentAlignment(lNeuigkeit,Alignment.TOP_CENTER);
        bottomGridBewNeu.setComponentAlignment(gAnzeigen,Alignment.TOP_CENTER);
        bottomGridBewNeu.setComponentAlignment(alleNeuigkeiten,Alignment.BOTTOM_CENTER);
        bottomGridBewNeu.setComponentAlignment(meineAbos,Alignment.BOTTOM_CENTER);



        mainGrid.addComponent(topPanel, 0, 0, 1, 0);
        mainGrid.addComponent(searchGrid, 0, 1, 1, 1);
        mainGrid.addComponent(horizontalLayout, 0, 4, 1, 4);

        mainGrid.setComponentAlignment(topPanel, Alignment.TOP_CENTER);
        mainGrid.setComponentAlignment(searchGrid, Alignment.TOP_CENTER);
       // Maingrid.setComponentAlignment(bottomGridBewNeu, Alignment.TOP_CENTER);

        this.addComponent(mainGrid);
        this.setComponentAlignment(mainGrid, Alignment.TOP_CENTER);
        this.setMargin(false);
        this.addStyleName("grid");

        loadProfil();

        //FeatureToggle


        if(FeatureToggleControl.getInstance().featureIsEnabled("BEWERBUNGEN")) {
            UI.getCurrent().access(() -> {
                GridLayout bottomGridBewNeu_2 = new GridLayout(2, 4);
                bottomGridBewNeu_2.setMargin(true);
                bottomGridBewNeu_2.setSizeFull();
                Label lBewerbung = new Label(ls1, ContentMode.HTML);
                Label lPatzhalter1 = new Label("&nbsp", ContentMode.HTML);

                bottomGridBewNeu_2.addComponent(lBewerbung,0,0,1,0);
                bottomGridBewNeu_2.setComponentAlignment(lBewerbung,Alignment.TOP_CENTER);

                ContainerLetztenBewerbungen containerBewerbungen  = ContainerLetztenBewerbungen.getInstance();
                Student student = ((Student) MyUI.getCurrent().getSession().getAttribute(Roles.Student));
                containerBewerbungen.load("Top 5",student.getEmail());
                Bewerbungen<BewerbungDTO> gBewerbungen = new Bewerbungen<>(containerBewerbungen, "StudentHomeView");
                gBewerbungen.setHeightMode(HeightMode.UNDEFINED);
                gBewerbungen.setWidth("705px");
                bottomGridBewNeu_2.addComponent(gBewerbungen,0,1,1,1);
                bottomGridBewNeu_2.setComponentAlignment(gBewerbungen,Alignment.TOP_CENTER);

                Button alleBewerbungen = new Button("Alle Bewerbungen", VaadinIcons.SEARCH);
                bottomGridBewNeu_2.addComponent(lPatzhalter1,0,2,1,2);
                bottomGridBewNeu_2.addComponent(alleBewerbungen,0,3,1,3);
                bottomGridBewNeu_2.setComponentAlignment(alleBewerbungen,Alignment.BOTTOM_CENTER);
                horizontalLayout.addComponent(bottomGridBewNeu_2,0);
                horizontalLayout.setComponentAlignment(bottomGridBewNeu_2,Alignment.MIDDLE_CENTER);
                alleBewerbungen.addClickListener((Button.ClickListener) clickEvent -> UI.getCurrent().getNavigator().navigateTo(Views.AlleBewerbungenView));
            });
        }
    }

    public void loadProfil() throws DatabaseException {
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
                Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
            }
        } else if (UI.getCurrent().getSession().getAttribute(Roles.Unternehmen) != null) {
            UI.getCurrent().getNavigator().getCurrentNavigationState();
        } else {
            UI.getCurrent().getNavigator().navigateTo(Views.LoginView);
        }
    }

    public static void stellenSuchen( String fachgebiet, String standort, String bundesland, String umkreis,
                                      String artSuche, String einstellungsart, Date abDatum, String branche) {

        ContainerNeuigkeiten container = ContainerNeuigkeiten.getInstance();
        container.loadSuche(fachgebiet, standort, bundesland, umkreis, artSuche, einstellungsart, abDatum, branche);


        Anzeigen<StellenanzeigeDTO> gAnzeigen = new Anzeigen<>("Student", container.getListe());
        gAnzeigen.setHeightMode(HeightMode.UNDEFINED);
        gAnzeigen.setWidth("1000px");
        gridAnzeige = gAnzeigen;

    }

// --Commented out by Inspection START (22.06.20, 23:45):
//    public static void stellenSuchenOnFly(String ortBund, String suchbegrif, String art, String branche, String beginDatum) {
//
//        List<StellenanzeigeDTO> data = containerOnFly.getListe().stream().peek(c -> {
//            if (c.getSuchbegriff() == null){c.setSuchbegriff("");}
//            if (c.getStandortBundesland() == null){c.setStandort("");}
//            if (c.getArt() == null){c.setArt("");}
//            if (c.getBranche() == null){c.setBranche("");}
//
//        }).filter( suche -> suche.getStandortBundesland().equals(ortBund) || suche.getStandortBundesland().equals("")
//                         || (suche.getSuchbegriff().equals(suchbegrif) ||  suche.getSuchbegriff().equals(""))
//                         || (suche.getSuchbegriff().equals(suchbegrif) ||  suche.getSuchbegriff().equals("")) && suche.getStandortBundesland().equals(ortBund) || suche.getStandortBundesland().equals("")
//                 ).collect(Collectors.toList());
//
//
//        Anzeigen<StellenanzeigeDTO> gAnzeigen = new  Anzeigen<StellenanzeigeDTO>("Student",data);
//        gAnzeigen.setHeightMode(HeightMode.UNDEFINED);
//        gAnzeigen.setWidth("1000px");
//        gridAnzeige = gAnzeigen;
//
//    }
// --Commented out by Inspection STOP (22.06.20, 23:45)

}