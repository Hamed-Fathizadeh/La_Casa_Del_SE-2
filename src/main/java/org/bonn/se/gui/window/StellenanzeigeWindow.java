package org.bonn.se.gui.window;

import com.vaadin.addon.onoffswitch.OnOffSwitch;
import com.vaadin.data.HasValue;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.model.dao.ContainerAnzDAO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.dialogs.DefaultConfirmDialogFactory;


public class StellenanzeigeWindow extends Window {

    public StellenanzeigeWindow(StellenanzeigeDTO stellenanzeige, Unternehmen unternehmen_data)  {
        setUp(stellenanzeige,unternehmen_data);
    }

    public void setUp(StellenanzeigeDTO stellenanzeige, Unternehmen unternehmen_data) {
        center();

        this.setWidth("80%");
        this.setHeight("90%");
        this.setModal(true);
        this.setResizable(false);
        this.setClosable(false);

        Panel panel = new Panel();
        panel.setWidthFull();


        GridLayout gridLayout = new GridLayout(5,15);
        gridLayout.setSizeFull();
        gridLayout.setMargin(true);

        Image logo = unternehmen_data.getLogo();

        Label firmenname = new Label("<b>Firmenname</b>",ContentMode.HTML);
        Label branche = new Label("<b>Branche</b>",ContentMode.HTML);
        Label art = new Label("<b>Art der Einstellung:</b>",ContentMode.HTML);
        Label ort= new Label("<b>Ort</b>",ContentMode.HTML);
        Label bundesland = new Label("<b>Bundesland</b>",ContentMode.HTML);
        Label information = new Label("<h3><b><font color=\"blue\">Informationen über das Unternehmen</font></b></h3>",ContentMode.HTML);
        Label kontakt = new Label("<h3><b><font color=\"blue\">Kontakt</font></b></h3>",ContentMode.HTML);
        Label beginn= new Label("<b>Beginn</b>",ContentMode.HTML);
        Label hauptsitz= new Label("<b>Hauptsitz</b>",ContentMode.HTML);
        Label gruendungsjahr= new Label("<b>Gründungsjahr</b>",ContentMode.HTML);
        Label reicheweite = new Label("<b>Reichweite</b>",ContentMode.HTML);
        Label anzMitarbeiter = new Label("<b>Anzahl der Mitarbeiter</b>",ContentMode.HTML);
        Label ansprechpartner = new Label("<b>Ansprechpartner/in</b>",ContentMode.HTML);
        Label email = new Label("<b>E-Mail</b>",ContentMode.HTML);
        Label kontaktnummer1 = new Label("<b>Kontaktnummer 1</b>",ContentMode.HTML);
        Label kontaktnummer2 = new Label("<b>Kontaktnummer 2</b>",ContentMode.HTML);
        Label stellenbeschreibung = new Label("<b>Stellenbeschreibung</b>",ContentMode.HTML);


        Label titel = new Label("<h2><b>"+stellenanzeige.getTitel()+"</font></b></h3>",ContentMode.HTML);
        Label firmenname_data = new Label(unternehmen_data.getCname());
        Label branche_data = new Label("Brnache");
        Label art_data = new Label(stellenanzeige.getSuchbegriff());
        Label ort_data = new Label(stellenanzeige.getStandort());
        Label bundesland_data = new Label(stellenanzeige.getBundesland());
        Label beginn_data = new Label(String.valueOf(stellenanzeige.getDatum()));
        Label hauptsitz_data = new Label(stellenanzeige.getHauptsitz());
        Label gruendungsjahr_data = new Label(String.valueOf(unternehmen_data.getGruendungsjahr()));
        Label reicheweite_data = new Label(String.valueOf(unternehmen_data.getReichweite()));
        Label anzMitarbeiter_data = new Label(String.valueOf(unternehmen_data.getMitarbeiteranzahl()));
        Label ansprechpartner_data = new Label(unternehmen_data.getVorname() + " " + unternehmen_data.getNachname());
        Label email_data = new Label(unternehmen_data.getEmail());
        Label kontaktnummer1_data = new Label("Kontaktnummer 1");
        Label kontaktnummer2_data = new Label("Kontaktnummer 2");
        Label suchbegriff_data = new Label(stellenanzeige.getSuchbegriff());
        Label beschreibung_data = new Label(stellenanzeige.getBeschreibung());




        gridLayout.addComponent(logo,0,1);
        gridLayout.addComponent(stellenbeschreibung,0,3);
        gridLayout.addComponent(firmenname,0,4);
        gridLayout.addComponent(branche,0,5);
        gridLayout.addComponent(art,0,8);
        gridLayout.addComponent(ort,0,9);
        gridLayout.addComponent(bundesland,0,10);
        gridLayout.addComponent(beginn,0,11);

        gridLayout.addComponent(titel,1,1,4,1);
        gridLayout.addComponent(information,3,2,4,2);
        gridLayout.addComponent(hauptsitz,3,3);
        gridLayout.addComponent(gruendungsjahr,3,4);
        gridLayout.addComponent(reicheweite,3,5);
        gridLayout.addComponent(anzMitarbeiter,3,6);
        gridLayout.addComponent(kontakt,3,7,4,7);
        gridLayout.addComponent(ansprechpartner,3,8);
        gridLayout.addComponent(email,3,9);
        gridLayout.addComponent(kontaktnummer1,3,10);
        gridLayout.addComponent(kontaktnummer2,3,11);



        gridLayout.addComponent(suchbegriff_data,1,3);
        gridLayout.addComponent(firmenname_data,1,4);
        gridLayout.addComponent(branche_data,1,5);
        gridLayout.addComponent(art_data,1,8);
        gridLayout.addComponent(ort_data,1,9);
        gridLayout.addComponent(bundesland_data,1,10);
        gridLayout.addComponent(beginn_data,1,11);

        gridLayout.addComponent(hauptsitz_data,4,3);
        gridLayout.addComponent(gruendungsjahr_data,4,4);
        gridLayout.addComponent(reicheweite_data,4,5);
        gridLayout.addComponent(anzMitarbeiter_data,4,6);
        gridLayout.addComponent(ansprechpartner_data,4,8);
        gridLayout.addComponent(email_data,4,9);
        gridLayout.addComponent(kontaktnummer1_data,4,10);
        gridLayout.addComponent(kontaktnummer2_data,4,11);



        beschreibung_data.setContentMode(ContentMode.HTML);

        gridLayout.addComponent(beschreibung_data,0,13,4,13);

        if(UI.getCurrent().getSession().getAttribute(Roles.Student) != null ) {
            Button bewerben = new Button("Bewerben");
            Button back = new Button("Zurück zu Ergebnissen");

            gridLayout.addComponent(bewerben, 4, 14, 4, 14);
            gridLayout.addComponent(back, 4, 0, 4, 0);

            bewerben.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {

                    StellenanzeigeWindow.this.close();
                    BewerbungWindow bewerbungWindow = new BewerbungWindow(stellenanzeige, unternehmen_data);
                    UI.getCurrent().addWindow(bewerbungWindow);
                }
            });

            back.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    StellenanzeigeWindow.this.close();
                    UI.getCurrent().getNavigator().navigateTo(Views.StudentHomeView);

                }
            });
        } else if(UI.getCurrent().getSession().getAttribute(Roles.Unternehmen) != null) {

            Button back = new Button("Zurück zu Anzeigen");
            Button bearbeiten = new Button("Bearbeiten");
            Button delete = new Button("Löschen");

            gridLayout.addComponent(back, 4, 0, 4, 0);
            gridLayout.addComponent(bearbeiten, 2, 12, 2, 12);
            gridLayout.addComponent(delete, 3, 12, 3, 12);


            final OnOffSwitch onoffSwitch = new OnOffSwitch(false);

// Vaadin8
            onoffSwitch.addValueChangeListener(new HasValue.ValueChangeListener<Boolean>() {
                @Override
                public void valueChange(HasValue.ValueChangeEvent<Boolean> event) {
                    boolean checked = event.getValue();
                    System.out.println("OnOffSwitch checked : " + checked);
                }
            });
           gridLayout.addComponent(onoffSwitch,4,12,4,12);


            back.addClickListener(event -> {
                this.close();
                UI.getCurrent().getNavigator().navigateTo(Views.UnternehmenHomeView);

            });
            bearbeiten.addClickListener(event -> {
                Button cancel = new Button("Abbrechen");
                Button save = new Button("Speichern");
                RichTextArea richTextArea = new RichTextArea();
                richTextArea.setValue(beschreibung_data.getValue());

                richTextArea.setSizeFull();

                TextField titel_bearbeiten = new TextField("");
                titel_bearbeiten.setValue(titel.getValue().substring(7,titel.getValue().length()-16));



                titel_bearbeiten.addValueChangeListener(event1 -> save.setEnabled(true));
                gridLayout.replaceComponent(titel,titel_bearbeiten);
                titel_bearbeiten.setSizeFull();
                gridLayout.replaceComponent(beschreibung_data, richTextArea);
                gridLayout.setComponentAlignment(richTextArea,Alignment.MIDDLE_CENTER);
                gridLayout.replaceComponent(bearbeiten, cancel);
                gridLayout.replaceComponent(delete, save);
                save.setEnabled(false);
                gridLayout.removeComponent(back);
                richTextArea.addValueChangeListener(event1 -> save.setEnabled(true));

                cancel.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        gridLayout.replaceComponent(richTextArea,beschreibung_data);
                        gridLayout.replaceComponent(cancel,bearbeiten);
                        gridLayout.replaceComponent(save,delete);
                        gridLayout.replaceComponent(titel_bearbeiten,titel);
                        gridLayout.addComponent(back, 4, 0, 4, 0);

                    }
                });

                save.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        ConfirmDialog.Factory df = new DefaultConfirmDialogFactory(){

                            @Override
                            public ConfirmDialog create(String caption, String message, String okCaption, String cancelCaption, String notOkCaption) {
                                return super.create("Bestätigung", message, okCaption, "Abbrechen", notOkCaption);
                            }
                        } ;

                        ConfirmDialog.setFactory(df);
                        ConfirmDialog.show(MyUI.getCurrent(), "Bist du dir sicher?",
                                new ConfirmDialog.Listener() {

                                    public void onClose(ConfirmDialog dialog) {
                                        if (dialog.isConfirmed()) {
                                            stellenanzeige.setTitel(titel_bearbeiten.getValue());
                                            stellenanzeige.setBeschreibung(richTextArea.getValue());
                                            try {
                                                ContainerAnzDAO.updateAnzeige(stellenanzeige);
                                            } catch (DatabaseException e) {
                                                e.printStackTrace();
                                            }
                                            gridLayout.replaceComponent(richTextArea, beschreibung_data);
                                            gridLayout.replaceComponent(cancel, bearbeiten);
                                            gridLayout.replaceComponent(save, delete);
                                            gridLayout.replaceComponent(titel_bearbeiten, titel);

                                            StellenanzeigeWindow.this.setUp(stellenanzeige, unternehmen_data);
                                        }
                                    }
                                });






                    }
                });


            });

            delete.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {

                    ConfirmDialog.show(MyUI.getCurrent(), "Bist du dir sicher?",
                            new ConfirmDialog.Listener() {

                                public void onClose(ConfirmDialog dialog) {
                                    if (dialog.isConfirmed()) {
                                        try {
                                            ContainerAnzDAO.deleteAnzeige(stellenanzeige);
                                        } catch (DatabaseException e) {
                                            e.printStackTrace();
                                        }
                                        StellenanzeigeWindow.this.close();
                                        UI.getCurrent().getNavigator().navigateTo(Views.UnternehmenHomeView);
                                    }
                                }
                            });
                }
            });

        }
        panel.setContent(gridLayout);
        this.setContent(panel);







    }



}
