/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Criar Server para receber comandos do OCC.
 *
 * @author omp
 */
public class CServer {

    private int portNumber;
    private ServerSocket serverSocket;

    public CServer(int portNumber) {
        this.portNumber = portNumber;
    }
    
    public void openServer() {
        try {
            this.serverSocket = new ServerSocket(this.portNumber);
        } catch(Exception e){System.out.println(e);}
    }
    
    public void awaitMessages(){
        try {
            Socket s = this.serverSocket.accept();//establishes connection   
            DataInputStream dis=new DataInputStream(s.getInputStream());  
            String  str=(String)dis.readUTF();  
            System.out.println("message= "+str);
        } catch(Exception e){System.out.println(e);}
    }
    
    
    public void closeServer() {
        try {
            this.serverSocket.close();
        } catch(Exception e){System.out.println(e);}
    }    

}
