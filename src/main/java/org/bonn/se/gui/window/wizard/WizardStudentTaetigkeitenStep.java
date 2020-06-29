package org.bonn.se.gui.window.wizard;

import com.vaadin.data.Binder;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import org.bonn.se.gui.component.StudentTaetigkeitenView;
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
    StudentTaetigkeitenView studentTaetigkeitenView;

    @Override
    public String getCaption() {
        return "Tätigkeiten";
    }

    @Override
    public Component getContent() {

        studentTaetigkeitenView = new StudentTaetigkeitenView();
        /*
        GridLayout gl_taetigkeiten = new GridLayout(3, 10);
        gl_taetigkeiten.setHeight("100%");
        gl_taetigkeiten.setWidth("100%");



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

        gl_taetigkeiten.addComponent(taetigkeit1,0,1);
        gl_taetigkeiten.addComponent(t1_beginn,1,1);
        gl_taetigkeiten.addComponent(t1_ende,2,1);
        gl_taetigkeiten.setComponentAlignment(taetigkeit1, Alignment.MIDDLE_CENTER);
        gl_taetigkeiten.setComponentAlignment(t1_beginn,Alignment.MIDDLE_CENTER);
        gl_taetigkeiten.setComponentAlignment(t1_ende,Alignment.MIDDLE_CENTER);
        gl_taetigkeiten.addComponent(plus,0,2);
        gl_taetigkeiten.setComponentAlignment(plus,Alignment.MIDDLE_CENTER);
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

                gl_taetigkeiten.removeComponent(plus);
                gl_taetigkeiten.removeComponent(minus);

                gl_taetigkeiten.addComponent(new RegistrationTextField("Tätigkeit (Optional)"), i_c[0], i_r[0]);
                gl_taetigkeiten.addComponent(new StudentDateField("Beginn"), i_c[1], i_r[0]);
                gl_taetigkeiten.addComponent(new StudentDateField("Ende"), i_c[2], i_r[0]);
                gl_taetigkeiten.setComponentAlignment(gl_taetigkeiten.getComponent(i_c[0], i_r[0]), Alignment.MIDDLE_CENTER);
                gl_taetigkeiten.setComponentAlignment(gl_taetigkeiten.getComponent(i_c[1], i_r[0]), Alignment.MIDDLE_CENTER);
                gl_taetigkeiten.setComponentAlignment(gl_taetigkeiten.getComponent(i_c[2], i_r[0]), Alignment.MIDDLE_CENTER);
                ((RegistrationTextField) gl_taetigkeiten.getComponent(i_c[0], i_r[0])).selectAll();

                binder.forField((RegistrationTextField) gl_taetigkeiten.getComponent(i_c[0], i_r[0]))
                        .asRequired("Bitte ausfüllen")
                        .bind(Taetigkeit::getTaetigkeitName, Taetigkeit::setTaetigkeitName);

                binder.forField((StudentDateField) gl_taetigkeiten.getComponent(i_c[1], i_r[0]))
                        .asRequired("Bitte ausfüllen")
                        .bind(Taetigkeit::getBeginn, Taetigkeit::setBeginn);

                binder.forField((StudentDateField) gl_taetigkeiten.getComponent(i_c[2] , i_r[0]))
                        .asRequired("Bitte ausfüllen")
                        .bind(Taetigkeit::getEnde, Taetigkeit::setEnde);

                if (i_r[0] <= 3) {
                    gl_taetigkeiten.addComponent(plus, i_c[0], i_r[0] + 1);
                    gl_taetigkeiten.setComponentAlignment(plus, Alignment.MIDDLE_CENTER);
                }
                i_r[0]++;
                gl_taetigkeiten.addComponent(minus,i_c[1],i_r[0]);
                gl_taetigkeiten.setComponentAlignment(minus, Alignment.MIDDLE_CENTER);
            } else {
                binder.validate().getFieldValidationErrors();
            }
        });


        minus.addClickListener((Button.ClickListener) event -> {


                gl_taetigkeiten.removeComponent(plus);
                gl_taetigkeiten.removeComponent(minus);
                i_r[0]--;
                binder.removeBinding(((StudentDateField) gl_taetigkeiten.getComponent(2, i_r[0])));
                binder.removeBinding(((StudentDateField) gl_taetigkeiten.getComponent(1, i_r[0])));
                binder.removeBinding(((RegistrationTextField) gl_taetigkeiten.getComponent(0, i_r[0])));

                for (int i = 0; i < gl_taetigkeiten.getColumns(); i++) {
                    gl_taetigkeiten.removeComponent(i, i_r[0]);

                }
                gl_taetigkeiten.addComponent(plus, i_c[0], i_r[0]);
                gl_taetigkeiten.addComponent(minus, i_c[1], i_r[0]);
                gl_taetigkeiten.setComponentAlignment(plus, Alignment.MIDDLE_CENTER);
                gl_taetigkeiten.setComponentAlignment(minus, Alignment.MIDDLE_CENTER);
                if (binder.isValid()) {
                    taetigkeitArrayList.remove(taetigkeitArrayList.size() - 1);
                }
            if ((gl_taetigkeiten.getComponentCount() == 5)) {
                gl_taetigkeiten.removeComponent(minus);
            }

        });

        RegisterStudentWindow registerStudentWindow = new RegisterStudentWindow();
        binder.addStatusChangeListener(event -> registerStudentWindow.getWizard().getNextButton().setEnabled(binder.isValid()));

        return gl_taetigkeiten;

         */

        return studentTaetigkeitenView;
    }

    @Override
    public boolean onAdvance() {

        Taetigkeit taetigkeit = new Taetigkeit();
        studentTaetigkeitenView.getBinder().writeBeanIfValid(taetigkeit);
        if(taetigkeit.getTaetigkeitName() != null) studentTaetigkeitenView.getTaetigkeitArrayList().add(taetigkeit);


        ((Student) UI.getCurrent().getSession().getAttribute(Roles.STUDENT)).setTaetigkeiten(studentTaetigkeitenView.getTaetigkeitArrayList());

        try {
            ProfilDAO.getInstance().createStudentProfil2((Student) UI.getCurrent().getSession().getAttribute(Roles.STUDENT));
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

