package org.bonn.se.gui.component;

import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Image;
import com.vaadin.ui.SingleSelect;
import com.vaadin.ui.UI;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.gui.window.StellenanzeigeWindow;
import org.bonn.se.model.dao.UserDAO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.ContainerNeuigkeiten;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;

import java.util.List;

public class Anzeigen<T extends StellenanzeigeDTO> extends Grid<T> {

    public Anzeigen(String str, ContainerNeuigkeiten container){
        super();

        this.setHeightMode(HeightMode.UNDEFINED);
        this.addStyleName("AnzeigeUnternehmen");
        this.setSelectionMode(SelectionMode.SINGLE);
        if(!str.equals("Student")) {
            this.setWidth("100%");
        }else{
            this.setWidth("800px");
        }
        this.setHeight("100%");

        // Allow column reordering
        this.setColumnReorderingAllowed(true);

        SingleSelect<StellenanzeigeDTO> selection = (SingleSelect<StellenanzeigeDTO>) this.asSingleSelect();

        // Der Event Listener für den Grid
        this.addSelectionListener(event -> {

            // Speichert den aktuell angewählten Wert bei klick auf die Zeile in der Var. "selected"
            StellenanzeigeDTO sa =  selection.getValue();
            Unternehmen unternehmen_data = null;
            try {
                unternehmen_data = UserDAO.getInstance().getUnternehmenByStellAnz(sa);
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
            if(sa.getStatus() == 3) {
                ((Unternehmen)MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).setStellenanzeigeDTO(sa);
                MyUI.getCurrent().getNavigator().navigateTo(Views.Stellenbeschreibung);
            } else {
                StellenanzeigeWindow stellenanzeigeWindow = new StellenanzeigeWindow(sa, unternehmen_data);
                UI.getCurrent().addWindow(stellenanzeigeWindow);
            }
        });

        List<T> liste = (List<T>) container.getListe();
        //this.setCaption("Anzahl Anzeigen " + container.getAnzahl());
        this.setItems( liste);

        ThemeResource resource = new ThemeResource("img/Anzeigen/rot.png");
        Image rot = new Image(null, resource);
        rot.setDescription("Offline");
        ThemeResource resource2 = new ThemeResource("img/Anzeigen/gruen.png");
        Image gruen = new Image(null, resource2);
        gruen.setDescription("Online");
        ThemeResource resource3 = new ThemeResource("img/Anzeigen/orange.png");
        Image orange = new Image(null, resource3);
        orange.setDescription("Entwurf");

        if(str.equals("Student")) {
            this.addComponentColumn(StellenanzeigeDTO::getUnternehmenLogo).setCaption("Unternehmen");
        }
        this.addColumn(StellenanzeigeDTO::getTitel).setCaption("Titel");
        this.addColumn(StellenanzeigeDTO::getStandort).setCaption("Ort");
        this.addColumn(StellenanzeigeDTO::getDatum).setCaption("Beginn");

        if(!str.equals("Student")) {
            this.addColumn(StellenanzeigeDTO::getArt).setCaption("Art");
            this.addComponentColumn(Sa -> (Sa.getStatus() == 1 ? new Image(null, resource2) : Sa.getStatus() == 2 ? new Image(null, resource) : new Image(null, resource3))).setCaption("Status");
        }

    }

 }

