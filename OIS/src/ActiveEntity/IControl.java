
package ActiveEntity;

import java.net.Socket;

/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public interface IControl {
    public void startSimulation(int nCustomers, Socket socket);
    public void suspendSimulation();
    public void resumeSimulation();
    public void stopSimulation();
    public void endSimulation();
}
