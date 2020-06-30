package org.bonn.se.gui.window;

import com.vaadin.addon.onoffswitch.OnOffSwitch;
import com.vaadin.data.HasValue;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se.control.AnzStatusControl;
import org.bonn.se.control.BewertungControl;
import org.bonn.se.control.FeatureToggleControl;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.model.dao.UserDAO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.ContainerAnzeigen;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.ImageConverter;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.dialogs.DefaultConfirmDialogFactory;
import org.vaadin.teemu.ratingstars.RatingStars;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class StellenanzeigeWindow extends Window {


    private OnOffSwitch onOffSwitch = new OnOffSwitch();
    Button bewerbungen;
    Button bewerben;
    public StellenanzeigeWindow(StellenanzeigeDTO stellenanzeige) {
        try {
            Unternehmen unternehmenData =  UserDAO.getUnternehmenByStellAnz(stellenanzeige);
            setUp(stellenanzeige, unternehmenData);
        } catch (DatabaseException | SQLException e) {
            Logger.getLogger(StellenanzeigeWindow.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void setUp(StellenanzeigeDTO stellenanzeige, Unternehmen unternehmenData)  {

        center();
        this.setWidth("80%");
        this.setHeight("90%");
        this.setModal(true);
        this.setResizable(false);
        this.setClosable(false);

        Panel panel = new Panel();
        panel.setWidthFull();

        GridLayout gridLayout = new GridLayout(5, 17);
        gridLayout.setWidthFull();
        gridLayout.setHeightUndefined();
        gridLayout.setMargin(true);

        Image logo;
        if (unternehmenData.getLogo() == null) {
            logo = ImageConverter.getUnknownProfilImage();
        } else {
            logo = ImageConverter.convertImagetoProfil(unternehmenData.getLogo());
        }

        Label firmenname = new Label("<b>Unternehmensname</b>",ContentMode.HTML);
        Label branche = new Label("<b>Branche</b>",ContentMode.HTML);
        Label art = new Label("<b>Art der Einstellung</b>",ContentMode.HTML);
        Label ort= new Label("<b>Ort</b>",ContentMode.HTML);
        Label bundesland = new Label("<b>Bundesland</b>",ContentMode.HTML);
        Label information = new Label("<h3><b><font color=\"blue\">Informationen über das Unternehmen</font></b></h3>",ContentMode.HTML);
        Label kontakt = new Label("<h3><b><font color=\"blue\">Kontakt</font></b></h3>",ContentMode.HTML);
        Label beginn= new Label("<b>Beginn</b>",ContentMode.HTML);
        Label hauptsitz= new Label("<b>Hauptsitz</b>",ContentMode.HTML);
        Label ansprechpartner = new Label("<b>Ansprechpartner/in</b>",ContentMode.HTML);
        Label email = new Label("<b>E-Mail</b>",ContentMode.HTML);
        Label kontaktnummer1 = new Label("<b>Kontaktnummer</b>",ContentMode.HTML);
        Label stellenbeschreibung = new Label("<b>Stellenbezeichnung</b>",ContentMode.HTML);
        Label bewertung = new Label("<b>Bewertung</b>",ContentMode.HTML);


        Label titel = new Label("<h2><b>"+stellenanzeige.getTitel()+"</font></b></h3>" ,ContentMode.HTML);
        Label unternehmensbeschreibungLabel = new Label("<h3><b><font color=\"blue\">Informationen über das Unternehmen</font></b></h3>",ContentMode.HTML);
        RichTextArea unternehmensbeschreibung = new RichTextArea();
        unternehmensbeschreibung.setValue(unternehmenData.getDescription() == null ? "Keine Angabe" :unternehmenData.getDescription());
        unternehmensbeschreibung.setReadOnly(true);
        unternehmensbeschreibung.setWidth("600px");

        Label firmennameData = new Label(unternehmenData.getCname());
        Label brancheData = new Label(unternehmenData.getBranche());
        Label artData = new Label(stellenanzeige.getArt());
        Label ortData = new Label(stellenanzeige.getStandort());
        Label bundeslandData = new Label(stellenanzeige.getBundesland());
        Label beginnData = new Label(String.valueOf(stellenanzeige.getDatum()));
        Label hauptsitzData = new Label(stellenanzeige.getHauptsitz());
        Label ansprechpartnerData = new Label(unternehmenData.getVorname() + " " + unternehmenData.getNachname());
        Label emailData = new Label(unternehmenData.getEmail());
        Label kontaktnummer1Data = new Label(unternehmenData.getKontaktnummer());
        Label suchbegriffData = new Label(stellenanzeige.getSuchbegriff());
        RichTextArea beschreibungData = new RichTextArea();
        beschreibungData.setSizeFull();
        beschreibungData.setValue(stellenanzeige.getBeschreibung());
        beschreibungData.setReadOnly(true);
        RatingStars rating = new RatingStars();
        rating.setMaxValue(5);
        rating.setReadOnly(true);
        rating.setValue(BewertungControl.bewertungByID(unternehmenData.getHauptsitz(),unternehmenData.getCname()));


        gridLayout.addComponent(logo,0,1);
        gridLayout.addComponent(unternehmensbeschreibungLabel,0,2);
        gridLayout.addComponent(unternehmensbeschreibung,0,3,1,6);
        gridLayout.addComponent(stellenbeschreibung,0,8);
        gridLayout.addComponent(art,0,9);
        gridLayout.addComponent(ort,0,10);
        gridLayout.addComponent(bundesland,0,11);
        gridLayout.addComponent(beginn,0,12);

        gridLayout.addComponent(titel,1,1,4,1);
        gridLayout.addComponent(information,3,2,4,2);
        gridLayout.addComponent(firmenname,3,3);
        gridLayout.addComponent(branche,3,4);
        gridLayout.addComponent(hauptsitz,3,5);
        gridLayout.addComponent(bewertung,3,6);
        gridLayout.addComponent(kontakt,3,7,4,7);
        gridLayout.addComponent(ansprechpartner,3,8);
        gridLayout.addComponent(email,3,9);
        gridLayout.addComponent(kontaktnummer1,3,10);




        gridLayout.addComponent(suchbegriffData,1,8);
        gridLayout.addComponent(artData,1,9);
        gridLayout.addComponent(ortData,1,10);
        gridLayout.addComponent(bundeslandData,1,11);
        gridLayout.addComponent(beginnData,1,12);
        gridLayout.addComponent(firmennameData,4,3);
        gridLayout.addComponent(brancheData,4,4);
        gridLayout.addComponent(hauptsitzData,4,5);
        gridLayout.addComponent(rating,4,6);
        gridLayout.addComponent(ansprechpartnerData,4,8);
        gridLayout.addComponent(emailData,4,9);
        gridLayout.addComponent(kontaktnummer1Data,4,10);



        gridLayout.addComponent(new Label("&nbsp", ContentMode.HTML),0,13,4,13);
        gridLayout.addComponent(beschreibungData,0,14,4,14);
        gridLayout.setComponentAlignment(beschreibungData,Alignment.MIDDLE_CENTER);

        if(UI.getCurrent().getSession().getAttribute(Roles.STUDENT) != null ) {

            Button back = new Button("Zurück zu Ergebnissen");
            gridLayout.addComponent(back, 4, 0, 4, 0);
            back.addClickListener((Button.ClickListener) event -> StellenanzeigeWindow.this.close());
        } else if(UI.getCurrent().getSession().getAttribute(Roles.UNTERNEHMEN) != null) {

            Button back = new Button("Zurück zu Anzeigen");
            Button bearbeiten = new Button("Bearbeiten");
            Button delete = new Button("Löschen");

            gridLayout.addComponent(back, 4, 0, 4, 0);
            gridLayout.addComponent(bearbeiten, 2, 0, 2, 0);
            gridLayout.addComponent(delete, 3, 0, 3, 0);

            onOffSwitch.setCaption("Status");
            if(stellenanzeige.getStatus() == 1) {
                onOffSwitch = new OnOffSwitch(true);

            } else if(stellenanzeige.getStatus() == 2) {
                 onOffSwitch = new OnOffSwitch(false);

            }
            HorizontalLayout horizontalLayout = new HorizontalLayout();

            Label status = new Label("Status");
            gridLayout.addComponent(status,0,0,0,0);
            horizontalLayout.addComponents(status,onOffSwitch);
            horizontalLayout.setMargin(false);
            gridLayout.addComponent(horizontalLayout,0,0,0,0);
            onOffSwitch.addValueChangeListener((HasValue.ValueChangeListener<Boolean>) event -> {
                boolean checked = event.getValue();
                if(checked) {
                    stellenanzeige.setStatus(1);
                    Notification.show("Anzeige online!");
                } else {
                    stellenanzeige.setStatus(2);
                    Notification.show("Anzeige offline!");
                }

                    AnzStatusControl.changeStatus(stellenanzeige);


                System.out.println("OnOffSwitch checked : " + checked);

            });

            back.addClickListener(event -> {
                this.close();
                UI.getCurrent().getNavigator().navigateTo(Views.UNTERNEHMENHOMEVIEW);

            });


            bearbeiten.addClickListener(event -> {
                Button cancel = new Button("Abbrechen");
                Button save = new Button("Speichern");
                RichTextArea richTextArea = new RichTextArea();
                richTextArea.setValue(beschreibungData.getValue());
                richTextArea.setSizeFull();

                TextField titelBearbeiten = new TextField("");
                titelBearbeiten.setValue(titel.getValue().substring(7,titel.getValue().length()-16));
                titelBearbeiten.setMaxLength(44);



                titelBearbeiten.addValueChangeListener(event1 -> save.setEnabled(true));
                gridLayout.replaceComponent(titel, titelBearbeiten);
                titelBearbeiten.setSizeFull();
                gridLayout.replaceComponent(beschreibungData, richTextArea);
                gridLayout.setComponentAlignment(richTextArea,Alignment.MIDDLE_CENTER);
                gridLayout.replaceComponent(bearbeiten, cancel);
                gridLayout.replaceComponent(delete, save);
                save.setEnabled(false);
                gridLayout.removeComponent(back);
                richTextArea.addValueChangeListener(event1 -> save.setEnabled(true));

                cancel.addClickListener((Button.ClickListener) event12 -> {
                    gridLayout.replaceComponent(richTextArea, beschreibungData);
                    gridLayout.replaceComponent(cancel,bearbeiten);
                    gridLayout.replaceComponent(save,delete);
                    gridLayout.replaceComponent(titelBearbeiten,titel);
                    gridLayout.addComponent(back, 4, 0, 4, 0);
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
                                (ConfirmDialog.Listener) dialog -> {
                                    if (dialog.isConfirmed()) {
                                        stellenanzeige.setTitel(titelBearbeiten.getValue());
                                        stellenanzeige.setBeschreibung(richTextArea.getValue());
                                        ContainerAnzeigen.getInstance().updateAnzeige(stellenanzeige);
                                        gridLayout.replaceComponent(richTextArea, beschreibungData);
                                        gridLayout.replaceComponent(cancel, bearbeiten);
                                        gridLayout.replaceComponent(save, delete);
                                        gridLayout.replaceComponent(titelBearbeiten, titel);

                                        StellenanzeigeWindow.this.setUp(stellenanzeige, unternehmenData);
                                    }
                                });
                    }
                });


            });

            delete.addClickListener((Button.ClickListener) event -> ConfirmDialog.show(MyUI.getCurrent(), "Bist du dir sicher?",
                    (ConfirmDialog.Listener) dialog -> {
                        if (dialog.isConfirmed()) {
                            ContainerAnzeigen.getInstance().deleteAnzeige(stellenanzeige);
                            StellenanzeigeWindow.this.close();
                            UI.getCurrent().getNavigator().navigateTo(Views.UNTERNEHMENHOMEVIEW);
                        }
                    }));

        }
        panel.setContent(gridLayout);

        if(FeatureToggleControl.getInstance().featureIsEnabled("BEWERBUNGEN")) {

            UI.getCurrent().access(() -> {
                if(UI.getCurrent().getSession().getAttribute(Roles.STUDENT) != null ) {

                    bewerben = new Button("Bewerben");

                    gridLayout.addComponent(new Label("&nbsp", ContentMode.HTML), 4, 15, 4, 15);
                    gridLayout.addComponent(bewerben, 4, 16, 4, 16);
                    gridLayout.setComponentAlignment(bewerben,Alignment.BOTTOM_RIGHT);

                    bewerben.addClickListener((Button.ClickListener) event -> {
                        if (((Student) MyUI.getCurrent().getSession().getAttribute(Roles.STUDENT)).hasLebenslauf()) {
                            StellenanzeigeWindow.this.close();
                            BewerbungWindow bewerbungWindow = new BewerbungWindow(stellenanzeige, "Student", null);
                            UI.getCurrent().addWindow(bewerbungWindow);

                        } else {
                            UI.getCurrent().addWindow(new ConfirmationWindow("Um dich zu bewerben musst du ein Lebenslauf in deine Profil hinterlegen!"));
                        }
                    });
                } else if(UI.getCurrent().getSession().getAttribute(Roles.UNTERNEHMEN) != null) {
                    bewerbungen = new Button("Zum Bewerbungen");
                    gridLayout.addComponent(bewerbungen, 1, 0, 1, 0);

                    bewerbungen.addClickListener(event -> {
                        StellenanzeigeBewerbungenWindow stellenanzeigeBewerbungenWindow = new StellenanzeigeBewerbungenWindow(stellenanzeige);
                        UI.getCurrent().addWindow(stellenanzeigeBewerbungenWindow);
                        this.close();

                    });
                }
            });
        }
        this.setContent(panel);
    }



}
