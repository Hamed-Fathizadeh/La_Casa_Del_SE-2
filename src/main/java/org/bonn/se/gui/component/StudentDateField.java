package org.bonn.se.gui.component;

import com.vaadin.ui.DateField;

public class StudentDateField extends DateField {

    public StudentDateField(String caption){
        this.setHeight("56px");
        this.setWidth("150px");
        this.setPlaceholder(caption);
    }
}
