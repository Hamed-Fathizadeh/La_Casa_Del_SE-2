package org.bonn.se.control;


import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;


public class CheckSMTP {


    public static void main(String args[]) throws IOException {
        InetAddress[] addresses = InetAddress.getAllByName("www.google.com");
        for (InetAddress address : addresses) {
            if (address.isReachable(10000))
            {
                System.out.println("Connected "+ address);
            }
            else
            {
                System.out.println("Failed "+address);
            }
        }
    }
    private static boolean isReachable(String addr, int openPort, int timeOutMillis) {

        try {
            try (Socket soc = new Socket()) {
                soc.connect(new InetSocketAddress(addr, openPort), timeOutMillis);
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
}



