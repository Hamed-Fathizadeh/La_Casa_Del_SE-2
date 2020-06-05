package org.bonn.se.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se.gui.component.TopPanelUser;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.model.objects.entitites.User;
import org.bonn.se.services.util.Views;


public class Settings extends VerticalLayout implements View {



    public void setUp(){
        VerticalLayout layout = new VerticalLayout();
        this.setSizeFull();
        //layout.setMargin(false);

        String ls3 = "<p class=MsoNormal><b><span style='font-size:20.0pt;line-height:107%;\n" +
                "font-family:\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:\n" +
                "minor-bidi;mso-bidi-theme-font:minor-bidi;color:#2F5597;mso-themecolor:accent1;\n" +
                "mso-themeshade:191;mso-style-textfill-fill-color:#2F5597;mso-style-textfill-fill-themecolor:\n" +
                "accent1;mso-style-textfill-fill-alpha:100.0%;mso-style-textfill-fill-colortransforms:\n" +
                "lumm=75000'>Settings<o:p></o:p></span></b></p>";

        TopPanelUser topPanelUser = new TopPanelUser();
        Label ls = new Label(ls3, ContentMode.HTML);


        Label label1 = new Label("<h2>Benutzerkonto löschen</h2>", ContentMode.HTML);

        Label label3 = new Label("<h3>Hier klnnen Sie Ihr Benutzerkonto löschen.Bitte beachten Sie, dass wenn" +
                "Sie Ihr Benutzerkonto löschen all Ihre Daten endgültig gelöscht werden und Ihre Email aus dem " +
                "System entfernt wird. Sollten Sie sich erneut bei Lacolsco anmekden wollen, müssen Sie sich neu registrieren.</h3>", ContentMode.HTML);

        Button loeschen = new Button("Konto löschen");

        layout.addComponent(ls);
        layout.setComponentAlignment(ls, Alignment.MIDDLE_CENTER);

        layout.addComponent(label1);
        layout.setComponentAlignment(label1, Alignment.MIDDLE_CENTER);

        layout.addComponent(label3);
        layout.setComponentAlignment(label3, Alignment.MIDDLE_CENTER);

        layout.addComponent(loeschen);
        layout.setComponentAlignment(loeschen, Alignment.MIDDLE_CENTER);

        loeschen.setEnabled(false);

        this.addComponent(layout);
        this.setSizeFull();

        loeschen.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

            }
        });


    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        User user = ((MyUI) UI.getCurrent()).getUser();
        if( user != null) {
            this.setUp();
        } else {

            UI.getCurrent().getNavigator().navigateTo(Views.UnternehmenHomeView);

        }
    }
}
