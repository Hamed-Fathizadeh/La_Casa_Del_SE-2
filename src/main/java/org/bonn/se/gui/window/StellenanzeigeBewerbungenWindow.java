package org.bonn.se.gui.window;

import com.vaadin.event.FieldEvents;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.bonn.se.control.BewerbungControl;
import org.bonn.se.gui.component.Bewerbungen;
import org.bonn.se.model.dao.BewertungDAO;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.ContainerLetztenBewerbungen;
import org.bonn.se.services.util.Views;
import org.vaadin.teemu.ratingstars.RatingStars;

import java.util.stream.Collectors;

public class StellenanzeigeBewerbungenWindow extends Window {

    public StellenanzeigeBewerbungenWindow(StellenanzeigeDTO stellenanzeige) {
        setUp(stellenanzeige);
    }

    public void setUp(StellenanzeigeDTO stellenanzeige) {
        this.center();
        this.setWidth("80%");
        this.setHeight("90%");
        this.setModal(true);
        this.setResizable(false);
        this.setClosable(false);

        Panel panel = new Panel();
        panel.setWidthFull();

        Button back = new Button("Zurück");

        back.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                StellenanzeigeBewerbungenWindow.this.close();
                UI.getCurrent().getNavigator().navigateTo(Views.UnternehmenHomeView);

            }
        });

        GridLayout mainGridLayout = new GridLayout(1, 4);
        mainGridLayout.setSizeFull();
        mainGridLayout.setMargin(true);



        this.addFocusListener(new FieldEvents.FocusListener() {
            public void focus(FieldEvents.FocusEvent event) {

                ContainerLetztenBewerbungen containerBewerbungen  = ContainerLetztenBewerbungen.getInstance();
                containerBewerbungen.loadByStellenAnzeigeID("Alle",stellenanzeige.getId());
                Bewerbungen<BewerbungDTO> bewerbungen = new Bewerbungen(containerBewerbungen,"Unternehmen");
                bewerbungen.setHeightMode(HeightMode.UNDEFINED);
                bewerbungen.setWidthFull();


                ContainerLetztenBewerbungen containerBewerbungenMarkiert  = ContainerLetztenBewerbungen.getInstance();
                containerBewerbungenMarkiert.loadByStellenAnzeigeID("Markiert",stellenanzeige.getId());
                Bewerbungen<BewerbungDTO> bewerbungenMakiert = new Bewerbungen(containerBewerbungenMarkiert,"Unternehmen");
                bewerbungenMakiert.setHeightMode(HeightMode.UNDEFINED);
                bewerbungenMakiert.setWidthFull();

                ContainerLetztenBewerbungen containerBewerbungenZusage  = ContainerLetztenBewerbungen.getInstance();
                containerBewerbungenZusage.loadByStellenAnzeigeID("Zusage",stellenanzeige.getId());
                Bewerbungen<BewerbungDTO> bewerbungenZugesagt = new Bewerbungen(containerBewerbungenZusage,"Unternehmen");
                bewerbungenZugesagt.setHeightMode(HeightMode.UNDEFINED);
                bewerbungenZugesagt.setWidthFull();

                ContainerLetztenBewerbungen containerBewerbungenAbgelehnt  = ContainerLetztenBewerbungen.getInstance();
                containerBewerbungenAbgelehnt.loadByStellenAnzeigeID("Abgelehnt",stellenanzeige.getId());
                Bewerbungen<BewerbungDTO> bewerbungenAbgelehnt = new Bewerbungen(containerBewerbungenAbgelehnt,"Unternehmen");
                bewerbungenAbgelehnt.setHeightMode(HeightMode.UNDEFINED);
                bewerbungenAbgelehnt.setWidthFull();



         //add TabSheet
                TabSheet tabSheet = new TabSheet();
                tabSheet.setHeight("700px");
                tabSheet.setWidth("1000px");
                tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
                tabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);

                tabSheet.addTab(bewerbungen,"Alle "+bewerbungen.getData().size());
                tabSheet.addTab(bewerbungenMakiert,"Markierte "+bewerbungenMakiert.getData().size());
                tabSheet.addTab(bewerbungenZugesagt,"Zugesagt "+bewerbungenZugesagt.getData().size());
                tabSheet.addTab(bewerbungenAbgelehnt,"Abgelehnt "+bewerbungenAbgelehnt.getData().size());

                mainGridLayout.removeAllComponents();

                mainGridLayout.addComponent(tabSheet,0,1);

                mainGridLayout.setComponentAlignment(tabSheet, Alignment.TOP_CENTER);

                mainGridLayout.addComponent(back,0,0);
                mainGridLayout.setComponentAlignment(back, Alignment.TOP_RIGHT);

            }
        });


        panel.setContent(mainGridLayout);
        this.setContent(mainGridLayout);


    }



}