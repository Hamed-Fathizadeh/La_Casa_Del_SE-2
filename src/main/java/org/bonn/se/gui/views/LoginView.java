package org.bonn.se.gui.views;


import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se.control.FeatureToggleControl;
import org.bonn.se.control.LoginControl;
import org.bonn.se.control.exception.NoSuchUserOrPassword;
import org.bonn.se.gui.component.RegistrationPasswordField;
import org.bonn.se.gui.component.RegistrationTextField;
import org.bonn.se.gui.window.PassChangeWindow;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;

import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.bonn.se.services.util.Views.RegisterUnternehmen;

public class LoginView extends VerticalLayout implements View {

    public void setUp(){
        this.setStyleName("toppanel");
        this.setMargin(true);

        Button regStudent = new Button("Registrierung Student");
        Button regUnternehmen= new Button("Registrierung Unternehmen");
        regStudent.setWidth("250px");
        regStudent.setHeight("45px");
        regUnternehmen.setWidth("280px");
        regUnternehmen.setHeight("45px");

        regStudent.addClickListener((Button.ClickListener) event -> UI.getCurrent().getNavigator().navigateTo(Views.RegisterStudent));

        regUnternehmen.addClickListener((Button.ClickListener) event -> UI.getCurrent().getNavigator().navigateTo(RegisterUnternehmen));
        Label lPatzhalter = new Label("&nbsp", ContentMode.HTML);

        GridLayout topGrid = new GridLayout(7,1);
        topGrid.setMargin(true);
        topGrid.addComponent(regStudent,4,0);
        topGrid.addComponent(lPatzhalter,5,0);
        topGrid.addComponent(regUnternehmen,6,0);

        topGrid.setComponentAlignment(regStudent,Alignment.TOP_RIGHT);
        topGrid.setComponentAlignment(regUnternehmen,Alignment.TOP_RIGHT);


        this.setSizeFull();


//Textfeld Login
        RegistrationTextField userLogin = new RegistrationTextField("E-Mail");
        userLogin.setWidth("310px");
        userLogin.selectAll();


//Textfelt Passwort
        RegistrationPasswordField passwordField = new RegistrationPasswordField ("Passwort");
        passwordField.setWidth("310px");


        GridLayout mainGrid = new GridLayout(1, 5);
        mainGrid.setHeightFull();
        //mainGrid.setWidth("1000px");
        mainGrid.setWidthFull();
        mainGrid.setMargin(true);
        mainGrid.setStyleName("login_bg");


//Vertikales Layout + Hinzufügen der Textfelder
        VerticalLayout layout = new VerticalLayout();
        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        FileResource resource = new FileResource(new File(basepath + "/VAADIN/themes/demo/img/RegisterStudent/Logo_Login.png"));
        Image logo = new Image("", resource);
        layout.addComponent(logo);



        layout.addComponent(userLogin);
        layout.addComponent(passwordField);

//Platzhalter
        Label label = new Label ( "&nbsp;", ContentMode.HTML);
        layout.addComponent(label);



//Button zum Login + Symbol auf Button

        Button buttonLogin = new Button("Login", VaadinIcons.SEARCH);
        buttonLogin.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        layout.addComponent(buttonLogin);
        layout.setComponentAlignment(buttonLogin, Alignment.MIDDLE_CENTER);

        //layout.addComponent(link4);
        //layout.setComponentAlignment(link4, Alignment.MIDDLE_CENTER);

        //Button für Passwort vergessen.

        //Erstellen und Hinzufügen eines Panels + Platzierung in die Mitte
        Panel panel = new Panel( "");
        panel.setWidth("40px");

        panel.setContent(layout);
        panel.setSizeUndefined();
        panel.setStyleName("login_bg");


        buttonLogin.addClickListener((Button.ClickListener) clickEvent -> {
            String login = userLogin.getValue();
            String password = passwordField.getValue();

            try {
                LoginControl.getInstance().checkAuthentication(login, password);
            } catch (NoSuchUserOrPassword ex) {
                Notification.show("Fehler", "Login oder Passwort falsch", Notification.Type.ERROR_MESSAGE);
                userLogin.clear();
                passwordField.clear();
            } catch (DatabaseException ex) {
                Notification.show("DB-Fehler", ex.getReason(), Notification.Type.ERROR_MESSAGE);
                userLogin.clear();
                passwordField.clear();
            } catch (SQLException throwables) {
                Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
            }
        });

        mainGrid.addComponent(topGrid,0,0,0,0);
        mainGrid.addComponent(panel,0,2,0,2);


        mainGrid.setComponentAlignment(topGrid,Alignment.TOP_RIGHT);
        mainGrid.setComponentAlignment(panel,Alignment.BOTTOM_CENTER);

        this.addComponent(mainGrid);
        this.setComponentAlignment(mainGrid,Alignment.MIDDLE_CENTER);


        if(FeatureToggleControl.getInstance().featureIsEnabled("SMTP")) {
            UI.getCurrent().access(() -> {
                Button buttonPassVerg = new Button("Passwort vergessen?");
                buttonPassVerg.addClickListener(e -> {
                    PassChangeWindow pcWindow = new PassChangeWindow();
                    UI.getCurrent().addWindow(pcWindow);
                });
                layout.addComponent(buttonPassVerg);
                layout.setComponentAlignment(buttonPassVerg, Alignment.MIDDLE_CENTER);
            });
        }




    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (UI.getCurrent().getSession().getAttribute(Roles.Student) != null) {
            UI.getCurrent().getNavigator().navigateTo(Views.StudentHomeView);
         } else if(UI.getCurrent().getSession().getAttribute(Roles.Unternehmen) != null) {
            UI.getCurrent().getNavigator().navigateTo(Views.UnternehmenHomeView);
         } else {
            this.setUp();
        }
    }
}
