package org.bonn.se.gui.window;

import com.vaadin.ui.UI;
import org.bonn.se.gui.component.CustomWindow;
import org.bonn.se.gui.component.CustomWizard;
import org.bonn.se.gui.window.wizard.WizardStepFertigWindow;
import org.bonn.se.gui.window.wizard.WizardStepRegisterSuccessWindow;
import org.bonn.se.gui.window.wizard.WizardUntBeschreibungStep;
import org.bonn.se.gui.window.wizard.WizardUntDatenStep;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.dialogs.DefaultConfirmDialogFactory;
import org.vaadin.teemu.wizards.Wizard;
import org.vaadin.teemu.wizards.event.*;

public class RegisterUnternehmenWindow extends CustomWindow implements WizardProgressListener {

    private static Wizard  wizard ;

    public RegisterUnternehmenWindow() {
        wizard = CustomWizard.getWizard();
        wizard.addListener(this);
        wizard.addStep(new WizardStepRegisterSuccessWindow(), "Erfolgreich");
        wizard.addStep(new WizardUntDatenStep(), "Daten");
        wizard.addStep(new WizardUntBeschreibungStep(), "Beschreibung");
        wizard.addStep(new WizardStepFertigWindow(),"Fertig");
        wizard.getBackButton().setVisible(false);
        setContent(wizard);
    }

    @Override
    public void activeStepChanged(WizardStepActivationEvent wizardStepActivationEvent) {

    }

    @Override
    public void stepSetChanged(WizardStepSetChangedEvent wizardStepSetChangedEvent) { }

    @Override
    public void wizardCompleted(WizardCompletedEvent wizardCompletedEvent) {
        wizard.setVisible(false);
        this.close();
        UI.getCurrent().getSession().setAttribute(Roles.UNTERNEHMEN,null);
        UI.getCurrent().getNavigator().navigateTo(Views.LOGINVIEW);
    }

    @Override
    public void wizardCancelled(WizardCancelledEvent wizardCancelledEvent) {
        ConfirmDialog.Factory bestaetigungUnt = new DefaultConfirmDialogFactory(){
            @Override
            public ConfirmDialog create(String caption, String message, String okCaption, String cancelCaption, String notOkCaption) {
                return super.create("Beenden", message, "Ja", "Nein", notOkCaption);
            }
        } ;


        ConfirmDialog.setFactory(bestaetigungUnt);


        ConfirmDialog.show(UI.getCurrent(), "Profilvervollständigung wirklich abbrechen und zum Login?",
                (ConfirmDialog.Listener) dialog -> {
                    if (dialog.isConfirmed()) {
                        wizard.setVisible(false);
                        RegisterUnternehmenWindow.this.close();
                        UI.getCurrent().getSession().setAttribute(Roles.UNTERNEHMEN,null);
                        UI.getCurrent().getNavigator().navigateTo(Views.LOGINVIEW);
                    }
                });
    }
}
