package org.bonn.se.gui.views;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se.control.ProfilControl;
import org.bonn.se.gui.component.StudentDatenView;
import org.bonn.se.gui.component.StudentKenntnisView;
import org.bonn.se.gui.component.StudentTaetigkeitenView;
import org.bonn.se.gui.component.TopPanelUser;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.model.objects.entitites.Adresse;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.services.util.Roles;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.dialogs.DefaultConfirmDialogFactory;

public class ProfilVerwaltenStudentView extends GridLayout implements View {

    private final Button bearbeitenDaten = new Button("Bearbeiten",this::addBearbeitenDatenClickListener);
    private final Button bearbeitenTaetigkeiten = new Button("Bearbeiten",this::addBearbeitenTaetigkeitenClickListener);
    //private final Button bearbeitenKenntnisse = new Button("Bearbeiten",this::addBearbeitenKenntnisClickListener);

    private final Button cancelDaten = new Button("Abbrechen",this::addCancelDatenClickListener);
    private final Button saveDaten = new Button("Speichern", this::addSaveDatenClickListener);
    private final Button cancelTaetigkeiten = new Button("Abbrechen",this::addCancelTaetigkeitenClickListener);
    private final Button saveTaetigkeiten = new Button("Speichern", this::addSaveTaetigkeitenClickListener);
   // private final Button cancelKenntnis = new Button("Abbrechen",this::addCancelKenntnisClickListener);
   //private final Button saveKenntnis = new Button("Speichern", this::addSaveKenntisClickListener);
    private HorizontalLayout buttonBar;
    private  boolean change = false;
    StudentDatenView grid;
    StudentTaetigkeitenView grid1;
    StudentKenntnisView grid2;


    private void addSaveDatenClickListener(Button.ClickEvent clickEvent) {
        ConfirmDialog.Factory df = new DefaultConfirmDialogFactory(){
            @Override
            public ConfirmDialog create(String caption, String message, String okCaption, String cancelCaption, String notOkCaption) {
                return super.create("Speichern", message, "Ja", "Nein", notOkCaption);
            }
        } ;
        ConfirmDialog.setFactory(df);
        ConfirmDialog.show(MyUI.getCurrent(), "Möchtest du wirklich speichern?",
                (ConfirmDialog.Listener) dialog -> {
                    if (dialog.isConfirmed()) {
                        Student student = (Student) UI.getCurrent().getSession().getAttribute(Roles.STUDENT);
                        student.setGDatum(grid.getgDatum().getValue());
                        student.setKontaktNr((grid.getMobilnr()).getValue());
                        Adresse adresse = new Adresse();
                        adresse.setStrasse(grid.getStrasse().getValue());
                        adresse.setPlz(grid.getOrt().getPlz());
                        adresse.setOrt(grid.getOrt().getOrt());
                        adresse.setBundesland(grid.getOrt().getBunesland());
                        student.setAdresse(adresse);
                        student.setStudiengang(grid.getStudiengang().getValue());
                        student.setAusbildung(grid.getAusbildung().getValue());
                        student.setAbschluss(grid.getAbschluss().getValue());
                        student.setPicture(grid.getUploadProfil().getValue());
                        student.setLebenslauf(grid.getLebenslauf().getValue());
                        if(student.getLebenslauf() != null ) {
                            student.setHasLebenslauf(true);
                        } else {
                            student.setHasLebenslauf(false);
                        }
                        /*

                        student.setItKenntnisList(grid2.getITKenntnisValue());
                        student.setSprachKenntnisList(grid2.getSprachenValue());

                         */
                        this.removeComponent(buttonBar);
                        this.addComponent(bearbeitenDaten,7,7);
                        ProfilControl.getInstance().updateStudentDaten(student);
                        grid.setReadOnly(true);
                        change =false;
                        UI.getCurrent().getSession().setAttribute(Roles.STUDENT,student);
                        /*
                        grid.setReadOnly(true);
                        grid2.setReadOnly(true);

                         */
                    }
                });
    }

    private void addSaveTaetigkeitenClickListener(Button.ClickEvent clickEvent) {
        ConfirmDialog.Factory df = new DefaultConfirmDialogFactory(){
            @Override
            public ConfirmDialog create(String caption, String message, String okCaption, String cancelCaption, String notOkCaption) {
                return super.create("Speichern", message, "Ja", "Nein", notOkCaption);
            }
        } ;
        ConfirmDialog.setFactory(df);
        ConfirmDialog.show(MyUI.getCurrent(), "Möchtest du wirklich speichern?",
                (ConfirmDialog.Listener) dialog -> {
                    if (dialog.isConfirmed()) {
                        Student student = (Student) UI.getCurrent().getSession().getAttribute(Roles.STUDENT);

                        student.setTaetigkeiten(grid1.getStudentValue());
                        this.removeComponent(buttonBar);
                        this.addComponent(bearbeitenTaetigkeiten,7,7);

                        ProfilControl.getInstance().updateStudentTaetigkeiten(student);

                        grid1.setReadOnly(true);
                        change =false;

                        UI.getCurrent().getSession().setAttribute(Roles.STUDENT,student);


                    }
                });
    }
/*
    private void addSaveKenntisClickListener(Button.ClickEvent clickEvent) {
        ConfirmDialog.Factory df = new DefaultConfirmDialogFactory(){
            @Override
            public ConfirmDialog create(String caption, String message, String okCaption, String cancelCaption, String notOkCaption) {
                return super.create("Speichern", message, "Ja", "Nein", notOkCaption);
            }
        } ;
        ConfirmDialog.setFactory(df);
        ConfirmDialog.show(MyUI.getCurrent(), "Möchtest du wirklich speichern?",
                (ConfirmDialog.Listener) dialog -> {
                    if (dialog.isConfirmed()) {

                        Student student = (Student) UI.getCurrent().getSession().getAttribute(Roles.STUDENT);

                        student.setItKenntnisList(grid2.getITKenntnisValue());
                        student.setSprachKenntnisList(grid2.getSprachenValue());

                        this.removeComponent(buttonBar);
                        this.addComponent(bearbeitenKenntnisse,7,7);
                        ProfilControl.getInstance().updateStudentKenntis(student);
                        grid2.setReadOnly(true);
                        change =false;
                    }
                });
    }
 */

    private void addCancelDatenClickListener(Button.ClickEvent clickEvent) {
        ConfirmDialog.Factory df = new DefaultConfirmDialogFactory(){
            @Override
            public ConfirmDialog create(String caption, String message, String okCaption, String cancelCaption, String notOkCaption) {
                return super.create("Abbrechen", message, "Ja", "Nein", notOkCaption);
            }
        } ;
        ConfirmDialog.setFactory(df);
        ConfirmDialog.show(MyUI.getCurrent(), "Möchtest du wirklich abbrechen?",
                (ConfirmDialog.Listener) dialog -> {
                    if (dialog.isConfirmed()) {
                        change = false;
                        this.removeComponent(buttonBar);
                        this.addComponent(bearbeitenDaten,7,7);
                        grid.setReadOnly(true);

                    }
                });
    }

    private void addCancelTaetigkeitenClickListener(Button.ClickEvent clickEvent) {
        ConfirmDialog.Factory df = new DefaultConfirmDialogFactory(){
            @Override
            public ConfirmDialog create(String caption, String message, String okCaption, String cancelCaption, String notOkCaption) {
                return super.create("Abbrechen", message, "Ja", "Nein", notOkCaption);
            }
        } ;
        ConfirmDialog.setFactory(df);
        ConfirmDialog.show(MyUI.getCurrent(), "Möchtest du wirklich abbrechen?",
                (ConfirmDialog.Listener) dialog -> {
                    if (dialog.isConfirmed()) {
                        change = false;
                        this.removeComponent(buttonBar);
                        this.addComponent(bearbeitenTaetigkeiten,7,7);
                        grid1.setReadOnly(true);

                    }
                });
    }
/*
    private void addCancelKenntnisClickListener(Button.ClickEvent clickEvent) {
        ConfirmDialog.Factory df = new DefaultConfirmDialogFactory(){
            @Override
            public ConfirmDialog create(String caption, String message, String okCaption, String cancelCaption, String notOkCaption) {
                return super.create("Abbrechen", message, "Ja", "Nein", notOkCaption);
            }
        } ;
        ConfirmDialog.setFactory(df);
        ConfirmDialog.show(MyUI.getCurrent(), "Möchtest du wirklich abbrechen?",
                (ConfirmDialog.Listener) dialog -> {
                    if (dialog.isConfirmed()) {
                        change = false;
                        this.removeComponent(buttonBar);
                        this.addComponent(bearbeitenKenntnisse,7,7);
                        grid2.setReadOnly(true);

                    }
                });
    }

 */

    private void addBearbeitenDatenClickListener(Button.ClickEvent clickEvent) {
        change = true;
        buttonBar = new HorizontalLayout();
        buttonBar.addComponents(cancelDaten, saveDaten);
        buttonBar.setMargin(true);
        this.removeComponent(bearbeitenDaten);
        this.addComponent(buttonBar,7,7);
        grid.setReadOnly(false);

    }

    private void addBearbeitenTaetigkeitenClickListener(Button.ClickEvent clickEvent) {
        change = true;
        buttonBar = new HorizontalLayout();
        buttonBar.addComponents(cancelTaetigkeiten, saveTaetigkeiten);
        buttonBar.setMargin(true);
        this.removeComponent(bearbeitenTaetigkeiten);
        this.addComponent(buttonBar,7,7);

        grid1.setReadOnly(false);

    }
/*
    private void addBearbeitenKenntnisClickListener(Button.ClickEvent clickEvent) {
        change = true;
        buttonBar = new HorizontalLayout();
        buttonBar.addComponents(cancelKenntnis, saveKenntnis);
        buttonBar.setMargin(true);
        this.removeComponent(bearbeitenKenntnisse);
        this.addComponent(buttonBar,7,7);

        grid2.setReadOnly(false);


    }

 */
    public void setUp() {
        TopPanelUser topPanelUser = new TopPanelUser();
        topPanelUser.setWidthFull();
        topPanelUser.setHeightUndefined();
        this.setMargin(false);
        this.setRows(10);
        this.setColumns(10);
        this.setSizeFull();
        this.addComponent(topPanelUser,0,0,9,0);


        grid = new StudentDatenView();
        grid.setHeight("470px");
        grid.setStudentValue();

        grid1 = new StudentTaetigkeitenView();
        grid1.setHeight("490px");
        grid1.setTaetigkeitValue();

        grid2 = new StudentKenntnisView();
        grid2.setHeight("490px");
        grid2.setKenntnisValue();
        grid2.setSpracheValue();

        this.addComponent(grid,0,2,9,5);

        this.setComponentAlignment(grid,Alignment.MIDDLE_CENTER);
        MenuBar bar = new MenuBar();
        MenuBar.Command typeCommand = (MenuBar.Command) selectedItem -> {
            for (MenuBar.MenuItem item : bar.getItems()) {
                item.setChecked(false);
            }

            if(change) {
               Notification.show("Achtung", "Bitte speichern sie ihre Änderungen oder brechen Sie ihre Änderungen ab!", Notification.Type.WARNING_MESSAGE)
               .setDelayMsec(300);
            } else {
                selectedItem.setChecked(true);
                if (selectedItem.getText().equals("Persönliche Daten")) {
                    this.removeComponent(7, 7);
                    this.addComponent(bearbeitenDaten, 7, 7);
                    this.removeComponent(0, 2);
                    this.addComponent(grid, 0, 2, 9, 5);
                } else if (selectedItem.getText().equals("Tätigkeiten")) {
                    this.removeComponent(7, 7);
                    this.addComponent(bearbeitenTaetigkeiten, 7, 7);
                    this.removeComponent(0, 2);
                    this.addComponent(grid1, 0, 2, 9, 5);
                } else {
                    this.removeComponent(7, 7);
                   // this.addComponent(bearbeitenKenntnisse, 7, 7);
                    this.removeComponent(0, 2);
                    this.addComponent(grid2, 0, 2, 9, 5);

                }
            }
        };





        bar.addItem("Persönliche Daten", VaadinIcons.USER,typeCommand).setCheckable(true);
        bar.addItem("Tätigkeiten", VaadinIcons.WORKPLACE,typeCommand).setCheckable(true);
        bar.addItem("Kenntnisse", VaadinIcons.DIPLOMA,typeCommand).setCheckable(true);
        typeCommand.menuSelected(bar.getItems().get(0));
        this.addComponent(bar,0,1,9,1);
        this.setComponentAlignment(bar,Alignment.MIDDLE_CENTER);

        this.addStyleName("grid");
        this.addComponent(new Label("&nbsp", ContentMode.HTML),7,6);
       // this.addComponent(bearbeitenDaten,7,7);
        this.addComponent(new Label("&nbsp", ContentMode.HTML),7,8);


        this.setComponentAlignment(bar, Alignment.TOP_CENTER);
        grid1.setReadOnly(true);
        grid.setReadOnly(true);
        grid2.setReadOnly(true);
    }




        @Override
        public void enter(ViewChangeListener.ViewChangeEvent event) {
                setUp();
        }
}
