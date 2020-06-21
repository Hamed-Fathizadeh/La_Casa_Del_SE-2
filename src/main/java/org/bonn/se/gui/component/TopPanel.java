package org.bonn.se.gui.component;

import com.vaadin.event.MouseEvents;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;

import static org.bonn.se.services.util.Views.*;

public class TopPanel  extends GridLayout {
    public TopPanel(String usertyp) {
        this.setSizeFull();

        String Button2;
        String Button1;
        String NavigateTo1;
        String NavigateTo2;
        //Logo
        ThemeResource resource = new ThemeResource("img/RegisterStudent/logo.png");
        Image logo = new Image(null,resource);
        if(usertyp.equals("Unternehmen")){
            Button1 = "Unternehmen";
            Button2 = "Login";
            NavigateTo1 = LoginView;
            NavigateTo2 = RegisterUnternehmen;
            logo.addClickListener(new MouseEvents.ClickListener() {
                @Override
                public void click(MouseEvents.ClickEvent event) {
                    UI.getCurrent().getNavigator().navigateTo(RegisterUnternehmen);
                }
            });
        }else if(usertyp.equals("Studenten")){
            Button1 = "Studenten";
            Button2 = "Login";
            NavigateTo1 = LoginView;
            NavigateTo2 = RegisterStudent;
            logo.addClickListener(new MouseEvents.ClickListener() {
                @Override
                public void click(MouseEvents.ClickEvent event) {
                    UI.getCurrent().getNavigator().navigateTo(RegisterStudent);
                }
            });
        }else{
            Button1 = "Registrierung Unternehmen";
            Button2 = "Registrierung Student";
            NavigateTo1 = RegisterStudent;
            NavigateTo2 = RegisterUnternehmen;
        }



        logo.setSizeUndefined();
        this.setRows(1);
        this.setColumns(10);
        this.setSizeFull();
        Button switchUnternehmen = new Button(Button1);

        Button login = new Button(Button2);

        switchUnternehmen.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo(NavigateTo2);
            }
        });



        login.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if(login.getCaption().equals("Registrierung Student")){
                    UI.getCurrent().getNavigator().navigateTo(NavigateTo2);
                }else {

                    UI.getCurrent().getNavigator().navigateTo(NavigateTo1);
                }
            }
        });

        this.addComponent(logo,0,0,0,0);
        this.addComponent(switchUnternehmen,8,0,8,0);
        this.addComponent(login,9,0,9,0);
        this.setComponentAlignment(switchUnternehmen,Alignment.MIDDLE_CENTER);
        this.setComponentAlignment(login,Alignment.MIDDLE_CENTER);

        this.addStyleName("toppanel");

    }


}