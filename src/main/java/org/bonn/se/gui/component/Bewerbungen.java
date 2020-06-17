package org.bonn.se.gui.component;

import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;

import org.bonn.se.control.BewerbungControl;
import org.bonn.se.gui.window.BewerbungWindow;
import org.bonn.se.gui.window.ConfirmationWindow;
import org.bonn.se.model.dao.BewertungDAO;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.model.objects.entitites.ContainerLetztenBewerbungen;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;
import org.vaadin.teemu.ratingstars.RatingStars;

import java.sql.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;






public class Bewerbungen<T extends BewerbungDTO> extends Grid<T>{
    List<T> data;
    public BewerbungDTO bewerbungDTO;

    public BewerbungDTO getBewerbungDTO() {
        return bewerbungDTO;
    }

    public void setBewerbungDTO(BewerbungDTO bewerbungDTO) {
        this.bewerbungDTO = bewerbungDTO;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> liste) {
        this.data = liste;
    }

    public int getAnzahlRow() {
        return data.size();
    }
    public Bewerbungen(ContainerLetztenBewerbungen container, String viewName){
        super();

        this.setHeightMode(HeightMode.UNDEFINED);
        this.addStyleName("AnzeigeUnternehmen");
        this.setSelectionMode(SelectionMode.SINGLE);
        this.setWidth("900px");
        this.setHeight("100%");
        this.setCaption("Treffer: "+ container.getAnzahl());


        // Allow column reordering
        this.setColumnReorderingAllowed(true);

        @SuppressWarnings("unchecked") SingleSelect<BewerbungDTO> selection = (SingleSelect<BewerbungDTO>) this.asSingleSelect();

        // Der Event Listener für den Grid
        this.addSelectionListener(event -> {

            if (UI.getCurrent().getSession().getAttribute(Roles.Student) != null) {

                Window subWindow = new Window("Bewertung abgeben oder Löschen");
                GridLayout  subContent = new GridLayout (2,2);
                subWindow.setContent(subContent);
                subWindow.setWidth("600px");
                subWindow.setHeight("300px");



                subContent.addComponent(new Label(""),0,0);

                Button bewerten = new Button("Bewertung abgeben");
                subContent.addComponent(bewerten,0,1);

                Button Loeschen = new Button("Löschen");
                subContent.addComponent(Loeschen,1,1);
                BewerbungDTO bewDTOtemp = selection.getValue();
                bewerten.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {

                        setUpBewertung(bewDTOtemp);
                        subWindow.close();
                    }
                });

                Loeschen.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {

                        if(bewDTOtemp != null){
                            setBewerbungDTO(bewDTOtemp);
                        }
                        try {
                            BewerbungControl.bewerbungLoeschen(bewDTOtemp);
                        } catch (DatabaseException e) {
                            e.printStackTrace();
                        }
                        subWindow.close();
                        ConfirmationWindow confWindow =  new ConfirmationWindow("Ihre Bewerbung wurde gelöscht");
                        UI.getCurrent().addWindow(confWindow);
                        confWindow.focus();
                        UI.getCurrent().getNavigator().navigateTo(viewName);
                    }
                });


                // Center it in the browser window
                subWindow.center();

                // Open it in the UI
                UI.getCurrent().addWindow(subWindow);



        }else{


                 BewerbungDTO bewDTOtemp = selection.getValue();
                 if(bewDTOtemp != null){
                     setBewerbungDTO(bewDTOtemp);
                 }

                BewerbungWindow bewerbungWindow = new BewerbungWindow(null, "Unternehmen", bewerbungDTO);
                if(bewerbungDTO.getStatus() == 9){
                    BewerbungControl.statusNeuBewAendern(bewerbungDTO.getBewerbungID());
                }
                UI.getCurrent().addWindow(bewerbungWindow);



        }
        });

        setUP(container);

    }


    public void setUP(ContainerLetztenBewerbungen container){


        this.removeAllColumns();

        data = (List<T>) container.getListe();
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
        ThemeResource resource4 = new ThemeResource("img/Anzeigen/makierung.png");

        //RatingStars ratingStars = new RatingStars();
        //ratingStars.setMaxValue(5);

        if(UI.getCurrent().getSession().getAttribute(Roles.Student) != null) {
            this.addComponentColumn(BewerbungDTO::getUnternehmenLogo).setCaption("Logo");
            this.addColumn(BewerbungDTO::getUnternehmenName).setCaption("Unternehmen").setWidth(150);
            this.addColumn(BewerbungDTO::getTitel).setCaption("Titel");
            this.addColumn(BewerbungDTO::getDatum).setCaption("Beginn");
            this.addColumn(Be -> (Be.getStatus() == 1 || Be.getStatus() == 9 ? "gesendet" : Be.getStatus() == 2 ? "abgelehnt" :  "gesendet")).setCaption("Status");
            this.addComponentColumn(p -> {
                RatingStars rating = new RatingStars();
                rating.setMaxValue(5);
                rating.setValue(p.getRating());
                rating.setReadOnly(true);
                return rating;
            }).setCaption("Bewertung");
        }else{
            this.addComponentColumn(im ->{
                VerticalLayout imageL = new VerticalLayout();
                imageL.addComponent(im.getStudent_picture());
                return imageL;
            }).setCaption("Bild");
            this.addColumn(BewerbungDTO::getStudent_vorname).setCaption("Vorname");
            this.addColumn(BewerbungDTO::getStudent_nachname).setCaption("Nachname");
            this.addColumn(BewerbungDTO::getStudent_studiengang).setCaption("Studiengang");
            this.addColumn(BewerbungDTO::getStudent_hoester_abschluss).setCaption("Höchster Abschluss");
            this.addColumn(BewerbungDTO::getStudent_hoester_abschluss).setCaption("Höchster Abschluss");
            this.addComponentColumn(Bew -> (Bew.isBewerbung_markiert() ? new Image(null, resource4) : null)).setCaption("Markiert");
            this.addComponentColumn(Bew -> (Bew.getStatus() == 9 ? new Label(" <style>p { color:red ; font-weight:bold;  font-size: 18px; }</style><p>Neu</p>", ContentMode.HTML): null)).setCaption("");

        } new Label("<b>Unternehmensname</b>", ContentMode.HTML);



    }


    public void setUpBewertung( BewerbungDTO inBew){

        Window subWindow = new Window("Bewertung");
        VerticalLayout subContent = new VerticalLayout();
        subWindow.setContent(subContent);

        RatingStars rating = new RatingStars();

        rating.setMaxValue(5);
        rating.setAnimated(true);


        rating.setReadOnly(false);

        subContent.addComponent(rating);

        subContent.addComponent(new Label("Bitte beachten Sie, dass sie jedes Unternehmen nur einmal bewerten können"));

        Button bewerten = new Button("Bewertung abgeben");
        subContent.addComponent(bewerten);

        bewerten.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                BewerbungDTO bw = inBew;
                bw.setRating(rating.getValue());
                BewertungDAO.bewertung(bw);


                // Open it in the UI
                subWindow.close();
            }
        });


        // Center it in the browser window
        subWindow.center();

        // Open it in the UI
        UI.getCurrent().addWindow(subWindow);

    }




}

