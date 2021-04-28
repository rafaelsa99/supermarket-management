
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
        boolean listening = true;
        try { 
            while (listening) {
	            new CMultiServerThread(serverSocket.accept()).start();
	        }
	    } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
    
    public void closeServer() {
        try {
            this.serverSocket.close();
        } catch(IOException e){System.out.println(e);}
    }  

    @Override
    public void run() {
        awaitMessages();
    }
    
    
}    
