package org.bonn.se.gui.views;


import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.bonn.se.gui.component.TopPanelUser;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.gui.window.StellenbeschreibungConfirmation;
import org.bonn.se.model.dao.ContainerAnzDAO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.dialogs.DefaultConfirmDialogFactory;

public class Stellenbeschreibung extends GridLayout implements View {
    StellenanzeigeDTO sa;
    public Stellenbeschreibung(StellenanzeigeDTO sa){
        this.sa = sa;
    }

    public void setUp() {
        this.setMargin(false);
        this.setSizeFull();
        this.setColumns(10);
        this.setRows(10);
        this.addStyleName("anzeige");
        TopPanelUser topPanel =  new TopPanelUser();
        topPanel.addStyleName("toppanel");



        final RichTextArea richTextArea = new RichTextArea();
        richTextArea.setSizeFull();

        if( ((Unternehmen)MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).getStellenanzeigeDTO() != null) {
         richTextArea.setValue( ((Unternehmen)MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).getStellenanzeigeDTO().getBeschreibung());
         ((Unternehmen)MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).setStellenanzeigeDTO(null);

        } else {
            richTextArea.setValue("<h1>Hallo</h1>\n" +
                    "<p>Hier können Sie ihre Stellenbeschreibung verfassen.</p>");
        }
        Button abbrechen = new Button("Abbrechen");

        abbrechen.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo(Views.UnternehmenHomeView);
            }
        });

        Button fertig = new Button("Fertig");
        fertig.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                ConfirmDialog.Factory df = new DefaultConfirmDialogFactory(){

                    @Override
                    public ConfirmDialog create(String caption, String message, String okCaption, String cancelCaption, String notOkCaption) {
                        return super.create("Bestätigung", message, "Ja", "Nein", notOkCaption);
                    }
                } ;
                ConfirmDialog.setFactory(df);
                ConfirmDialog.show(MyUI.getCurrent(), "Möchtest du die Anzeige online schalten?",
                        new ConfirmDialog.Listener() {

                            public void onClose(ConfirmDialog dialog) {
                                if (dialog.isConfirmed()) {
                                    if (UI.getCurrent().getSession().getAttribute(Roles.Unternehmen) instanceof Unternehmen) {

                                        ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen))
                                                .getStellenanzeige().setBeschreibung(richTextArea.getValue());
                                        ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen))
                                                .getStellenanzeige().setStatus(1);
                                    }

                                } else{
                                    if (UI.getCurrent().getSession().getAttribute(Roles.Unternehmen) instanceof Unternehmen) {
                                        ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen))
                                                .getStellenanzeige().setBeschreibung(richTextArea.getValue());
                                        ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen))
                                                .getStellenanzeige().setStatus(2);
                                    }
                                }
                                try {
                                    ContainerAnzDAO.setAnzeige();

                                } catch (DatabaseException e) {
                                    e.printStackTrace();
                                }
                                StellenbeschreibungConfirmation stellenbeschreibungConfirmation = new StellenbeschreibungConfirmation(((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen))
                                        .getStellenanzeige());
                                MyUI.getCurrent().addWindow(stellenbeschreibungConfirmation);
                            }
                        });






            }
        });

        Button entwurf = new Button("Entwurf speichern");
        entwurf.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {

                if (UI.getCurrent().getSession().getAttribute(Roles.Unternehmen) instanceof Unternehmen) {
                    ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen))
                            .getStellenanzeige().setBeschreibung(richTextArea.getValue());
                    ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen))
                            .getStellenanzeige().setStatus(3);
                }
                try {
                    ContainerAnzDAO.setAnzeige();

                } catch (DatabaseException e) {
                    e.printStackTrace();
                }
                StellenbeschreibungConfirmation stellenbeschreibungConfirmation = new StellenbeschreibungConfirmation(((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen))
                        .getStellenanzeige());
                MyUI.getCurrent().addWindow(stellenbeschreibungConfirmation);


            }
        });


        this.addComponent(topPanel, 0, 0, 9, 1);
        this.addComponent(richTextArea,0,2,9,7);
        this.addComponent(abbrechen,3,8,3,8);
        this.addComponent(entwurf,5,8,5,8);

        this.addComponent(fertig,6,8,6,8);

        this.setComponentAlignment(richTextArea,Alignment.TOP_CENTER);
        this.setComponentAlignment(abbrechen,Alignment.MIDDLE_CENTER);
        this.setComponentAlignment(entwurf,Alignment.MIDDLE_CENTER);
        this.setComponentAlignment(fertig,Alignment.MIDDLE_CENTER);
        this.setComponentAlignment(topPanel, Alignment.TOP_LEFT);

        this.setMargin(false);



    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if( ( MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen)!= null )) {
            this.setUp();
        } else {
            UI.getCurrent().getNavigator().navigateTo(Views.RegisterUnternehmen);
        }
    }


}
