package org.bonn.se.control;

import org.bonn.se.model.dao.FeatureToggleDAO;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.SQLException;

public class FeatureToggleControl {


    private static FeatureToggleControl instance;

    public static FeatureToggleControl getInstance() {
        if(instance == null){
            instance = new FeatureToggleControl();
        }
        return instance ;
    }

   public boolean featureIsEnabled(String feature) {

       try {
           return FeatureToggleDAO.getInstance().featureIsEnabled(feature);
       } catch (DatabaseException | SQLException e) {
           e.printStackTrace();
       }
       return false;
   }
   public void setSMTPFeature(boolean status) {
       try {
           FeatureToggleDAO.getInstance().setFeatureSMTP(status);
       } catch (DatabaseException | SQLException e) {
           e.printStackTrace();
       }
   }
}