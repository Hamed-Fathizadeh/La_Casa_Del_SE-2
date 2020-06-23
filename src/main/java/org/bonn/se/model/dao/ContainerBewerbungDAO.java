package org.bonn.se.model.dao;

import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContainerBewerbungDAO {

    private static ContainerBewerbungDAO instance;

    public static ContainerBewerbungDAO getInstance() {
        return instance == null ? instance = new ContainerBewerbungDAO() : instance;
    }

// --Commented out by Inspection START (22.06.20, 23:40):
//    public List<BewerbungDTO> loadNeueBewerbungen()throws DatabaseException{
//        List<BewerbungDTO> liste = new ArrayList<>();
//        ResultSet set;
//        Unternehmen unternehmen = (Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen);
//        try {
//            Statement statement = JDBCConnection.getInstance().getStatement();
//            set = statement.executeQuery("select * from lacasa.view_bewerbung\n" +
//                    "where firmenname = '"+unternehmen.getCname()+"' and hauptsitz = '"+unternehmen.getHauptsitz()+"' and status = 9"
//            );
//
//        } catch (SQLException | DatabaseException throwables) {
//            throwables.printStackTrace();
//            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
//        }
//        try {
//
//            while (set.next()) {
//                BewerbungDTO bewerbung = new BewerbungDTO(set.getInt(1),set.getDate(2),set.getString(3),
//                        set.getBytes(4),set.getInt(5),set.getInt(6),
//                        set.getInt(7),set.getString(8), set.getString(9),set.getBytes(10),
//                        set.getString(11),set.getString(12),set.getDouble(22)
//                );
//                liste.add(bewerbung);
//
//
//            }
//
//        } catch (SQLException throwables) {
//            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
//        } finally {
//            JDBCConnection.getInstance().closeConnection();
//        }
//        return liste;
//
//    }
// --Commented out by Inspection STOP (22.06.20, 23:40)


    public List<BewerbungDTO> load(String str, String email ) throws DatabaseException, SQLException {
        List<BewerbungDTO> liste = new ArrayList<>();
        ResultSet set = null;
        try {
              String limit = " ";
              if(str.equals("Alle")) {
              }else{
                  limit = "LIMIT 5";
              }
                 Statement statement = JDBCConnection.getInstance().getStatement();
                 set = statement.executeQuery("select * from lacasa.view_bewerbung \n" +
                                                  "where email ='"+ email +"' and (status = 1 or status = 9) order by datum desc \n" +limit
                                             );


            while (set.next()) {
                BewerbungDTO bewerbung = new BewerbungDTO(set.getInt(1),set.getDate(2),set.getString(3),
                                                          set.getBytes(4),set.getInt(5),set.getInt(6),
                                                          set.getInt(7),set.getString(8), set.getString(9),set.getBytes(10),
                                                          set.getString(11),set.getString(12),set.getDouble(22)
                                                         );
                liste.add(bewerbung);


            }

        } catch (SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            assert set != null;
            set.close();
            JDBCConnection.getInstance().closeConnection();
        }
        return liste;
    }


    public List<BewerbungDTO> loadByStellenAnzeigeID(String str, int saID) throws DatabaseException, SQLException {
        List<BewerbungDTO> liste = new ArrayList<>();
        ResultSet set = null;
        try {
                    Statement statement = JDBCConnection.getInstance().getStatement();
            switch (str) {
                case "Alle":
                    set = statement.executeQuery("select * from lacasa.view_bewerbung\n" +
                            " where s_anzeige_id = " + saID + "and status != 3" +
                            " order by datum desc");
                    break;
                case "Markiert":
                    set = statement.executeQuery("select * from lacasa.view_bewerbung\n" +
                            " where s_anzeige_id = " + saID + " and status != 3 " + " and markiert = true" +
                            " order by datum desc");
                    break;
                case "Zusage":
                    set = statement.executeQuery("select * from lacasa.view_bewerbung\n" +
                            " where s_anzeige_id = " + saID + " and status = 2 " +
                            " order by datum desc");
                    break;
                case "Abgelehnt":
                    set = statement.executeQuery("select * from lacasa.view_bewerbung\n" +
                            " where s_anzeige_id = " + saID + " and status = 3 " +
                            " order by datum desc");
                    break;
                default:
                    set = statement.executeQuery("select * from lacasa.view_bewerbung\n" +
                            " where s_anzeige_id = " + saID + "and status != 3" +
                            " order by datum desc");
                    break;
            }


            while (set.next()) {
                BewerbungDTO bewerbung = new BewerbungDTO(
                                                            set.getInt(1),set.getDate(2),set.getString(3),
                                                            set.getBytes(4),set.getInt(5),set.getInt(6),
                                                            set.getInt(7),set.getString(8), set.getString(9),set.getBytes(10),
                                                            set.getString(11),set.getString(12),set.getDate(13),set.getString(14),
                                                            set.getString(15),set.getString(16),set.getInt(17), set.getBytes(18),
                                                            set.getString(19),set.getString(20),set.getString(21),
                                                            set.getDouble(22),set.getBoolean(23)
                                                        );
                liste.add(bewerbung);


            }

        } catch (SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            assert set != null;
            set.close();
            JDBCConnection.getInstance().closeConnection();
        }
        return liste;
    }

}
