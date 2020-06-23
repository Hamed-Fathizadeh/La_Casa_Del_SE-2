package org.bonn.se.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import org.bonn.se.gui.component.TopPanelUser;
import org.bonn.se.model.objects.entitites.User;

public class ProfilVerwaltenUnternehmenView extends GridLayout implements View {
    static final GridLayout gridAnzeig = null;


    public void setUp()  {
        User user = new User();

        //Für Test
        user.setEmail("test1@test.de");
        user.setType("C");



        //Grid Einstellungen und Top Panel
        TopPanelUser topPanel;

        topPanel = new TopPanelUser();

        topPanel.setMargin(false);

        this.setMargin(false);
        this.setColumns(10);
        this.setRows(3);
        this.setHeightUndefined();
        this.setWidthFull();

        GridLayout gridLayout = new GridLayout(3,13);
        gridLayout.setHeightUndefined();
        gridLayout.setWidthFull();

        this.addComponent(topPanel, 0, 0, 9, 0);
        topPanel.setHeightUndefined();
        topPanel.addStyleName("toppanel");
        Label label = new Label("<h1>Allgemeine Angaben</h1>", ContentMode.HTML);


    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        this.setUp();

    }

}
