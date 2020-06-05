package org.bonn.se.gui.views;


import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se.gui.component.RegistrationTextField;
import org.bonn.se.gui.component.TopPanel;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.model.objects.entitites.Stellenanzeige;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.model.objects.entitites.User;
import org.bonn.se.services.util.*;

public class AnzeigeErstellen extends GridLayout implements View {

    public void setUp() {


        this.setRows(4);
        this.setColumns(10);


        this.addStyleName("anzeige");
        this.setSizeFull();

        GridLayout formGrid = new GridLayout(2,6);
        formGrid.addStyleName("anzeigeErstellen_formgrid");

        formGrid.setMargin(true);


        TopPanel topPanel =  new TopPanel("Für Unternehmen");
        topPanel.addStyleName("toppanel");


        Label label = new Label("<h1>Allgemeine Angaben</h1>",ContentMode.HTML);
        RegistrationTextField titel = new RegistrationTextField("Titel der Anzeige");
        titel.setWidth("600px");

        RegistrationTextField stellenbeschreibung = new RegistrationTextField("Stellenbeschreibung");
        stellenbeschreibung.setWidth("600px");

        ComboBox<String> ort = new ComboBox<>();
        ort.setPlaceholder("Ort");
        ort.setWidth(300.0f, Unit.PIXELS);
        ort.setHeight("56px");

        OrtService Ortservice = new OrtService("Stadt - Bund");
        ort.setDataProvider(Ortservice::fetch, Ortservice::count);


        DateField beginn = new DateField();
        beginn.setHeight("56px");
        beginn.setWidth("200px");
        beginn.setPlaceholder("Beginn");

        ComboBox<String> art = new ComboBox<>();
        art.setItems(DatenUnternehmenProfil.einstellungsart);
        art.setHeight("56px");
        art.setWidth("350px");
        art.setPlaceholder("Art der Einstellung");

        Button cancel = new Button("Abbrechen");

        Button weiter = new Button("Weiter zur Stellenbeschreibung");
        weiter.setEnabled(false);


        this.addComponent(topPanel, 0, 0, 9, 1);

        this.addComponent(formGrid, 2, 2, 7, 2);

        formGrid.addComponent(label,0,0,1,0);
        formGrid.setComponentAlignment(label,Alignment.MIDDLE_CENTER);

        formGrid.addComponent(titel,0,1,1,1);
        formGrid.setComponentAlignment(titel,Alignment.MIDDLE_CENTER);

        formGrid.addComponent(stellenbeschreibung,0,2,1,2);
        formGrid.setComponentAlignment(stellenbeschreibung,Alignment.MIDDLE_CENTER);

        formGrid.addComponent(ort,0,3,1,3);
        formGrid.setComponentAlignment(ort,Alignment.MIDDLE_LEFT);

        formGrid.addComponent(beginn,0,4,0,4);
        formGrid.setComponentAlignment(beginn,Alignment.MIDDLE_LEFT);

        formGrid.addComponent(art,1,4,1,4);
        formGrid.setComponentAlignment(art,Alignment.MIDDLE_RIGHT);

        formGrid.addComponent(cancel,0,5,0,5);
        formGrid.setComponentAlignment(cancel,Alignment.MIDDLE_CENTER);

        formGrid.addComponent(weiter,1,5,1,5);
        formGrid.setComponentAlignment(weiter,Alignment.MIDDLE_CENTER);

        formGrid.setSpacing(true);


        this.setComponentAlignment(topPanel, Alignment.TOP_LEFT);
        this.setComponentAlignment(formGrid,Alignment.MIDDLE_CENTER);
        this.setMargin(false);



        cancel.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo(Views.UnternehmenHomeView);

            }
        });


        Binder<Stellenanzeige> binder = new Binder<>(Stellenanzeige.class);
        binder.forField(titel)
                .asRequired("Titel muss vergeben werden!")
                .bind(Stellenanzeige::getTitel,Stellenanzeige::setTitel);

        binder.forField(stellenbeschreibung)
                .asRequired("Bitte wählen Sie eine Stellenbeschreibung aus!")
                .bind(Stellenanzeige::getBeschreibung,Stellenanzeige::setBeschreibung);

        binder.forField(ort)
                .asRequired("Bitte geben Sie einen Standort ein!")
                .bind(Stellenanzeige::getStandort,Stellenanzeige::setStandort);

        binder.forField(beginn)
                .asRequired("Bitte Einstellungsdatum eingeben!")
                .bind(Stellenanzeige::getDatum,Stellenanzeige::setDatum);

        binder.forField(art)
                .asRequired("Bitte Art der Einstellung festlegen!")
                .bind(Stellenanzeige::getArt,Stellenanzeige::setArt);

        Stellenanzeige stellenanzeige = new Stellenanzeige();

        binder.setBean(stellenanzeige);


        binder.addStatusChangeListener(
                event -> weiter.setEnabled(binder.isValid()));

        weiter.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (UI.getCurrent().getSession().getAttribute(Roles.Unternehmen) instanceof Unternehmen) {
                    stellenanzeige.setFirmenname(((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).getCname());
                    stellenanzeige.setHauptsitz(((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).getHauptsitz());


                    ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).setStellenanzeige(stellenanzeige);
                }

                UI.getCurrent().getNavigator().navigateTo(Views.Stellenbeschreibung);

            }
        });




    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        User user = ((MyUI) UI.getCurrent()).getUser();
        if( user != null) {
            this.setUp();
        } else {

            UI.getCurrent().getNavigator().navigateTo(Views.RegisterUnternehmen);

        }
    }


}
