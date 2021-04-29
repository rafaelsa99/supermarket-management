
package SACustomer;

/**
 * Interface of the control to the SA customer.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public interface ICustomer_Control {
    /**
     * Start the simulation.
     * @param nCustomers number of customers to the simulation
     */
    public void start( int nCustomers );
    /**
     * End the simulation.
     */
    public void end();
}
