package org.bonn.se.control;

import org.bonn.se.model.dao.UserDAO;
import org.bonn.se.model.objects.dto.RegistrationResult;
import org.bonn.se.model.objects.entitites.User;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.ControlException;

import java.util.ArrayList;

public class RegistrierenControl {
    //container
    final ArrayList<User> liste;

    public RegistrierenControl(ArrayList<User> liste){
        this.liste=liste;
    }
    public boolean DoesExist(User user){
        for (User u:liste){
            if(u.getEmail().equals(user.getEmail())){
                return true;
            }
        }
        return false;
    }
    public void add(User user) throws ControlException {
        if(!DoesExist(user)){
            liste.add(user);
        }
        else
            throw new ControlException();
    }
    public RegistrationResult validation(User user){
        RegistrationResult result= new RegistrationResult();
        if(user.getEmail().isEmpty()){
            result.setResult(false);
            result.setReason("Email Failed");
        }
        else {
            result.setResult(true);
            result.setReason("satisfying");
        }
        return result;
    }

    public static void registerUser(User user){
        try {
            UserDAO.getInstance().registerUser(user);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }

    }





}
