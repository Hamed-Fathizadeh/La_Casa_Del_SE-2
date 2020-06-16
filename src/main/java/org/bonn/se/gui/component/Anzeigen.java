package org.bonn.se.gui.component;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.gui.window.StellenanzeigeWindow;
import org.bonn.se.model.dao.UserDAO;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.ContainerLetztenBewerbungen;
import org.bonn.se.model.objects.entitites.ContainerNeuigkeiten;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;
import org.vaadin.teemu.ratingstars.RatingStars;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Anzeigen< T extends StellenanzeigeDTO > extends Grid<T> {
    List<T> data;
    ContainerNeuigkeiten container;
    static int anzahlNeuBewerbungen = 0;
    static int gesamtNeuBewerbungen = 0;

    public int getGesamtNeuBewerbungen() {

        return gesamtNeuBewerbungen;
    }

    public int setGesamtNeuBewerbungen(int gesamtNeuBew) {
        this.gesamtNeuBewerbungen += gesamtNeuBew;
        return gesamtNeuBew;
    }

    public int getAnzahlNeuBewerbungen() {
        return anzahlNeuBewerbungen;
    }

    public int setAnzahlNeuBewerbungen(int anzahlNeuBewerbungen) {

        this.anzahlNeuBewerbungen = anzahlNeuBewerbungen;
        setGesamtNeuBewerbungen(this.anzahlNeuBewerbungen );
        return this.anzahlNeuBewerbungen;
    }

    @Override
    public List<T> getData() {
        return data;
    }

    public int getAnzahlRow() {
        return data.size();
    }

    public Grid<T> setData(List<T> data) {
        this.removeAllColumns();
        this.data = data;
        setUp();
        return this;
    }

    public Anzeigen(String str, ContainerNeuigkeiten container){
        super();

        this.setHeightMode(HeightMode.UNDEFINED);
        this.addStyleName("AnzeigeUnternehmen");
        this.setSelectionMode(SelectionMode.SINGLE);
        this.setCaption("Treffer: "+ container.getAnzahl());


        if(!str.equals("Student")) {
            this.setWidth("150px");
        }else{
            this.setWidth("1500px");
        }
        this.setHeight("500px");

        // Allow column reordering wir können es auch ändern
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
                Iterator it =((Unternehmen)MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).getStellenanzeigenDTO().iterator();
                while (it.hasNext()) {
                    StellenanzeigeDTO temp = ((StellenanzeigeDTO)it.next());
                    if(temp.getId() == sa.getId() ){
                        Collections.swap(((Unternehmen)MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).getStellenanzeigenDTO(),
                                ((Unternehmen)MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).getStellenanzeigenDTO().indexOf(temp),
                                ((Unternehmen)MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).getStellenanzeigenDTO().size()-1);
                        break;

                    }

                }
                ((Unternehmen)MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).setStellenanzeigeDTO(sa);
                MyUI.getCurrent().getNavigator().navigateTo(Views.Stellenbeschreibung);
            } else {
                StellenanzeigeWindow stellenanzeigeWindow = new StellenanzeigeWindow(sa, unternehmen_data);
                UI.getCurrent().addWindow(stellenanzeigeWindow);
            }
        });

        data = (List<T>) container.getListe();
        setUp();



    }
    public void setUp(){


        //this.setCaption("Anzahl Anzeigen " + container.getAnzahl());
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
        String day ="";
        long div;
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
        this.addColumn(StellenanzeigeDTO::getTitel).setCaption("Titel");
        this.addColumn(StellenanzeigeDTO::getStandort).setCaption("Ort");
        this.addColumn(StellenanzeigeDTO::getDatum).setCaption("Beginn");

        if(UI.getCurrent().getSession().getAttribute(Roles.Unternehmen) != null) {
            this.addColumn(StellenanzeigeDTO::getArt).setCaption("Art");
            this.addComponentColumn(Sa -> (Sa.getStatus() == 1 ? new Image(null, resource2) : Sa.getStatus() == 2 ? new Image(null, resource) : new Image(null, resource3))).setCaption("Status").setId("Status");
            this.addComponentColumn(Sa -> ( new Label(" <style>p { color:black ; font-weight:bold;  font-size: 18px; }</style><p>"+setAnzahlNeuBewerbungen(Sa.getanzahlNeuBewerbung())+"</p>", ContentMode.HTML))   ).setCaption("Neue Bewerbungen").setId("Anzahl neue Bewerbungen");

        }

    }
}
