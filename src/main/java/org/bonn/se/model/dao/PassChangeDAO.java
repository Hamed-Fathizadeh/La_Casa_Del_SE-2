package org.bonn.se.model.dao;

import org.bonn.se.model.objects.dto.PassChangeRequest;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PassChangeDAO extends AbstractDAO{
    public static PassChangeDAO dao = null;

    public static PassChangeDAO getInstance(){
        if(dao == null){
            dao = new PassChangeDAO();
        }
        return dao;
    }

    public boolean changePass(PassChangeRequest dto){
        Statement statement = this.getStatement();
        try{
            statement.executeUpdate("update lacasa.tab_user set passwort = '" +dto.getNewPass()+ "' where upper(email) = '" +dto.getEmail().toUpperCase()+ "'");
            return true;
        }catch(SQLException ex){
            Logger.getLogger(PassChangeDAO.class.getName()).log(Level.SEVERE,null,ex);
            return false;
        }finally {
            try {
                JDBCConnection.getInstance().closeConnection();
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
        }
    }
}
