
package SAPaymentBox;

/**
 * Interface of the control for the payment box.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public interface IPaymentBox_Control {
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
    /**
     * Set the customer timeout to pay
     * @param timeoutPayment customer timeout
     */
    public void setTimeoutPayment(int timeoutPayment);
}
