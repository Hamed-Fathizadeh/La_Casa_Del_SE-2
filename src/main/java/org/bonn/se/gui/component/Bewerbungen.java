package org.bonn.se.gui.component;

import com.vaadin.data.ValueProvider;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import org.bonn.se.gui.ui.MyUI;

import org.bonn.se.model.dao.BewertungDAO;
import org.bonn.se.model.dao.ProfilDAO;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.model.objects.entitites.ContainerLetztenBewerbungen;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;
import org.vaadin.teemu.ratingstars.RatingStars;

import java.sql.*;

import java.util.List;






public class Bewerbungen<T extends BewerbungDTO> extends Grid<T>{

    public Bewerbungen(ContainerLetztenBewerbungen container, String userType){
        super();

        this.setHeightMode(HeightMode.UNDEFINED);
        this.addStyleName("AnzeigeUnternehmen");
        this.setSelectionMode(SelectionMode.SINGLE);
        this.setWidth("900px");
        this.setHeight("100%");

        // Allow column reordering
        this.setColumnReorderingAllowed(true);

        SingleSelect<BewerbungDTO> selection = (SingleSelect<BewerbungDTO>) this.asSingleSelect();

        // Der Event Listener für den Grid
        this.addSelectionListener(event -> {

            // Create a sub-window and set the content
            Window subWindow = new Window("Bewertung");
            VerticalLayout subContent = new VerticalLayout();
            subWindow.setContent(subContent);

            // Put some components in it
           // subContent.addComponent(new Label("Bewerten"));

            //selection.getValue().getUnternehmenName();
            //selection.getValue().getUnternehmenHauptsitz();

            RatingStars rating = new RatingStars();

                rating.setMaxValue(5);
                rating.setAnimated(true);

                rating.setValue(rating.getValue());

                rating.setReadOnly(false);

                subContent.addComponent(rating);

                subContent.addComponent(new Label("Bitte beachten Sie, dass sie jedes Unternehmen nur einmal bewerten können"));

                Button bewerten = new Button("Bewertung abgeben");
                subContent.addComponent(bewerten);

                bewerten.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        ResultSet set = null;
                        Student student = (Student) UI.getCurrent().getSession().getAttribute(Roles.Student);
                        try {
                            Statement statement = JDBCConnection.getInstance().getStatement();
                            set = statement.executeQuery("SELECT * "
                                    + "FROM lacasa.tab_bewertung "
                                    + "WHERE upper(lacasa.tab_bewertung.firmenname) != '" + selection.getValue().getUnternehmenName().toUpperCase()  + "'"
                                    + "AND upper(lacasa.tab_bewertung.hauptsitz) != '" + selection.getValue().getUnternehmenHauptsitz().toUpperCase() + "'"
                                    + "AND lacasa.tab_bewertung.student_id !=  '" + student.getStudent_id() + "'" );
                        } catch (SQLException | DatabaseException throwables) {
                            throwables.printStackTrace();
                           //throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");

                        }
                        try {
                            while (set.next()) {
                                BewerbungDTO bw = selection.getValue();
                                BewertungDAO.bewertung(bw);

                            }
                        } catch (SQLException | DatabaseException throwables) {
                            throwables.printStackTrace();
                        } finally {
                            try {
                                JDBCConnection.getInstance().closeConnection();
                            } catch (DatabaseException e) {
                                e.printStackTrace();
                            }
                        }
                        Window subWindow = new Window("Bewertung");
                        VerticalLayout subContent = new VerticalLayout();
                        subWindow.setContent(subContent);
                        subContent.addComponent(new Label("Sie haben bereits eine Bewertung abgegeben! Eine neue Bewertung ist nicht möglich!"));
                        subWindow.center();

                        // Open it in the UI
                        UI.getCurrent().addWindow(subWindow);
                    }
                });




            // Center it in the browser window
            subWindow.center();

            // Open it in the UI
            UI.getCurrent().addWindow(subWindow);
        });

        List<T> liste = (List<T>) container.getListe();
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
        RatingStars ratingStars = new RatingStars();
        ratingStars.setMaxValue(5);

        if(userType.equals("Student")) {
            this.addComponentColumn(BewerbungDTO::getUnternehmenLogo).setCaption("Unternehmen");
            this.addComponentColumn(p -> {
                RatingStars rating = new RatingStars();
                rating.setMaxValue(5);
                rating.setValue(p.getRating());
                rating.setReadOnly(true);
                return rating;
            }).setCaption("Bewertung");
            this.addColumn(BewerbungDTO::getTitel).setCaption("Titel");
            this.addColumn(BewerbungDTO::getDatum).setCaption("Beginn");
            this.addColumn(Be -> (Be.getStatus() == 1 ? "gesendet" : Be.getStatus() == 2 ? "in Bearbeitung" : Be.getStatus() == 3 ? "Entwurf" : "gelöscht")).setCaption("Status");
        }else{
            this.addComponentColumn(BewerbungDTO::getStudent_picture).setCaption("Bild");
            this.addColumn(BewerbungDTO::getStudent_vorname).setCaption("Vorname");
            this.addColumn(BewerbungDTO::getStudent_nachname).setCaption("Nachname");
            this.addColumn(BewerbungDTO::getStudent_studiengang).setCaption("Studiengang");
            this.addColumn(BewerbungDTO::getStudent_hoester_abschluss).setCaption("Höchster Abschluss");
            this.setSizeFull();
        }
    }



}

