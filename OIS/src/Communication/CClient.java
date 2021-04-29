
package Communication;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Client Object, Used to send messages
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class CClient {
    private final String hostName;
    private final int portNumber;
    private Socket echoSocket;

    /**
    * CClient Constructor
    * @param hostName Host
    * @param portNumber Port Number
    */
    public CClient(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }
    /**
    * Open Connection with Socket Server
    */
    public boolean openServer() {
        try {
            Socket echoSocket = new Socket(this.hostName, this.portNumber);
            echoSocket.close();
            return true;
        } catch(IOException e){
            System.out.println("Couldn't get I/O for the connection to " +
                hostName);
            return false;
        }
    }
    /**
    * Responsable by send messages to Server
    * @param message Message to be sended
    */
    public void sendMessage(String message){
       try (
            Socket kkSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
        ) {
           out.print(message);
        } catch (UnknownHostException e) {
            System.out.println("Don't know about host " + hostName);
        } catch (IOException e) {
            System.out.println("Couldn't get I/O for the connection to " +
                hostName);
        }
    }
}
