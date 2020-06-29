package org.bonn.se.gui.window.wizard;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
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

            GridLayout gl_success = new GridLayout();
            gl_success.setSizeFull();
            Label label = new Label("Vielen Dank für ihre Registrierung!", ContentMode.HTML);
            gl_success.addComponent(label);
            gl_success.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
            return gl_success;
        }

        @Override
        public boolean onAdvance() {
            return true;
        }


}
