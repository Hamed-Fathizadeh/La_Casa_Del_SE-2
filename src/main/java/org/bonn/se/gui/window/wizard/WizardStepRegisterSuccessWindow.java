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
            Label label = new Label("<title> Designtexte für Registrierung </title>\n" +
                    "<h1 style=\"text-align: center;\"><font face=\"Arial Black\" size=\"16\" color=\"#003853\">Vielen Dank für Ihre Registrierung &#128516;</font></h1>\n" +
                    "<div><font size=\"6\" color=\"#0000ff\" face=\"Verdana\" size=\"5\" ><br></font></div>\n" +
                    "<div><font size=\"6\" color=\"#45BB89\" face=\"Arial Black\"><br></font></div>\n" +
                    "<div style=\"text-align: center;\"><font color=\"#FF00AA\" \n" +
                    "=\"Verdana\" size=\"6\" ><b style=\"\">LaColSco empfiehlt Ihnen um noch mehr Erfolg bei der Suche zu haben,<br> sich etwas Zeit für die nächsten Schritte zu nehmen !</b></font></div>\n" +
                    "<div style=\"text-align: center;\"><font face=\"Arial Black\"><font color=\"#008000\"><b><br></b></font></font></div>\n" +
                    "<div style=\"text-align: center;\"><font face=\"Arial Black\"><font color=\"#008000\"><b><br></b></font></font></div>\n" +
                    "<div style=\"text-align: center;\"><font face=\"Arial Black\"><br></div>\n" +
                    "<div style=\"text-align: center;\"><b>\n" +
                    "<font color=\"#003853\" size=\"14\">Wir wünschen Ihnen viel Spaß &#x1F609\n" +
                    "</font><font size=\"7\" color=\"#0000ff\">&nbsp;</font></b></div>\n" +
                    "<div><br></div>", ContentMode.HTML);
            gl_success.addComponent(label);
            gl_success.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
            return gl_success;
        }

        @Override
        public boolean onAdvance() {
            return true;
        }


}
