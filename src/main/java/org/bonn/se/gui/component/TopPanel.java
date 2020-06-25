package org.bonn.se.gui.component;

import com.vaadin.event.MouseEvents;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;

import static org.bonn.se.services.util.Views.*;

public class TopPanel  extends GridLayout {
    public TopPanel(String usertyp) {
        this.setSizeFull();
        this.setMargin(true);

        String buttonTwo;
        String buttonOne;
        String navigateToOne;
        String navigateToTwo;
        ThemeResource resource = new ThemeResource("img/RegisterStudent/logo.png");
        Image logo = new Image(null,resource);
        if(usertyp.equals("Unternehmen")){
            buttonOne = "Unternehmen";
            buttonTwo = "Login";
            navigateToOne = LoginView;
            navigateToTwo = RegisterUnternehmen;
            logo.addClickListener((MouseEvents.ClickListener) event -> UI.getCurrent().getNavigator().navigateTo(RegisterUnternehmen));
        }else if(usertyp.equals("Studenten")){
            buttonOne = "Studenten";
            buttonTwo = "Login";
            navigateToOne = LoginView;
            navigateToTwo = RegisterStudent;
            logo.addClickListener((MouseEvents.ClickListener) event -> UI.getCurrent().getNavigator().navigateTo(RegisterStudent));
        }else{
            buttonOne = "Registrierung Unternehmen";
            buttonTwo = "Registrierung Student";
            navigateToOne = RegisterStudent;
            navigateToTwo = RegisterUnternehmen;
        }



        logo.setSizeUndefined();
        this.setRows(1);
        this.setColumns(10);
        this.setSizeFull();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button switchUnternehmen = new Button(buttonOne);
        Button login = new Button(buttonTwo);
        horizontalLayout.addComponents(switchUnternehmen,login);
        horizontalLayout.setMargin(true);
        switchUnternehmen.addClickListener((Button.ClickListener) event -> UI.getCurrent().getNavigator().navigateTo(navigateToTwo));



        login.addClickListener((Button.ClickListener) event -> {
            if(login.getCaption().equals("Registrierung Student")){
                UI.getCurrent().getNavigator().navigateTo(navigateToTwo);
            }else {

                UI.getCurrent().getNavigator().navigateTo(navigateToOne);
            }
        });

        this.addComponent(logo,0,0,0,0);
        this.addComponent(horizontalLayout,8,0,9,0);
        this.setComponentAlignment(horizontalLayout,Alignment.MIDDLE_RIGHT);

        this.addStyleName("toppanel");

    }


}