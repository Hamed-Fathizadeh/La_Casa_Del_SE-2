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
import org.vaadin.easyuploads.UploadField;

public class ProfilVerwaltenStudentView extends GridLayout implements View {
    private final Student student = (Student) UI.getCurrent().getSession().getAttribute(Roles.STUDENT);

    private final Button bearbeiten = new Button("Bearbeiten",this::addBearbeitenClickListener);
    private final Button cancel = new Button("Abbrechen",this::addCancelClickListener);
    private final Button save = new Button("Speichern", this::addSaveClickListener);
    private HorizontalLayout buttonBar;

    StudentDatenView grid;
    StudentTaetigkeitenView grid1;
    StudentKenntnisView grid2;
    UploadField uploadProfil;

    private void addSaveClickListener(Button.ClickEvent clickEvent) {
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
                        student.setTaetigkeiten(grid1.getStudentValue());
                        student.setItKenntnisList(grid2.getITKenntnisValue());
                        student.setSprachKenntnisList(grid2.getSprachenValue());
                        this.removeComponent(buttonBar);
                        this.addComponent(bearbeiten,7,7);
                        ProfilControl.getInstance().updateStudent(student);
                        grid1.setReadOnly(true);
                        grid.setReadOnly(true);
                        grid2.setReadOnly(true);
                    }
                });
    }

    private void addCancelClickListener(Button.ClickEvent clickEvent) {
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
                        this.removeComponent(buttonBar);
                        this.addComponent(bearbeiten,7,9);
                        grid.setReadOnly(true);
                        grid1.setReadOnly(true);
                        grid2.setReadOnly(true);
                    }
                });
    }

    private void addBearbeitenClickListener(Button.ClickEvent clickEvent) {

        buttonBar = new HorizontalLayout();
        buttonBar.addComponents(cancel,save);
        buttonBar.setMargin(true);
        this.removeComponent(bearbeiten);
        this.addComponent(buttonBar,7,7);
        grid.setReadOnly(false);
        grid1.setReadOnly(false);
        grid2.setReadOnly(false);
    }


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

        this.addComponent(grid,0,2,9,5);

        this.setComponentAlignment(grid,Alignment.MIDDLE_CENTER);
        MenuBar bar = new MenuBar();
        MenuBar.Command typeCommand = (MenuBar.Command) selectedItem -> {
            for (MenuBar.MenuItem item : bar.getItems()) {
                item.setChecked(false);
            }
                selectedItem.setChecked(true);
            if (selectedItem.getText().equals("Persönliche Daten")) {
                this.removeComponent(0,2);
                this.addComponent(grid,0,2,9, 5);
            } else if(selectedItem.getText().equals("Tätigkeiten")) {
                this.removeComponent(0,2);
                this.addComponent(grid1,0,2,9,5);
            } else {
                this.removeComponent(0,2);
                this.addComponent(grid2,0,2,9,5);
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
        this.addComponent(bearbeiten,7,7);
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
