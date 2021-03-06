package org.bonn.se.gui.window.wizard;

import com.vaadin.ui.*;
import org.bonn.se.control.UnternehmenDescriptionControl;
import org.bonn.se.gui.component.UnternehmenBeschreibungView;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;
import org.vaadin.teemu.wizards.WizardStep;

public class WizardUntBeschreibungStep  implements WizardStep {
        UnternehmenBeschreibungView unternehmenBeschreibungView;
        @Override
        public String getCaption() {
            return "Unternehmensbeschreibung";
        }

        @Override
        public Component getContent() {

            unternehmenBeschreibungView = new UnternehmenBeschreibungView();

            return unternehmenBeschreibungView;
/*
            VerticalLayout verticalLayout = new VerticalLayout();
            verticalLayout.setSizeFull();
            verticalLayout.setMargin(false);

            richTextArea = new RichTextArea();
            richTextArea.setWidthFull();
            richTextArea.setValue("Schreiben Sie etwas hier über Ihr Unternehmen");

            richTextArea.setSizeFull();
            verticalLayout.addComponent(richTextArea);
            verticalLayout.setComponentAlignment(richTextArea, Alignment.MIDDLE_CENTER);



            return verticalLayout;

 */
        }

        @Override
        public boolean onAdvance() {

            UnternehmenDescriptionControl unternehmenDescriptionControl = UnternehmenDescriptionControl.getInstance();
            try {
                ((Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.UNTERNEHMEN)).setDescription(unternehmenBeschreibungView.getRichTextArea().getValue());
                unternehmenDescriptionControl.setDescription();
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        public boolean onBack() {
            return false;
        }
}
