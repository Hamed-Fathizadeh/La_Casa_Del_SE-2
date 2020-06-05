package org.bonn.se.services.db.exception;

public class DatabaseException extends Exception {
    private String reason;

    public DatabaseException(String reason) {
        this.reason=reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
