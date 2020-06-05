package org.bonn.se.model.dao;

import java.sql.ResultSet;
import java.sql.Statement;

public class RegisterDAO extends AbstractDAO{

    public static  RegisterDAO dao = null;

    private RegisterDAO() {

    }

    public static RegisterDAO getInstance() {
        if(dao == null){
            dao = new RegisterDAO();
        }
        return dao;
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
