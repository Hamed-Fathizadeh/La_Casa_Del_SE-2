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
import org.bonn.se.control.UserSearchControl;
import org.bonn.se.gui.component.RegistrationPasswordField;
import org.bonn.se.gui.component.RegistrationTextField;
import org.bonn.se.gui.component.TopPanel;
import org.bonn.se.gui.window.RegisterStudentWindow;
import org.bonn.se.model.dao.UserDAO;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.User;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;


public class RegisterStudentView extends GridLayout implements View {

    public void setUp() {



        System.out.println("LOG: get UI-Objekt in RegisterStudent!" + VaadinSession.getCurrent().toString());

        this.setColumns(10);
        this.setRows(10);

        this.addStyleName("grid");
        this.setSizeFull();



        TopPanel topPanel_Student =  new TopPanel("Unternehmen");
        topPanel_Student.addStyleName("toppanel");
        topPanel_Student.setHeight("120px");

        String ls1 = "<div class=WordSection1>\n" +
                "\n" +
                "<p class=MsoNormal><b><span style='font-size:36.0pt;line-height:107%;\n" +
                "font-family:\"Edwardian Script ITC\";color:#002635'>»&nbsp;Willkommen bei <span\n" +
                "class=SpellE>Lacolsco</span><o:p></o:p></span></b></p>\n" +
                "\n" +
                "<p class=MsoNormal><b><span style='font-size:36.0pt;line-height:107%;\n" +
                "font-family:\"Edwardian Script ITC\";color:#002635'>Registrieren Sie sich jetzt!<o:p></o:p></span></b></p>\n" +
                "\n" +
                "</div>";

        Label head = new Label(ls1, ContentMode.HTML);

        FormLayout form_Student = new FormLayout();
        form_Student.setMargin(true);
        RegistrationTextField vorname = new RegistrationTextField("Vorname");
        vorname.selectAll();
        vorname.setValue(VaadinService.getCurrent().getBaseDirectory().toString());


        RegistrationTextField nachname = new RegistrationTextField( "Nachname");
        nachname.setValue(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath());
        RegistrationTextField email = new RegistrationTextField("E-Mail");
        RegistrationPasswordField passwort = new RegistrationPasswordField("Passwort");

        Button registerStudentButton = new Button("Registrieren");
        registerStudentButton.setEnabled(false);

        Button vNummerButton = new Button("Verifikation");
        vNummerButton.setEnabled(false);

        form_Student.addComponents(head,vorname,nachname,email,passwort,registerStudentButton);
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

        ThemeResource resource = new ThemeResource("img/RegisterStudent/student_pic.png");


        Image bild_Student = new Image(null,resource);

        form_Student.setMargin(false);
        this.addComponent(topPanel_Student, 0, 0, 9, 1);
        this.addComponent(form_Student, 0, 5, 0, 5);
        this.addComponent(bild_Student, 9, 5, 9, 5);
        this.setComponentAlignment(topPanel_Student, Alignment.TOP_LEFT);
        this.setComponentAlignment(form_Student, Alignment.MIDDLE_LEFT);

        this.setComponentAlignment(bild_Student, Alignment.MIDDLE_RIGHT);


        this.setMargin(false);


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

                            UI.getCurrent().getSession().setAttribute(Roles.Student,student);


                            RegisterStudentWindow registerStudentWindow = new RegisterStudentWindow();
                            UI.getCurrent().addWindow(registerStudentWindow);

                        }

                    } catch(DatabaseException e){
                        e.printStackTrace();
                    }

                });


        binder.addStatusChangeListener(
                event -> registerStudentButton.setEnabled(binder.isValid()));

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