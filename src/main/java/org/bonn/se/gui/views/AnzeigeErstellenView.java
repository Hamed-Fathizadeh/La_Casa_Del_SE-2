package org.bonn.se.gui.views;


import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;

import org.bonn.se.control.JobTitelControl;
import org.bonn.se.gui.component.OrtField;
import org.bonn.se.gui.component.RegistrationTextField;
import org.bonn.se.gui.component.TopPanelUser;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.*;

import java.sql.SQLException;

public class AnzeigeErstellenView extends GridLayout implements View {
        private OrtField ort;

    public void setUp() throws DatabaseException, SQLException {


        this.setRows(4);
        this.setColumns(10);


        this.addStyleName("anzeige");
        this.setSizeFull();

        GridLayout formGrid = new GridLayout(2,6);
        formGrid.addStyleName("anzeigeErstellen_formgrid");

        formGrid.setMargin(true);


        TopPanelUser topPanel =  new TopPanelUser();
        topPanel.addStyleName("toppanel");


        Label label = new Label("<h1>Allgemeine Angaben</h1>",ContentMode.HTML);
        RegistrationTextField titel = new RegistrationTextField("Titel der Anzeige");
        titel.setWidth("600px");


        NativeSelect<String> jobtitel = new NativeSelect<>("Bitte wählen Sie eine Stellenbeschreibung!",JobTitelControl.getJobTitelList());
        jobtitel.setHeight("56px");
        jobtitel.setWidth("600px");
        jobtitel.setEmptySelectionAllowed(false);


        ort = new OrtField("Standort");
        ort.setWidth(300.0f, Unit.PIXELS);
        ort.setHeight("56px");

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

        formGrid.addComponent(jobtitel,0,2,1,2);
        formGrid.setComponentAlignment(jobtitel,Alignment.MIDDLE_CENTER);

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



        cancel.addClickListener((Button.ClickListener) event -> UI.getCurrent().getNavigator().navigateTo(Views.UnternehmenHomeView));


        Binder<StellenanzeigeDTO> binder = new Binder<>(StellenanzeigeDTO.class);
        binder.forField(titel)
                .asRequired("Titel muss vergeben werden!")
                .bind(StellenanzeigeDTO::getTitel,StellenanzeigeDTO::setTitel);

        binder.forField(jobtitel)
                .asRequired("Bitte wählen Sie eine Stellenbeschreibung aus!")
                .bind(StellenanzeigeDTO::getBeschreibung,StellenanzeigeDTO::setBeschreibung);

        binder.forField(ort)
                .asRequired("Bitte geben Sie einen Standort ein!")
                .bind(StellenanzeigeDTO::getStandort,StellenanzeigeDTO::setStandort);

        binder.forField(beginn)
                .asRequired("Bitte Einstellungsdatum eingeben!")
                .bind(StellenanzeigeDTO::getDatum,StellenanzeigeDTO::setDatum);

        binder.forField(art)
                .asRequired("Bitte Art der Einstellung festlegen!")
                .bind(StellenanzeigeDTO::getArt,StellenanzeigeDTO::setArt);

        StellenanzeigeDTO stellenanzeigeDTO = new StellenanzeigeDTO();

        binder.setBean(stellenanzeigeDTO);


        binder.addStatusChangeListener(
                event -> weiter.setEnabled(binder.isValid()));

        weiter.addClickListener((Button.ClickListener) event -> {
            if (UI.getCurrent().getSession().getAttribute(Roles.Unternehmen) instanceof Unternehmen) {
                stellenanzeigeDTO.setFirmenname(((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).getCname());
                stellenanzeigeDTO.setHauptsitz(((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).getHauptsitz());
                stellenanzeigeDTO.setStandort(stellenanzeigeDTO.getStandort());
                stellenanzeigeDTO.setStandort(ort.getOrt());
                stellenanzeigeDTO.setBundesland(ort.getBundesland());

                ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).setStellenanzeige(stellenanzeigeDTO);
            }

            UI.getCurrent().getNavigator().navigateTo(Views.Stellenbeschreibung);

        });




    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        if (UI.getCurrent().getSession().getAttribute(Roles.Student) != null) {
            UI.getCurrent().getNavigator().getCurrentNavigationState();
        } else if (UI.getCurrent().getSession().getAttribute(Roles.Unternehmen) != null) {
            try {
                this.setUp();
            } catch (DatabaseException | SQLException e) {
                e.printStackTrace();
            }
        } else {
            UI.getCurrent().getNavigator().navigateTo(Views.MainView);
        }
    }



}