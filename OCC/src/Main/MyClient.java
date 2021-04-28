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
        int portNumber = 9999;
        
        try (
            Socket kkSocket = new Socket(hostName,portNumber);
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(kkSocket.getInputStream()));
        ) {
            BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;

            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                if (fromServer.equals("Bye."))
                    break;
                
                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }
            }
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
