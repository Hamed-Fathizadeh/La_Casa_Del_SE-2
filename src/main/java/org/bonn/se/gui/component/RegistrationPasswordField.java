package org.bonn.se.gui.component;

import com.vaadin.ui.PasswordField;

public class RegistrationPasswordField extends PasswordField {


    public RegistrationPasswordField(String caption){
        this.setHeight("56px");
        this.setWidth("408px");
        this.setPlaceholder(caption);
    }

}
