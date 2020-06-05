package org.bonn.se.model.objects.dto;

import org.bonn.se.model.dao.UserDAO;

import java.io.Serializable;

public class UserDTO implements Serializable {
    private static final long serialVersionUID = -7860243025833384447L;
    private String email = null;
    private String passwort = null;
    private String type = null;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "email='" + email + '\'' +
                ", passwort='" + passwort + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

        public String getType() {
            //Lazy Load
            if(this.type == null){
                setType();
            }
            return this.type;
        }

        private void setType() {

            this.type = UserDAO.getInstance().getUserType(this.getEmail());
        }

    }


