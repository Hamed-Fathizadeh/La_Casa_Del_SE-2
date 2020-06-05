package org.bonn.se.gui.ui;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import org.bonn.se.gui.views.*;
import org.bonn.se.model.objects.entitites.User;

import javax.servlet.annotation.WebServlet;

import static org.bonn.se.services.util.Views.*;


/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("demo")
@Title("Lacolsco")
@PreserveOnRefresh
public class MyUI extends UI {

    private User user = null;
    public User getUser() {
        return user;
    }

    public User setUser(User user) {
        this.user = user;
        return user;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        this.setSizeFull();

        System.out.println("LOG: neues UI-Objekt erzeugt!" + VaadinSession.getCurrent().toString());

        Navigator navi = new Navigator(this , this );
        navi.addView(RegisterStudent, RegisterStudent.class);
        navi.addView(MainView, org.bonn.se.gui.views.MainView.class);//loginview
        navi.addView(RegisterUnternehmen, RegisterUnternehmer.class);
        navi.addView(StudentHomeView, StudentHomeView.class);
        navi.addView(UnternehmenHomeView, org.bonn.se.gui.views.UnternehmenHomeView.class);
        navi.addView(AnzeigeErstellen, org.bonn.se.gui.views.AnzeigeErstellen.class);
        navi.addView(Stellenbeschreibung, org.bonn.se.gui.views.Stellenbeschreibung.class);
        navi.addView(ProfilVerwaltenStudent, org.bonn.se.gui.views.ProfilVerwaltenStudent.class);
        navi.addView(AlleBewerbungenView, org.bonn.se.gui.views.AlleBewerbungenView.class);



        UI.getCurrent().getNavigator().navigateTo(RegisterStudent);

    }

    public MyUI getMyUI(){
        return (MyUI) UI.getCurrent();
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
