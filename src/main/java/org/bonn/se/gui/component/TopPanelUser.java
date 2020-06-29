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
import org.bonn.se.services.util.ImageConverter;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;

public class TopPanelUser extends GridLayout {
        final MenuBar bar;
        MenuBar.MenuItem item1;

    public TopPanelUser() {

        this.setRows(1);
        this.setColumns(10);
        this.setStyleName("toppanel");


        this.setMargin(false);
        this.setWidthFull();
        this.setHeightUndefined();

        ThemeResource resource = new ThemeResource("img/RegisterStudent/logo.png");

        Button imagePropertyInfo = new Button(resource);
        imagePropertyInfo.setStyleName(ValoTheme.BUTTON_BORDERLESS);

        imagePropertyInfo.addClickListener((Button.ClickListener) clickEvent -> {
            if(UI.getCurrent().getSession().getAttribute(Roles.STUDENT) != null) {
                UI.getCurrent().getNavigator().navigateTo(Views.STUDENTHOMEVIEW);
            }else{
                UI.getCurrent().getNavigator().navigateTo(Views.UNTERNEHMENHOMEVIEW);
            }
        });

        this.addComponent(imagePropertyInfo,0,0,0,0);
        this.setComponentAlignment(imagePropertyInfo, Alignment.MIDDLE_LEFT);

        bar = new MenuBar();

        bar.addStyleName("user-menu");

        if(UI.getCurrent().getSession().getAttribute(Roles.STUDENT) != null) {
           Image profilbild = ImageConverter.convertImagetoMenu(((Student)UI.getCurrent().getSession().getAttribute(Roles.STUDENT)).getPicture());
            item1 = bar.addItem(
                    ((Student)UI.getCurrent().getSession().getAttribute(Roles.STUDENT)).getVorname()
                    ,profilbild.getSource(),
                    null);

        } else if(UI.getCurrent().getSession().getAttribute(Roles.UNTERNEHMEN) != null) {
            Image firmaLogo = ImageConverter.convertImagetoMenu(((Unternehmen)UI.getCurrent().getSession().getAttribute(Roles.UNTERNEHMEN)).getLogo());
            item1 = bar.addItem(
                    ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.UNTERNEHMEN)).getCname(),
                    firmaLogo.getSource(),
                    null);

        }



        if(UI.getCurrent().getSession().getAttribute(Roles.STUDENT) != null) {
            item1.addItem("Home", VaadinIcons.HOME, (MenuBar.Command) menuItem -> UI.getCurrent().getNavigator().navigateTo(Views.STUDENTHOMEVIEW));
            item1.addItem("Mein Profil", VaadinIcons.USER, (MenuBar.Command) menuItem -> MyUI.getCurrent().getNavigator().navigateTo(Views.PROFILVERWALTENSTUDENT));
            item1.addItem("SETTINGS", VaadinIcons.COG, (MenuBar.Command) menuItem -> UI.getCurrent().getNavigator().navigateTo(Views.SETTINGS));
            item1.addSeparator();
            item1.addItem("Logout", VaadinIcons.SIGN_OUT, (MenuBar.Command) menuItem -> LoginControl.logoutUser());
        }else {
            item1.addItem("Home", VaadinIcons.HOME, (MenuBar.Command) menuItem -> UI.getCurrent().getNavigator().navigateTo(Views.UNTERNEHMENHOMEVIEW));
            item1.addItem("Mein Profil", VaadinIcons.USER, (MenuBar.Command) menuItem -> MyUI.getCurrent().getNavigator().navigateTo(Views.PROFILVERWALTENUNTERNEHMEN));
            item1.addItem("SETTINGS", VaadinIcons.COG, (MenuBar.Command) menuItem -> UI.getCurrent().getNavigator().navigateTo(Views.SETTINGS));
            item1.addSeparator();
            item1.addItem("Logout", VaadinIcons.SIGN_OUT, (MenuBar.Command) menuItem -> LoginControl.logoutUser());
        }
        item1.getMenuBar().getItems().get(0).setStyleName("NameUserWhite");
        this.addComponent(bar,9,0,9,0);
        this.setComponentAlignment(bar, Alignment.MIDDLE_CENTER);

        if(FeatureToggleControl.getInstance().featureIsEnabled("BEWERBUNGEN") & UI.getCurrent().getSession().getAttribute(Roles.STUDENT) != null) {

            UI.getCurrent().access(() -> item1.addItemBefore("Letzte Bewerbungen", VaadinIcons.CLIPBOARD_TEXT,
                    (MenuBar.Command) menuItem ->UI.getCurrent().getNavigator().navigateTo(Views.ALLEBEWERBUNGENVIEW),item1.getChildren().get(2)));
        }
    }
}
