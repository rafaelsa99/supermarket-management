package Communication;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



/**
 * Criar Server para receber comandos do OCC.
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class CServer{

    private volatile boolean isRunning = true;
    private final int portNumber;
    private ServerSocket serverSocket;
    private Socket socket;
    
    public CServer(int portNumber) {
        this.portNumber = portNumber;
    }
    
    public void openServer() {
        try {
            this.serverSocket = new ServerSocket(this.portNumber);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    public void awaitConnection(){
        try {
            socket = this.serverSocket.accept();//establishes connection   
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    public String awaitMessages(){
        String str = null;
        try {
            try (DataInputStream dis = new DataInputStream(socket.getInputStream())) {
                str=(String)dis.readUTF();
            }
        } catch(IOException e){System.out.println(e);}
        return str;
    }
    
    public void closeServer() {
        try {
            this.socket.close();
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
                    break;
                case "CT":
                    break;
                default:
                    System.out.println("Unexpected Type");
                    break;
            }
        }
    }
}    
