package org.bonn.se.gui.window.wizard;

import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import org.vaadin.teemu.wizards.WizardStep;

public class WizardStepRegisterSuccessWindow implements WizardStep {

        @Override
        public String getCaption() {
            return "Erfolgreich";
        }

        @Override
        public boolean onBack() {
        return true;
    }


    @Override
        public Component getContent() {

            GridLayout glSuccess = new GridLayout();
            Label label = new Label("Vielen Dank für ihre Registrierung!");
            glSuccess.addComponent(label);
            return glSuccess;
        }

        @Override
        public boolean onAdvance() {
            return true;
        }


}
