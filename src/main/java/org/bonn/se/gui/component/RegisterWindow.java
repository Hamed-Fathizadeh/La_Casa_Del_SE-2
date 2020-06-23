package org.bonn.se.gui.component;

import com.vaadin.ui.Window;

public class RegisterWindow extends Window {



    public RegisterWindow() {
        this.center();
        this.setDraggable(false);
        this.setResizable(false);
        this.setClosable(false);
        this.setModal(true);
        this.setHeight("80%");
        this.setWidth("80%");
    }
}
