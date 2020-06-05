package org.bonn.se.model.objects.dto;

public class PassChangeRequest {
    private String newPass = null;
    private String email = null;

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }




}
