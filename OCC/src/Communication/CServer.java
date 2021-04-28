
package Communication;

import Main.OCC;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Criar Server para receber comandos do OCC.
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class CServer extends Thread{

    private volatile boolean isRunning = true;
    private final int portNumber;
    private ServerSocket serverSocket;

    public CServer(int portNumber) {
        this.portNumber = portNumber;
    }
    
    public void openServer() {
        try {
            this.serverSocket = new ServerSocket(this.portNumber);
        } catch(IOException e){System.out.println(e);}
    }
    
    public void awaitMessages(){
        try {
            Socket s = this.serverSocket.accept();//establishes connection   
            DataInputStream dis=new DataInputStream(s.getInputStream());  
            String  str=(String)dis.readUTF();  
            System.out.println("message= "+str);
        } catch(IOException e){System.out.println(e);}
    }
    
    public void closeServer() {
        try {
            this.serverSocket.close();
        } catch(IOException e){System.out.println(e);}
    }
    
    public void parseMessage(String msg){
        
        String[] aux;
        String type;//Manager = MA, Customer = CT, Cashier = CA
        if(!msg.equals("stop")){
            type = msg.substring(0, 2);
             System.out.println(type);
            switch(type){
                case "MA":
                    OCC.updateState("Manager", msg.substring(3));
                    break;
                case "CT":
                    aux = msg.substring(3).split(":");
                    OCC.updateState("Customer", aux[1], Integer.parseInt(aux[0]));
                    break;
                case "CA":
                    OCC.updateState("Cashier", msg.substring(3));
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
        } catch(IOException e){System.out.println(e);}
    }
    
    @Override
    public void run() {
        resolveMessages();
    }
    
    public void kill() {
       isRunning = false;
   }
   
}    
