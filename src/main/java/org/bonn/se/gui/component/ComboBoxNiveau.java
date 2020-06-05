package org.bonn.se.gui.component;

import com.vaadin.ui.ComboBox;

import java.util.Collection;

public class ComboBoxNiveau  extends ComboBox<String> {

    public ComboBoxNiveau(String caption, Collection<String> collection){
        super(caption,collection);
        this.setPlaceholder("Niveau");
        this.setHeight("56px");
        this.setWidth("180px");
    }
}
