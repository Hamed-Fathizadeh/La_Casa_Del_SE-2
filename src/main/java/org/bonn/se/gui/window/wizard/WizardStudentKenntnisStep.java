package org.bonn.se.gui.window.wizard;

import com.github.appreciated.material.MaterialTheme;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se.gui.component.ComboBoxNiveau;
import org.bonn.se.gui.component.PopUpTextField;
import org.bonn.se.gui.window.RegisterStudentWindow;
import org.bonn.se.model.dao.ProfilDAO;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.DatenStudentProfil;
import org.bonn.se.services.util.Roles;
import org.vaadin.teemu.wizards.WizardStep;

import java.util.ArrayList;

public class WizardStudentKenntnisStep implements WizardStep {

        ArrayList<Student.SprachKenntnis> sprachKenntnisArrayList;
        Binder<Student.SprachKenntnis> binder1;
        Binder<Student.ITKenntnis> binder;
        ArrayList<Student.ITKenntnis> itKenntnisArrayList;

        @Override
        public String getCaption() {
            return "Kenntnisse";
        }

        @Override
        public Component getContent() {
            GridLayout gridLayout = new GridLayout(5, 10);
            gridLayout.setHeight("100%");
            gridLayout.setWidth("100%");




            Label label2 = new Label("<h3><p><font color=\"blue\">Kenntnisse</font></p></h3>", ContentMode.HTML);
            label2.setHeight("45px");
            gridLayout.addComponent(label2,0,1,1,1);
            gridLayout.setComponentAlignment(label2, Alignment.MIDDLE_CENTER);

            itKenntnisArrayList = new ArrayList<>();
            PopUpTextField itKenntnis1 = new PopUpTextField("Bsp. MS-Office");
            itKenntnis1.setWidth("200px");
            ComboBoxNiveau niveau1 = new ComboBoxNiveau("", DatenStudentProfil.collection3);



            Button plus = new Button(VaadinIcons.PLUS);
            plus.addStyleName(MaterialTheme.BUTTON_FLOATING_ACTION);

            Button minus = new Button(VaadinIcons.MINUS);
            minus.addStyleNames(MaterialTheme.BUTTON_DANGER,MaterialTheme.BUTTON_FLOATING_ACTION);

            binder = new Binder<>(Student.ITKenntnis.class);


            binder.forField(itKenntnis1)
                    .asRequired("Bitte Feld ausfüllen!")
                    .bind(Student.ITKenntnis::getKenntnis, Student.ITKenntnis::setKenntnis);
            binder.forField(niveau1)
                    .asRequired("Bitte Niveau ausfüllen")
                    .bind(Student.ITKenntnis::getNiveau, Student.ITKenntnis::setNiveau);

            gridLayout.addComponent(itKenntnis1,0,2);
            gridLayout.addComponent(niveau1,1,2);
            gridLayout.addComponent(plus,0,3);
            gridLayout.setComponentAlignment(itKenntnis1,Alignment.MIDDLE_CENTER);
            gridLayout.setComponentAlignment(niveau1,Alignment.MIDDLE_CENTER);
            gridLayout.setComponentAlignment(plus,Alignment.MIDDLE_CENTER);


            final int[] i_c = {0,1,2,3,4};
            final int[] i_r = {3};
            plus.addClickListener((Button.ClickListener) event -> {

                if (binder.isValid()) {
                    Student.ITKenntnis itKenntnis = new Student.ITKenntnis();
                    try {
                        binder.writeBean(itKenntnis);
                    } catch (ValidationException e) {
                        e.printStackTrace();
                    }
                    itKenntnisArrayList.add(itKenntnis);

                    gridLayout.removeComponent(plus);
                    gridLayout.removeComponent(minus);

                    gridLayout.addComponent(new PopUpTextField("Bsp. MS-Office"), i_c[0], i_r[0]);
                    gridLayout.getComponent(i_c[0], i_r[0]).setWidth("200px");
                    gridLayout.addComponent(new ComboBoxNiveau("",DatenStudentProfil.collection3), i_c[1], i_r[0]);
                    gridLayout.setComponentAlignment(gridLayout.getComponent(i_c[0], i_r[0]), Alignment.MIDDLE_CENTER);
                    gridLayout.setComponentAlignment(gridLayout.getComponent(i_c[1], i_r[0]), Alignment.MIDDLE_CENTER);
                    ((PopUpTextField) gridLayout.getComponent(i_c[0], i_r[0])).selectAll();

                    binder.forField((PopUpTextField) gridLayout.getComponent(i_c[0], i_r[0]))
                            .asRequired("Bitte Feld ausfüllen!")
                            .bind(Student.ITKenntnis::getKenntnis, Student.ITKenntnis::setKenntnis);
                    binder.forField((ComboBoxNiveau) gridLayout.getComponent(i_c[1], i_r[0]))
                            .asRequired("Bitte Niveau ausfüllen")
                            .bind(Student.ITKenntnis::getNiveau, Student.ITKenntnis::setNiveau);


                    if (i_r[0] <= 4) {
                        gridLayout.addComponent(plus, i_c[0], i_r[0] + 1);
                        gridLayout.setComponentAlignment(plus, Alignment.MIDDLE_CENTER);
                    }
                    i_r[0]++;
                    gridLayout.addComponent(minus, i_c[1], i_r[0]);
                    gridLayout.setComponentAlignment(minus, Alignment.MIDDLE_CENTER);


                } else {
                    binder.validate().getFieldValidationErrors();
                }
            });



            minus.addClickListener((Button.ClickListener) event -> {

                gridLayout.removeComponent(plus);
                gridLayout.removeComponent(minus);
                i_r[0]--;
                binder.removeBinding( (PopUpTextField) gridLayout.getComponent(i_c[0], i_r[0]));
                binder.removeBinding( ((ComboBoxNiveau) gridLayout.getComponent(i_c[1], i_r[0])));

                for (int i = 0; i <= 1 ; i++) {
                    gridLayout.removeComponent(i,i_r[0]);

                }



                gridLayout.addComponent(plus,i_c[0],i_r[0]);
                if(i_r[0] != 3) {
                    gridLayout.addComponent(minus, i_c[1], i_r[0]);
                }
                gridLayout.setComponentAlignment(plus,Alignment.MIDDLE_CENTER);
                gridLayout.setComponentAlignment(minus,Alignment.MIDDLE_CENTER);
                if(binder.isValid()) {
                    itKenntnisArrayList.remove(itKenntnisArrayList.size() - 1);
                }
            });



            Label label3 = new Label("<h3><p><font color=\"blue\">Sprachen</font></p></h3>",ContentMode.HTML);
            label3.setHeight("45px");
            gridLayout.addComponent(label3,3,1,4,1);
            gridLayout.setComponentAlignment(label3,Alignment.MIDDLE_CENTER);

            PopUpTextField sprachKenntnis1 = new PopUpTextField("Bsp. Englisch");
            sprachKenntnis1.setWidth("200px");

            sprachKenntnisArrayList = new ArrayList<>();

            ComboBoxNiveau niveau21 = new ComboBoxNiveau("",DatenStudentProfil.collection2);




            Button plus1 = new Button(VaadinIcons.PLUS);
            plus1.addStyleName(MaterialTheme.BUTTON_FLOATING_ACTION);

            Button minus1 = new Button(VaadinIcons.MINUS);
            minus1.addStyleNames(MaterialTheme.BUTTON_DANGER,MaterialTheme.BUTTON_FLOATING_ACTION);

            binder1 = new Binder<>(Student.SprachKenntnis.class);


            binder1.forField(sprachKenntnis1)
                    .asRequired("Bitte Feld ausfüllen!")
                    .bind(Student.SprachKenntnis::getKenntnis, Student.SprachKenntnis::setKenntnis);
            binder1.forField(niveau21)
                    .asRequired("Bitte Niveau ausfüllen")
                    .bind(Student.SprachKenntnis::getNiveau, Student.SprachKenntnis::setNiveau);

            gridLayout.addComponent(sprachKenntnis1,3,2);
            gridLayout.addComponent(niveau21,4,2);
            gridLayout.addComponent(plus1,3,3);
            gridLayout.setComponentAlignment(plus1, Alignment.MIDDLE_CENTER);

            gridLayout.setComponentAlignment(sprachKenntnis1,Alignment.MIDDLE_CENTER);
            gridLayout.setComponentAlignment(niveau21,Alignment.MIDDLE_CENTER);

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

                    gridLayout.removeComponent(plus1);
                    gridLayout.removeComponent(minus1);

                    gridLayout.addComponent(new PopUpTextField("Bsp. Englisch"), j_c[3], j_r[0]);
                    gridLayout.getComponent(j_c[3], j_r[0]).setWidth("200px");
                    gridLayout.addComponent(new ComboBoxNiveau("",DatenStudentProfil.collection2), j_c[4], j_r[0]);
                    gridLayout.setComponentAlignment(gridLayout.getComponent(j_c[3], j_r[0]), Alignment.MIDDLE_CENTER);
                    gridLayout.setComponentAlignment(gridLayout.getComponent(j_c[4], j_r[0]), Alignment.MIDDLE_CENTER);
                    ((PopUpTextField) gridLayout.getComponent(j_c[3], j_r[0])).selectAll();

                    binder1.forField((PopUpTextField) gridLayout.getComponent(j_c[3], j_r[0]))
                            .asRequired("Bitte Feld ausfüllen!")
                            .bind(Student.SprachKenntnis::getKenntnis, Student.SprachKenntnis::setKenntnis);
                    binder1.forField((ComboBoxNiveau) gridLayout.getComponent(j_c[4], j_r[0]))
                            .asRequired("Bitte Niveau ausfüllen")
                            .bind(Student.SprachKenntnis::getNiveau, Student.SprachKenntnis::setNiveau);


                    if (j_r[0] <= 4) {
                        gridLayout.addComponent(plus1, j_c[3], j_r[0] + 1);
                        gridLayout.setComponentAlignment(plus1, Alignment.MIDDLE_CENTER);
                    }
                    j_r[0]++;

                    gridLayout.addComponent(minus1,j_c[4],j_r[0]);
                    gridLayout.setComponentAlignment(minus1, Alignment.MIDDLE_CENTER);

                } else {
                    binder1.validate().getFieldValidationErrors();
                }
            });
            minus1.addClickListener((Button.ClickListener) event -> {

                gridLayout.removeComponent(plus1);
                gridLayout.removeComponent(minus1);
                j_r[0]--;
                binder.removeBinding( (PopUpTextField) gridLayout.getComponent(j_c[0], j_r[0]));
                binder.removeBinding( ((ComboBoxNiveau) gridLayout.getComponent(j_c[1], j_r[0])));

                for (int i = 0; i <= 1 ; i++) {
                    gridLayout.removeComponent(i,j_r[0]);

                }



                gridLayout.addComponent(plus1,j_c[0],j_r[0]);
                if(j_r[0] != 3) {
                    gridLayout.addComponent(minus1, j_c[1], j_r[0]);
                }
                gridLayout.setComponentAlignment(plus1,Alignment.MIDDLE_CENTER);
                gridLayout.setComponentAlignment(minus1,Alignment.MIDDLE_CENTER);
                if(binder1.isValid()) {
                    sprachKenntnisArrayList.remove(sprachKenntnisArrayList.size() - 1);
                }
            });

            RegisterStudentWindow registerStudentWindow = new RegisterStudentWindow();
            registerStudentWindow.getWizard().getNextButton().setEnabled(false);


            //     binder.addStatusChangeListener(event -> wizard.getNextButton().setEnabled(binder.isValid()));
            //    binder1.addStatusChangeListener(event -> wizard.getNextButton().setEnabled(binder.isValid()));


            return gridLayout;
        }

        @Override
        public boolean onAdvance() {
            Student.ITKenntnis itKenntnis = new Student.ITKenntnis();
            Student.SprachKenntnis sprachKenntnis = new Student.SprachKenntnis();
            try {
                if(binder.isValid()) {
                    binder.writeBean(itKenntnis);
                    itKenntnisArrayList.add(itKenntnis);
                    ((Student)UI.getCurrent().getSession().getAttribute(Roles.Student)).setItKenntnisList(itKenntnisArrayList);

                }
                if (binder1.isValid()) {
                    binder1.writeBean(sprachKenntnis);
                    sprachKenntnisArrayList.add(sprachKenntnis);
                    ((Student)UI.getCurrent().getSession().getAttribute(Roles.Student)).setSprachKenntnisList(sprachKenntnisArrayList);

                }
            } catch (ValidationException e) {
                e.printStackTrace();
            }

            try {
                ProfilDAO.getInstance().createStudentProfil3((Student) UI.getCurrent().getSession().getAttribute(Roles.Student));
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


