package org.bonn.se.model.dao;

import java.sql.ResultSet;
import java.sql.Statement;

public class RegisterDAO extends AbstractDAO{

    private static RegisterDAO instance;

    public static RegisterDAO getInstance() {
        return instance == null ? instance = new RegisterDAO() : instance;
    }

    public void registerUser() {
        Statement statement = this.getStatement();
        ResultSet rs = null;
/*
        try {
            rs =   statement.executeQuery("SELECT * "
                    + "FROM hoteluser.user_to_role "
                    + "WHERE hoteluser.user_to_role.login = \'" + user.getLogin() + "\'");
        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }


        User user = null;

        try {
            while(rs.next()) {
                user = new User();
                role.setBezeichnung(rs.getString(2));
                liste.add(role);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return liste;

*/
    }
}
