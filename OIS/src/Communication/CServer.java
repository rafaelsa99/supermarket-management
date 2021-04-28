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
    
    public Socket awaitMessages(){
        Socket s = null;
        try { 
                s = serverSocket.accept();
	    } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
        }
        return s;
    }
    
    public void closeServer() {
        try {
            this.serverSocket.close();
        } catch(IOException e){System.out.println(e);}
    }
}    
