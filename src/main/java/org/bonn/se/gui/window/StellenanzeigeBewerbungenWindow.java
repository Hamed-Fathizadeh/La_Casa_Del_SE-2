package org.bonn.se.gui.window;

import com.vaadin.event.FieldEvents;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.bonn.se.gui.component.Bewerbungen;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.ContainerLetztenBewerbungen;

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
        this.setClosable(true);

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



        ContainerLetztenBewerbungen containerBewerbungenMakiert  = ContainerLetztenBewerbungen.getInstance();
        containerBewerbungenMakiert.loadByStellenAnzeigeID("Makiert",stellenanzeige.getId());
        Bewerbungen<BewerbungDTO> bewerbungenMakiert = new Bewerbungen(containerBewerbungen,"Unternehmen");
        bewerbungenMakiert.setHeightMode(HeightMode.UNDEFINED);
        bewerbungenMakiert.setWidthFull();





 //add TabSheet
        TabSheet tabSheet = new TabSheet();
        tabSheet.setHeight("700px");
        tabSheet.setWidth("1500px");
        tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
        tabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);

        tabSheet.addTab(bewerbungen,"Alle");
        tabSheet.addTab(bewerbungenMakiert,"Markierte");
                mainGridLayout.removeAllComponents();
                mainGridLayout.addComponent(tabSheet,0,1);

                mainGridLayout.setComponentAlignment(tabSheet, Alignment.TOP_CENTER);

            }
        });



        this.setContent(mainGridLayout);


    }



}