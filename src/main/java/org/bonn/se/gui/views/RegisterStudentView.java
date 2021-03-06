package org.bonn.se.gui.views;


import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se.control.FeatureToggleControl;
import org.bonn.se.control.UserSearchControl;
import org.bonn.se.gui.component.RegistrationPasswordField;
import org.bonn.se.gui.component.RegistrationTextField;
import org.bonn.se.gui.component.TopPanel;
import org.bonn.se.gui.window.ConfirmationWindow;
import org.bonn.se.gui.window.RegisterStudentWindow;
import org.bonn.se.model.dao.UserDAO;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.User;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.JavaMailUtil;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.VerifikationNummer;
import org.bonn.se.services.util.Views;

import java.util.logging.Level;
import java.util.logging.Logger;


public class RegisterStudentView extends Panel implements View {

    public void setUp() {

        System.out.println("LOG: get UI-Objekt in RegisterStudent!" + VaadinSession.getCurrent().toString());
        GridLayout mainGrid = new GridLayout(10,10);


        mainGrid.addStyleName("grid");
        mainGrid.setWidthFull();
        mainGrid.setHeightUndefined();



        TopPanel topPanelStudent =  new TopPanel("Unternehmen");
        topPanelStudent.addStyleName("toppanel");
        topPanelStudent.setHeight("120px");

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

        FormLayout formStudent = new FormLayout();
        formStudent.setMargin(true);
        RegistrationTextField vorname = new RegistrationTextField("Vorname");
        vorname.selectAll();
        vorname.setValue(VaadinService.getCurrent().getBaseDirectory().toString());


        RegistrationTextField nachname = new RegistrationTextField( "Nachname");
        nachname.setValue(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath());
        RegistrationTextField email = new RegistrationTextField("E-Mail");
        RegistrationPasswordField passwort = new RegistrationPasswordField("Passwort");

        Button registerStudentButton = new Button("Registrieren");
        registerStudentButton.setEnabled(false);


        Label lPatzhalter = new Label("&nbsp", ContentMode.HTML);

        formStudent.addComponents(lPatzhalter,head,vorname,nachname,email,passwort,registerStudentButton);
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
                .asRequired("Bitte Passwort eingeben!")
                .withValidator(new StringLengthValidator(
                        "Passwort muss mindestens 8 Zeichen lang sein", 8, null))
                .bind(User::getPasswort,User::setPasswort);

        User user = new User();

        binder.setBean(user);

        ThemeResource resource = new ThemeResource("img/RegisterStudent/student_pic2.png");
        ThemeResource resources = new ThemeResource("img/RegisterStudent/JobfindenB.png");

        Image bildStudent = new Image(null,resource);
        Image bild_Register = new Image(null,resources);

        formStudent.setMargin(false);
        mainGrid.addComponent(topPanelStudent, 0, 0, 9, 1);
        mainGrid.addComponent(lPatzhalter, 0, 2, 9, 2);
        mainGrid.addComponent(formStudent, 0, 5, 0, 5);
        mainGrid.addComponent(bildStudent, 9, 5, 9, 5);
        mainGrid.addComponent(bild_Register, 0, 7, 9, 7);
        mainGrid.setComponentAlignment(topPanelStudent, Alignment.TOP_LEFT);
        mainGrid.setComponentAlignment(formStudent, Alignment.BOTTOM_LEFT);
        mainGrid.setComponentAlignment(bildStudent, Alignment.TOP_RIGHT);
        mainGrid.setComponentAlignment(bild_Register, Alignment.BOTTOM_CENTER);
        mainGrid.setMargin(false);

        this.setContent(mainGrid);
        this.setSizeFull();



        registerStudentButton.addClickListener(
                event -> {

                    try {

                        if (UserSearchControl.getInstance().existUser(email.getValue())) {
                            email.setValue("");
                            email.setPlaceholder("E-Mail existiert schon!");
                            email.setComponentError(new UserError("Bitte eine andere E-Mail verwenden."));

                        } else {
                            user.setType("S");
                            UserDAO.getInstance().registerUser(user);
                            registerStudentButton.setEnabled(false);
                            Student student = new Student();
                            student.setEmail(user.getEmail());
                            student.setVorname(user.getVorname());
                            student.setNachname(user.getNachname());
                            student.setPasswort(user.getPasswort());

                            UI.getCurrent().getSession().setAttribute(Roles.STUDENT,student);


                            RegisterStudentWindow registerStudentWindow = new RegisterStudentWindow();
                            UI.getCurrent().addWindow(registerStudentWindow);

                        }

                    } catch(DatabaseException e){
                        e.printStackTrace();
                    }

                });


        binder.addStatusChangeListener(
                event -> registerStudentButton.setEnabled(binder.isValid()));



        if(FeatureToggleControl.getInstance().featureIsEnabled("SMTP")) {
            UI.getCurrent().access(() -> {
                Button vNummerButton = new Button("Verifikation");
                vNummerButton.setEnabled(false);
                RegistrationPasswordField vNummerField = new RegistrationPasswordField("Verifizierungscode");

        binder.forField(vNummerField)
                .asRequired("Verification Nummer")
                .withValidator(new StringLengthValidator(
                        "Verification Nummer ist falsch!", 5, 5));


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

                vNummerButton.addClickListener(
                event -> {
                    try {

                        JavaMailUtil.sendMail(email.getValue(),getVnummer(),vorname.getValue());
                        //Notification.show("Wir haben Ihnen einen Email gesendet!");
                        UI.getCurrent().addWindow(new ConfirmationWindow("Wir haben einen Email an diese Adresse gesendet: "+email.getValue()));
                    } catch (Exception e) {
                        UI.getCurrent().addWindow(new ConfirmationWindow("Fehler beim email senden!"));
                        Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, e);
                    }

                });

        binder2.addStatusChangeListener(
                event -> vNummerButton.setEnabled(binder2.isValid()));


                formStudent.addComponent(vNummerField,5);
                formStudent.addComponent(vNummerButton,6);

            });


        }

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

    String vnummer;
    public String getVnummer(){
        vnummer = "12345";//new VerifikationNummer().getRandNummer();
        return vnummer;
    }

}
