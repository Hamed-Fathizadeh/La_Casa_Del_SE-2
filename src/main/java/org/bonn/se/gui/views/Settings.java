package org.bonn.se.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se.gui.component.TopPanelUser;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.User;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;


public class Settings extends GridLayout implements View {



    public void setUp(){
        //VerticalLayout layout = new VerticalLayout();
        //this.setSizeFull();
        //layout.setMargin(false);
        this.setRows(4);
        this.setColumns(10);
        this.addStyleName("Settings");
        this.setSizeFull();

        GridLayout formGrid = new GridLayout(1, 4);
        formGrid.addStyleName("einstellungen");
        formGrid.setMargin(true);

        TopPanelUser topPanelUser = new TopPanelUser();
        topPanelUser.addStyleName("toppanel");


//spruch oben Settings

        String ls3 = "<p class=MsoNormal><b><span style='font-size:28.0pt;line-height:107%;\n" +
                "font-family:\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:\n" +
                "minor-bidi;mso-bidi-theme-font:minor-bidi;color:#ffffff;mso-themecolor:accent1;\n" +
                "mso-themeshade:191;mso-style-textfill-fill-color:#ffffff;mso-style-textfill-fill-themecolor:\n" +
                "accent1;mso-style-textfill-fill-alpha:100.0%;mso-style-textfill-fill-colortransforms:\n" +
                "lumm=75000'>Settings<o:p></o:p></span></b></p>";

        Label lSpruch = new Label(ls3, ContentMode.HTML);

      //  Label ls = new Label(String.format("<font size = \"5\" color=\"white\"> Settings" ), ContentMode.HTML);
        //Student student = (Student) MyUI.getCurrent().getSession().getAttribute(Roles.Student);


        Label label1 = new Label("<h2>Benutzerkonto löschen</h2>", ContentMode.HTML);

        Label label3 = new Label("<h3>Hier können Sie Ihr Benutzerkonto löschen. Bitte beachten Sie, dass wenn " +
                "Sie Ihr Benutzerkonto löschen<br>all Ihre Daten endgültig gelöscht werden und Ihre Email aus dem " +
                "System entfernt wird. Sollten Sie sich erneut<br>bei Lacolsco anmelden wollen, müssen Sie sich neu registrieren.</h3>", ContentMode.HTML);

        Button loeschen = new Button("Konto löschen");

        Label line = new Label("<hr>",ContentMode.HTML);

        this.addComponent(topPanelUser,0,0,9,0);
        this.addComponent(formGrid, 2,2,7,2);

        this.addComponent(lSpruch, 1,1,9,1);
        this.setComponentAlignment(lSpruch, Alignment.TOP_CENTER);

        formGrid.addComponent(label1);
        formGrid.setComponentAlignment(label1, Alignment.MIDDLE_CENTER);

        formGrid.addComponent(label3);
        formGrid.setComponentAlignment(label3, Alignment.MIDDLE_CENTER);

        formGrid.addComponent(loeschen);
        formGrid.setComponentAlignment(loeschen, Alignment.MIDDLE_CENTER);

        formGrid.addComponent(line);
        formGrid.setComponentAlignment(line, Alignment.MIDDLE_CENTER);

        loeschen.setEnabled(true);


        this.setComponentAlignment(formGrid, Alignment.MIDDLE_CENTER);
        this.setMargin(false);


        loeschen.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

            }
        });


    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {


        if (UI.getCurrent().getSession().getAttribute(Roles.Student) != null) {
            this.setUp();

        } else if(UI.getCurrent().getSession().getAttribute(Roles.Unternehmen) != null) {
            this.setUp();

        } else {
          UI.getCurrent().getNavigator().getCurrentNavigationState();
        }
    }
}
