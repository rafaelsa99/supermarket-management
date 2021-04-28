
package ActiveEntity;

/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public interface IControl {
    public void startSimulation( int nCustomers, int movementTimeout, int paymentTimeout, boolean operationMode, int operationTimeout );
    public void suspendSimulation();
    public void resumeSimulation();
    public void stopSimulation();
    public void endSimulation();
    public void managerStep();
}
