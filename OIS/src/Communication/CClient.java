
package Communication;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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
    
    public void sendMessage(String Message){
        try {
            try (DataOutputStream dout = new DataOutputStream(this.echoSocket.getOutputStream())) {
                dout.writeUTF(Message);
                dout.flush();
            }
        } catch(IOException e){System.out.println(e);}
    }
    
    /*public void closeServer() {
        try {
            this.echoSocket.close();
        } catch(IOException e){System.out.println(e);}
    }*/
}
