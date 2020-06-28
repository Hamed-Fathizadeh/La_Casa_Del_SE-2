package org.bonn.se.gui.window;

import com.vaadin.server.Page;
import com.vaadin.ui.UI;
import org.bonn.se.gui.component.CustomWindow;
import org.bonn.se.gui.component.CustomWizard;
import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.gui.window.wizard.*;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.dialogs.DefaultConfirmDialogFactory;
import org.vaadin.teemu.wizards.Wizard;
import org.vaadin.teemu.wizards.event.*;

public class RegisterStudentWindow extends CustomWindow implements WizardProgressListener {

    private static Wizard  wizard;



    public RegisterStudentWindow() {
        wizard = CustomWizard.getWizard();
        wizard.addListener(this);
        wizard.addStep(new WizardStepRegisterSuccessWindow(), "Erfolgreich");
        wizard.addStep(new WizardStudentDatenStep(), "Daten");
        wizard.addStep(new WizardStudentTaetigkeitenStep(), "Tätigkeiten");
        wizard.addStep(new WizardStudentKenntnisStep(), "Kenntnisse");
        wizard.addStep(new WizardStepFertigWindow(),"Fertig");
        wizard.getBackButton().setVisible(false);
        setContent(wizard);
    }

    public Wizard getWizard(){
        return wizard;
    }


    @Override
    public void activeStepChanged(WizardStepActivationEvent event) {
        Page.getCurrent().setTitle(event.getActivatedStep().getCaption());


    }

    @Override
    public void stepSetChanged(WizardStepSetChangedEvent wizardStepSetChangedEvent) {

    }

    @Override
    public void wizardCompleted(WizardCompletedEvent wizardCompletedEvent) {
        wizard.setVisible(false);
        this.close();
        UI.getCurrent().getSession().setAttribute(Roles.STUDENT,null);

        MyUI.getCurrent().getNavigator().navigateTo(Views.LOGINVIEW);
    }

    @Override
    public void wizardCancelled(WizardCancelledEvent wizardCancelledEvent) {
        ConfirmDialog.Factory df = new DefaultConfirmDialogFactory(){

            @Override
            public ConfirmDialog create(String caption, String message, String okCaption, String cancelCaption, String notOkCaption) {
                return super.create("Beenden", message, "Ja", "Nein", notOkCaption);
            }
        } ;

        ConfirmDialog.setFactory(df);
        ConfirmDialog.show(UI.getCurrent(), "Profilvervollständigung wirklich abbrechen und zum Login?",
                (ConfirmDialog.Listener) dialog -> {
                    if (dialog.isConfirmed()) {
                        wizard.setVisible(false);
                        RegisterStudentWindow.this.close();
                        UI.getCurrent().getSession().setAttribute(Roles.STUDENT,null);
                        UI.getCurrent().getNavigator().navigateTo(Views.LOGINVIEW);
                    }
                });

    }

}