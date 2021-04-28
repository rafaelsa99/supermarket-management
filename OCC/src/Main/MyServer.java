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
import Communication.CMultiServerThread;
import java.io.*;
import java.net.*;

public class MyServer extends Thread {

    private Socket socket = null;
        
    public static void main(String[] args) {

//    if (args.length != 1) {
//        System.err.println("Usage: java KKMultiServer <port number>");
//        System.exit(1);
//    }

        //int portNumber = Integer.parseInt(args[0]);
        int portNumber = 6669;
        boolean listening = true;
        
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) { 
            while (listening) {
	            new CMultiServerThread(serverSocket.accept()).start();
	        }
	    } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }       
}
