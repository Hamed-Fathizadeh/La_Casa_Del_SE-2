package org.bonn.se.gui.views;


import com.vaadin.client.ui.Icon;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.bonn.se.control.UserSearch;
import org.bonn.se.gui.component.*;
import org.bonn.se.gui.window.ProfilStudentWindow;
import org.bonn.se.model.dao.UserDAO;
import org.bonn.se.model.objects.entitites.Stellenanzeige;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.model.objects.entitites.User;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;

import java.io.File;


public class RegisterStudent extends GridLayout implements View {

    public void setUp() {
        System.out.println("LOG: get UI-Objekt in RegisterStudent!" + VaadinSession.getCurrent().toString());
        // this.setSizeFull();
        // this.setSpacing(true);

        this.setColumns(10);
        this.setRows(10);

        this.addStyleName("grid");
        this.setSizeFull();



        TopPanel topPanel =  new TopPanel("Unternehmen");
        topPanel.addStyleName("toppanel");
        //this.addComponent(new TopPanel("Für Unternehmen"));
        // this.addComponent(new Label(""));
        Label head = new Label("<h2><p><font color=\"blue\"> Willkommen bei Lacolsco Registrieren Sie sich jetzt.... !!!.</font></p></h2>", ContentMode.HTML);

        FormLayout form = new FormLayout();
        form.setMargin(true);
        RegistrationTextField vorname = new RegistrationTextField("Vorname");
        vorname.selectAll();


        RegistrationTextField nachname = new RegistrationTextField( "Nachname");
        RegistrationTextField email = new RegistrationTextField("E-Mail");
        RegistrationPasswordField passwort = new RegistrationPasswordField("Passwort");
      //  RegistrationPasswordField Vnummer = new RegistrationPasswordField("Verifizierungscode");

        Button registerButton = new Button("Registrieren");
        registerButton.setEnabled(false);

        Button VnummerButton = new Button("Verifikation");
        VnummerButton.setEnabled(false);

        form.addComponents(head,vorname,nachname,email,passwort,registerButton);
        //this.addComponent(form);
        Binder<User> binder = new Binder<>(User.class);

        binder.forField(vorname)
                .asRequired("Vorname muss angegeben werden!")
                .bind(User::getVorname,User::setVorname);

        binder.forField(nachname)
                .asRequired("Nachname muss angegeben werden!")
                .bind(User::getNachname,User::setNachname);
        binder.forField(email)
                .asRequired("E-Mail muss angegeben werden")
                .withValidator(new EmailValidator("Keine gültige E-Mail Adresse!"))
                .bind(User::getEmail,User::setEmail);
        binder.forField(passwort)
                .withValidator(new StringLengthValidator(
                        "Passwort muss mindestens 8 Zeichen lang sein", 8, null))
                .bind(User::getPasswort,User::setPasswort);

        User user = new User();

        binder.setBean(user);

        /*
        binder.forField(Vnummer)
                .asRequired("Verification Nummer")
                .withValidator(new StringLengthValidator(
                        "Verification Nummer ist falsch!", 5, 5));
        */
        /*
        Button test = new Button("Test PopUp");
        test.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                ProfilStudentWindow window = new ProfilStudentWindow(null);
                UI.getCurrent().addWindow(window);
            }
        });

         */
        ThemeResource resource = new ThemeResource("img/RegisterStudent/student_pic.png");
//        ThemeResource resource1 = new ThemeResource("img/RegisterStudent/jobfinden.png");


        Image bild = new Image(null,resource);
//        Image bild1 = new Image(null,resource1);



        form.setMargin(false);

        this.addComponent(topPanel, 0, 0, 9, 1);
        this.addComponent(form, 0, 5, 0, 5);
        this.addComponent(bild, 9, 5, 9, 5);
//        this.addComponent(bild1, 9, 7, 9, 7);


        this.setComponentAlignment(topPanel, Alignment.TOP_LEFT);
        this.setComponentAlignment(form, Alignment.MIDDLE_LEFT);
        this.setComponentAlignment(bild, Alignment.MIDDLE_RIGHT);
//        this.setComponentAlignment(bild1, Alignment.MIDDLE_CENTER);

        this.setMargin(false);
        registerButton.addClickListener(
                event -> {

                    try {

                        if (UserSearch.getInstance().existUser(email.getValue())) {
                            email.setValue("");
                            email.setPlaceholder("E-Mail existiert schon!");
                            email.setComponentError(new UserError("Bitte eine andere E-Mail verwenden."));
                        }
                        /*
                        else if(!vnummer.equals(Vnummer.getValue())){
                            UI.getCurrent().addWindow(new ConfirmationWindow("Verifizierungscode ist falsch!"));
                            Vnummer.clear();
                        }

                         */
                        else {
                            user.setType("S");
                            UserDAO.registerUser(user);

                           // UserDAO.registerStudent(student.getEmail(),student.getPasswort(),student.getVorname(),student.getNachname() ,'s');
                            registerButton.setEnabled(false);
                            Student student = new Student();
                            student.setEmail(user.getEmail());
                            student.setVorname(user.getVorname());
                            student.setNachname(user.getNachname());
                            student.setPasswort(user.getPasswort());

                            UI.getCurrent().getSession().setAttribute(Roles.Student,student);
                            ProfilStudentWindow window = new ProfilStudentWindow(student);
                            UI.getCurrent().addWindow(window);

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

        this.addComponent(button,9,9);

        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Unternehmen unternehmen = new Unternehmen();
                unternehmen.setLogo(new Image("",new FileResource(new File("src/main/webapp/VAADIN/themes/demo/images/Unknown.png"))));
                Stellenanzeige stellenanzeige = new Stellenanzeige();
                unternehmen.setHauptsitz("Test");
                stellenanzeige.setFirmenname("Test");
                stellenanzeige.setTitel("Test");
                stellenanzeige.setId(42);
                UI.getCurrent().getSession().setAttribute("Unternehmen",Roles.Unternehmen);
                //StellenanzeigeWindow stellenanzeigeWindow = new StellenanzeigeWindow(stellenanzeige,unternehmen);

               // UI.getCurrent().addWindow(stellenanzeigeWindow);
                UI.getCurrent().getNavigator().navigateTo(Views.AnzeigeErstellen);


            }
        });


    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

            this.setUp();

    }

  /*  String vnummer;
    public String getVnummer(){
        vnummer = new VerifikationNummer().getRandNummer();
        return vnummer;
    }

   */
}

