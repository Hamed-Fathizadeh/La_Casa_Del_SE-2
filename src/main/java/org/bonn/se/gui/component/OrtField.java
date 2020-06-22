package org.bonn.se.gui.component;

import com.vaadin.ui.ComboBox;
import org.bonn.se.services.util.OrtService;

public class OrtField extends ComboBox<String> {

    String ort;
    String bundesland;

    public OrtField(String caption) {
        this.setHeight("56px");
        this.setWidth("408px");
        OrtService ortService = new OrtService("Stadt, Bund");

        this.setDataProvider(ortService::fetch, ortService::count);

        this.setPlaceholder(caption);

    }


    public String getOrt() {
        if(this.getValue() == null) {
            return null;
        } else {
            return this.getValue().substring(0,this.getValue().indexOf(", "));
        }
    }

    public String getBundesland() {
        if(this.getValue() == null) {
            return null;
        } else {
            return this.getValue().substring(this.getValue().lastIndexOf(" ")+1);
        }
    }

}
