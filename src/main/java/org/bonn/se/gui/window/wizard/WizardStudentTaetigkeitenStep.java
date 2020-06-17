package org.bonn.se.gui.window.wizard;

import com.github.appreciated.material.MaterialTheme;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import org.bonn.se.gui.component.RegistrationTextField;
import org.bonn.se.gui.component.StudentDateField;
import org.bonn.se.gui.window.RegisterStudentWindow;
import org.bonn.se.model.dao.ProfilDAO;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.Taetigkeit;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;
import org.vaadin.teemu.wizards.WizardStep;

import java.util.ArrayList;

public class WizardStudentTaetigkeitenStep implements WizardStep {

    final ArrayList<Taetigkeit> taetigkeitArrayList = new ArrayList<>();
    final Binder<Taetigkeit> binder = new Binder<>(Taetigkeit.class);
    Button plus;

    @Override
    public String getCaption() {
        return "Tätigkeiten";
    }

    @Override
    public Component getContent() {
        GridLayout gridLayout = new GridLayout(3, 10);
        gridLayout.setHeight("100%");
        gridLayout.setWidth("100%");



        RegistrationTextField taetigkeit1 = new RegistrationTextField("Tätigkeit (Optional)");
        StudentDateField t1_beginn = new StudentDateField("Beginn");
        StudentDateField t1_ende = new StudentDateField("Ende");

        plus = new Button(VaadinIcons.PLUS);
        plus.addStyleName(MaterialTheme.BUTTON_FLOATING_ACTION);

        Button minus = new Button(VaadinIcons.MINUS);
        minus.addStyleNames(MaterialTheme.BUTTON_DANGER,MaterialTheme.BUTTON_FLOATING_ACTION);


        binder.forField(taetigkeit1)
                .asRequired("Bitte ausfüllen")
                .bind(Taetigkeit::getTaetigkeitName,Taetigkeit::setTaetigkeitName);

        binder.forField(t1_beginn)
                .asRequired("Bitte ausfüllen")
                .bind(Taetigkeit::getBeginn,Taetigkeit::setBeginn);

        binder.forField(t1_ende)
                .asRequired("Bitte ausfüllen")
                .bind(Taetigkeit::getEnde,Taetigkeit::setEnde);

        gridLayout.addComponent(taetigkeit1,0,1);
        gridLayout.addComponent(t1_beginn,1,1);
        gridLayout.addComponent(t1_ende,2,1);
        gridLayout.setComponentAlignment(taetigkeit1, Alignment.MIDDLE_CENTER);
        gridLayout.setComponentAlignment(t1_beginn,Alignment.MIDDLE_CENTER);
        gridLayout.setComponentAlignment(t1_ende,Alignment.MIDDLE_CENTER);
        gridLayout.addComponent(plus,0,2);
        gridLayout.setComponentAlignment(plus,Alignment.MIDDLE_CENTER);
        taetigkeit1.selectAll();


        final int[] i_c = {0,1,2};
        final int[] i_r = {2};
        plus.addClickListener((Button.ClickListener) event -> {

            if (binder.isValid()) {
                Taetigkeit taetigkeit = new Taetigkeit();
                try {
                    binder.writeBean(taetigkeit);
                } catch (ValidationException e) {
                    e.printStackTrace();
                }
                taetigkeitArrayList.add(taetigkeit);

                gridLayout.removeComponent(plus);
                gridLayout.removeComponent(minus);

                gridLayout.addComponent(new RegistrationTextField("Tätigkeit (Optional)"), i_c[0], i_r[0]);
                gridLayout.addComponent(new StudentDateField("Beginn"), i_c[1], i_r[0]);
                gridLayout.addComponent(new StudentDateField("Ende"), i_c[2], i_r[0]);
                gridLayout.setComponentAlignment(gridLayout.getComponent(i_c[0], i_r[0]), Alignment.MIDDLE_CENTER);
                gridLayout.setComponentAlignment(gridLayout.getComponent(i_c[1], i_r[0]), Alignment.MIDDLE_CENTER);
                gridLayout.setComponentAlignment(gridLayout.getComponent(i_c[2], i_r[0]), Alignment.MIDDLE_CENTER);
                ((RegistrationTextField) gridLayout.getComponent(i_c[0], i_r[0])).selectAll();

                binder.forField((RegistrationTextField) gridLayout.getComponent(i_c[0], i_r[0]))
                        .asRequired("Bitte ausfüllen")
                        .bind(Taetigkeit::getTaetigkeitName, Taetigkeit::setTaetigkeitName);

                binder.forField((StudentDateField) gridLayout.getComponent(i_c[1], i_r[0]))
                        .asRequired("Bitte ausfüllen")
                        .bind(Taetigkeit::getBeginn, Taetigkeit::setBeginn);

                binder.forField((StudentDateField) gridLayout.getComponent(i_c[2] , i_r[0]))
                        .asRequired("Bitte ausfüllen")
                        .bind(Taetigkeit::getEnde, Taetigkeit::setEnde);

                if (i_r[0] <= 3) {
                    gridLayout.addComponent(plus, i_c[0], i_r[0] + 1);
                    gridLayout.setComponentAlignment(plus, Alignment.MIDDLE_CENTER);
                }
                i_r[0]++;

                gridLayout.addComponent(minus,i_c[1],i_r[0]);
                gridLayout.setComponentAlignment(minus, Alignment.MIDDLE_CENTER);

            } else {
                binder.validate().getFieldValidationErrors();
            }
        });


        minus.addClickListener((Button.ClickListener) event -> {

            gridLayout.removeComponent(plus);
            gridLayout.removeComponent(minus);
            i_r[0]--;
            binder.removeBinding( ((StudentDateField)gridLayout.getComponent(2,i_r[0])));
            binder.removeBinding( ((StudentDateField)gridLayout.getComponent(1,i_r[0])));
            binder.removeBinding( ((RegistrationTextField)gridLayout.getComponent(0,i_r[0])));

            for (int i = 0; i < gridLayout.getColumns() ; i++) {
                gridLayout.removeComponent(i,i_r[0]);

            }



            gridLayout.addComponent(plus,i_c[0],i_r[0]);
            gridLayout.addComponent(minus,i_c[1],i_r[0]);
            gridLayout.setComponentAlignment(plus,Alignment.MIDDLE_CENTER);
            gridLayout.setComponentAlignment(minus,Alignment.MIDDLE_CENTER);
            if(binder.isValid()) {
                taetigkeitArrayList.remove(taetigkeitArrayList.size() - 1);
            }




        });

        RegisterStudentWindow registerStudentWindow = new RegisterStudentWindow();
        binder.addStatusChangeListener(event -> registerStudentWindow.getWizard().getNextButton().setEnabled(binder.isValid()));




        return gridLayout;
    }

    @Override
    public boolean onAdvance() {

        Taetigkeit taetigkeit = new Taetigkeit();
        binder.writeBeanIfValid(taetigkeit);
        taetigkeitArrayList.add(taetigkeit);

        ((Student) UI.getCurrent().getSession().getAttribute(Roles.Student)).setTaetigkeiten(taetigkeitArrayList);

        try {
            ProfilDAO.getInstance().createStudentProfil2((Student) UI.getCurrent().getSession().getAttribute(Roles.Student));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }


        return true;
    }

    @Override
    public boolean onBack() {
        return true;
    }
}

