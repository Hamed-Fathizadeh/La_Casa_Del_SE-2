package org.bonn.se.gui.ui;

import com.vaadin.annotations.*;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import org.bonn.se.control.CheckSMTP;
import org.bonn.se.control.FeatureToggleControl;
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
        navi.addView(REGISTERSTUDENT, RegisterStudentView.class);
        navi.addView(LOGINVIEW, LoginView.class);
        navi.addView(REGISTERUNTERNEHMEN, RegisterUnternehmerView.class);
        navi.addView(STUDENTHOMEVIEW, StudentHomeView.class);
        navi.addView(UNTERNEHMENHOMEVIEW, org.bonn.se.gui.views.UnternehmenHomeView.class);
        navi.addView(ANZEIGEERSTELLEN, AnzeigeErstellenView.class);
        navi.addView(STELLENBESCHREIBUNG,  StellenbeschreibungView.class);
        navi.addView(PROFILVERWALTENSTUDENT, ProfilVerwaltenStudentView.class);
        navi.addView(PROFILVERWALTENUNTERNEHMEN, ProfilVerwaltenUnternehmenView.class);
        navi.addView(ALLEBEWERBUNGENVIEW, org.bonn.se.gui.views.AlleBewerbungenView.class);
        navi.addView(SETTINGS, SettingsView.class);

        if(CheckSMTP.pingHost("smtp.gmail.com",587,100)){
            FeatureToggleControl.getInstance().setSMTPFeature(true);
        } else {
            FeatureToggleControl.getInstance().setSMTPFeature(false);
        }


        UI.getCurrent().getNavigator().navigateTo(REGISTERSTUDENT);

    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = true)
    public static class MyUIServlet extends VaadinServlet {
    }
}
