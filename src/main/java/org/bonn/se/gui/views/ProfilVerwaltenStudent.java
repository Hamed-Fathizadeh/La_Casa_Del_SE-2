package org.bonn.se.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.bonn.se.gui.component.TopPanelUser;
import org.bonn.se.model.dao.ProfilVerwaltenDAO;
import org.bonn.se.services.util.Views;

public class ProfilVerwaltenStudent extends GridLayout implements View {
    public void setUp() {
        this.setMargin(true);
        this.setColumns(3);
        this.setRows(10);
        this.setSizeFull();
        TopPanelUser topPanel = new TopPanelUser();

        TextField vorname = new TextField("Vorname");
        TextField nachname = new TextField("Nachname");
        TextField email = new TextField("E-Mail");
        TextField telnr = new TextField("Tel Nr");
        TextField ausbildung = new TextField("Ausbildung");
        TextField studium = new TextField("Studium");
        TextField abschluss = new TextField("Höchster Abschluss");

        Button bearbeitenButton = new Button("Profil Bearbeiten");

        Button abbrechenButton = new Button("Abbrechen");

        Button fertigButton = new Button("Fertig");


        this.addComponent(topPanel, 0, 0, 2, 1);
        //this.addComponent(form, 0, 2, 0, 2);
        this.addComponent(vorname, 0,2);
        this.addComponent(nachname, 0,3);
        this.addComponent(email, 0, 5);
        this.addComponent(telnr, 0, 6);
        this.addComponent(ausbildung, 0, 7);
        this.addComponent(studium, 0, 8);
        this.addComponent(abschluss, 0, 9);

        this.addComponent(abbrechenButton,2,11);
        this.addComponent(bearbeitenButton,1,11);

        vorname.setValue(ProfilVerwaltenDAO.getVorname(" "));
        vorname.setReadOnly(true);

        nachname.setValue(ProfilVerwaltenDAO.getVorname(" "));
        nachname.setReadOnly(true);

        email.setValue(ProfilVerwaltenDAO.getEmail(" "));
        email.setReadOnly(true);

        telnr.setValue(ProfilVerwaltenDAO.getTelnr(" "));
        telnr.setReadOnly(true);

        ausbildung.setValue(" ");
        ausbildung.setReadOnly(true);

        studium.setValue(" ");
        studium.setReadOnly(true);

        abschluss.setValue("");
        abschluss.setReadOnly(true);



        this.setComponentAlignment(topPanel, Alignment.TOP_LEFT);
        this.setMargin(false);

        bearbeitenButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                vorname.setReadOnly(false);
                nachname.setReadOnly(false);
                email.setReadOnly(true);
                telnr.setReadOnly(false);
                ausbildung.setReadOnly(false);
                studium.setReadOnly(false);
                abschluss.setReadOnly(false);
            }
        });

//abschluss.addValueChangeListener() farbe zu basteln
        abbrechenButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().getNavigator().navigateTo(Views.RegisterStudent);
            }
        });
    }
        @Override
        public void enter (ViewChangeListener.ViewChangeEvent event){
            setUp();

        }

}


