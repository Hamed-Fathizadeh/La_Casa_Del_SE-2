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


public class SuchbegriffDAO extends AbstractDAO {

    private static SuchbegriffDAO instance;

    public static SuchbegriffDAO getInstance() {
        return instance == null ? instance = new SuchbegriffDAO() : instance;
    }
    public List<String> getSuchbegriffe() {

        ResultSet set = null;
        List<String> liste = new ArrayList<>();

        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT suchbegriff FROM lacasa.tab_suchbegriff ");


            while (true) {
                assert set != null;
                if (!set.next()) break;
                liste.add(set.getString(1));

            }
        }catch (SQLException | DatabaseException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
         }finally {
            assert set != null;
            try {
                set.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                JDBCConnection.getInstance().closeConnection();
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
        }

        return liste;
    }

}