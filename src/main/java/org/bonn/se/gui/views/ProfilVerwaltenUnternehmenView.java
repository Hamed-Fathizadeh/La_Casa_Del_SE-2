package org.bonn.se.gui.views;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se.control.ProfilControl;
import org.bonn.se.gui.component.TopPanelUser;
import org.bonn.se.gui.component.UnternehmenBeschreibungView;
import org.bonn.se.gui.component.UnternehmenDatenView;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.model.objects.entitites.Adresse;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.util.Roles;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.dialogs.DefaultConfirmDialogFactory;

public class ProfilVerwaltenUnternehmenView extends GridLayout implements View {
    private final Unternehmen unternehmen = (Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen);

    private final Button bearbeiten = new Button("Bearbeiten",this::addBearbeitenClickListener);
    private final Button cancel = new Button("Abbrechen",this::addCancelClickListener);
    private final Button save = new Button("Speichern", this::addSaveClickListener);
    private HorizontalLayout buttonBar;

    UnternehmenDatenView gridDaten;
    UnternehmenBeschreibungView gridBeschreibung;

    private void addSaveClickListener(Button.ClickEvent clickEvent) {
        ConfirmDialog.Factory confirmUnt = new DefaultConfirmDialogFactory(){
            @Override
            public ConfirmDialog create(String caption, String message, String okCaption, String cancelCaption, String notOkCaption) {
                return super.create("Speichern", message, "Ja", "Nein", notOkCaption);
            }
        } ;
        ConfirmDialog.setFactory(confirmUnt);
        ConfirmDialog.show(MyUI.getCurrent(), "Möchtest Sie wirklich speichern?",
                (ConfirmDialog.Listener) dialog -> {
                    if (dialog.isConfirmed()) {
                        this.removeComponent(buttonBar);
                        this.addComponent(bearbeiten,7,9);
                        unternehmen.setKontaktnummer(gridDaten.getKontaktnummer().getValue());
                        Adresse adresse = new Adresse();
                        adresse.setStrasse(gridDaten.getStrasse().getValue());
                        adresse.setPlz(gridDaten.getOrt().getPlzField().getValue());
                        if(unternehmen.getAdresse().getOrt() == null || unternehmen.getAdresse().getBundesland() == null ) {
                            adresse.setOrt(gridDaten.getOrt().getOrtField().getOrt());
                            adresse.setBundesland(gridDaten.getOrt().getBunesland());
                        }
                        unternehmen.setAdresse(adresse);
                        unternehmen.setBranche(gridDaten.getBranche().getValue());
                        unternehmen.setDescription(gridBeschreibung.getRichTextArea().getValue());
                        ProfilControl.getInstance().updateUnternehmen(unternehmen);
                        gridBeschreibung.setReadOnly(true);
                        gridDaten.setReadOnly(true);
                    }
                });
    }

    private void addCancelClickListener(Button.ClickEvent clickEvent) {
        ConfirmDialog.Factory cancelConf = new DefaultConfirmDialogFactory(){
            @Override
            public ConfirmDialog create(String caption, String message, String okCaption, String cancelCaption, String notOkCaption) {
                return super.create("Abbrechen", message, "Ja", "Nein", notOkCaption);
            }
        } ;
        ConfirmDialog.setFactory(cancelConf);
        ConfirmDialog.show(MyUI.getCurrent(), "Möchtest du wirklich abbrechen?",
                (ConfirmDialog.Listener) dialog -> {
                    if (dialog.isConfirmed()) {
                        this.removeComponent(buttonBar);
                        this.addComponent(bearbeiten,7,9);
                        gridDaten.setReadOnly(true);
                        gridBeschreibung.setReadOnly(true);
                    }
                });
    }

    private void addBearbeitenClickListener(Button.ClickEvent clickEvent) {

        buttonBar = new HorizontalLayout();
        buttonBar.addComponents(cancel,save);
        buttonBar.setMargin(true);
        this.removeComponent(bearbeiten);
        this.addComponent(buttonBar,7,7);
        gridDaten.setReadOnly(false);
        gridBeschreibung.setReadOnly(false);
    }


    public void setUp() {
        TopPanelUser topPanelUser = new TopPanelUser();
        topPanelUser.setWidthFull();
        topPanelUser.setHeightUndefined();
        this.setMargin(false);
        this.setRows(10);
        this.setColumns(10);
        this.setSizeFull();
        this.addComponent(topPanelUser, 0, 0, 9, 0);


        gridDaten = new UnternehmenDatenView();
        gridDaten.setUnternehmenValue();
        gridDaten.setHeight("470px");

        gridBeschreibung = new UnternehmenBeschreibungView();
        gridBeschreibung.setBeschreibungValue();
        gridBeschreibung.setHeight("490px");


        this.addComponent(gridDaten, 0, 2, 9, 5);

        this.setComponentAlignment(gridDaten, Alignment.MIDDLE_CENTER);
        MenuBar bar = new MenuBar();
        MenuBar.Command typeCommand = (MenuBar.Command) selectedItem -> {
            for (MenuBar.MenuItem item : bar.getItems()) {
                item.setChecked(false);
            }
            selectedItem.setChecked(true);
            if (selectedItem.getText().equals("Unternehmensdaten")) {
                this.removeComponent(0, 2);
                this.addComponent(gridDaten, 0, 2, 9, 5);
            } else if (selectedItem.getText().equals("Unternehmensbeschreibung")) {
                this.removeComponent(0, 2);
                this.addComponent(gridBeschreibung, 0, 2, 9, 5);
            }
        };

        bar.addItem("Unternehmensdaten", VaadinIcons.OFFICE, typeCommand).setCheckable(true);
        bar.addItem("Unternehmensbeschreibung", VaadinIcons.WORKPLACE, typeCommand).setCheckable(true);
        typeCommand.menuSelected(bar.getItems().get(0));
        this.addComponent(bar, 0, 1, 9, 1);
        this.setComponentAlignment(bar, Alignment.MIDDLE_CENTER);

        this.addStyleName("grid");
        this.addComponent(new Label("&nbsp", ContentMode.HTML),7,6);
        this.addComponent(bearbeiten,7,7);
        this.addComponent(new Label("&nbsp", ContentMode.HTML),7,8);
        this.setComponentAlignment(bar, Alignment.TOP_CENTER);
        gridBeschreibung.setReadOnly(true);
        gridDaten.setReadOnly(true);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.setUp();
    }

}
