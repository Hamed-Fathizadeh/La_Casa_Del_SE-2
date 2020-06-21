package org.bonn.se.gui.component;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ProfilStudentTaetigkeit extends VerticalLayout {
    TextField beschreibung;
    StudentDateField beginn;
    StudentDateField ende;

    public ProfilStudentTaetigkeit() {
        beschreibung = new TextField("Tätigkeit");
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        beginn = new StudentDateField("Beginn");
        beginn.setCaption("Beginn");
        ende = new StudentDateField("Ende");
        ende.setCaption("Ende");
        horizontalLayout.addComponents(beginn,ende);
        this.addComponents(beschreibung,horizontalLayout);

        beginn.setHeight("37px");
        ende.setHeight("37px");
        beschreibung.setHeight("37px");

        beginn.addStyleName("inline-label");
        ende.addStyleName("inline-label");
        beschreibung.addStyleName("inline-label");

        beginn.setReadOnly(true);
        ende.setReadOnly(true);
        beschreibung.setReadOnly(true);




    }



    public TextField getBeschreibungField() {
        return beschreibung;
    }

    public StudentDateField getBeginnField() {
        return beginn;
    }

    public StudentDateField getEndeField() {
        return ende;
    }


}
