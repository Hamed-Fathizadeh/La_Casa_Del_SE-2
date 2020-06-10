package org.bonn.se.gui.window;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import org.vaadin.teemu.wizards.WizardStep;

public class WizardStepFertig implements WizardStep {

        @Override
        public String getCaption() {
            return "Fertig";
        }

        @Override
        public Component getContent() {
            Label message = new Label("Sehr gut sie können sich nun einloggen.....");
            return message;
        }

        @Override
        public boolean onAdvance() {
            return true;
        }

        @Override
        public boolean onBack() {
            return false;
        }



}
