package org.bonn.se.gui.views;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se.control.UserSearchControl;
import org.bonn.se.gui.component.OrtField;
import org.bonn.se.gui.component.RegistrationPasswordField;
import org.bonn.se.gui.component.RegistrationTextField;
import org.bonn.se.gui.component.TopPanel;
import org.bonn.se.gui.window.RegisterUnternehmenWindow;
import org.bonn.se.model.dao.UserDAO;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.model.objects.entitites.User;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;


public class RegisterUnternehmerView extends Panel implements View {

    public void setUp() {

        GridLayout mainGrid = new GridLayout(10,10);
        mainGrid.addStyleName("grid");
        mainGrid.setWidthFull();
        mainGrid.setHeightUndefined();


        String ls1 = "<div class=WordSection1>\n" +
                "\n" +
                "<p class=MsoNormal><b><span style='font-size:36.0pt;line-height:107%;\n" +
                "font-family:\"\"YACgEa4Wckw 0\", _fb_, auto\";color:#003853'>Willkommen bei <span\n" +
                "class=SpellE>Lacolsco</span><o:p></o:p></span></b></p>\n" +
                "\n" +
                "<p class=MsoNormal><b><span style='font-size:36.0pt;line-height:107%;\n" +
                "font-family:\"\"YACgEa4Wckw 0\", _fb_, auto\";color:#003853'>Registrieren Sie sich jetzt!<o:p></o:p></span></b></p>\n" +
                "\n" +
                "</div>";

        Label head = new Label(ls1, ContentMode.HTML);

        OrtField hauptsitz = new OrtField("Hauptsitz");

        TopPanel topPanelUnt =  new TopPanel("Studenten");
        topPanelUnt.addStyleName("toppanel");
        topPanelUnt.setHeight("120px");

        FormLayout formUnt = new FormLayout();
        formUnt.setMargin(true);
        RegistrationTextField firmenname = new RegistrationTextField("Unternehmensname");
        firmenname.selectAll();

        RegistrationTextField vorname = new RegistrationTextField("Vorname");
        RegistrationTextField nachname = new RegistrationTextField("Nachname");
        RegistrationTextField email = new RegistrationTextField("E-Mail");
        RegistrationPasswordField passwort = new RegistrationPasswordField("Passwort");

        Button registerUntButton = new Button("Registrieren");
        registerUntButton.setEnabled(false);
        Label lPatzhalter = new Label("&nbsp", ContentMode.HTML);

        formUnt.addComponents(lPatzhalter,head,firmenname,hauptsitz,vorname,nachname,email,passwort,registerUntButton);

        Binder<User> binder = new Binder<>(User.class);
        binder.forField(firmenname)
                .asRequired("Firmenname muss angegeben werden!")
                .bind(User::getCname,User::setCname);
        binder.forField(hauptsitz)
                .asRequired("Firmenhauptsitz muss angegeben werden")
                .bind(User::getHauptsitz,User::setHauptsitz);
        binder.forField(vorname)
                .asRequired("Vorname muss angegeben werden!")
                .bind(User::getVorname, User::setVorname);

        binder.forField(nachname)
                .asRequired("Nachname muss angegeben werden!")
                .bind(User::getNachname, User::setNachname);

        binder.forField(email)
                .asRequired("E-Mail muss angegeben werden")
                .withValidator(new EmailValidator("Keine gültige E-Mail Adresse!"))
                .bind(User::getEmail, User::setEmail);

        binder.forField(passwort)
                .asRequired("Password may not be empty")
                .withValidator(new StringLengthValidator(
                        "Passwort muss mindestens 8 Zeichen lang sein", 8, null))
                .bind(User::getPasswort, User::setPasswort);

        User user = new User();
        binder.setBean(user);
        Button test = new Button("Test PopUp");
        test.addClickListener((Button.ClickListener) event -> {
            System.out.println(hauptsitz.getOrt());
            System.out.println(hauptsitz.getBundesland());

        });
        ThemeResource resource = new ThemeResource("img/RegisterUnternehmen/unternehmen2.png");
        ThemeResource resources = new ThemeResource("img/RegisterStudent/JobfindenB.png");

        Image bildUnt = new Image(null,resource);
        Image bild_Register = new Image(null,resources);

        formUnt.setMargin(false);
        mainGrid.addComponent(topPanelUnt, 0, 0, 9, 1);
        mainGrid.addComponent(lPatzhalter, 0, 2, 9, 2);
        mainGrid.addComponent(formUnt, 0, 5, 0, 5);
        mainGrid.addComponent(bildUnt, 9, 5, 9, 5);
        mainGrid.addComponent(bild_Register, 0, 7, 9, 7);
        mainGrid.setComponentAlignment(topPanelUnt, Alignment.TOP_LEFT);
        mainGrid.setComponentAlignment(formUnt, Alignment.BOTTOM_LEFT);
        mainGrid.setComponentAlignment(bildUnt, Alignment.TOP_RIGHT);
        mainGrid.setComponentAlignment(bild_Register, Alignment.BOTTOM_CENTER);
        mainGrid.setMargin(false);

        this.setContent(mainGrid);
        this.setSizeFull();

        registerUntButton.addClickListener(
                event -> {
                    try {

                        if (UserSearchControl.getInstance().existUser(email.getValue())) {
                            email.setValue("");
                            email.setPlaceholder("E-Mail existiert schon!");
                            email.setComponentError(new UserError("Bitte eine andere E-Mail verwenden."));
                        } else {
                            user.setType("C");

                            registerUntButton.setEnabled(false);
                            user.setHauptsitz(hauptsitz.getOrt());
                            user.setBundesland(hauptsitz.getBundesland());
                            Unternehmen unternehmen = new Unternehmen();
                            unternehmen.setEmail(user.getEmail());
                            unternehmen.setVorname(user.getVorname());
                            unternehmen.setNachname(user.getNachname());
                            unternehmen.setPasswort(user.getPasswort());
                            unternehmen.setHauptsitz(user.getHauptsitz());
                            unternehmen.setBundesland(user.getBundesland());
                            unternehmen.setCname(user.getCname());

                            UserDAO.getInstance().registerUser(user);


                            UI.getCurrent().getSession().setAttribute(Roles.UNTERNEHMEN,unternehmen);
                            RegisterUnternehmenWindow registerUnternehmenWindow = new RegisterUnternehmenWindow();
                            UI.getCurrent().addWindow(registerUnternehmenWindow);
                        }

                    } catch(DatabaseException e){
                        e.printStackTrace();
                    }
                });


        binder.addStatusChangeListener(
                event -> registerUntButton.setEnabled(binder.isValid()));

    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        if (UI.getCurrent().getSession().getAttribute(Roles.STUDENT) != null) {
            UI.getCurrent().getNavigator().navigateTo(Views.STUDENTHOMEVIEW);
        } else if(UI.getCurrent().getSession().getAttribute(Roles.UNTERNEHMEN) != null) {
            UI.getCurrent().getNavigator().navigateTo(Views.UNTERNEHMENHOMEVIEW);
        } else {
            this.setUp();
        }
    }

}


