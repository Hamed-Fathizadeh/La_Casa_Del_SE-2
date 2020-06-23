package org.bonn.se.gui.component;

import com.vaadin.ui.TextField;

public class ProfilStudentTextField extends TextField  {

    public ProfilStudentTextField(String caption, String value){
        this.setHeight("37px");
        this.setWidth("250px");
        this.setCaption(caption);
        this.setValue(value);
        this.setReadOnly(true);
    }

}
