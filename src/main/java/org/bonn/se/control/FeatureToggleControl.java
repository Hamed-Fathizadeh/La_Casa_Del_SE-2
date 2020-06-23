package org.bonn.se.control;

import org.bonn.se.model.dao.FeatureToggleDAO;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.SQLException;

public class FeatureToggleControl {


    private static FeatureToggleControl instance;

    public static FeatureToggleControl getInstance() {
        return instance == null ? instance = new FeatureToggleControl() : instance;
    }

   public boolean featureIsEnabled(String feature) {

       try {
           return FeatureToggleDAO.getInstance().featureIsEnabled(feature);
       } catch (DatabaseException | SQLException e) {
           e.printStackTrace();
       }
       return false;
   }
}