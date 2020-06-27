package org.bonn.se.gui.component;

import com.github.appreciated.material.MaterialTheme;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se.control.ComponentControl;
import org.bonn.se.gui.window.RegisterStudentWindow;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.services.util.Roles;

import java.util.ArrayList;
import java.util.Collection;

public class StudentKenntnisView extends GridLayout {
    private ArrayList<Student.SprachKenntnis> sprachKenntnisArrayList;
    private Binder<Student.SprachKenntnis> binder1;
    private Binder<Student.ITKenntnis> binder;
    private ArrayList<Student.ITKenntnis> itKenntnisArrayList;
    private Button plus;
    private Button minus;
    private PopUpTextField itKenntnis1;
    private ComboBoxNiveau niveau1;
    private PopUpTextField sprachKenntnis1;
    private ComboBoxNiveau niveau21;
    private Button plus1;
    private Button minus1;

    public ArrayList<Student.SprachKenntnis> getSprachKenntnisArrayList() {
        return sprachKenntnisArrayList;
    }

    public StudentKenntnisView setSprachKenntnisArrayList(ArrayList<Student.SprachKenntnis> sprachKenntnisArrayList) {
        this.sprachKenntnisArrayList = sprachKenntnisArrayList;
        return this;
    }

    public Binder<Student.SprachKenntnis> getBinder1() {
        return binder1;
    }

    public StudentKenntnisView setBinder1(Binder<Student.SprachKenntnis> binder1) {
        this.binder1 = binder1;
        return this;
    }

    public Binder<Student.ITKenntnis> getBinder() {
        return binder;
    }

    public StudentKenntnisView setBinder(Binder<Student.ITKenntnis> binder) {
        this.binder = binder;
        return this;
    }

    public ArrayList<Student.ITKenntnis> getItKenntnisArrayList() {
        return itKenntnisArrayList;
    }

    public StudentKenntnisView setItKenntnisArrayList(ArrayList<Student.ITKenntnis> itKenntnisArrayList) {
        this.itKenntnisArrayList = itKenntnisArrayList;
        return this;
    }

    public StudentKenntnisView() {

            this.setColumns(5);
            this.setRows(10);
            this.setHeight("100%");
            this.setWidth("100%");
            Collection<String> itNiveauList =   ComponentControl.getInstance().getITNiveau();
            Collection<String> spracheList = ComponentControl.getInstance().getSpracheNiveau();




            Label label2 = new Label("<h3><p><font color=\"blue\">Kenntnisse</font></p></h3>", ContentMode.HTML);
            label2.setHeight("45px");
            this.addComponent(label2,0,1,1,1);
            this.setComponentAlignment(label2, Alignment.MIDDLE_CENTER);

            itKenntnisArrayList = new ArrayList<>();
            itKenntnis1 = new PopUpTextField("Bsp. MS-Office");
            itKenntnis1.setWidth("200px");
            niveau1 = new ComboBoxNiveau(itNiveauList);




            plus = new Button(VaadinIcons.PLUS);
            plus.addStyleName(MaterialTheme.BUTTON_FLOATING_ACTION);

            minus = new Button(VaadinIcons.MINUS);
            minus.addStyleNames(MaterialTheme.BUTTON_DANGER,MaterialTheme.BUTTON_FLOATING_ACTION);

            binder = new Binder<>(Student.ITKenntnis.class);


            binder.forField(itKenntnis1)
                    .asRequired("Bitte Feld ausfüllen!")
                    .bind(Student.ITKenntnis::getKenntnis, Student.ITKenntnis::setKenntnis);
            binder.forField(niveau1)
                    .asRequired("Bitte Niveau ausfüllen")
                    .bind(Student.ITKenntnis::getNiveau, Student.ITKenntnis::setNiveau);

            this.addComponent(itKenntnis1,0,2);
            this.addComponent(niveau1,1,2);
            this.addComponent(plus,0,3);
            this.setComponentAlignment(itKenntnis1,Alignment.MIDDLE_CENTER);
            this.setComponentAlignment(niveau1,Alignment.MIDDLE_CENTER);
            this.setComponentAlignment(plus,Alignment.MIDDLE_CENTER);


            final int[] i_c = {0,1,2,3,4};
            final int[] kenntnisIR = {3};
            plus.addClickListener((Button.ClickListener) event -> {

                if (binder.isValid()) {
                    Student.ITKenntnis itKenntnis = new Student.ITKenntnis();
                    try {
                        binder.writeBean(itKenntnis);
                    } catch (ValidationException e) {
                        e.printStackTrace();
                    }
                    itKenntnisArrayList.add(itKenntnis);

                    this.removeComponent(plus);
                    this.removeComponent(minus);

                    this.addComponent(new PopUpTextField("Bsp. MS-Office"), i_c[0], kenntnisIR[0]);
                    this.getComponent(i_c[0], kenntnisIR[0]).setWidth("200px");
                    this.addComponent(new ComboBoxNiveau(itNiveauList), i_c[1], kenntnisIR[0]);
                    this.setComponentAlignment(this.getComponent(i_c[0], kenntnisIR[0]), Alignment.MIDDLE_CENTER);
                    this.setComponentAlignment(this.getComponent(i_c[1], kenntnisIR[0]), Alignment.MIDDLE_CENTER);
                    ((PopUpTextField) this.getComponent(i_c[0], kenntnisIR[0])).selectAll();

                    binder.forField((PopUpTextField) this.getComponent(i_c[0], kenntnisIR[0]))
                            .asRequired("Bitte Feld ausfüllen!")
                            .bind(Student.ITKenntnis::getKenntnis, Student.ITKenntnis::setKenntnis);
                    binder.forField((ComboBoxNiveau) this.getComponent(i_c[1], kenntnisIR[0]))
                            .asRequired("Bitte Niveau ausfüllen")
                            .bind(Student.ITKenntnis::getNiveau, Student.ITKenntnis::setNiveau);


                    if (kenntnisIR[0] <= 4) {
                        this.addComponent(plus, i_c[0], kenntnisIR[0] + 1);
                        this.setComponentAlignment(plus, Alignment.MIDDLE_CENTER);
                    }
                    kenntnisIR[0]++;
                    this.addComponent(minus, i_c[1], kenntnisIR[0]);
                    this.setComponentAlignment(minus, Alignment.MIDDLE_CENTER);


                } else {
                    binder.validate().getFieldValidationErrors();
                }
            });



            minus.addClickListener((Button.ClickListener) event -> {

                this.removeComponent(plus);
                this.removeComponent(minus);
                kenntnisIR[0]--;
                binder.removeBinding( (PopUpTextField) this.getComponent(i_c[0], kenntnisIR[0]));
                binder.removeBinding( ((ComboBoxNiveau) this.getComponent(i_c[1], kenntnisIR[0])));

                for (int i = 0; i <= 1 ; i++) {
                    this.removeComponent(i,kenntnisIR[0]);

                }



                this.addComponent(plus,i_c[0],kenntnisIR[0]);
                if(kenntnisIR[0] != 3) {
                    this.addComponent(minus, i_c[1], kenntnisIR[0]);
                    this.setComponentAlignment(minus,Alignment.MIDDLE_CENTER);

                }
                this.setComponentAlignment(plus,Alignment.MIDDLE_CENTER);
                if(binder.isValid()) {
                    itKenntnisArrayList.remove(itKenntnisArrayList.size() - 1);
                }
            });



            Label label3 = new Label("<h3><p><font color=\"blue\">Sprachen</font></p></h3>",ContentMode.HTML);
            label3.setHeight("45px");
            this.addComponent(label3,3,1,4,1);
            this.setComponentAlignment(label3,Alignment.MIDDLE_CENTER);

            sprachKenntnis1 = new PopUpTextField("Bsp. Englisch");
            sprachKenntnis1.setWidth("200px");

            sprachKenntnisArrayList = new ArrayList<>();

            niveau21 = new ComboBoxNiveau(spracheList);




            plus1 = new Button(VaadinIcons.PLUS);
            plus1.addStyleName(MaterialTheme.BUTTON_FLOATING_ACTION);

            minus1 = new Button(VaadinIcons.MINUS);
            minus1.addStyleNames(MaterialTheme.BUTTON_DANGER,MaterialTheme.BUTTON_FLOATING_ACTION);

            binder1 = new Binder<>(Student.SprachKenntnis.class);


            binder1.forField(sprachKenntnis1)
                    .asRequired("Bitte Feld ausfüllen!")
                    .bind(Student.SprachKenntnis::getKenntnis, Student.SprachKenntnis::setKenntnis);
            binder1.forField(niveau21)
                    .asRequired("Bitte Niveau ausfüllen")
                    .bind(Student.SprachKenntnis::getNiveau, Student.SprachKenntnis::setNiveau);

            this.addComponent(sprachKenntnis1,3,2);
            this.addComponent(niveau21,4,2);
            this.addComponent(plus1,3,3);
            this.setComponentAlignment(plus1, Alignment.MIDDLE_CENTER);

            this.setComponentAlignment(sprachKenntnis1,Alignment.MIDDLE_CENTER);
            this.setComponentAlignment(niveau21,Alignment.MIDDLE_CENTER);

            final int[] j_c = {0,1,2,3,4};
            final int[] j_r = {3};
            plus1.addClickListener((Button.ClickListener) event -> {

                if (binder1.isValid()) {
                    Student.SprachKenntnis sprachKenntnis = new Student.SprachKenntnis();
                    try {
                        binder1.writeBean(sprachKenntnis);
                    } catch (ValidationException e) {
                        e.printStackTrace();
                    }
                    sprachKenntnisArrayList.add(sprachKenntnis);

                    this.removeComponent(plus1);
                    this.removeComponent(minus1);

                    this.addComponent(new PopUpTextField("Bsp. Englisch"), j_c[3], j_r[0]);
                    this.getComponent(j_c[3], j_r[0]).setWidth("200px");
                    this.addComponent(new ComboBoxNiveau(spracheList), j_c[4], j_r[0]);
                    this.setComponentAlignment(this.getComponent(j_c[3], j_r[0]), Alignment.MIDDLE_CENTER);
                    this.setComponentAlignment(this.getComponent(j_c[4], j_r[0]), Alignment.MIDDLE_CENTER);
                    ((PopUpTextField) this.getComponent(j_c[3], j_r[0])).selectAll();

                    binder1.forField((PopUpTextField) this.getComponent(j_c[3], j_r[0]))
                            .asRequired("Bitte Feld ausfüllen!")
                            .bind(Student.SprachKenntnis::getKenntnis, Student.SprachKenntnis::setKenntnis);
                    binder1.forField((ComboBoxNiveau) this.getComponent(j_c[4], j_r[0]))
                            .asRequired("Bitte Niveau ausfüllen")
                            .bind(Student.SprachKenntnis::getNiveau, Student.SprachKenntnis::setNiveau);


                    if (j_r[0] <= 4) {
                        this.addComponent(plus1, j_c[3], j_r[0] + 1);
                        this.setComponentAlignment(plus1, Alignment.MIDDLE_CENTER);
                    }
                    j_r[0]++;

                    this.addComponent(minus1,j_c[4],j_r[0]);
                    this.setComponentAlignment(minus1, Alignment.MIDDLE_CENTER);

                } else {
                    binder1.validate().getFieldValidationErrors();
                }
            });
            minus1.addClickListener((Button.ClickListener) event -> {

                this.removeComponent(plus1);
                this.removeComponent(minus1);
                j_r[0]--;
                binder1.removeBinding( (PopUpTextField) this.getComponent(j_c[3], j_r[0]));
                binder1.removeBinding( ((ComboBoxNiveau) this.getComponent(j_c[4], j_r[0])));

                for (int i = 3; i <= 4 ; i++) {
                    this.removeComponent(i,j_r[0]);

                }



                this.addComponent(plus1,j_c[3],j_r[0]);
                if(j_r[0] != 3) {
                    this.addComponent(minus1, j_c[4], j_r[0]);
                    this.setComponentAlignment(minus1,Alignment.MIDDLE_CENTER);

                }
                this.setComponentAlignment(plus1,Alignment.MIDDLE_CENTER);
                if(binder1.isValid()) {
                    sprachKenntnisArrayList.remove(sprachKenntnisArrayList.size() - 1);
                }
            });

            RegisterStudentWindow registerStudentWindow = new RegisterStudentWindow();
            registerStudentWindow.getWizard().getNextButton().setEnabled(false);
        }



    public void setKenntnisValue() {
        Student student = (Student) UI.getCurrent().getSession().getAttribute(Roles.Student);
        if(!student.getItKenntnisList().isEmpty()) {
            for (int i = 0; i < student.getItKenntnisList().size(); i++) {
                ((PopUpTextField)this.getComponent(0,i+2)).setValue(student.getItKenntnisList().get(i).getKenntnis());
                ((ComboBoxNiveau)this.getComponent(1,i+2)).setValue(student.getItKenntnisList().get(i).getNiveau());

                if (!(student.getItKenntnisList().size() - 1 == i)) {
                    plus.click();
                }


            }
        } else {
            this.setEnabled(false);
        }

        if(!student.getSprachKenntnisList().isEmpty()) {
            for (int i = 0; i < student.getSprachKenntnisList().size(); i++) {
                ((PopUpTextField)this.getComponent(3,i+2)).setValue(student.getSprachKenntnisList().get(i).getKenntnis());
                ((ComboBoxNiveau)this.getComponent(4,i+2)).setValue(student.getSprachKenntnisList().get(i).getNiveau());
                if (!(student.getSprachKenntnisList().size() - 1 == i)) {
                    plus1.click();
                }
            }
        } else {
            this.setEnabled(false);
        }


    }

    public void setReadOnly(boolean status) {

        Student student = (Student) UI.getCurrent().getSession().getAttribute(Roles.Student);
        for (int i = 0; i < student.getItKenntnisList().size(); i++) {
            ((PopUpTextField)   this.getComponent(0,i+2)).setReadOnly(status);
            ((ComboBoxNiveau) this.getComponent(1,i+2)).setReadOnly(status);
        }
        for (int i = 0; i < student.getSprachKenntnisList().size(); i++) {
            ((PopUpTextField)   this.getComponent(3,i+2)).setReadOnly(status);
            ((ComboBoxNiveau) this.getComponent(4,i+2)).setReadOnly(status);
        }
        plus.setVisible(!status);
        plus1.setVisible(!status);
        minus1.setVisible(!status);
        minus.setVisible(!status);

    }

    public ArrayList<Student.ITKenntnis> getITKenntnisValue() {
            Student.ITKenntnis itKenntnis = new Student.ITKenntnis();
            binder.writeBeanIfValid(itKenntnis);
            itKenntnisArrayList.add(itKenntnis);
        /*
        ArrayList<Student.ITKenntnis> itKenntnisArrayList = new ArrayList<>();
        for (int i = 0; i <this.getRows() ; i++) {
            Student.ITKenntnis itKenntnis = new Student.ITKenntnis();
            if(this.getComponent(0,i+2) != null || this.getComponent(0,i+2).getClass() == ) {
                itKenntnis.setKenntnis(((PopUpTextField) this.getComponent(0, i + 2)).getValue());
                itKenntnis.setNiveau(((ComboBoxNiveau) this.getComponent(1, i + 2)).getValue());
                itKenntnisArrayList.add(itKenntnis);
            }
        }

         */
        return itKenntnisArrayList;
    }

    public ArrayList<Student.SprachKenntnis> getSprachenValue() {
        Student.SprachKenntnis sprachKenntnis = new Student.SprachKenntnis();
        binder1.writeBeanIfValid(sprachKenntnis);
        sprachKenntnisArrayList.add(sprachKenntnis);

        /*
        ArrayList<Student.SprachKenntnis> sprachKenntnisArrayList = new ArrayList<>();
        for (int i = 0; i <this.getRows() ; i++) {
            Student.SprachKenntnis sprachKenntnis = new Student.SprachKenntnis();
            if(this.getComponent(3,i+2).getClass() == PopUpTextField.class && this.getComponent(3,i+2) != null) {
                sprachKenntnis.setKenntnis(((PopUpTextField) this.getComponent(3, i + 2)).getValue());
                sprachKenntnis.setNiveau(((ComboBoxNiveau) this.getComponent(4, i + 2)).getValue());
                sprachKenntnisArrayList.add(sprachKenntnis);
            }
        }
        */

        return sprachKenntnisArrayList;
    }

}
