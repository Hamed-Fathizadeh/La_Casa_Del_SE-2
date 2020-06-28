package org.bonn.se.gui.component;

import com.github.appreciated.material.MaterialTheme;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.UI;
import org.bonn.se.gui.window.RegisterStudentWindow;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.Taetigkeit;
import org.bonn.se.services.util.Roles;

import java.util.ArrayList;

public class StudentTaetigkeitenView extends GridLayout {

    private ArrayList<Taetigkeit> taetigkeitArrayList = new ArrayList<>();

    public ArrayList<Taetigkeit> getTaetigkeitArrayList() {
        return taetigkeitArrayList;
    }


    public Binder<Taetigkeit> getBinder() {
        return binder;
    }

    private Binder<Taetigkeit> binder;
    private Button plus;
    private Button minus;

    public StudentTaetigkeitenView() {

        this.setColumns(3);
        this.setRows(10);
        this.setHeight("100%");
        this.setWidth("100%");


        RegistrationTextField taetigkeit1 = new RegistrationTextField("Tätigkeit (Optional)");
        StudentDateField t1_beginn = new StudentDateField("Beginn");

        StudentDateField t1_ende = new StudentDateField("Ende");


        plus = new Button(VaadinIcons.PLUS);
        plus.addStyleName(MaterialTheme.BUTTON_FLOATING_ACTION);

        minus = new Button(VaadinIcons.MINUS);
        minus.addStyleNames(MaterialTheme.BUTTON_DANGER, MaterialTheme.BUTTON_FLOATING_ACTION);
        binder = new Binder<>(Taetigkeit.class);

        binder.forField(taetigkeit1)
                .asRequired("Bitte ausfüllen")
                .bind(Taetigkeit::getTaetigkeitName, Taetigkeit::setTaetigkeitName);

        binder.forField(t1_beginn)
                .asRequired("Bitte ausfüllen")
                .bind(Taetigkeit::getBeginn, Taetigkeit::setBeginn);

        binder.forField(t1_ende)
                .asRequired("Bitte ausfüllen")
                .bind(Taetigkeit::getEnde, Taetigkeit::setEnde);

        this.addComponent(taetigkeit1, 0, 1);
        this.addComponent(t1_beginn, 1, 1);
        this.addComponent(t1_ende, 2, 1);
        this.setComponentAlignment(taetigkeit1, Alignment.MIDDLE_CENTER);
        this.setComponentAlignment(t1_beginn, Alignment.MIDDLE_CENTER);
        this.setComponentAlignment(t1_ende, Alignment.MIDDLE_CENTER);
        this.addComponent(plus, 0, 2);
        this.setComponentAlignment(plus, Alignment.MIDDLE_CENTER);
        taetigkeit1.selectAll();


        final int[] i_c = {0, 1, 2};
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

                this.removeComponent(plus);
                this.removeComponent(minus);

                this.addComponent(new RegistrationTextField("Tätigkeit (Optional)"), i_c[0], i_r[0]);
                this.addComponent(new StudentDateField("Beginn"), i_c[1], i_r[0]);
                this.addComponent(new StudentDateField("Ende"), i_c[2], i_r[0]);
                this.setComponentAlignment(this.getComponent(i_c[0], i_r[0]), Alignment.MIDDLE_CENTER);
                this.setComponentAlignment(this.getComponent(i_c[1], i_r[0]), Alignment.MIDDLE_CENTER);
                this.setComponentAlignment(this.getComponent(i_c[2], i_r[0]), Alignment.MIDDLE_CENTER);
                ((RegistrationTextField) this.getComponent(i_c[0], i_r[0])).selectAll();

                binder.forField((RegistrationTextField) this.getComponent(i_c[0], i_r[0]))
                        .asRequired("Bitte ausfüllen")
                        .bind(Taetigkeit::getTaetigkeitName, Taetigkeit::setTaetigkeitName);

                binder.forField((StudentDateField) this.getComponent(i_c[1], i_r[0]))
                        .asRequired("Bitte ausfüllen")
                        .bind(Taetigkeit::getBeginn, Taetigkeit::setBeginn);

                binder.forField((StudentDateField) this.getComponent(i_c[2], i_r[0]))
                        .asRequired("Bitte ausfüllen")
                        .bind(Taetigkeit::getEnde, Taetigkeit::setEnde);

                if (i_r[0] <= 3) {
                    this.addComponent(plus, i_c[0], i_r[0] + 1);
                    this.setComponentAlignment(plus, Alignment.MIDDLE_CENTER);
                }
                i_r[0]++;
                this.addComponent(minus, i_c[1], i_r[0]);
                this.setComponentAlignment(minus, Alignment.MIDDLE_CENTER);
            } else {
                binder.validate().getFieldValidationErrors();
            }
        });


        minus.addClickListener((Button.ClickListener) event -> {


            this.removeComponent(plus);
            this.removeComponent(minus);
            i_r[0]--;
            binder.removeBinding(((StudentDateField) this.getComponent(2, i_r[0])));
            binder.removeBinding(((StudentDateField) this.getComponent(1, i_r[0])));
            binder.removeBinding(((RegistrationTextField) this.getComponent(0, i_r[0])));

            for (int i = 0; i < this.getColumns(); i++) {
                this.removeComponent(i, i_r[0]);

            }
            this.addComponent(plus, i_c[0], i_r[0]);
            this.addComponent(minus, i_c[1], i_r[0]);
            this.setComponentAlignment(plus, Alignment.MIDDLE_CENTER);
            this.setComponentAlignment(minus, Alignment.MIDDLE_CENTER);
            if (binder.isValid()) {
                taetigkeitArrayList.remove(taetigkeitArrayList.size() - 1);
            }
            if ((this.getComponentCount() == 5)) {
                this.removeComponent(minus);
            }

        });

        RegisterStudentWindow registerStudentWindow = new RegisterStudentWindow();
        binder.addStatusChangeListener(event -> registerStudentWindow.getWizard().getNextButton().setEnabled(binder.isValid()));
    }


    public void setTaetigkeitValue() {
        Student student = (Student) UI.getCurrent().getSession().getAttribute(Roles.Student);
        if(!student.getTaetigkeiten().isEmpty()) {
            for (int i = 0; i < student.getTaetigkeiten().size(); i++) {
                ((RegistrationTextField) this.getComponent(0, i + 1)).setValue(student.getTaetigkeiten().get(i).getTaetigkeitName());
                ((StudentDateField) this.getComponent(1, i + 1)).setValue(student.getTaetigkeiten().get(i).getBeginn());
                ((StudentDateField) this.getComponent(2, i + 1)).setValue(student.getTaetigkeiten().get(i).getEnde());
                if (!(student.getTaetigkeiten().size() - 1 == i)) {
                    plus.click();
                }
            }
        } else {
            this.setEnabled(false);
        }
    }

    public void setReadOnly(boolean status) {

        Student student = (Student) UI.getCurrent().getSession().getAttribute(Roles.Student);
        for (int i = 0; i < student.getTaetigkeiten().size(); i++) {
            ((RegistrationTextField) this.getComponent(0, i + 1)).setReadOnly(status);
            ((StudentDateField) this.getComponent(1, i + 1)).setReadOnly(status);
            ((StudentDateField) this.getComponent(2, i + 1)).setReadOnly(status);
        }
        plus.setVisible(!status);
        minus.setVisible(!status);


    }

    public ArrayList<Taetigkeit> getStudentValue() {
        ArrayList<Taetigkeit> taetigkeitArrayList = new ArrayList<>();
            for (int i = 0; i <this.getRows() ; i++) {
                Taetigkeit taetigkeit = new Taetigkeit();
                if(this.getComponent(2,i+1) != null) {
                    taetigkeit.setTaetigkeitName(((RegistrationTextField) this.getComponent(0, i + 1)).getValue());
                    taetigkeit.setBeginn(((StudentDateField) this.getComponent(1, i + 1)).getValue());
                    taetigkeit.setEnde(((StudentDateField) this.getComponent(2, i + 1)).getValue());
                    taetigkeitArrayList.add(taetigkeit);
                }
        }
            return taetigkeitArrayList;
    }
}




