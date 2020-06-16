package org.bonn.se.gui.component;

import com.vaadin.ui.*;
import org.bonn.se.gui.window.StellenanzeigeWindow;
import org.bonn.se.model.dao.UserDAO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.ContainerNeuigkeiten;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.ImageConverter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

public class Neugkeiten extends GridLayout {
    Image Ulogo;

    public Neugkeiten(ContainerNeuigkeiten container){
       super(1, container.getAnzahl()==0 ? 1:container.getAnzahl() );
      // this.setMargin(true);

       if(container.getAnzahl()> 0) {
           for (int i = 0; i < container.getAnzahl(); i++) {
               StellenanzeigeDTO sa = container.getAnzeige(i);
               GridLayout glayout = new GridLayout(2, 1);
               //glayout.setMargin(true);
              // glayout.addStyleName("whiteBackground");


               Unternehmen unternehmen = null;
               try {
                   unternehmen = UserDAO.getInstance().getUnternehmenByStellAnz(sa);
               } catch (DatabaseException e) {
                   e.printStackTrace();
               }

               TextArea txArea = new TextArea();
               txArea.setWidth("600px");
               txArea.setHeight("170px");
               String day ="";
               long div = Period.between(sa.getZeitstempel().toLocalDate(),LocalDate.now()).getDays();
               if(div == 0){
                   day = "Heute";
               }else if( div == 1){
                   day = "1 Tag";
               }else if (div > 1 & div <= 30){
                   day = div +" Tagen";
               }else{
                   day = " mehr als 30 Tagen";
               }
               txArea.setValue(sa.getTitel() + "\n \n" +
                               sa.getBeschreibung().substring(0, sa.getBeschreibung().length() > 60 ?60: sa.getBeschreibung().length()) + "...\n" +
                               "Bereich: " +sa.getSuchbegriff() + "\n" +
                               "in: " + sa.getStandort() + " - "+sa.getBundesland()+"\n" +
                               "Firma: " + sa.getFirmenname()+ "\n" +
                               "Online seit "+ day
                              );
               txArea.setReadOnly(true);
               if(unternehmen.getLogo() != null) {
                   Ulogo= ImageConverter.convertImagetoMenu(unternehmen.getLogo());
               }
              // Ulogo.setHeight("130px");
               Ulogo.addStyleName("whiteBackground");
               glayout.addComponent(txArea,1,0,1,0);
              // glayout.addComponent(Ulogo,0,0,0,0);

               glayout.setComponentAlignment(txArea, Alignment.BOTTOM_CENTER);
              // glayout.setComponentAlignment(Ulogo, Alignment.MIDDLE_CENTER);
               glayout.setId(Integer.toString(sa.getId()));

               glayout.addLayoutClickListener(event -> {

                   Unternehmen unternehmen_data = null;
                   try {
                       unternehmen_data = UserDAO.getInstance().getUnternehmenByStellAnz(sa);
                   } catch (DatabaseException e) {
                       e.printStackTrace();
                   }
                   StellenanzeigeWindow stellenanzeigeWindow = null;
                   try {
                       stellenanzeigeWindow = new StellenanzeigeWindow(sa,unternehmen_data);
                   } catch (DatabaseException e) {
                       e.printStackTrace();
                   } catch (SQLException throwables) {
                       throwables.printStackTrace();
                   }
                   UI.getCurrent().addWindow(stellenanzeigeWindow);

               });
              
               
               this.addComponent(glayout, 0, i, 0, i);
               this.setComponentAlignment(glayout, Alignment.BOTTOM_CENTER);
              // this.addStyleName("whiteBackground");
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
