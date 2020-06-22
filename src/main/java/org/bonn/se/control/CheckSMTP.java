package org.bonn.se.control;


import java.io.IOException;
import java.net.InetAddress;

public class CheckSMTP {


    public static void main(String[] args) throws IOException {
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

}



