package org.bonn.se.gui.window;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import org.vaadin.teemu.wizards.WizardStep;

public class WizardStepRegisterSuccess implements WizardStep {

        @Override
        public String getCaption() {
            return "Erfolgreich";
        }

        @Override
        public Component getContent() {
            Label message = new Label("Vielen Dank für ihre Registrierung!");

            return message;
        }

        @Override
        public boolean onAdvance() {
            return true;
        }

        @Override
        public boolean onBack() {
            return true;
        }


}
