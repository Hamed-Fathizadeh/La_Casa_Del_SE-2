package org.bonn.se.gui.window.wizard;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import org.vaadin.teemu.wizards.WizardStep;

public class WizardStepFertigWindow implements WizardStep {

        @Override
        public String getCaption() {
            return "Fertig";
        }

        @Override
        public Component getContent() {
            GridLayout glFertig = new GridLayout();
            glFertig.setSizeFull();
            Label label = new Label("<title> Designtexte für Registrierung </title>\n" +
                    "<h1 style=\"text-align: center;\"><font face=\"Arial Black\" size=\"22\" color=\"#003853\">Sehr gut &#128516</font></h1>\n" +
                    "<div style=\"text-align: center;\"><b>\n" +
                    "<font color=\"#003853\" size=\"22\" face=\"Arial Black\" >Sie k&oumlnnen sich nun einloggen &#128512\n" +
                    "</font><font size=\"20\" color=\"#0000ff\">&nbsp;</font></b></div>\n" +
                    "<div><br></div>", ContentMode.HTML);
            glFertig.addComponent(label);
            glFertig.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
            return glFertig;
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
