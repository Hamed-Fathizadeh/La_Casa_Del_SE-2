package org.bonn.se.control;

import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FeatureToggleControl {

    public static boolean featureIsEnabled(String feature) throws DatabaseException, SQLException {
        ResultSet set;
        Statement statement = JDBCConnection.getInstance().getStatement();

        set = statement.executeQuery("SELECT lacasa.tab_toggle_configuration.status" +
                " FROM lacasa.tab_toggle_configuration" +
                " WHERE feature_name = '" + feature + "' AND status = TRUE");

        if (set.next() == false) {
            return false;

        } else {
            return true;
        }
    }
}
