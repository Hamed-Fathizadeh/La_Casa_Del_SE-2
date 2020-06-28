package org.bonn.se.gui.component;

import com.vaadin.data.Binder;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.bonn.se.model.objects.entitites.Taetigkeit;

public class ProfilStudentTaetigkeit extends VerticalLayout {
    final TextField beschreibung;
    final StudentDateField beginn;
    final StudentDateField ende;

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

        Binder<Taetigkeit> binder = new Binder<>(Taetigkeit.class);
        binder.forField(beschreibung)
                .asRequired("Bitte ausfüllen")
                .bind(Taetigkeit::getTaetigkeitName, Taetigkeit::setTaetigkeitName);

        binder.forField(beginn)
                .asRequired("Bitte ausfüllen")
                .bind(Taetigkeit::getBeginn, Taetigkeit::setBeginn);

        binder.forField(ende)
                .asRequired("Bitte ausfüllen")
                .withValidator(
                        endDate -> endDate
                                .isAfter(beginn.getValue()) || endDate.isEqual(null),
                        "Beginndatum darf nicht nach Enddatum sein!")
                .bind(Taetigkeit::getEnde, Taetigkeit::setEnde);


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
