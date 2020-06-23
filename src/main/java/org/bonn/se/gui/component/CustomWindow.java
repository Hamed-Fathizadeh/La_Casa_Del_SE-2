package org.bonn.se.gui.component;

import com.vaadin.ui.Window;

public class CustomWindow extends Window {



    public CustomWindow() {
        this.center();
        this.setDraggable(false);
        this.setResizable(false);
        this.setClosable(false);
        this.setModal(true);
        this.setHeight("80%");
        this.setWidth("80%");
    }
}
