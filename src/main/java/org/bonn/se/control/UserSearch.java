package org.bonn.se.control;


import org.bonn.se.model.dao.UserDAO;
import org.bonn.se.services.db.exception.DatabaseException;

public class UserSearch {


    private UserSearch(){}



    public static UserSearch search = null;

    public static UserSearch getInstance() {
        if(search == null) {
            search = new UserSearch();
        }
        return search;
    }

    public boolean existUser(String email ) throws DatabaseException {
        return UserDAO.getInstance().getUserbyEmail(email);
    }
}
