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
        List<String> suchbegriffList = new ArrayList<>();

        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT suchbegriff FROM lacasa.tab_suchbegriff ");
            while (true) {
                assert set != null;
                if (!set.next()) break;

                suchbegriffList.add(set.getString(1));
            }

        }catch (SQLException | DatabaseException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
         }finally {
            assert set != null;
            try {
                set.close();
            } catch (SQLException throwables) {
                Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE,null,throwables);
            }
            try {
                JDBCConnection.getInstance().closeConnection();
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
        }

        return suchbegriffList;
    }


    public List<String> getAbschluss() throws DatabaseException, SQLException {

        ResultSet set = null;
        List<String> listeAbschluss = new ArrayList<>();
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT * FROM lacasa.tab_hoester_abschluss");
            while (true) {
                assert set != null;
                if (!set.next()) break;
                listeAbschluss.add(set.getString(1));
            }
        }catch (SQLException  throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            assert set != null;
            set.close();
            JDBCConnection.getInstance().closeConnection();
        }
        return listeAbschluss;
    }

    public List<String> getSpracheNiveau() throws DatabaseException, SQLException {

        ResultSet set = null;
        List<String> listeSpracheNiveau = new ArrayList<>();
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT * FROM lacasa.tab_niveau_sprache");
            while (true) {
                assert set != null;
                if (!set.next()) break;
                listeSpracheNiveau.add(set.getString(1));
            }
        }catch (SQLException  throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            assert set != null;
            set.close();
            JDBCConnection.getInstance().closeConnection();
        }
        return listeSpracheNiveau;
    }

    public List<String> getITNiveau() throws DatabaseException, SQLException {

        ResultSet set = null;
        List<String> listeITNiveau = new ArrayList<>();
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT * FROM lacasa.tab_niveau_it");
            while (true) {
                assert set != null;
                if (!set.next()) break;
                listeITNiveau.add(set.getString(1));
            }
        }catch (SQLException  throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            assert set != null;
            set.close();
            JDBCConnection.getInstance().closeConnection();
        }
        return listeITNiveau;
    }

    public List<String> getEinstellungsart() throws DatabaseException, SQLException {

        ResultSet set = null;
        List<String> listeEinstellungsart = new ArrayList<>();
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT * FROM lacasa.tab_art");
            while (true) {
                assert set != null;
                if (!set.next()) break;
                listeEinstellungsart.add(set.getString(1));
            }
        }catch (SQLException  throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            assert set != null;
            set.close();
            JDBCConnection.getInstance().closeConnection();
        }
        return listeEinstellungsart;
    }

    public List<String> getBranche() throws SQLException, DatabaseException {

        ResultSet set = null;
        List<String> listeBranche = new ArrayList<>();

        Statement statement = JDBCConnection.getInstance().getStatement();
        try {
            set = statement.executeQuery("SELECT name FROM lacasa.tab_branche ");
            while (true) {
                assert set != null;
                if (!set.next()) break;
                listeBranche.add(set.getString(1));
            }
        } catch (SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            assert set != null;
            set.close();

            JDBCConnection.getInstance().closeConnection();
        }
        return listeBranche;

    }


}