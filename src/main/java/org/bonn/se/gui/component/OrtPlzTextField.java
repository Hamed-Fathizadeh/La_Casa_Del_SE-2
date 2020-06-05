package org.bonn.se.gui.component;


import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import org.bonn.se.services.util.OrtService;
import org.vaadin.textfieldformatter.NumeralFieldFormatter;


public class OrtPlzTextField extends HorizontalLayout {

    TextField plz = new TextField();


    final private ComboBox<String> comboBund = new ComboBox<>();



    public OrtPlzTextField(){

        plz = new TextField();
        new NumeralFieldFormatter("","",5,0,false).extend(plz);

        plz.setPlaceholder("PLZ");
        plz.setHeight("56px");
        plz.setWidth("90px");


        comboBund.setPlaceholder("Ort");
        comboBund.setWidth(300.0f, Unit.PIXELS);
        comboBund.setHeight("56px");

        OrtService Ortservice = new OrtService("Stadt - Bund");
        comboBund.setDataProvider(Ortservice::fetch, Ortservice::count);


        this.addComponents(plz,comboBund);
    }

    public TextField getPlz() {
        return plz;
    }

    public ComboBox getBundesland() {
        return comboBund;
    }
}
