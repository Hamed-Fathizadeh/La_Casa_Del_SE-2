package org.bonn.se.gui.window;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Notification.Type;
import org.bonn.se.gui.component.OrtPlzTextField;
import org.bonn.se.gui.component.PlaceHolderField;
import org.bonn.se.gui.component.PopUpTextField;
import org.bonn.se.gui.component.RegistrationTextField;
import org.bonn.se.model.dao.ProfilDAO;
import org.bonn.se.model.objects.entitites.Adresse;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.DatenUnternehmenProfil;
import org.bonn.se.services.util.ImageUploader;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;

import java.io.File;

public class ProfilUnternehmerWindow extends Window {

   private Image image = new Image();

    public ProfilUnternehmerWindow(Unternehmen user) {

        setWindow1(user);
    }

    public void setWindow1(Unternehmen user) {


        center();

        this.setHeight("80%");
        this.setWidth("80%");
        this.setDraggable(false);
        this.setResizable(false);
        GridLayout gridLayout = new GridLayout(2, 3);
        gridLayout.setHeight("100%");
        gridLayout.setWidth("100%");

        this.addCloseListener(new CloseListener() {
            @Override
            public void windowClose(CloseEvent e) {
                UI.getCurrent().getNavigator().navigateTo(Views.MainView);

            }
        });

        Label head = new Label("<h1><p><font color=\"blue\">Schließen Sie Ihre Registrierung vollständig ab !!</font></p></h1>", ContentMode.HTML);
        head.setHeight("30px");


        FormLayout form1 = new FormLayout();

        ImageUploader receiver = new ImageUploader();
        // Create the upload with a caption and set receiver later
        Upload upload = new Upload("", receiver);
        upload.addSucceededListener(receiver);
        upload.setButtonCaption("Profilbild hochladen");

        image.setSource(new FileResource(new File("src/main/webapp/image/Unknown.png")));

        upload.addStartedListener(new Upload.StartedListener() {
            @Override
            public void uploadStarted(Upload.StartedEvent event) {
                form1.removeComponent(image);
                Image image = ImageUploader.getImage();
                image.setHeight(150,Unit.PIXELS);
                image.setWidth(150,Unit.PIXELS);
                form1.addComponent(image,0);


            }
        });
        form1.setWidth("300px");
        form1.setMargin(true);


        PopUpTextField Kontaktnummer = new PopUpTextField("Kontaktnummer");
        PopUpTextField Kontaktnummer2= new PopUpTextField("2.Kontaktnummer");





        form1.addComponents( image,upload,Kontaktnummer,Kontaktnummer2);

        form1.setComponentAlignment(image, Alignment.TOP_LEFT);
        Button skip = new Button("Überspringen");
        skip.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        skip.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                setWindow2(user);
            }
        });

        FormLayout form2 = new FormLayout();
        form2.setWidth("300px");
        form2.setMargin(true);
        PlaceHolderField place1 = new PlaceHolderField();
        PopUpTextField strasse = new PopUpTextField("Strasse");
        OrtPlzTextField ort = new OrtPlzTextField();
        PlaceHolderField place2 = new PlaceHolderField();


        ComboBox<String> branche1 = new ComboBox<>("", DatenUnternehmenProfil.branche1);
        branche1.setPlaceholder("Branche");
        branche1.setHeight("56px");
        branche1.setWidth("300px");

        ComboBox<String> branche2 = new ComboBox<>("", DatenUnternehmenProfil.branche2);
        branche2.setPlaceholder("3. Branche (Optional)");
        branche2.setHeight("56px");
        branche2.setWidth("300px");

        ComboBox<String> branche3 = new ComboBox<>("", DatenUnternehmenProfil.branche3);
        branche3.setPlaceholder("2. Branche (Optional)");
        branche3.setHeight("56px");
        branche3.setWidth("300px");

        form2.addComponents(place1, strasse, ort, place2, branche1,branche2,branche3);






        Button weiter = new Button("Weiter");
        weiter.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                String[] sOrt = ort.getBundesland().getValue().toString().split(" - ");
                try {
                    ProfilDAO.createUnternehmenProfil1(user.getEmail(),ImageUploader.getFile(),Kontaktnummer.getValue(), Kontaktnummer2.getValue()
                            ,strasse.getValue(),ort.getPlz().getValue(),sOrt[0],sOrt[1],branche1.getValue(),branche2.getValue(),branche3.getValue(),
                            user.getCname(),user.getHauptsitz());
                } catch (DatabaseException e) {
                    e.printStackTrace();
                }
                Unternehmen unternehmen =(Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen);

                unternehmen.setLogo(image);
                if(image != null) {
                    unternehmen.setLogo(image);
                }else{
                    ThemeResource resource = new ThemeResource("img/RegisterStudent/Unknown.png");
                    Image UnknowenPic = new Image(null,resource);
                    unternehmen.setLogo(UnknowenPic);
                }
                unternehmen.setKontaktnummer(Kontaktnummer.getValue());
                unternehmen.setKontaktnummer(Kontaktnummer2.getValue());
                unternehmen.setBranche(branche1.getValue());
                unternehmen.setBranche(branche2.getValue());
                unternehmen.setBranche(branche3.getValue());


                Adresse adresse = new Adresse(strasse.getValue(),ort.getPlz().getValue(),sOrt[0]);
                unternehmen.setAdresse(adresse);
                UI.getCurrent().getSession().setAttribute(Roles.Unternehmen,unternehmen);
                setWindow2(user);
            }
        });

        gridLayout.addComponent(head, 0, 0, 1, 0);
        gridLayout.addComponent(form1, 0, 1, 0, 1);
        gridLayout.addComponent(form2, 1, 1, 1, 1);
        gridLayout.addComponent(skip, 0, 2, 0, 2);
        gridLayout.addComponent(weiter, 1, 2, 1, 2);


        setContent(gridLayout);
        gridLayout.setComponentAlignment(head, Alignment.TOP_CENTER);
        gridLayout.setComponentAlignment(form1, Alignment.TOP_LEFT);
        gridLayout.setComponentAlignment(form2, Alignment.TOP_LEFT);
        gridLayout.setComponentAlignment(skip, Alignment.TOP_CENTER);
        gridLayout.setComponentAlignment(weiter, Alignment.TOP_CENTER);

    }

    public void setWindow2(Unternehmen user) {
        center();
        this.setModal(true);

        this.setHeight("80%");
        this.setWidth("80%");
        this.setDraggable(false);
        this.setResizable(false);
        GridLayout gridLayout = new GridLayout(3, 3);
        gridLayout.setHeight("100%");
        gridLayout.setWidth("100%");

        this.addCloseListener(new CloseListener() {
            @Override
            public void windowClose(CloseEvent e) {
                UI.getCurrent().getNavigator().navigateTo(Views.MainView);

            }
        });
        Label head = new Label("<h1><p><font color=\"blue\"> Allgemeine Informationen</font></p></h1>", ContentMode.HTML);
        head.setHeight("70px");
        Button skip = new Button("Überspringen");
        skip.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        skip.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                setWindow3(user);
            }
        });
        FormLayout form2 = new FormLayout();
        form2.setWidth("300px");
        form2.setMargin(true);
        RegistrationTextField mitarbeiteranzahl = new RegistrationTextField("Mitarbeiteranzahl");
        RegistrationTextField gruendungsjahr = new RegistrationTextField("Gründungsjah");

        ComboBox<String> reichweit = new ComboBox<>("", DatenUnternehmenProfil.reichweite);
        reichweit.setPlaceholder("Reichweite");
        reichweit.setHeight("56px");
        reichweit.setWidth("300px");

        form2.addComponents(mitarbeiteranzahl,gruendungsjahr,reichweit);

        Button weiter2 = new Button("Weiter");
        weiter2.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
               /*try{
                   ProfilDAO.createUnternehmenProfil2(user.getMitarbeiteranzahl(),user.getGruendungsjahr(),user.getReichweite());
               }catch(DatabaseException e){
                   e.printStackTrace();
               }
               Unternehmen unternehmen = (Unternehmen)UI.getCurrent().getSession().getAttribute(Roles.Unternehmen);
               unternehmen.setMitarbeiteranzahl(Integer.valueOf(String.valueOf(mitarbeiteranzahl)));
               unternehmen.setGruendungsjahr(Integer.parseInt(String.valueOf(gruendungsjahr)));
               unternehmen.setReichweite(reichweit.getValue());
               UI.getCurrent().getSession().setAttribute(Roles.Unternehmen,unternehmen);
                setWindow3(user);*/
                try {
                    ProfilDAO.createUnternehmenProfil2((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen));
                } catch (DatabaseException e) {
                    e.printStackTrace();
                }

                setWindow3(user);
            }
        });


        gridLayout.addComponent(head, 0, 0, 0, 0);
        gridLayout.addComponent(form2, 0, 1, 0, 1);
        gridLayout.addComponent(skip, 0, 2, 0, 2);
        gridLayout.addComponent(weiter2, 1, 2, 1, 2);

        setContent(gridLayout);
        gridLayout.setComponentAlignment(head, Alignment.TOP_CENTER);
        gridLayout.setComponentAlignment(form2, Alignment.TOP_CENTER);
        gridLayout.setComponentAlignment(skip, Alignment.TOP_CENTER);
        gridLayout.setComponentAlignment(weiter2, Alignment.TOP_RIGHT);



    }
    private void setWindow3(Unternehmen user) {
        center();
        this.setModal(true);
        this.setHeight("80%");
        this.setWidth("80%");
        this.setDraggable(false);
        this.setResizable(false);
        GridLayout gridLayout = new GridLayout(3, 3);
        gridLayout.setHeight("100%");
        gridLayout.setWidth("100%");

        this.addCloseListener(new CloseListener() {
            @Override
            public void windowClose(CloseEvent e) {
                UI.getCurrent().getNavigator().navigateTo(Views.MainView);

            }
        });
        Label head = new Label("<h1><p><font color=\"blue\"> Schreiben Sie etwas zu Ihrem Unternehmen !!\n</font></p></h1>", ContentMode.HTML);
        head.setHeight("70px");
        FormLayout form1= new FormLayout();
        form1.setWidth("300px");
        form1.setMargin(true);
        form1.addComponent(head);

        FormLayout form2= new FormLayout();
        final RichTextArea sample = new RichTextArea();
        sample.setValue("The quick brown fox jumps over the lazy dog.");
        sample.setSizeFull();

        sample.addValueChangeListener(event -> Notification.show("Value changed:",
                String.valueOf(event.getValue()), Type.TRAY_NOTIFICATION));

        form2.addComponent(sample);
        Button fertig = new Button("Fertig");
        fertig.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    ProfilDAO.createUnternehmenProfil3((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen));
                } catch (DatabaseException e) {
                    e.printStackTrace();
                }
                ProfilUnternehmerWindow.this.close();
                UI.getCurrent().getNavigator().navigateTo(Views.UnternehmenHomeView);
            }

        });

        gridLayout.addComponent(head,0,0,0,0);
        gridLayout.addComponent(form2,0,1,0,1);
        gridLayout.addComponent(fertig,0,2,0,2);

        setContent(gridLayout);
        
        gridLayout.setComponentAlignment(head,Alignment.TOP_CENTER);
        gridLayout.setComponentAlignment(form2,Alignment.MIDDLE_CENTER);
        gridLayout.setComponentAlignment(fertig,Alignment.TOP_RIGHT);




    }
}
