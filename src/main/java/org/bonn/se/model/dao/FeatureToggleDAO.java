package org.bonn.se.model.dao;

import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FeatureToggleDAO {


    private static FeatureToggleDAO instance;

    public static FeatureToggleDAO getInstance() {
        return instance == null ? instance = new FeatureToggleDAO() : instance;
    }

    public boolean featureIsEnabled(String feature) throws DatabaseException {
        ResultSet set;
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

            if (set.next() == false) {
                return false;

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
        return false;
    }
}