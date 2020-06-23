package org.bonn.se.services.db.exception;

public class DatabaseException extends Exception {
    private final String reason;

    public DatabaseException(String reason) {
        this.reason=reason;
    }

    public String getReason() {
        return reason;
    }

// --Commented out by Inspection START (22.06.20, 23:33):
//    public void setReason(String reason) {
//        this.reason = reason;
//    }
// --Commented out by Inspection STOP (22.06.20, 23:33)
}
