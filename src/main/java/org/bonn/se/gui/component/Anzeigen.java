package org.bonn.se.gui.component;

import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.gui.window.StellenanzeigeWindow;
import org.bonn.se.model.dao.UserDAO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;
import org.vaadin.teemu.ratingstars.RatingStars;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Anzeigen< T extends StellenanzeigeDTO > extends Grid<T> {
    List<T> data;
    // --Commented out by Inspection (22.06.20, 23:28):static int anzahlNeuBewerbungen = 0;//jumlah pelamar
    static int gesamtNeuBewerbungen = 0;//jumal pelamar keseluran

    public int getGesamtNeuBewerbungen() {

        return gesamtNeuBewerbungen;
    }

    public int setGesamtNeuBewerbungen(int gesamtNeuBew) {
        gesamtNeuBewerbungen += gesamtNeuBew;
        return gesamtNeuBew;
    }

// --Commented out by Inspection START (22.06.20, 23:13):
//    public int getAnzahlNeuBewerbungen() {
//        return anzahlNeuBewerbungen;
//    }
// --Commented out by Inspection STOP (22.06.20, 23:13)

// --Commented out by Inspection START (22.06.20, 23:13):
//    public int setAnzahlNeuBewerbungen(int anzahlNeuBewerbungen) {
//
//        Anzeigen.anzahlNeuBewerbungen = anzahlNeuBewerbungen;
//        setGesamtNeuBewerbungen(Anzeigen.anzahlNeuBewerbungen);
//        return Anzeigen.anzahlNeuBewerbungen;
//    }
// --Commented out by Inspection STOP (22.06.20, 23:13)

    @Override
    public List<T> getData() {
        return data;
    }

    public int getAnzahlRow() {
        return data.size();
    }

    public void setData(List<T> data) {
        this.removeAllColumns();
        this.data = data;
        setUp();
    }

    public Anzeigen(String str, List<StellenanzeigeDTO> dataInput){
        super();

        this.setHeightMode(HeightMode.UNDEFINED);
        this.addStyleName("AnzeigeUnternehmen");
        this.setSelectionMode(SelectionMode.SINGLE);
        this.setCaption("Treffer: "+ dataInput.size());


        if(!str.equals("Student")) {
            this.setWidth("150px");
        }else{
            this.setWidth("1500px");
        }
        this.setHeight("800px");

        // Allow column reordering wir können es auch ändern
        this.setColumnReorderingAllowed(true);


        SingleSelect<StellenanzeigeDTO> selection = (SingleSelect<StellenanzeigeDTO>) this.asSingleSelect();

        // Der Event Listener für den Grid
       this.addSelectionListener(event -> {

            // Speichert den aktuell angewählten Wert bei klick auf die Zeile in der Var. "selected"
            StellenanzeigeDTO sa =  selection.getValue();
            Unternehmen unternehmenData = null;
            try {
                UserDAO.getInstance();
                unternehmenData = UserDAO.getUnternehmenByStellAnz(sa);
            } catch (DatabaseException | SQLException e) {
                Logger.getLogger(Anzeigen.class.getName()).log(Level.SEVERE, null, e);
            }
            if(sa.getStatus() == 3) {
                for (StellenanzeigeDTO temp : ((Unternehmen) MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).getStellenanzeigenDTO()) {
                    if (temp.getId() == sa.getId()) {
                        Collections.swap(((Unternehmen) MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).getStellenanzeigenDTO(),
                                ((Unternehmen) MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).getStellenanzeigenDTO().indexOf(temp),
                                ((Unternehmen) MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).getStellenanzeigenDTO().size() - 1);
                        break;

                    }

                }
                ((Unternehmen)MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).setStellenanzeigeDTO(sa);
                MyUI.getCurrent().getNavigator().navigateTo(Views.Stellenbeschreibung);
            } else {
                StellenanzeigeWindow stellenanzeigeWindow;
                stellenanzeigeWindow = new StellenanzeigeWindow(sa, unternehmenData);
                UI.getCurrent().addWindow(stellenanzeigeWindow);
            }
        });

        data = (List<T>) dataInput;
        setUp();



    }
    public void setUp(){

        this.setItems( data);

        ThemeResource resource = new ThemeResource("img/Anzeigen/rot.png");
        Image rot = new Image(null, resource);
        rot.setDescription("Offline");
        ThemeResource resource2 = new ThemeResource("img/Anzeigen/gruen.png");
        Image gruen = new Image(null, resource2);
        gruen.setDescription("Online");
        ThemeResource resource3 = new ThemeResource("img/Anzeigen/orange.png");
        Image orange = new Image(null, resource3);
        orange.setDescription("Entwurf");

        if(UI.getCurrent().getSession().getAttribute(Roles.Student) != null) {
            this.addComponentColumn(im ->{
                VerticalLayout imageL = new VerticalLayout();
                imageL.addComponent(im.getUnternehmenLogo());
                return imageL;
            }).setCaption("Logo");
            this.addColumn(StellenanzeigeDTO::getFirmenname).setCaption("Unternehmen");
            this.addColumn(StellenanzeigeDTO::getZeitstempel).setCaption("Online seit");
            this.addComponentColumn(p -> {
                RatingStars rating = new RatingStars();
                rating.setMaxValue(5);
                rating.setValue(p.getBewertung());
                rating.setReadOnly(true);
                return rating;
            }).setCaption("Bewertung");
        }
        this.addColumn(StellenanzeigeDTO::getStandort).setCaption("Ort");
        this.addColumn(StellenanzeigeDTO::getDatum).setCaption("Beginn");
        this.addColumn(StellenanzeigeDTO::getSuchbegriff).setCaption("Stelle");

        if(UI.getCurrent().getSession().getAttribute(Roles.Unternehmen) != null) {
            this.addColumn(StellenanzeigeDTO::getArt).setCaption("Art");
            this.addComponentColumn(sa -> (sa.getStatus() == 1 ? new Image(null, resource2) : sa.getStatus() == 2 ? new Image(null, resource) : new Image(null, resource3))).setCaption("Status").setId("Status");
            this.addComponentColumn(sa -> ( new Label(" <style>p { color:black ; font-weight:bold;  font-size: 18px; }</style><p>"+setGesamtNeuBewerbungen(sa.getanzahlNeuBewerbung())+"</p>", ContentMode.HTML))   ).setCaption("Neue Bewerbungen").setId("Anzahl neue Bewerbungen");
        }

    }
}
