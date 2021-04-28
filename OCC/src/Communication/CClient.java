
package Communication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Criar cliente para enviar comandos para o OCC.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class CClient {
    private final String hostName;
    private final int portNumber;

    public CClient(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }
    
    public boolean openServer() {
        try {
            System.out.println(portNumber);
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
           BufferedReader stdIn =
                new BufferedReader(new StringReader(message));
            if (stdIn.readLine() != null) {
                out.println(stdIn.readLine());
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
    }
}
