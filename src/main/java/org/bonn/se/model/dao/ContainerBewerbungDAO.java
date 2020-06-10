package org.bonn.se.model.dao;

import com.vaadin.ui.UI;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ContainerBewerbungDAO {

    public static ContainerBewerbungDAO dao = null;

    private ContainerBewerbungDAO() {

    }

    public static ContainerBewerbungDAO getInstance() {
        if (dao == null) {
            dao = new ContainerBewerbungDAO();
        }
        return dao;
    }

    public static List<BewerbungDTO> load(String str) throws DatabaseException {
        List<BewerbungDTO> liste = new ArrayList<>();
        ResultSet set;
        Student student = (Student) UI.getCurrent().getSession().getAttribute(Roles.Student);
        try {
              String limit = " ";
              if(str.equals("Alle")) {
              }else{
                  limit = "LIMIT 5";
              }
                 Statement statement = JDBCConnection.getInstance().getStatement();
                 set = statement.executeQuery("select * from lacasa.view_bewerbung \n" +
                                                  "where email ='"+student.getEmail()+"'\n" +limit
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
                                                          set.getString(11),set.getString(12),set.getDouble(13)
                                                         );
                liste.add(bewerbung);


            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
        return liste;
    }
}
