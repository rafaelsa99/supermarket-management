
package Communication;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Criar cliente para enviar comandos para o OCC.
 * @author omp
 */
public class CClient {
    private final String hostName;
    private final int portNumber;
    private Socket echoSocket;

    public CClient(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }
    
    public boolean openServer() {
        try {
            Socket echoSocket = new Socket(this.hostName, this.portNumber);
            echoSocket.close();
            return true;
        } catch(IOException e){
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            return false;
        }
    }
    
    public void sendMessage(String message){
       try (
            Socket kkSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
        ) {
           out.print(message);
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
        }
    }
}
