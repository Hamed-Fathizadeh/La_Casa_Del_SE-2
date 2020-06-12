package org.bonn.se.gui.views;


import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se.control.LoginControl;
import org.bonn.se.control.exception.NoSuchUserOrPassword;
import org.bonn.se.gui.component.RegistrationPasswordField;
import org.bonn.se.gui.component.RegistrationTextField;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.model.objects.entitites.User;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;

import java.io.File;

import static org.bonn.se.services.util.Views.RegisterUnternehmen;

//LoginSeite
public class MainView extends VerticalLayout implements View {

    public void setUp(){
        this.setStyleName("toppanel");

        Button regStudent = new Button("Registrierung Student");
        Button regUnternehmen= new Button("Registrierung Unternehmen");

        regStudent.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo(Views.RegisterStudent);
            }
        });

        regUnternehmen.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo(RegisterUnternehmen);
            }
        });

        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.addComponent(regStudent);
        hLayout.addComponent(regUnternehmen);

        hLayout.setComponentAlignment(regStudent,Alignment.TOP_RIGHT);
        hLayout.setComponentAlignment(regUnternehmen,Alignment.TOP_RIGHT);

        //this.addComponent(new Label(""));
//Gesamtgröße des Bildschirms auf komplette Größe beziehen
        this.setSizeFull();


//Textfeld Login
        RegistrationTextField userLogin = new RegistrationTextField("E-Mail");
        userLogin.setWidth("310px");
        userLogin.selectAll();


//Textfelt Passwort
        RegistrationPasswordField passwordField = new RegistrationPasswordField ("Passwort");
        passwordField.setWidth("310px");


        GridLayout Maingrid = new GridLayout(1, 5);
        Maingrid.setSizeFull();
        Maingrid.setStyleName("login_bg");


//Vertikales Layout + Hinzufügen der Textfelder
        VerticalLayout layout = new VerticalLayout();

        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        FileResource resource = new FileResource(new File(basepath + "/VAADIN/themes/demo/img/RegisterStudent/Logo_Login.png"));
        Image Logo = new Image("", resource);
        layout.addComponent(Logo);



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
        /*
        Button buttonPassVerg = new Button("Passwort vergessen?");
        buttonPassVerg.addClickListener(e -> {
            PassChangeWindow pcWindow = new PassChangeWindow();
            UI.getCurrent().addWindow(pcWindow);

        });

        layout.addComponent(buttonPassVerg);
        layout.setComponentAlignment(buttonPassVerg, Alignment.MIDDLE_CENTER);
         */
        //Erstellen und Hinzufügen eines Panels + Platzierung in die Mitte
        Panel panel = new Panel( "");
        panel.setWidth("40px");

        panel.setContent(layout);
        panel.setSizeUndefined();
        panel.setStyleName("login_bg");


        buttonLogin.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                String login = userLogin.getValue();
                String password = passwordField.getValue();

                try {
                    LoginControl.checkAuthentication(login, password);
                } catch (NoSuchUserOrPassword ex) {
                    Notification.show("Fehler", "Login oder Passwort falsch", Notification.Type.ERROR_MESSAGE);
                    userLogin.clear();
                    passwordField.clear();
                } catch (DatabaseException ex) {
                    Notification.show("DB-Fehler", ex.getReason(), Notification.Type.ERROR_MESSAGE);
                    userLogin.clear();
                    passwordField.clear();
                }
            }
        });

        Maingrid.addComponent(hLayout,0,0,0,0);
        Maingrid.addComponent(panel,0,2,0,2);


        Maingrid.setComponentAlignment(hLayout,Alignment.TOP_RIGHT);
        Maingrid.setComponentAlignment(panel,Alignment.BOTTOM_CENTER);

        this.addComponent(Maingrid);
        this.setComponentAlignment(Maingrid,Alignment.MIDDLE_CENTER);

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
