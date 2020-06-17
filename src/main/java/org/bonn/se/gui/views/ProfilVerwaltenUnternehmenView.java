package org.bonn.se.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import org.bonn.se.gui.component.TopPanelUser;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.model.objects.entitites.User;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;

import java.sql.SQLException;

public class ProfilVerwaltenUnternehmenView extends GridLayout implements View {
    static GridLayout GridAnzeig = null;

    public static GridLayout getGridAnzeig() {
        return GridAnzeig;
    }

    public static void setGridAnzeig(GridLayout gridAnzeig) {
        GridAnzeig = gridAnzeig;
    }


    public void setUp()  {
        User user = new User();

        //Für Test
        user.setEmail("test1@test.de");
        user.setType("C");

        Unternehmen unternehmen =((Unternehmen) MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen));


        //Grid Einstellungen und Top Panel
        TopPanelUser topPanel = null;
        try {
            topPanel = new TopPanelUser();
        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
