package Communication;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



/**
 * Socket Server responsable by receiveing messages from clients
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class CServer{

    private volatile boolean isRunning = true;
    private final int portNumber;
    private ServerSocket serverSocket;
    
    /**
    * CServer Constructor
    * @param portNumber Port Number
    */
    public CServer(int portNumber) {
        this.portNumber = portNumber;
    }
    
    /**
    * Open Server to receive Connections
    */
    public void openServer() {
        try {
            this.serverSocket = new ServerSocket(this.portNumber);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    /**
    * Await to receive messages
    */
    public Socket awaitMessages(){
        Socket s = null;
        try { 
            s = serverSocket.accept();
	    } catch (IOException e) {
            System.out.println("Could not listen on port " + portNumber);
        }
        return s;
    }
    
    /**
    * Close Socket Server
    */
    public void closeServer() {
        try {
            this.serverSocket.close();
        } catch(IOException e){System.out.println(e);}
    }
}    
