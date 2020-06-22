package org.bonn.se.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se.control.LoginControl;
import org.bonn.se.gui.component.TopPanelUser;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.model.dao.UserDAO;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.dialogs.DefaultConfirmDialogFactory;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SettingsView extends VerticalLayout implements View {

    static GridLayout mainGrid = new GridLayout(2, 5);
    public void setUp() throws DatabaseException, SQLException {

        mainGrid = new GridLayout(1, 5);
        mainGrid.setSizeFull();
        TopPanelUser topPanel = new TopPanelUser();
        String dimensionSet= "200px";
// spruch oben

        String ls3 = "<p class=MsoNormal><b><span style='font-size:28.0pt;line-height:107%;\n" +
                "font-family:\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:\n" +
                "minor-bidi;mso-bidi-theme-font:minor-bidi;color:#ffffff;mso-themecolor:accent1;\n" +
                "mso-themeshade:191;mso-style-textfill-fill-color:#ffffff;mso-style-textfill-fill-themecolor:\n" +
                "accent1;mso-style-textfill-fill-alpha:100.0%;mso-style-textfill-fill-colortransforms:\n" +
                "lumm=75000'>Settings<o:p></o:p></span></b></p>";


        Label lSpruch = new Label(ls3, ContentMode.HTML);
        lSpruch.setHeight(dimensionSet);
        lSpruch.setWidth(dimensionSet);
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


        bottomGridBewNeu.addStyleName("bottomGridBewNeu");
        bottomGridBewNeu.setMargin(true);
        bottomGridBewNeu.setColumnExpandRatio(0,22);


        bottomGridBewNeu.addComponent(formGrid,0,0);

        bottomGridBewNeu.setComponentAlignment(formGrid,Alignment.TOP_CENTER);
        bottomGridBewNeu.setHeight("500px");

        mainGrid.addComponent(topPanel, 0, 0);
        mainGrid.addComponent(vLayoutSpruch, 0, 1);
        mainGrid.addComponent(bottomGridBewNeu, 0, 2);

        mainGrid.setComponentAlignment(topPanel, Alignment.TOP_CENTER);
        mainGrid.setComponentAlignment(vLayoutSpruch, Alignment.TOP_CENTER);
        mainGrid.setComponentAlignment(bottomGridBewNeu, Alignment.TOP_CENTER);


        this.addComponent(mainGrid);
        this.setComponentAlignment(mainGrid, Alignment.TOP_CENTER);
        this.setMargin(false);
        this.addStyleName("backSeite");

        loeschen.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                ConfirmDialog.Factory df = new DefaultConfirmDialogFactory(){

                    @Override
                    public ConfirmDialog create(String caption, String message, String okCaption, String cancelCaption, String notOkCaption) {
                        return super.create("Benutzerkonto löschen", message, "Ja", "Abbrechen", notOkCaption);
                    }
                } ;

                ConfirmDialog.setFactory(df);
                ConfirmDialog.show(MyUI.getCurrent(), "Möchten Sie ihr Konto wirklich löschen?",
                        new ConfirmDialog.Listener() {

                            public void onClose(ConfirmDialog dialog) {
                                if (dialog.isConfirmed()) {
                                    //SQL-BEFEHL
                                    if(MyUI.getCurrent().getSession().getAttribute(Roles.Student) != null){
                                        Student student = (Student) MyUI.getCurrent().getSession().getAttribute(Roles.Student);
                                        try {
                                            UserDAO.deleteUser(student.getEmail());
                                        } catch (DatabaseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    else{
                                       Unternehmen unternehmen= (Unternehmen) MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen);
                                        try {
                                            UserDAO.deleteUser(unternehmen.getEmail());
                                        } catch (DatabaseException e) {
                                            e.printStackTrace();
                                        }
                                    }



                                    Window subWindow = new Window("Löschung Ihres Kontos");
                                    VerticalLayout subContent = new VerticalLayout();
                                    subWindow.setWidth("600px");
                                    subWindow.setHeight(dimensionSet);
                                    subWindow.setContent(subContent);
                                    subContent.addComponent(new Label("Ihr Konto wurde erfolgreich gelöscht!"));
                                    subWindow.center();
                                    UI.getCurrent().addWindow(subWindow);


                                    LoginControl.logoutUser();
                                }
                            }
                        });
            }
        });


    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {


        if (UI.getCurrent().getSession().getAttribute(Roles.Student) != null) {
            try {
                this.setUp();
            } catch (DatabaseException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
            }

        } else if(UI.getCurrent().getSession().getAttribute(Roles.Unternehmen) != null) {
            try {
                this.setUp();
            } catch (DatabaseException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
            }

        } else {
            UI.getCurrent().getNavigator().getCurrentNavigationState();
        }
    }
}
