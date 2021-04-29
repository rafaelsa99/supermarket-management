
package SAManager;

/**
 * Interface of the control for the SA manager.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public interface IManager_Control {
    /**
     * Start the simulation.
     * @param nCustomers number of customers of the simulation
     */
    public void start(int nCustomers);
    /**
     * Suspend the simulation.
     */
    public void suspend();
    /**
     * Resume the simulation.
     */
    public void resume();
    /**
     * Stop the simulation.
     */
    public void stop();
    /**
     * End the simulation.
     */
    public void end();
    /**
     * Set the operation mode to auto.
     * @param timeout operation timeout
     */
    public void auto(int timeout);
    /**
     * Set the operation mode to manual.
     */
    public void manual();
    /**
     * Authorization to accept the next customer on manual.
     */
    public void step();
}
