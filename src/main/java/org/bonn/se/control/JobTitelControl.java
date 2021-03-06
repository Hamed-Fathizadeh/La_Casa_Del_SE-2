package org.bonn.se.control;

import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class JobTitelControl {

    private JobTitelControl(){

    }


        public static Collection<String> getJobTitelList () throws DatabaseException, SQLException {
            ResultSet set = null;
            Collection<String> jobtitel = new ArrayList<>();

            try {
                Statement statement = JDBCConnection.getInstance().getStatement();

                set = statement.executeQuery("SELECT * FROM lacasa.tab_suchbegriff");

                while (set.next()) {
                    jobtitel.add(set.getString(1));
                }
                } catch (SQLException | DatabaseException throwables) {
                throwables.printStackTrace();
                throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
            } finally {
                assert set != null;
                set.close();
                JDBCConnection.getInstance().closeConnection();
            }
            return jobtitel;
        }

}
