package org.bonn.se.gui.window;

import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Window;
import org.bonn.se.gui.component.Bewerbungen;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.ContainerLetztenBewerbungen;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.util.DeletFile;
import org.bonn.se.services.util.PdfUploader;

public class StellenanzeigeBewerbungenWindow extends Window {

    public StellenanzeigeBewerbungenWindow(StellenanzeigeDTO stellenanzeige) {
        setUp(stellenanzeige);
    }

    public void setUp(StellenanzeigeDTO stellenanzeige) {
        this.center();
        this.setWidth("80%");
        this.setHeight("90%");
        this.setModal(true);
        this.setResizable(false);
        //this.setClosable(false);

        Panel panel = new Panel();
        panel.setWidthFull();
        Button back = new Button("Zurück");

        back.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                StellenanzeigeBewerbungenWindow.this.close();
                if(PdfUploader.getPath()!= null) {
                    DeletFile.delete(PdfUploader.getPath());
                }
            }
        });

        GridLayout mainGridLayout = new GridLayout(1, 4);
        mainGridLayout.setSizeFull();
        mainGridLayout.setMargin(true);

        ContainerLetztenBewerbungen containerBewerbungen  = ContainerLetztenBewerbungen.getInstance();
        containerBewerbungen.loadByStellenAnzeigeID("Alle",stellenanzeige.getId());
        Bewerbungen<BewerbungDTO> bewerbungen = new Bewerbungen(containerBewerbungen,"Unternehmen");
        bewerbungen.setHeightMode(HeightMode.UNDEFINED);

        mainGridLayout.addComponent(bewerbungen,0,1);



        this.setContent(mainGridLayout);


    }

}