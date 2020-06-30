package org.bonn.se.gui.component;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.gui.window.StellenanzeigeWindow;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.util.ImageConverter;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;
import org.vaadin.teemu.ratingstars.RatingStars;

import java.util.Collections;
import java.util.List;

public class Anzeigen< T extends StellenanzeigeDTO > extends Grid<T> {
    List<T> data;
    static int gesamtNeuBewerbungen = 0;

    public int setGesamtNeuBewerbungen(int gesamtNeuBew) {
        gesamtNeuBewerbungen += gesamtNeuBew;
        return gesamtNeuBew;
    }



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

            if(sa.getStatus() == 3) {
                for (StellenanzeigeDTO temp : ((Unternehmen) MyUI.getCurrent().getSession().getAttribute(Roles.UNTERNEHMEN)).getStellenanzeigenDTO()) {
                    if (temp.getId() == sa.getId()) {
                        Collections.swap(((Unternehmen) MyUI.getCurrent().getSession().getAttribute(Roles.UNTERNEHMEN)).getStellenanzeigenDTO(),
                                ((Unternehmen) MyUI.getCurrent().getSession().getAttribute(Roles.UNTERNEHMEN)).getStellenanzeigenDTO().indexOf(temp),
                                ((Unternehmen) MyUI.getCurrent().getSession().getAttribute(Roles.UNTERNEHMEN)).getStellenanzeigenDTO().size() - 1);
                        break;

                    }

                }
                ((Unternehmen)MyUI.getCurrent().getSession().getAttribute(Roles.UNTERNEHMEN)).setStellenanzeigeDTO(sa);
                MyUI.getCurrent().getNavigator().navigateTo(Views.STELLENBESCHREIBUNG);
            } else {
                StellenanzeigeWindow stellenanzeigeWindow;
                stellenanzeigeWindow = new StellenanzeigeWindow(sa);
                UI.getCurrent().addWindow(stellenanzeigeWindow);
            }
        });

        data = (List<T>) dataInput;
        setUp();

    }
    public void setUp(){

        this.setItems( data);




        if(UI.getCurrent().getSession().getAttribute(Roles.STUDENT) != null) {
            this.addComponentColumn(StellenanzeigeDTO::getUnternehmenLogo).setCaption("Logo").setWidth(80.0);
            this.addColumn(StellenanzeigeDTO::getFirmenname).setCaption("Unternehmen");
            this.addColumn(StellenanzeigeDTO::getZeitstempel).setCaption("Online seit").setId("Online seit");
            this.addComponentColumn(p -> {
                RatingStars rating = new RatingStars();
                rating.setMaxValue(5);
                rating.setValue(p.getBewertung());
                rating.setReadOnly(true);
                return rating;
            }).setCaption("Bewertung").setId("Bewertung");
        }
        this.addColumn(StellenanzeigeDTO::getStandort).setCaption("Ort");
        this.addColumn(StellenanzeigeDTO::getDatum).setCaption("Beginn");
        this.addColumn(StellenanzeigeDTO::getSuchbegriff).setCaption("Stelle");

        if(UI.getCurrent().getSession().getAttribute(Roles.UNTERNEHMEN) != null) {
            this.addColumn(StellenanzeigeDTO::getArt).setCaption("Art");
            this.addComponentColumn(sa -> (sa.getStatus() == 1 ? ImageConverter.getStatusGruen(): sa.getStatus() == 2 ? ImageConverter.getStatusRot() : ImageConverter.getStatusOrange())).setCaption("Status").setId("Status");
            this.addComponentColumn(sa -> ( new Label(" <style>p { color:black ; font-weight:bold;  font-size: 18px; }</style><p>"+setGesamtNeuBewerbungen(sa.getanzahlNeuBewerbung())+"</p>", ContentMode.HTML))   ).setCaption("Neue Bewerbungen").setId("Anzahl neue Bewerbungen");
        }

    }
}
