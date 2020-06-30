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
import org.bonn.se.services.db.exception.DatabaseException;
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
       RegistrationPasswordField vNummer = new RegistrationPasswordField("Verifizierungscode");

       Button passAendern = new Button("Ändern");
       passAendern.setEnabled(false);

       Button vNummerButton = new Button("Verifikation");
       vNummerButton.setEnabled(false);

       Label emptyLabel = new Label("&nbsp;", ContentMode.HTML);

       content.addComponent(emptyLabel);
       content.addComponent(email);
       content.addComponent(neuPasswort);
       content.addComponent(vNummer);
       content.addComponent(vNummerButton);
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


       binder.forField(vNummer)
               .asRequired("Verification Nummer")
               .withValidator(new StringLengthValidator(
                       "Verifizierungscode ist falsch!", 5, 5))
               .bind(User::getPasswort, User::setPasswort);


       passAendern.addClickListener(
               event -> {



                       if (!vnummer.equals(vNummer.getValue())) {
                           UI.getCurrent().addWindow(new ConfirmationWindow("Verifizierungscode ist falsch!"));
                           vNummer.clear();
                       }
                       else if(UserSearchControl.getInstance().existUser(email.getValue())) {
                           PassChangeRequest request = new PassChangeRequest();
                           request.setEmail(email.getValue());
                           request.setNewPass(neuPasswort.getValue());
                           try {
                               PassChangeControl.getInstance().changePass(request);
                           } catch (DatabaseException e) {
                               Logger.getLogger(PassChangeWindow.class.getName()).log(Level.SEVERE,null,e);


                           }
                           this.close();
                       }
                       else {
                           Notification.show("Fehler!");
                           vNummer.clear();
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

       vNummerButton.addClickListener(
               event -> {

                  if( UserSearchControl.getInstance().existUser(email.getValue())) {
                      try {

                          JavaMailUtil.sendMail(email.getValue(), getVnummer(), "");
                          UI.getCurrent().addWindow(new ConfirmationWindow("Wir haben einen Email an diese Adresse gesendet: " + email.getValue()));
                      } catch (Exception e) {
                          ConfirmationWindow confirmationWindow = new ConfirmationWindow("Fehler beim Email senden!");
                          confirmationWindow.setCaption("");
                          UI.getCurrent().addWindow(confirmationWindow);
                          Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, e);
                      }
                  }else{
                      ConfirmationWindow confirmationWindow = new ConfirmationWindow("Mit der E-Mail: "+email.getValue()+" ist kein User registriert!");
                      confirmationWindow.setCaption("");
                      UI.getCurrent().addWindow(confirmationWindow);
                      email.clear();
                  }

               });

       binder2.addStatusChangeListener(
               event -> vNummerButton.setEnabled(binder2.isValid()));

   }



    String vnummer;
    public String getVnummer(){
        vnummer = new VerifikationNummer().getRandNummer();
        return vnummer;
    }

}
