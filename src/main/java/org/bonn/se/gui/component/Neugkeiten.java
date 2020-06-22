package org.bonn.se.gui.component;

import com.vaadin.ui.*;
import org.bonn.se.gui.window.StellenanzeigeWindow;
import org.bonn.se.model.dao.UserDAO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.ContainerNeuigkeiten;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.ImageConverter;

import java.time.LocalDate;
import java.time.Period;

public class Neugkeiten extends GridLayout {
    Image uLogo;

    public Neugkeiten(ContainerNeuigkeiten container){
       super(1, container.getAnzahl()==0 ? 1:container.getAnzahl() );

       if(container.getAnzahl()> 0) {
           for (int i = 0; i < container.getAnzahl(); i++) {
               StellenanzeigeDTO sa = container.getAnzeige(i);
               GridLayout glayout = new GridLayout(2, 1);

               Unternehmen unternehmen = null;
               try {
                   UserDAO.getInstance();
                   unternehmen = UserDAO.getUnternehmenByStellAnz(sa);
               } catch (DatabaseException e) {
                   e.printStackTrace();
               }

               TextArea txArea = new TextArea();
               txArea.setWidth("600px");
               txArea.setHeight("170px");
               String day;
               long div = Period.between(sa.getZeitstempel().toLocalDate(),LocalDate.now()).getDays();
               if(div == 0){
                   day = "Heute";
               }else if( div == 1){
                   day = "1 Tag";
               }else if (div > 1 && div <= 30){
                   day = div +" Tagen";
               }else{
                   day = " mehr als 30 Tagen";
               }
               txArea.setValue(sa.getTitel() + "\n \n" +
                               sa.getBeschreibung().substring(0, Math.min(sa.getBeschreibung().length(), 60)) + "...\n" +
                               "Bereich: " +sa.getSuchbegriff() + "\n" +
                               "in: " + sa.getStandort() + " - "+sa.getBundesland()+"\n" +
                               "Firma: " + sa.getFirmenname()+ "\n" +
                               "Online seit "+ day
                              );
               txArea.setReadOnly(true);
               if(unternehmen.getLogo() != null) {
                   uLogo= ImageConverter.convertImagetoMenu(unternehmen.getLogo());
               }

               uLogo.addStyleName("whiteBackground");
               glayout.addComponent(txArea,1,0,1,0);


               glayout.setComponentAlignment(txArea, Alignment.BOTTOM_CENTER);

               glayout.setId(Integer.toString(sa.getId()));

               glayout.addLayoutClickListener(event -> {

                   Unternehmen unternehmenData = null;
                   try {
                       UserDAO.getInstance();
                       unternehmenData = UserDAO.getUnternehmenByStellAnz(sa);
                   } catch (DatabaseException e) {
                       e.printStackTrace();
                   }
                   StellenanzeigeWindow stellenanzeigeWindow;

                       stellenanzeigeWindow = new StellenanzeigeWindow(sa,unternehmenData);

                   UI.getCurrent().addWindow(stellenanzeigeWindow);

               });
              
               
               this.addComponent(glayout, 0, i, 0, i);
               this.setComponentAlignment(glayout, Alignment.BOTTOM_CENTER);
           }
       }else{
           TextArea txArea = new TextArea();
           txArea.setWidth("600px");
           txArea.setHeight("170px");
           txArea.setValue("Es gibt keine Aktuelle Stellenanzeige!");
           txArea.setReadOnly(true);
           this.addComponent(txArea,0,0,0,0);
           this.setComponentAlignment(txArea, Alignment.MIDDLE_CENTER);
       }
   }

}
