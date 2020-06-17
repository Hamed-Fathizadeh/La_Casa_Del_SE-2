package org.bonn.se.control;


import org.bonn.se.model.dao.UserDAO;
import org.bonn.se.services.db.exception.DatabaseException;

public class UserSearchControl {


    private UserSearchControl(){}



    public static UserSearchControl search = null;

    public static UserSearchControl getInstance() {
        if(search == null) {
            search = new UserSearchControl();
        }
        return search;
    }

    public boolean existUser(String email )  {
        try {
            return UserDAO.getInstance().getUserbyEmail(email);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
