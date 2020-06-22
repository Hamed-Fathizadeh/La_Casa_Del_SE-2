package org.bonn.se.model.dao;

import com.vaadin.ui.UI;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;

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

    public static List<BewerbungDTO> loadNeueBewerbungen()throws DatabaseException{
        List<BewerbungDTO> liste = new ArrayList<>();
        ResultSet set;
        Unternehmen unternehmen = (Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen);
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("select * from lacasa.view_bewerbung\n" +
                    "where firmenname = '"+unternehmen.getCname()+"' and hauptsitz = '"+unternehmen.getHauptsitz()+"' and status = 9"
            );

        } catch (SQLException | DatabaseException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        }
        try {

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
            JDBCConnection.getInstance().closeConnection();
        }
        return liste;

    }


    public static List<BewerbungDTO> load(String str, String email ) throws DatabaseException {
        List<BewerbungDTO> liste = new ArrayList<>();
        ResultSet set;
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

        } catch (SQLException | DatabaseException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        }
        try {

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
            JDBCConnection.getInstance().closeConnection();
        }
        return liste;
    }


    public static List<BewerbungDTO> loadByStellenAnzeigeID(String str, int saID) throws DatabaseException {
        List<BewerbungDTO> liste = new ArrayList<>();
        ResultSet set;
        Student student = (Student) UI.getCurrent().getSession().getAttribute(Roles.Student);
        try {
                    Statement statement = JDBCConnection.getInstance().getStatement();
                if(str.equals("Alle")) {
                    set = statement.executeQuery("select * from lacasa.view_bewerbung\n" +
                            " where s_anzeige_id = " + saID + "and status != 3"+
                            " order by datum desc");
                }else if(str.equals("Markiert")){
                    set = statement.executeQuery("select * from lacasa.view_bewerbung\n" +
                            " where s_anzeige_id = " + saID + " and status != 3 "+ " and markiert = true"+
                            " order by datum desc");
                }
                else if(str.equals("Zusage")){
                    set = statement.executeQuery("select * from lacasa.view_bewerbung\n" +
                            " where s_anzeige_id = " + saID + " and status = 2 "+
                            " order by datum desc");
                }else if(str.equals("Abgelehnt")){
                    set = statement.executeQuery("select * from lacasa.view_bewerbung\n" +
                            " where s_anzeige_id = " + saID + " and status = 3 "+
                            " order by datum desc");
                }else{
                    set = statement.executeQuery("select * from lacasa.view_bewerbung\n" +
                            " where s_anzeige_id = " + saID + "and status != 3"+
                            " order by datum desc");
                }

        } catch (SQLException | DatabaseException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        }
        try {

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
            JDBCConnection.getInstance().closeConnection();
        }
        return liste;
    }

}
