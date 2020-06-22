package org.bonn.se.gui.component;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.bonn.se.control.FeatureToggleControl;
import org.bonn.se.control.LoginControl;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.ImageConverter;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;

import java.sql.SQLException;

public class TopPanelUser extends GridLayout {
        MenuBar bar;
        MenuBar.MenuItem item1;

    public TopPanelUser() throws DatabaseException, SQLException {

        this.setRows(1);
        this.setColumns(10);
        this.setStyleName("toppanel");


        this.setMargin(false);
        this.setWidthFull();
        this.setHeightUndefined();

        ThemeResource resource = new ThemeResource("img/RegisterStudent/logo.png");

        Button imagePropertyInfo = new Button(resource);
        imagePropertyInfo.setStyleName(ValoTheme.BUTTON_BORDERLESS);


        imagePropertyInfo.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if(UI.getCurrent().getSession().getAttribute(Roles.Student) != null) {
                    UI.getCurrent().getNavigator().navigateTo(Views.StudentHomeView);
                }else{
                    UI.getCurrent().getNavigator().navigateTo(Views.UnternehmenHomeView);
                }
            }
        });

        this.addComponent(imagePropertyInfo,0,0,0,0);
        this.setComponentAlignment(imagePropertyInfo, Alignment.MIDDLE_LEFT);

        bar = new MenuBar();

        bar.addStyleName("user-menu");
        item1 = null;

        if(UI.getCurrent().getSession().getAttribute(Roles.Student) != null) {
           Image profilbild = ImageConverter.convertImagetoMenu(((Student)UI.getCurrent().getSession().getAttribute(Roles.Student)).getPicture());

            item1 = bar.addItem(
                    ((Student) UI.getCurrent().getSession().getAttribute(Roles.Student)).getVorname()
                    ,profilbild.getSource(),
                    null);
        } else if(UI.getCurrent().getSession().getAttribute(Roles.Unternehmen) != null) {
            Image firmaLogo = ImageConverter.convertImagetoMenu(((Unternehmen)UI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).getLogo());
            item1 = bar.addItem(
                    ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).getCname(),
                    firmaLogo.getSource(),
                    null);
        }
//create menubar



        item1.addItem("Mein Profil", VaadinIcons.USER, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                MyUI.getCurrent().getNavigator().navigateTo(Views.ProfilVerwaltenStudent);
            }
        });

        item1.addItem("Neuigkeiten", VaadinIcons.INFO_CIRCLE, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                LoginControl.logoutUser();
            }
        });


            item1.addItem("Settings", VaadinIcons.COG, new MenuBar.Command() {
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

        if(FeatureToggleControl.getInstance().featureIsEnabled("BEWERBUNGEN")) {

            UI.getCurrent().access(new Runnable() {
                @Override
                public void run() {

                    item1.addItemBefore("Letzte Bewerbungen", VaadinIcons.CLIPBOARD_TEXT, new MenuBar.Command() {
                        @Override
                        public void menuSelected(MenuBar.MenuItem menuItem) {
                            LoginControl.logoutUser();
                        }
                    },item1.getChildren().get(2));
                }

            });
        }
    }
}
