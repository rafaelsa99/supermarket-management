
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
            this.echoSocket = new Socket(this.hostName, this.portNumber);
            return true;
        } catch(IOException e){
            System.out.println(e);
            return false;
        }
    }
    
public void sendMessage(String message){
        try (
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
        ) {
            BufferedReader stdIn =
                new BufferedReader(new StringReader(message));
            if (stdIn.readLine() != null) {
                System.out.println("Client: " + stdIn.readLine());
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
    
    /*public void closeServer() {
        try {
            this.echoSocket.close();
        } catch(IOException e){System.out.println(e);}
    }*/
}
