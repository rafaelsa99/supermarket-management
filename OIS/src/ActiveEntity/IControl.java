
package ActiveEntity;

/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public interface IControl {
    /**
    * Start a simulation based on the parameters received by the OCC
    * @param nCustomers The number of Costumers to be used in the simulation
     * @param movementTimeout customer movement timeout
     * @param paymentTimeout customer payment timeout
     * @param operationMode manager operation mode
     * @param operationTimeout manager operation timeout
    */
    public void startSimulation( int nCustomers, int movementTimeout, int paymentTimeout, boolean operationMode, int operationTimeout );
    /**
    * Suspends the Simulation.
    */
    public void suspendSimulation();
    /**
    * Resumes the Simulation.
    */
    public void resumeSimulation();
     /**
    * Stops the Simulation.
    */
    public void stopSimulation();
    /**
    * Ends the Simulation.
    */
    public void endSimulation();
    /**
     * Manager step, if operation mode is manual.
     */
    public void managerStep();
    /**
     * Updates the manager operation mode to auto
     * @param timeout manager timeout
     */
    public void managerAuto(int timeout);
    /**
     * Updates the manager operation mode to manual
     */
    public void managerManual();
}
