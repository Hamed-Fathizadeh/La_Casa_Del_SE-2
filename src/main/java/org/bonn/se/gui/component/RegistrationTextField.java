package org.bonn.se.gui.component;


import com.vaadin.ui.TextField;

public class RegistrationTextField extends TextField {

    public RegistrationTextField(String caption) {

        this.setHeight("56px");
        this.setWidth("408px");
        this.setPlaceholder(caption);
    }
}
