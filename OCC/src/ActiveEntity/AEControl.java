

package ActiveEntity;

import Idle.IIdle_Control;
import java.net.Socket;

/**
 * Esta entidade é responsável por fazer executar os comandos originados no OCC
 * tais como start, stop, end, etc....
 * 
 * @author omp
 */
public class AEControl extends Thread {

    private final IIdle_Control idle;
    
    public AEControl( IIdle_Control idle /* mais áreas partilhadas */ ) {
        this.idle = idle;
    }
    public void start( int nCustomers, Socket socket ) {
        idle.start( nCustomers );
    }
    public void end() {
        // terminar Customers em idle
        idle.end();
        // terminar restantes Customers e outras AE
    }
    // mais comandos 
    
    
    @Override
    public void run() {
        // ver qual a msg recebida, executar comando e responder
    }
}
