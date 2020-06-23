package org.bonn.se.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import org.bonn.se.gui.component.Bewerbungen;
import org.bonn.se.gui.component.TopPanelUser;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.model.objects.entitites.ContainerLetztenBewerbungen;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;

public class AlleBewerbungenView extends VerticalLayout implements View {

    static GridLayout mainGrid = new GridLayout(2, 5);
    public void setUp() {

        mainGrid = new GridLayout(1, 5);
        mainGrid.setSizeFull();
        TopPanelUser topPanel = new TopPanelUser();

        String ls3 = "<p class=MsoNormal><b><span style='font-size:28.0pt;line-height:107%;\n" +
                "font-family:\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:\n" +
                "minor-bidi;mso-bidi-theme-font:minor-bidi;color:#ffffff;mso-themecolor:accent1;\n" +
                "mso-themeshade:191;mso-style-textfill-fill-color:#ffffff;mso-style-textfill-fill-themecolor:\n" +
                "accent1;mso-style-textfill-fill-alpha:100.0%;mso-style-textfill-fill-colortransforms:\n" +
                "lumm=75000'>Alle Bewerbungen<o:p></o:p></span></b></p>";

        Label lSpruch = new Label(ls3, ContentMode.HTML);



        ContainerLetztenBewerbungen containerBewerbungen  = ContainerLetztenBewerbungen.getInstance();
        containerBewerbungen.load("Alle");
        Bewerbungen<BewerbungDTO> bewerbungen = new Bewerbungen(containerBewerbungen,"AlleBewerbungenView");
        bewerbungen.setHeightMode(HeightMode.UNDEFINED);

        GridLayout bottomGridBewNeu = new GridLayout(1, 1);
        bottomGridBewNeu.setSizeFull();
        bottomGridBewNeu.addStyleName("bottomGridBewNeu");
        bottomGridBewNeu.setMargin(true);


        bottomGridBewNeu.addComponent(bewerbungen,0,0);

        bottomGridBewNeu.setComponentAlignment(bewerbungen,Alignment.TOP_CENTER);


        mainGrid.addComponent(topPanel, 0, 0);
        mainGrid.addComponent(lSpruch, 0, 1);
        mainGrid.addComponent(bottomGridBewNeu, 0, 2);

        mainGrid.setComponentAlignment(topPanel, Alignment.TOP_CENTER);
        mainGrid.setComponentAlignment(lSpruch, Alignment.TOP_CENTER);
        mainGrid.setComponentAlignment(bottomGridBewNeu, Alignment.TOP_CENTER);


        this.addComponent(mainGrid);
        this.setComponentAlignment(mainGrid, Alignment.TOP_CENTER);
        this.setMargin(false);
        this.addStyleName("grid");

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {


        if (UI.getCurrent().getSession().getAttribute(Roles.Student) != null) {
                this.setUp();
        } else if (UI.getCurrent().getSession().getAttribute(Roles.Unternehmen) != null) {
            UI.getCurrent().getNavigator().getCurrentNavigationState();
        } else {
            UI.getCurrent().getNavigator().navigateTo(Views.LoginView);
        }

    }

}

