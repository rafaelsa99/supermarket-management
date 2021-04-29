
package Communication;

import Main.OCC;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Communication client to send commands to the OIS.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class CClient {
    /** Hostname of the OIS. */
    private final String hostName;
    /** Port of the OIS. */
    private final int portNumber;

    /**
     * CClient instantiation.
     * @param hostName Hostname of the OIS
     * @param portNumber Port of the OIS
     */
    public CClient(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }
    
    /**
     * Connect to the OIS server to test the connection.
     * @return true if the connection succeeded. false otherwise.
     */
    public boolean openServer() {
        try {
            Socket echoSocket = new Socket(this.hostName, this.portNumber);
            echoSocket.close();
            return true;
        } catch(IOException e){
            System.out.println("Couldn't get I/O for the connection to " + hostName);
            return false;
        }
    }
    
    /**
     * Send message to OIS.
     * Connection is established and the message is sent.
     * @param message message to be sent
     */
    public void sendMessage(String message){
       try (
            Socket kkSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
        ) {
           out.print(message);
        } catch (UnknownHostException e) {
            System.out.println("Don't know about host " + hostName);
            OCC.endSimulation();
        } catch (IOException e) {
            System.out.println("Couldn't get I/O for the connection to " + hostName);
            OCC.endSimulation();
        }
    }
}
