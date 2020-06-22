package org.bonn.se.control;


import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import org.bonn.se.control.exception.NoSuchUserOrPassword;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.model.dao.ProfilDAO;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.model.objects.entitites.User;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginControl {

    private LoginControl(){

    }

    private static LoginControl instance;

    public static LoginControl getInstance() {
        return instance == null ? instance = new LoginControl() : instance;
    }

    public void checkAuthentication ( String login , String password) throws NoSuchUserOrPassword, DatabaseException {

//DB-Zugriff
        ResultSet set;
        String c = "'";
        if(password.indexOf('/') != -1 || password.indexOf(c.charAt(0)) != -1){
            throw new DatabaseException("Die Zeichen ' oder / sind nicht als Passwort erlaubt!.");
        }

        try {
            Statement statement = JDBCConnection.getInstance().getStatement();

            set = statement.executeQuery("SELECT * "
                    + "FROM lacasa.tab_user "
                    + "WHERE upper(lacasa.tab_user.email) = '" +login.toUpperCase() + "'"
                    + " AND lacasa.tab_user.passwort = '" + password + "'");

        } catch (SQLException | DatabaseException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        }

        User user = null;
        try {
            if ( set.next()) {

                user = new User();
                user.setVorname(set.getString("vorname"));
                user.setNachname(set.getString("nachname"));
                user.setEmail((set.getString(1)));
                user.setPasswort(set.getString(2));
                user.setType(set.getString(5));

                if(set.getString(5).equals("C")) {
                        Unternehmen unternehmen = new Unternehmen();
                        MyUI.getCurrent().getSession().setAttribute(Roles.Unternehmen,unternehmen);
                        unternehmen.setEmail(user.getEmail());
                        unternehmen.setVorname(user.getVorname());
                        unternehmen.setNachname(user.getNachname());
                        unternehmen = ProfilDAO.getInstance().getUnternehmenProfil(unternehmen);

                        UI.getCurrent().getSession().setAttribute(Roles.Unternehmen,unternehmen);

                    } else if(set.getString(5).equals("S")) {
                        Student student;
                        student = ProfilDAO.getInstance().getStudent(user.getEmail());

                        UI.getCurrent().getSession().setAttribute(Roles.Student,student);


                     } else {
                        throw new NoSuchUserOrPassword();
                     }
          }else{
                throw new DatabaseException("Fehler Passwort oder Email ist falsch!");
            }
        } catch (SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }

        UI.getCurrent().getNavigator().navigateTo(Views.LoginView);
    }

    public static void logoutUser() {

        VaadinSession vaadinSession = UI.getCurrent().getSession();
        vaadinSession.setAttribute(Roles.Student,null);
        vaadinSession.setAttribute(Roles.Unternehmen,null);
        UI.getCurrent().getNavigator().navigateTo(Views.LoginView);
    }


}

