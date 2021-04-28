/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

/**
 *
 * @author luisc
 */
import java.io.*;
import java.net.*;

public class MyClient {

    public static void main(String[] args) {
//        try {
//            Socket s = new Socket("localhost", 6669);
//            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
//            dout.writeUTF("MA|ENTRANCE_HALL");
//            dout.flush();
//            dout.close();
//            s.close();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        
        String hostName = "localhost";
        int portNumber = 6669;
        
       try (
            Socket kkSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
        ) {
           out.print("OLAAAA");
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
    }
}
