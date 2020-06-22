package org.bonn.se.gui.window;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se.control.PassChangeControl;
import org.bonn.se.control.UserSearchControl;
import org.bonn.se.gui.component.RegistrationPasswordField;
import org.bonn.se.gui.component.RegistrationTextField;
import org.bonn.se.model.objects.dto.PassChangeRequest;
import org.bonn.se.model.objects.entitites.User;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.util.JavaMailUtil;
import org.bonn.se.services.util.VerifikationNummer;

import java.util.logging.Level;
import java.util.logging.Logger;


public class PassChangeWindow extends Window {

   public  PassChangeWindow(){
       super("Passwort ändern!");
       center();


       VerticalLayout content = new VerticalLayout();
       content.setMargin(true);
       setContent(content);

       RegistrationTextField email = new RegistrationTextField("E-Mail");
       RegistrationPasswordField neuPasswort = new RegistrationPasswordField("Neue Passwort");
       RegistrationPasswordField Vnummer = new RegistrationPasswordField("Verifizierungscode");

       Button passAendern = new Button("Ändern");
       passAendern.setEnabled(false);

       Button VnummerButton = new Button("Verifikation");
       VnummerButton.setEnabled(false);

       Label emptyLabel = new Label("&nbsp;", ContentMode.HTML);

       content.addComponent(emptyLabel);
       content.addComponent(email);
       content.addComponent(neuPasswort);
       content.addComponent(Vnummer);
       content.addComponent(VnummerButton);
       content.addComponent(passAendern);

       Binder<User> binder = new Binder<>(User.class);

       binder.forField(email)
               .asRequired("E-Mail muss angegeben werden")
               .withValidator(new EmailValidator("Keine gültige E-Mail Adresse!"))
               .bind(User::getEmail, User::setEmail);

       binder.forField(neuPasswort)
               .asRequired("Password may not be empty")
               .withValidator(new StringLengthValidator(
                       "Passwort muss mindestens 8 Zeichen lang sein", 8, null))
               .bind(User::getPasswort, User::setPasswort);


       binder.forField(Vnummer)
               .asRequired("Verification Nummer")
               .withValidator(new StringLengthValidator(
                       "Verifizierungscode ist falsch!", 5, 5))
               .bind(User::getPasswort, User::setPasswort);


       passAendern.addClickListener(
               event -> {



                       if (!vnummer.equals(Vnummer.getValue())) {
                           UI.getCurrent().addWindow(new ConfirmationWindow("Verifizierungscode ist falsch!"));
                           Vnummer.clear();
                       }
                       else if(UserSearchControl.getInstance().existUser(email.getValue())) {
                           PassChangeRequest request = new PassChangeRequest();
                           request.setEmail(email.getValue());
                           request.setNewPass(neuPasswort.getValue());
                           PassChangeControl.getInstance().changePass(request);
                           this.close();
                       }
                       else {
                           Notification.show("Fehler!");
                           Vnummer.clear();
                       }



               });

       binder.addStatusChangeListener(
               event -> passAendern.setEnabled(binder.isValid()));

       Binder<User> binder2 = new Binder<>(User.class);

       binder2.forField(email)
               .asRequired("Password may not be empty")
               .withValidator(new StringLengthValidator(
                       "Passwort muss mindestens 8 Zeichen lang sein", 8, null))
               .bind(User::getPasswort, User::setPasswort);
       binder2.forField(neuPasswort)
               .asRequired("Password may not be empty")
               .withValidator(new StringLengthValidator(
                       "Passwort muss mindestens 8 Zeichen lang sein", 8, null))
               .bind(User::getPasswort, User::setPasswort);

       VnummerButton.addClickListener(
               event -> {
                   try {

                       JavaMailUtil.sendMail(email.getValue(),getVnummer(),"");
                       //Notification.show("Wir haben Ihnen einen Email gesendet!");
                       UI.getCurrent().addWindow(new ConfirmationWindow("Wir haben einen Email an diese Adresse gesendet: "+email.getValue()));
                   } catch (Exception e) {
                       UI.getCurrent().addWindow(new ConfirmationWindow("Fehler beim Email senden!"));
                       Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, e);
                   }

               });

       binder2.addStatusChangeListener(
               event -> VnummerButton.setEnabled(binder2.isValid()));

   }



    String vnummer;
    public String getVnummer(){
        vnummer = new VerifikationNummer().getRandNummer();
        return vnummer;
    }

}
