package org.bonn.se.gui.component;


import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import org.bonn.se.services.util.OrtService;
import org.vaadin.textfieldformatter.NumeralFieldFormatter;


public class OrtPlzTextField extends HorizontalLayout {

    final TextField plz ;
    final OrtField comboBund;

    public OrtPlzTextField() {

        plz = new TextField();
        new NumeralFieldFormatter("","",5,0,false).extend(plz);

        plz.setPlaceholder("PLZ");
        plz.setHeight("56px");
        plz.setWidth("90px");


        comboBund = new OrtField("Ort");
        comboBund.setWidth(300.0f, Unit.PIXELS);
        comboBund.setHeight("56px");

        OrtService ortService = new OrtService("Stadt, Bund");
        comboBund.setDataProvider(ortService::fetch, ortService::count);


        this.addComponents(plz,comboBund);
    }

    public String getPlz() {
        return plz.getValue();
    }
    public TextField getPlzField() {
        return plz;
    }
    public OrtField getOrtField(){ return comboBund;}

    public String getOrt() {
        return comboBund.getOrt();
    }
    public String getBunesland() {
        return comboBund.getBundesland();
    }

}
