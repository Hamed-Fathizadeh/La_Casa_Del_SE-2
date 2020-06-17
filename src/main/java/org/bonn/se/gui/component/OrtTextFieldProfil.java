package org.bonn.se.gui.component;


import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import org.bonn.se.services.util.OrtService;
import org.vaadin.textfieldformatter.NumeralFieldFormatter;


public class OrtTextFieldProfil extends HorizontalLayout {

    TextField plz = new TextField();


    final private ComboBox<String> comboBund = new ComboBox<>();



    public OrtTextFieldProfil(){

        plz = new TextField();
        new NumeralFieldFormatter("","",5,0,false).extend(plz);

        //plz.setValue("PLZ");
        plz.setHeight("37px");
        plz.setWidth("90px");


        //comboBund.setPlaceholder("Ort");
        comboBund.setWidth(150.0f, Unit.PIXELS);
        comboBund.setHeight("37px");

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
