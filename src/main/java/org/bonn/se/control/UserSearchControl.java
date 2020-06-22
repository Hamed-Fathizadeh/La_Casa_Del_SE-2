package org.bonn.se.control;


import org.bonn.se.model.dao.UserDAO;
import org.bonn.se.services.db.exception.DatabaseException;

public class UserSearchControl {


    private static UserSearchControl instance;

    public static UserSearchControl getInstance() {
        return instance == null ? instance = new UserSearchControl() : instance;
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
