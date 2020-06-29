package org.bonn.se.gui.component;

import com.vaadin.ui.*;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.util.Roles;

public class UnternehmenBeschreibungView extends VerticalLayout {
    private RichTextArea richTextArea;

    public RichTextArea getRichTextArea() {
        return richTextArea;
    }

    public UnternehmenBeschreibungView() {
        this.setSizeFull();
        this.setMargin(false);
        richTextArea = new RichTextArea();
        richTextArea.setWidthFull();
        richTextArea.setValue("Schreiben Sie etwas hier über Ihr Unternehmen");
        richTextArea.setSizeFull();
        this.addComponent(richTextArea);
        this.setComponentAlignment(richTextArea, Alignment.MIDDLE_CENTER);
    }

    public void setReadOnly(boolean status) {
        richTextArea.setReadOnly(status);
    }

    public void setBeschreibungValue() {
        Unternehmen unternehmen = (Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.UNTERNEHMEN);
        richTextArea.setValue(unternehmen.getDescription());
    }
}
