package org.bonn.se.model.dao;

import org.bonn.se.model.objects.dto.PassChangeRequest;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PassChangeDAO extends AbstractDAO{
    private static PassChangeDAO instance;

    public static PassChangeDAO getInstance() {
        return instance == null ? instance = new PassChangeDAO() : instance;
    }

    public boolean changePass(PassChangeRequest dto) throws DatabaseException {
        Statement statement = this.getStatement();
        try{
            statement.executeUpdate("update lacasa.tab_user set passwort = '" +dto.getNewPass()+ "' where upper(email) = '" +dto.getEmail().toUpperCase()+ "'");
            return true;
        }catch(SQLException ex){
            Logger.getLogger(PassChangeDAO.class.getName()).log(Level.SEVERE,null,ex);
            return false;
        }finally {
                JDBCConnection.getInstance().closeConnection();
        }
    }
}
