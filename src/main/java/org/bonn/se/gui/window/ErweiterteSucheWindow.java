package org.bonn.se.gui.window;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se.gui.component.OrtField;
import org.bonn.se.gui.views.StudentHomeView;
import org.bonn.se.services.util.*;

import java.sql.Date;
import java.time.LocalDate;

public class ErweiterteSucheWindow extends Window {
    GridLayout GridAnzeig = null;
    public ErweiterteSucheWindow() {
        setWindow();
    }
    private void setWindow() {
        center();
        this.setHeight("80%");
        this.setWidth("80%");
        this.setDraggable(false);
        this.setResizable(false);
        GridLayout gridLayout = new GridLayout(1, 3);
        gridLayout.setHeight("100%");
        gridLayout.setWidth("100%");

        this.addCloseListener((CloseListener) e -> UI.getCurrent().getNavigator().navigateTo(Views.StudentHomeView));

        Label head = new Label("<h1><p><font color=\"blue\">Erweiterte Suche\n</font></p></h1>", ContentMode.HTML);
        head.setHeight("70px");
        head.setWidth("300px");


// add combobox
        ComboBox<String> comboNachWas = new ComboBox<>();
        comboNachWas.setPlaceholder("Nach was suchen Sie?");
        SuchbegrifService Sservice = new SuchbegrifService();
        comboNachWas.setDataProvider(Sservice::fetch, Sservice::count);
        comboNachWas.setHeight("56px");
        comboNachWas.setWidth("300px");


// add combobox ortBund
        OrtField comboOrtBund = new OrtField("Ort");
        comboOrtBund.setHeight("56px");
        comboOrtBund.setWidth("300px");

// add combo umkreis

        ComboBox<String> comboUmkreis = new ComboBox<>();
        comboUmkreis.setWidth(200.0f, Unit.PIXELS);
        comboUmkreis.setItems("Ganzer Ort", "+10 km", "+25 km", "+50 km", "+75 km","+100 km");
        comboUmkreis.setValue("Ganzer Ort");
        comboUmkreis.setHeight("56px");
        comboUmkreis.setWidth("200px");


        ComboBox<String> comboEinstellungsart = new ComboBox<>();
        comboEinstellungsart.setItems("Feste Anstellung","Befristeter Vertrag","Praktikum","Werkstudent",
                                        "Praktikum/Werkstudent","Trainee","Ausbildung/Studium",
                                        "Bachelor-/Master-/Diplom-Arbeiten","Promotion/Habilitation","Freie Mitarbeit/Projektmitarbeit");
        comboEinstellungsart.setPlaceholder("Einstellungsart");
        comboEinstellungsart.setHeight("56px");
        comboEinstellungsart.setWidth("300px");


        ComboBox<String> ComboBranche = new ComboBox<>();
        ComboBranche.setPlaceholder("Branche");
        ComboBranche.setHeight("56px");
        ComboBranche.setWidth("300px");
        BrancheService SserviceBranche = new BrancheService();
        ComboBranche.setDataProvider(SserviceBranche::fetch, SserviceBranche::count);

        //Datum
        DateField wann_datum = new DateField();
        wann_datum.setHeight("56px");
        wann_datum.setWidth("200px");
        wann_datum.setPlaceholder("ab Wann? dd.mm.yyyy");
        LocalDate emptyDate = LocalDate.parse("0001-01-01");

        Button buttonSearch = new Button("Job finden!", VaadinIcons.SEARCH);

        buttonSearch.addClickListener(
                event -> {

                            StudentHomeView.stellenSuchen(comboNachWas.getValue(), comboOrtBund.getOrt(), comboOrtBund.getBundesland(),comboUmkreis.getValue(),"Erweitert",
                                                          comboEinstellungsart.getValue(), Date.valueOf(wann_datum.getValue()== null? emptyDate :wann_datum.getValue()), ComboBranche.getValue()
                                                         );
                             ErweiterteSucheWindow.this.close();
                             UI.getCurrent().getNavigator().navigateTo(Views.StudentHomeView);

                            StudentHomeView.getMaingrid().addComponent(StudentHomeView.getGridAnzeige(), 0, 2, 1, 2);
                            StudentHomeView.getMaingrid().setComponentAlignment(StudentHomeView.getGridAnzeige(), Alignment.MIDDLE_CENTER);
                         });

        Label lPatzhalter = new Label("&nbsp", ContentMode.HTML);
        Label lPatzhalter2 = new Label("&nbsp", ContentMode.HTML);

        HorizontalLayout hori = new HorizontalLayout();
        hori.addComponents(lPatzhalter,comboNachWas, comboOrtBund, comboUmkreis);

        hori.setComponentAlignment(comboNachWas,Alignment.TOP_CENTER);
        hori.setComponentAlignment(comboOrtBund,Alignment.TOP_CENTER);
        hori.setComponentAlignment(comboUmkreis,Alignment.TOP_CENTER);


        HorizontalLayout hor = new HorizontalLayout();
        hor.addComponents(lPatzhalter2,comboEinstellungsart, ComboBranche,wann_datum, buttonSearch);

        hor.setComponentAlignment(comboEinstellungsart,Alignment.TOP_CENTER);
        hor.setComponentAlignment(wann_datum,Alignment.TOP_CENTER);
        hor.setComponentAlignment(ComboBranche,Alignment.TOP_CENTER);



           gridLayout.addComponent(head, 0, 0, 0, 0);
            gridLayout.addComponent(hori, 0, 1, 0, 1);
             gridLayout.addComponent(hor, 0, 2, 0, 2);

        setContent(gridLayout);
        gridLayout.setComponentAlignment(head, Alignment.TOP_CENTER);
        gridLayout.setComponentAlignment(hori, Alignment.TOP_LEFT);
        gridLayout.setComponentAlignment(hor, Alignment.TOP_LEFT);

    }



}
