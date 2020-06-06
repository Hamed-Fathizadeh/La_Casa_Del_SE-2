package org.bonn.se.gui.component;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import org.bonn.se.control.LoginControl;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.util.Roles;

public class TopPanelUser extends HorizontalLayout {

    public TopPanelUser(){

        this.setSizeFull();
        this.setStyleName("toppanel");

        GridLayout Topgrid = new GridLayout(6, 1);
        Topgrid.setMargin(true);
        Topgrid.setSizeFull();

        ThemeResource resource = new ThemeResource("img/RegisterStudent/logo.png");
        Image logo = new Image(null,resource);

        logo.setSizeUndefined();
        logo.addStyleName("logo");
        Topgrid.addComponent(logo,0,0,0,0);
        Topgrid.setComponentAlignment(logo, Alignment.BOTTOM_LEFT);

//add image



        Image profilbild = null;
        Image firma_logo = null;

        MenuBar bar = new MenuBar();
        // MenuBar.MenuItem item1 = bar.addItem("Menü", null);
        bar.addStyleName("user-menu");
        MenuBar.MenuItem item1 = null;

        if(MyUI.getCurrent().getSession().getAttribute(Roles.Student) != null) {
            profilbild = ((Student) MyUI.getCurrent().getSession().getAttribute(Roles.Student)).getImage();
            profilbild.setHeight(70, Unit.PIXELS);
            profilbild.setWidth(70, Unit.PIXELS);
            // Topgrid.addComponent(profilbild,4,0,4,0);
            //   Topgrid.setComponentAlignment(profilbild, Alignment.BOTTOM_RIGHT);
            item1 = bar.addItem(
                    ((Student) MyUI.getCurrent().getSession().getAttribute(Roles.Student)).getVorname(),
                    profilbild.getSource(),
                    null);
        } else if(MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen) != null) {
            firma_logo = ((Unternehmen) MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).getLogo();
            firma_logo.setHeight(70, Unit.PIXELS);
            firma_logo.setWidth(70, Unit.PIXELS);
            item1 = bar.addItem(
                    ((Unternehmen) MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).getCname(),
                    firma_logo.getSource(),
                    null);
        }
//create menubar



        item1.addItem("Mein Profil", VaadinIcons.SEARCH, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                LoginControl.logoutUser();
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
        item1.addSeparator();
        item1.addItem("Logout", VaadinIcons.SIGN_OUT, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                LoginControl.logoutUser();
            }
        });

        Topgrid.addComponent(bar,5,0,5,0);
        Topgrid.setComponentAlignment(bar, Alignment.BOTTOM_CENTER);

        this.addComponent(Topgrid);
        this.setComponentAlignment(Topgrid, Alignment.TOP_CENTER);

    }

}
