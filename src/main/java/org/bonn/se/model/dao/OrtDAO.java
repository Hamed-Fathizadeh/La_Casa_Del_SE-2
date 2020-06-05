package org.bonn.se.model.dao;

import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrtDAO extends AbstractDAO{
    public static OrtDAO dao = null;

    private OrtDAO() {

    }
    public static OrtDAO getInstance() {
        if (dao == null) {
            dao = new OrtDAO();
        }
        return dao;
    }

    public List<String> getOrt() {

        ResultSet set = null;

        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT * FROM lacasa.tab_orte order by ort");
        } catch (SQLException | DatabaseException throwables) {
            throwables.printStackTrace();

        }
        List<String> liste = new ArrayList<>();
        try {
            while (true) {
                assert set != null;
                if (!set.next()) break;
                liste.add(set.getString(1)+" - "+set.getString(2));

            }
        }catch (SQLException  throwables) {
            throwables.printStackTrace();
        }

        return liste;
    }

    public List<String> getBund() {

        ResultSet set = null;

        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT bundesland FROM lacasa.tab_orte order by bundesland");
        } catch (SQLException | DatabaseException throwables) {
            throwables.printStackTrace();

        }finally {
            try {
                JDBCConnection.getInstance().closeConnection();
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
        }
        List<String> liste = new ArrayList<>();
        try {
            while (true) {
                assert set != null;
                if (!set.next()) break;
                liste.add(set.getString(1));

            }
        }catch (SQLException  throwables) {
            throwables.printStackTrace();
        }

        return liste;
    }

}
