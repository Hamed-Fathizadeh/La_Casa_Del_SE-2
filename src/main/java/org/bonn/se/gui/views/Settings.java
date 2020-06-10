package org.bonn.se.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import org.bonn.se.control.LoginControl;
import org.bonn.se.gui.component.Bewerbungen;
import org.bonn.se.gui.component.TopPanelUser;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.gui.window.RegisterStudentWindow;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.model.objects.entitites.ContainerLetztenBewerbungen;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.User;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.dialogs.DefaultConfirmDialogFactory;


public class Settings  extends VerticalLayout implements View {

    static GridLayout Maingrid = new GridLayout(2, 5);
    public void setUp() {

        Maingrid = new GridLayout(1, 5);
        Maingrid.setSizeFull();
        TopPanelUser topPanel = new TopPanelUser();

// spruch oben

        String ls3 = "<p class=MsoNormal><b><span style='font-size:28.0pt;line-height:107%;\n" +
                "font-family:\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:\n" +
                "minor-bidi;mso-bidi-theme-font:minor-bidi;color:#ffffff;mso-themecolor:accent1;\n" +
                "mso-themeshade:191;mso-style-textfill-fill-color:#ffffff;mso-style-textfill-fill-themecolor:\n" +
                "accent1;mso-style-textfill-fill-alpha:100.0%;mso-style-textfill-fill-colortransforms:\n" +
                "lumm=75000'>Settings<o:p></o:p></span></b></p>";


        Label lSpruch = new Label(ls3, ContentMode.HTML);
        lSpruch.setHeight("200px");
        lSpruch.setWidth("200px");
        GridLayout vLayoutSpruch = new GridLayout(1,1);
        vLayoutSpruch.addComponent(lSpruch,0,0);
        vLayoutSpruch.addStyleName("grid");
        vLayoutSpruch.setMargin(true);
        vLayoutSpruch.setSizeFull();
        vLayoutSpruch.setComponentAlignment(lSpruch, Alignment.TOP_CENTER);


        GridLayout formGrid = new GridLayout(1, 4);
        formGrid.addStyleName("AnzeigeUnternehmen");
        formGrid.setMargin(true);
        formGrid.setWidth("1000px");
        formGrid.setHeight("300px");


        Label label1 = new Label("<h2>Benutzerkonto löschen</h2>", ContentMode.HTML);

        Label label3 = new Label("<h3>Hier können Sie Ihr Benutzerkonto löschen. Bitte beachten Sie, dass wenn " +
                "Sie Ihr Benutzerkonto löschen<br>all Ihre Daten endgültig gelöscht werden und Ihre Email aus dem " +
                "System entfernt wird. Sollten Sie sich erneut<br>bei Lacolsco anmelden wollen, müssen Sie sich neu registrieren.</h3>", ContentMode.HTML);

        Button loeschen = new Button("Konto löschen");

        Label line = new Label("<hr>",ContentMode.HTML);

        formGrid.addComponent(label1);
        formGrid.setComponentAlignment(label1, Alignment.TOP_CENTER);

        formGrid.addComponent(label3);
        formGrid.setComponentAlignment(label3, Alignment.MIDDLE_CENTER);

        formGrid.addComponent(loeschen);
        formGrid.setComponentAlignment(loeschen, Alignment.MIDDLE_CENTER);

        formGrid.addComponent(line);
        formGrid.setComponentAlignment(line, Alignment.BOTTOM_CENTER);


        GridLayout bottomGridBewNeu = new GridLayout(1, 1);

        //bottomGridBewNeu.setHeight("700px");
        bottomGridBewNeu.addStyleName("bottomGridBewNeu");
        bottomGridBewNeu.setMargin(true);
        bottomGridBewNeu.setColumnExpandRatio(0,22);


        bottomGridBewNeu.addComponent(formGrid,0,0);

        bottomGridBewNeu.setComponentAlignment(formGrid,Alignment.TOP_CENTER);
        bottomGridBewNeu.setHeight("500px");

        Maingrid.addComponent(topPanel, 0, 0);
       Maingrid.addComponent(vLayoutSpruch, 0, 1);
        Maingrid.addComponent(bottomGridBewNeu, 0, 2);

        Maingrid.setComponentAlignment(topPanel, Alignment.TOP_CENTER);
       Maingrid.setComponentAlignment(vLayoutSpruch, Alignment.TOP_CENTER);
        Maingrid.setComponentAlignment(bottomGridBewNeu, Alignment.TOP_CENTER);


        this.addComponent(Maingrid);
        this.setComponentAlignment(Maingrid, Alignment.TOP_CENTER);
        this.setMargin(false);
        this.addStyleName("backSeite");

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        if( UI.getCurrent().getSession().getAttribute(Roles.Student) != null) {
           this.setUp();

        } else  if( UI.getCurrent().getSession().getAttribute(Roles.Unternehmen) != null) {
            this.setUp();
        }
        else{

         UI.getCurrent().getNavigator().getCurrentNavigationState();
        }

    }

}

