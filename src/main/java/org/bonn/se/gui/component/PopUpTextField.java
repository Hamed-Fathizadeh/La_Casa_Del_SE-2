package org.bonn.se.gui.component;
import com.vaadin.ui.TextField;


public class PopUpTextField extends TextField {


    public PopUpTextField(String caption){
        this.setHeight("56px");
        this.setWidth("300px");
        this.setPlaceholder(caption);
    }
}
