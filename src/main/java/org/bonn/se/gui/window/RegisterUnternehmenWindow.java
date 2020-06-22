package org.bonn.se.gui.window;

import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
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

public class RegisterUnternehmenWindow extends Window implements WizardProgressListener {

    private final Wizard wizard;

    public RegisterUnternehmenWindow() {

        this.center();
        this.setDraggable(false);
        this.setResizable(false);
        this.setClosable(false);
        this.setModal(true);
        this.setHeight("80%");
        this.setWidth("80%");
        wizard = new Wizard();

        wizard.setUriFragmentEnabled(true);
        wizard.getBackButton().setCaption("Zurück");
        wizard.getFinishButton().setCaption("Fertig");
        wizard.getNextButton().setCaption("Weiter");
        wizard.getCancelButton().setCaption("Abbrechen");
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
        UI.getCurrent().getSession().setAttribute(Roles.Unternehmen,null);
        UI.getCurrent().getNavigator().navigateTo(Views.LoginView);
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
                        RegisterUnternehmenWindow.this.close();
                        UI.getCurrent().getSession().setAttribute(Roles.Unternehmen,null);
                        UI.getCurrent().getNavigator().navigateTo(Views.LoginView);
                    }
                });
    }
}
