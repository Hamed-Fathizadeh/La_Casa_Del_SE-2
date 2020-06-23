package org.bonn.se.gui.views;


import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.bonn.se.control.AnzStatusControl;
import org.bonn.se.gui.component.TopPanelUser;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.gui.window.StellenanzeigeConfWindow;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.ContainerAnzeigen;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.dialogs.DefaultConfirmDialogFactory;

public class StellenbeschreibungView extends GridLayout implements View {

    public void setUp() {
        this.setMargin(false);
        this.setSizeFull();
        this.setColumns(10);
        this.setRows(10);
        this.addStyleName("anzeige");
        TopPanelUser topPanel =  new TopPanelUser();
        topPanel.addStyleName("toppanel");



        final RichTextArea richTextArea = new RichTextArea();
        richTextArea.setWidth("940px");
        richTextArea.setHeightFull();


        richTextArea.setValue("<h1>Hallo</h1>\n" + "<p>Hier können Sie ihre Stellenbeschreibung verfassen.</p>");

        Button abbrechen = new Button("Abbrechen");

        abbrechen.addClickListener((Button.ClickListener) event -> UI.getCurrent().getNavigator().navigateTo(Views.UnternehmenHomeView));

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
                        (ConfirmDialog.Listener) dialog -> {
                            StellenanzeigeDTO stellenanzeigeDTO = ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen))
                                    .getStellenanzeigeDTO();
                            stellenanzeigeDTO.setBeschreibung(richTextArea.getValue());
                            if (dialog.isConfirmed()) {
                                if (stellenanzeigeDTO.getStatus() == 3) {

                                        stellenanzeigeDTO.setStatus(1);
                                        AnzStatusControl.changeStatus(stellenanzeigeDTO);
                                        ContainerAnzeigen.getInstance().updateAnzeige(stellenanzeigeDTO);

                                } else if (UI.getCurrent().getSession().getAttribute(Roles.Unternehmen) instanceof Unternehmen) {
                                    ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen))
                                            .getStellenanzeigeDTO().setStatus(1);
                                        ContainerAnzeigen.getInstance().setAnzeige((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen));
                                }
                            } else {
                                if (stellenanzeigeDTO.getStatus() == 3) {

                                        stellenanzeigeDTO.setStatus(2);
                                        AnzStatusControl.changeStatus(stellenanzeigeDTO);
                                        ContainerAnzeigen.getInstance().updateAnzeige(stellenanzeigeDTO);

                                } else if (UI.getCurrent().getSession().getAttribute(Roles.Unternehmen) instanceof Unternehmen) {
                                    ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen))
                                            .getStellenanzeigeDTO().setStatus(2);
                                            ContainerAnzeigen.getInstance().setAnzeige((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen));

                                }
                            }
                            StellenanzeigeConfWindow stellenanzeigeConfWindow
                                    = new StellenanzeigeConfWindow(((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen))
                                    .getStellenanzeigeDTO());
                            MyUI.getCurrent().addWindow(stellenanzeigeConfWindow);
                        });






            }
        });

        Button entwurf = new Button("Entwurf speichern");
        entwurf.addClickListener((Button.ClickListener) event -> {
            StellenanzeigeDTO stellenanzeigeDTO = ((Unternehmen) MyUI.getCurrent().getSession().getAttribute(Roles.Unternehmen))
                    .getStellenanzeigeDTO();
            stellenanzeigeDTO.setBeschreibung(richTextArea.getValue());
            if( stellenanzeigeDTO.getStatus() == 3) {
                    ContainerAnzeigen.getInstance().updateAnzeige(stellenanzeigeDTO);
            } else {

                if (UI.getCurrent().getSession().getAttribute(Roles.Unternehmen) instanceof Unternehmen) {
                    ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen))
                            .getStellenanzeigeDTO().setStatus(3);
                }
                    ContainerAnzeigen.getInstance().setAnzeige((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen));

            }
                StellenanzeigeConfWindow stellenanzeigeConfWindow =
                        new StellenanzeigeConfWindow(((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen)).getStellenanzeigeDTO());
                MyUI.getCurrent().addWindow(stellenanzeigeConfWindow);


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

        if (UI.getCurrent().getSession().getAttribute(Roles.Unternehmen) != null) {
                this.setUp();
        } else if (UI.getCurrent().getSession().getAttribute(Roles.Student) != null) {
            UI.getCurrent().getNavigator().getCurrentNavigationState();

        } else {
            UI.getCurrent().getNavigator().navigateTo(Views.LoginView);
        }
    }

}
