package org.bonn.se.gui.component;

import org.vaadin.teemu.wizards.Wizard;

public class CustomWizard  {

    private static Wizard wizard;



    public static Wizard getWizard(){
        wizard   = new Wizard();
        wizard.setUriFragmentEnabled(true);
        wizard.getBackButton().setCaption("Zurück");
        wizard.getFinishButton().setCaption("Fertig");
        wizard.getNextButton().setCaption("Weiter");
        wizard.getCancelButton().setCaption("Abbrechen");
        return wizard;
    }


}
