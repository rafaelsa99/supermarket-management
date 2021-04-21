/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication;

import Main.OIS;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Function;

/**
 * Criar Server para receber comandos do OCC.
 *
 * @author omp
 */
public class CServer extends Thread{

    private volatile boolean isRunning = true;
    private int portNumber;
    private ServerSocket serverSocket;
    private OIS oisObject;

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
    
    public void parseMessage(String msg){
        
        String[] aux;
        String type;//Manager = MA, Customer = CT, Cashier = CA
        if(!msg.equals("stop")){
            type = msg.substring(0, 2);
             System.out.println(type);
            switch(type){
                case "MA":
                    break;
                case "CT":
                    break;
                default:
                    System.out.println("Unexpected Type");
                    break;
            }
        }
    }

    public void resolveMessages(){
        String msg="";
        try {
            Socket s = this.serverSocket.accept();//establishes connection   
            DataInputStream dis=new DataInputStream(s.getInputStream());
            while(!msg.equals("stop") && isRunning && dis.available() > 0){
                msg = (String)dis.readUTF();
                parseMessage(msg);
                System.out.println("message= " + msg);
            }
        } catch(Exception e){System.out.println(e);}
    }

    public void setOccObject(OIS oisObject) {
        this.oisObject = oisObject;
    }
    
    @Override
    public void run() {
        resolveMessages();
    }
    
    public void kill() {
       isRunning = false;
   }
   
}    
