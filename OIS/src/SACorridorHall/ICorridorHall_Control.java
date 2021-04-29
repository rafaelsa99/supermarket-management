
package SACorridorHall;

/**
 * Interface of the control for the corridor hall.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public interface ICorridorHall_Control {
    /**
     * Start the simulation.
     */
    public void start();
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
}
