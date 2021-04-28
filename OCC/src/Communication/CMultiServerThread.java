/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication;


import Main.*;
import java.net.*;
import java.io.*;

public class CMultiServerThread extends Thread {
    private Socket socket = null;

    public CMultiServerThread(Socket socket) {
        super("CMultiServerThread");
        this.socket = socket;
    }
    
    public void run() {

        try (
            BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream()));
        ) {
            String inputLine;
            ProcessProtocol kkp = new ProcessProtocol();
            while ((inputLine = in.readLine()) != null)
                kkp.processInput(inputLine);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}