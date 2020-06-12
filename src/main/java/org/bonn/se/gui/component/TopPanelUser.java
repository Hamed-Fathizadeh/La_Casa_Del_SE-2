package org.bonn.se.gui.component;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import org.bonn.se.control.LoginControl;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.util.ImageConverter;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;

public class TopPanelUser extends GridLayout {

    public TopPanelUser(){

        this.setRows(1);
        this.setColumns(10);
        this.setStyleName("toppanel");


        this.setMargin(false);
        this.setWidthFull();
        this.setHeightUndefined();

        ThemeResource resource = new ThemeResource("img/RegisterStudent/logo.png");
        Image logo = new Image(null,resource);

        logo.setSizeUndefined();
        logo.addStyleName("logo");
        this.addComponent(logo,0,0,0,0);
        this.setComponentAlignment(logo, Alignment.MIDDLE_LEFT);


        Image profilbild;
        Image firma_logo;

        MenuBar bar = new MenuBar();
        // MenuBar.MenuItem item1 = bar.addItem("Menü", null);
        bar.addStyleName("user-menu");
        MenuBar.MenuItem item1 = null;

        if(MyUI.getCurrent().getSession().getAttribute(Roles.Student) != null) {
            profilbild = ImageConverter.convertImagetoMenu(((Student)MyUI.getCurrent().getSession().getAttribute(Roles.Student)).getPicture());
            // Topgrid.addComponent(profilbild,4,0,4,0);
            //test
            //   Topgrid.setComponentAlignment(profilbild, Alignment.BOTTOM_RIGHT);
            item1 = bar.addItem(
                    ((Student) MyUI.getCurrent().getSession().getAttribute(Roles.Student)).getVorname()
                    ,profilbild.getSource(),
                    null);
        } else if(MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen) != null) {
            firma_logo = ImageConverter.convertImagetoMenu(((Unternehmen)MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).getLogo());
            item1 = bar.addItem(
                    ((Unternehmen) MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).getCname(),
                    firma_logo.getSource(),
                    null);
        }
//create menubar



        item1.addItem("Mein Profil", VaadinIcons.SEARCH, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                MyUI.getCurrent().getNavigator().navigateTo(Views.ProfilVerwaltenStudent);
            }
        });

        item1.addItem("Neuigkeiten", VaadinIcons.SEARCH, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                LoginControl.logoutUser();
            }
        });

        item1.addItem("Letzte Bewerbungen", VaadinIcons.SEARCH, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                LoginControl.logoutUser();
            }
        });

        item1.addItem("Settings", VaadinIcons.SEARCH, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                UI.getCurrent().getNavigator().navigateTo(Views.Settings);
            }
        });

        item1.addSeparator();
        item1.addItem("Logout", VaadinIcons.SIGN_OUT, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                LoginControl.logoutUser();
            }
        });
        this.addComponent(bar,9,0,9,0);
        this.setComponentAlignment(bar, Alignment.MIDDLE_CENTER);
    }
}
