package org.bonn.se.gui.component;

import com.vaadin.ui.TextField;
import org.vaadin.textfieldformatter.NumeralFieldFormatter;

public class NumeralField extends TextField  {



    public NumeralField(String caption) {

        new NumeralFieldFormatter("", "", 10, 0, true).extend(this);
        this.setHeight("56px");
        this.setWidth("300px");
        this.setPlaceholder(caption);

    }


}
