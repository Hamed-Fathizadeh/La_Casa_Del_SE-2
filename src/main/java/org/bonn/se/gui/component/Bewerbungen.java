package org.bonn.se.gui.component;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import org.bonn.se.control.BewerbungControl;
import org.bonn.se.gui.window.BewerbungWindow;
import org.bonn.se.gui.window.ConfirmationWindow;
import org.bonn.se.model.dao.BewertungDAO;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.model.objects.entitites.ContainerLetztenBewerbungen;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.ImageConverter;
import org.bonn.se.services.util.Roles;
import org.vaadin.teemu.ratingstars.RatingStars;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Bewerbungen<T extends BewerbungDTO> extends Grid<T>{
    List<T> data;
    private BewerbungDTO bewerbungDTO;


    public void setBewerbungDTO(BewerbungDTO bewerbungDTO) {
        this.bewerbungDTO = bewerbungDTO;
    }

    public List<T> getData() {
        return data;
    }

    public Bewerbungen(ContainerLetztenBewerbungen container, String viewName){
        super();

        this.setHeightMode(HeightMode.UNDEFINED);
        this.addStyleName("AnzeigeUnternehmen");
        this.setSelectionMode(SelectionMode.SINGLE);
        this.setWidth("900px");
        this.setHeight("100%");
        this.setCaption("Treffer: "+ container.getAnzahl());


        this.setColumnReorderingAllowed(true);

        @SuppressWarnings("unchecked") SingleSelect<BewerbungDTO> selection = (SingleSelect<BewerbungDTO>) this.asSingleSelect();

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

                Button loeschen = new Button("Löschen");
                subContent.addComponent(loeschen,1,1);
                BewerbungDTO bewDTOtemp = selection.getValue();
                bewerten.addClickListener((Button.ClickListener) clickEvent -> {

                    setUpBewertung(bewDTOtemp);
                    subWindow.close();
                });

                loeschen.addClickListener((Button.ClickListener) clickEvent -> {

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
                });


                subWindow.center();

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


        if(UI.getCurrent().getSession().getAttribute(Roles.Student) != null) {
            this.addComponentColumn(BewerbungDTO::getUnternehmenLogo).setCaption("Logo");
            this.addColumn(BewerbungDTO::getUnternehmenName).setCaption("Unternehmen").setWidth(150);
            this.addColumn(BewerbungDTO::getTitel).setCaption("Titel");
            this.addColumn(BewerbungDTO::getDatum).setCaption("Beginn");
            this.addColumn(be -> (be.getStatus() == 1 || be.getStatus() == 9 ? "gesendet" : be.getStatus() == 2 ? "zugesagt" :  "abgelehnt")).setCaption("Status");
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
            this.addColumn(BewerbungDTO::getStudent_ausbildung).setCaption("Ausbildung");
            this.addComponentColumn(bew -> (bew.isBewerbung_markiert() ? ImageConverter.getMarkierung() : null)).setCaption("Markiert");
            this.addComponentColumn(bew -> (bew.getStatus() == 9 ? new Label(" <style>p { color:red ; font-weight:bold;  font-size: 18px; }</style><p>Neu</p>", ContentMode.HTML): null)).setCaption("");

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

        bewerten.addClickListener((Button.ClickListener) clickEvent -> {
            inBew.setRating(rating.getValue());
            try {
                BewertungDAO.getInstance().bewertung(inBew);
            } catch (DatabaseException e) {
                Logger.getLogger(Bewerbungen.class.getName()).log(Level.SEVERE,null,e);

            } catch (SQLException throwables) {
                Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE,null,throwables);
            }

            subWindow.close();
        });


        subWindow.center();

        UI.getCurrent().addWindow(subWindow);

    }




}

