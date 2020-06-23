package org.bonn.se.model.dao;

import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrtDAO extends AbstractDAO{

    private static OrtDAO instance;

    public static OrtDAO getInstance() {
        return instance == null ? instance = new OrtDAO() : instance;
    }

    public List<String> getOrt() throws DatabaseException, SQLException {

        ResultSet set = null;
        List<String> liste = new ArrayList<>();

        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT * FROM lacasa.tab_orte order by ort");


            while (true) {
                assert set != null;
                if (!set.next()) break;
                liste.add(set.getString(1)+", "+set.getString(2));

            }
        }catch (SQLException | DatabaseException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            assert set != null;
            set.close();
            JDBCConnection.getInstance().closeConnection();
        }

        return liste;
    }

    public List<String> getBund() throws DatabaseException, SQLException {

        ResultSet set = null;
        List<String> liste = new ArrayList<>();

        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT bundesland FROM lacasa.tab_orte order by bundesland");


            while (true) {
                assert set != null;
                if (!set.next()) break;
                liste.add(set.getString(1));

            }
        }catch (SQLException  throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            assert set != null;
            set.close();
            JDBCConnection.getInstance().closeConnection();
        }

        return liste;
    }

}
