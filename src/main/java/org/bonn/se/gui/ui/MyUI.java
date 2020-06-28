package org.bonn.se.gui.ui;

import com.vaadin.annotations.*;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import org.bonn.se.control.CheckSMTP;
import org.bonn.se.control.FeatureToggleControl;
import org.bonn.se.gui.views.LoginView;
import org.bonn.se.gui.views.StudentHomeView;
import org.bonn.se.gui.views.*;

import javax.servlet.annotation.WebServlet;

import static org.bonn.se.services.util.Views.*;


@Push
@Theme("demo")
@Title("Lacolsco")
@PreserveOnRefresh
public class MyUI extends UI {


    @Override
    public void init(VaadinRequest vaadinRequest) {
        this.setSizeFull();


        Navigator navi = new Navigator(this , this );
        navi.addView(RegisterStudent, RegisterStudentView.class);
        navi.addView(LoginView, LoginView.class);
        navi.addView(RegisterUnternehmen, RegisterUnternehmerView.class);
        navi.addView(StudentHomeView, StudentHomeView.class);
        navi.addView(UnternehmenHomeView, org.bonn.se.gui.views.UnternehmenHomeView.class);
        navi.addView(AnzeigeErstellen, AnzeigeErstellenView.class);
        navi.addView(Stellenbeschreibung,  StellenbeschreibungView.class);
        navi.addView(ProfilVerwaltenStudent, ProfilVerwaltenStudentView.class);
        navi.addView(ProfilVerwaltenUnternehmen, ProfilVerwaltenUnternehmenView.class);
        navi.addView(AlleBewerbungenView, org.bonn.se.gui.views.AlleBewerbungenView.class);
        navi.addView(Settings, SettingsView.class);

        if(CheckSMTP.pingHost("smtp.gmail.com",587,100)){
            FeatureToggleControl.getInstance().setSMTPFeature(true);
        } else {
            FeatureToggleControl.getInstance().setSMTPFeature(false);
        }


        UI.getCurrent().getNavigator().navigateTo(RegisterStudent);

    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
