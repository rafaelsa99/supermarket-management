/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication;

import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Criar cliente para enviar comandos para o OCC.
 * @author omp
 */
public class CClient {
    private String hostName;
    private int portNumber;
    private Socket echoSocket;

    public CClient(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }
    
    public void openServer() {
        try {
            this.echoSocket = new Socket(this.hostName, this.portNumber);
        } catch(Exception e){System.out.println(e);}
    }
    
    public void sendMessage(String Message){
        try {
            DataOutputStream dout=new DataOutputStream(this.echoSocket.getOutputStream());  
            dout.writeUTF(Message);  
            dout.flush();  
            dout.close();
        } catch(Exception e){System.out.println(e);}
    }
    
    public void closeServer() {
        try {
            this.echoSocket.close();
        } catch(Exception e){System.out.println(e);}
    }
}
