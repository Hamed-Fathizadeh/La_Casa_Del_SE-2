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

import org.bonn.se.control.UserSearch;
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


public class RegisterUnternehmer extends GridLayout implements View {

    public void setUp() {

        this.setColumns(10);
        this.setRows(10);

        this.addStyleName("grid");
        this.setSizeFull();

        Label head = new Label("<h2><p><font color=\"blue\"> Willkommen bei Lacolsco Registrieren Sie sich jetzt.... !!!.</font></p></h2>", ContentMode.HTML);
        this.setMargin(false);
        this.addStyleName("grid");


        OrtField hauptsitz = new OrtField("Hauptsitz");




        TopPanel topPanel =  new TopPanel("Studenten");
        topPanel.addStyleName("toppanel");

        FormLayout form = new FormLayout();
        form.setMargin(true);
        RegistrationTextField firmenname = new RegistrationTextField("Unternehmensname");
        firmenname.selectAll();

        RegistrationTextField vorname = new RegistrationTextField("Vorname");
        RegistrationTextField nachname = new RegistrationTextField("Nachname");
        RegistrationTextField email = new RegistrationTextField("E-Mail");
        RegistrationPasswordField passwort = new RegistrationPasswordField("Passwort");
        //     RegistrationPasswordField vnummer = new RegistrationPasswordField("Verifizierungscode");

        Button registerButton = new Button("Registrieren");
        registerButton.setEnabled(false);
/*
        Button VnummerButton = new Button("Verifikation");
        VnummerButton.setEnabled(false);
*/
        form.addComponents(head,firmenname,hauptsitz,vorname,nachname,email,passwort,registerButton);
        //this.addComponent(form);
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
/*
        binder.forField(vnummer)
                .asRequired("Verification Nummer")
                .withValidator(new StringLengthValidator(
                        "Verification Nummer ist falsch!", 5, 5));
*/
        User user = new User();
        binder.setBean(user);
        Button test = new Button("Test PopUp");
        test.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                System.out.println(hauptsitz.getOrt());
                System.out.println(hauptsitz.getBundesland());

            }
        });
        ThemeResource resource = new ThemeResource("img/RegisterUnternehmen/unternehmen.png");

        Image bild = new Image(null,resource);


        form.setMargin(false);

        this.addComponent(topPanel, 0, 0, 9, 2);
        this.addComponent(form, 0, 5, 0, 5);
        this.addComponent(bild, 9, 5, 9, 5);

        this.setComponentAlignment(topPanel, Alignment.TOP_LEFT);
        this.setComponentAlignment(form, Alignment.MIDDLE_LEFT);
        this.setComponentAlignment(bild, Alignment.MIDDLE_RIGHT);
        this.setMargin(false);

        registerButton.addClickListener(
                event -> {

                    try {

                        if (UserSearch.getInstance().existUser(email.getValue())) {
                            email.setValue("");
                            email.setPlaceholder("E-Mail existiert schon!");
                            email.setComponentError(new UserError("Bitte eine andere E-Mail verwenden."));
                        } else {
                            user.setType("C");


                            // UserDAO.registerStudent(student.getEmail(),student.getPasswort(),student.getVorname(),student.getNachname() ,'s');


                            registerButton.setEnabled(false);
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


                            UI.getCurrent().getSession().setAttribute(Roles.Unternehmen,unternehmen);
                            RegisterUnternehmenWindow registerUnternehmenWindow = new RegisterUnternehmenWindow();
                            UI.getCurrent().addWindow(registerUnternehmenWindow);
                        }

                    } catch(DatabaseException e){
                        e.printStackTrace();
                    }
                });


        binder.addStatusChangeListener(
                event -> registerButton.setEnabled(binder.isValid()));

        //hafa change#########################################################
/*
        Binder<User> binder2 = new Binder<>(User.class);
        binder2.forField(firmenname)
                .asRequired("Firmenname muss angegeben werden")
                .bind(User::getCname,User::setCname);

        binder2.forField(vorname)
                .asRequired("Vorname muss angegeben werden!")
                .bind(User::getVorname, User::setVorname);

        binder2.forField(nachname)
                .asRequired("Nachname muss angegeben werden!")
                .bind(User::getNachname, User::setNachname);

        binder2.forField(email)
                .asRequired("Password may not be empty")
                .withValidator(new StringLengthValidator(
                        "Passwort muss mindestens 8 Zeichen lang sein", 8, null))
                .bind(User::getPasswort, User::setPasswort);
        binder2.forField(passwort)
                .asRequired("Password may not be empty")
                .withValidator(new StringLengthValidator(
                        "Passwort muss mindestens 8 Zeichen lang sein", 8, null))
                .bind(User::getPasswort, User::setPasswort);

        VnummerButton.addClickListener(
                event -> {
                    try {

                        JavaMailUtil.sendMail(email.getValue(),getVnummer(),vorname.getValue());
                        //Notification.show("Wir haben Ihnen einen Email gesendet!");
                        UI.getCurrent().addWindow(new ConfirmationWindow("Wir haben einen Email an diese Adresse gesendet: "+email.getValue()));
                    } catch (Exception e) {
                        UI.getCurrent().addWindow(new ConfirmationWindow("Fehler beim email senden!"));
                        e.printStackTrace();
                    }

                });

        binder2.addStatusChangeListener(
                event -> VnummerButton.setEnabled(binder2.isValid()));

*/





        Button button = new Button("Test");

        this.addComponent(test,9,9);



    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) { this.setUp(); }
/*
    String vnummer;
    public String getVnummer(){
        vnummer = new VerifikationNummer().getRandNummer();
        return vnummer;
    }
*/

}



