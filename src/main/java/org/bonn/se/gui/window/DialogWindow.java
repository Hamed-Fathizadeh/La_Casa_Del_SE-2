package org.bonn.se.gui.window;

import com.vaadin.ui.*;

public class DialogWindow extends Window {

    public static boolean isDialogStatus() {
        return dialogStatus;
    }

    static boolean dialogStatus;
    public DialogWindow(String text ){

        VerticalLayout verticalLayout = new VerticalLayout();

        Label label = new Label(text);

        verticalLayout.addComponents(label);

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Button ja = new Button("Ja");
        Button nein = new Button("Nein");



        ja.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                dialogStatus = true;
                DialogWindow.this.close();
            }
        });

        nein.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                dialogStatus=false;
                DialogWindow.this.close();
            }
        });
        this.setClosable(false);
        this.setDraggable(false);
        this.setResizable(false);
        this.setModal(true);

        this.setHeight("200px");
        this.setWidth("300px");

        horizontalLayout.addComponents(ja,nein);

        verticalLayout.addComponents(horizontalLayout);

        this.setContent(verticalLayout);
    }





}
