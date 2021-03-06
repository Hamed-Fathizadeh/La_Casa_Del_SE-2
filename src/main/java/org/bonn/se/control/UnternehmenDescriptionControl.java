package org.bonn.se.control;

import org.bonn.se.gui.ui.MyUI;
import org.bonn.se.model.dao.AbstractDAO;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Roles;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UnternehmenDescriptionControl extends AbstractDAO {


    private static UnternehmenDescriptionControl instance;

    public static UnternehmenDescriptionControl getInstance() {
        if (instance == null){
            instance = new UnternehmenDescriptionControl();
        }
        return instance ;
    }


    public void setDescription () throws DatabaseException {
        String sql = "UPDATE lacasa.tab_unternehmen "
                + "SET description = ? WHERE email = ?";
        PreparedStatement statement = getPreparedStatement(sql);

        try {
            statement.setString(1,((Unternehmen) MyUI.getCurrent().getSession().getAttribute(Roles.UNTERNEHMEN)).getDescription());
            statement.setString(2,((Unternehmen) MyUI.getCurrent().getSession().getAttribute(Roles.UNTERNEHMEN)).getEmail());
            statement.executeUpdate();
    } catch (SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
    } finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }
}
