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

public class BrancheDAO {

    public static BrancheDAO dao = null;

    public BrancheDAO() {

    }
    public static BrancheDAO getInstance() {
        if (dao == null) {
            dao = new BrancheDAO();
        }
        return dao;
    }

    public List<String> getBranche() {

        ResultSet set = null;

        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT name FROM lacasa.tab_branche ");
        } catch (SQLException | DatabaseException throwables) {
            throwables.printStackTrace();

        }
        List<String> liste = new ArrayList<String>();
        try {
            while (true) {
                assert set != null;
                if (!set.next()) break;
                liste.add(set.getString(1));

            }
        }catch (SQLException  throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        }finally {
            try {
                JDBCConnection.getInstance().closeConnection();
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
        }

        return liste;
    }
}

