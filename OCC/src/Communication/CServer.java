
package Communication;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Server to receive messages from the OIS.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class CServer extends Thread{

    /** Port of the server. */
    private final int portNumber;
    /** Server socket. */
    private ServerSocket serverSocket;

    /**
     * Communication server instantiation.
     * @param portNumber Port of the server
     */
    public CServer(int portNumber) {
        this.portNumber = portNumber;
    }
    
    /**
     * Creates the server socket.
     */
    public void openServer() {
        try {
            this.serverSocket = new ServerSocket(this.portNumber);
        } catch(IOException e){System.out.println(e);}
    }
    
    /**
     * Await new clients while server socket is open.
     * For each client launches a new thread.
     */
    public void awaitMessages(){
        try { 
            while (true) {
	        new CMultiServerThread(serverSocket.accept()).start();
	    }
	} catch (IOException e) {}
    }
    
    /**
     * Close the server socket.
     */
    public void closeServer() {
        try {
            this.serverSocket.close();
        } catch(IOException e){System.out.println(e);}
    }  

    /**
     * Life cycle of the server.
     * Await new clients while server socket is open.
     */
    @Override
    public void run() {
        awaitMessages();
    }
}    
