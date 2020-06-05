package org.bonn.se.services.util;

public class ControlException extends Exception {
    public ControlException(){
        System.out.println("user exist schon");
    }
}
