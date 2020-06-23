package org.bonn.se.model.dao;

import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FeatureToggleDAO {


    private static FeatureToggleDAO instance;

    public static FeatureToggleDAO getInstance() {
        return instance == null ? instance = new FeatureToggleDAO() : instance;
    }

    public boolean featureIsEnabled(String feature) throws DatabaseException, SQLException {
        ResultSet set = null;
        Statement statement = null;
        try {
            statement = JDBCConnection.getInstance().getStatement();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        try {
            set = statement.executeQuery("SELECT lacasa.tab_toggle_configuration.status" +
                    " FROM lacasa.tab_toggle_configuration" +
                    " WHERE feature_name = '" + feature + "' AND status = TRUE");

            if (!set.next()) {
                return false;

            }
        } catch (SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            assert set != null;
            set.close();
            JDBCConnection.getInstance().closeConnection();
        }
        return true;
    }
}
