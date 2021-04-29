
package SACustomer;

import Common.STCustomer;
import Communication.CClient;
import List.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Shared area for the customer.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class SACustomer implements ICustomer_Customer,
                               ICustomer_Control {
    /** Reentrant Lock for synchronization */
    private final ReentrantLock rl;
    /** array of conditions for the customers wait to be called for the simulation */
    private final Condition[] stay;
    /** condition for control wait to all customers be on idle */
    private final Condition setEmpty;
    /** condition for control wait for a customer to leave the SA */
    private final Condition customerLeaving;
    /** List for the customers ordered by id */
    private final List list;
    /** array of flags indicating if a customer can leave */
    private final boolean[] leave;
    /** flag indicating that the simulation has ended */
    private boolean end;
    /** flag indicating that the simulation is active */
    private boolean simulationActive;
    /** Communication Client */
    private final CClient cClient;
    
    /**
     * Shared area customer instantiation
     * @param maxCustomers total customers
     * @param cc communication client
     */
    public SACustomer(int maxCustomers, CClient cc) {
        rl = new ReentrantLock(true);
        list = new List(maxCustomers, true);
        setEmpty = rl.newCondition();
        stay = new Condition[maxCustomers];
        customerLeaving = rl.newCondition();
        leave = new boolean[maxCustomers];
        for ( int i = 0; i < maxCustomers; i++ ) {
            stay[ i ] = rl.newCondition();
            leave[ i ] = false;
        }
        end = false;
        simulationActive = false;
        cClient = cc;
    }
    
    /**
     * Customers wait to be called for the shopping simulation,
     * @param customerId customer id
     * @return OUTSIDE_HALL, the next state of the customer
     */
    @Override
    public STCustomer idle( int customerId ) {
        STCustomer stCustomer = STCustomer.IDLE;
        try{
            rl.lock();
            list.in(customerId); 
            if(list.getCount() == stay.length){
                setEmpty.signal();
                if(simulationActive){
                    cClient.sendMessage("ED");
                    simulationActive = false;
                }
            }
            while(!leave[customerId]) // Loop waiting for the authorization to start the simulation
                stay[customerId].await();
            if(end)
                return STCustomer.END;
            leave[customerId] = false;
            customerLeaving.signal(); // Notify that it has left the list
            stCustomer = STCustomer.OUTSIDE_HALL;
        } catch(InterruptedException ex){
            System.err.println(ex.toString());
        } finally{
            rl.unlock();
        }
        return stCustomer;
    }
    /**
     * Start the simulation.
     * @param nCustomers number of customers to the simulation
     */
    @Override
    public void start( int nCustomers ) {
        try{
            rl.lock();
            while(list.getCount()!= stay.length) // Check if all customers are in idle
                setEmpty.await();
            simulationActive = true;
            for (int i = 0; i < nCustomers; i++){
                int customerIdToLeave = list.outFirst(); // Remove the customer with lower ID
                leave[customerIdToLeave] = true;
                stay[customerIdToLeave].signal(); // Notify customers that the one with the lower ID can leave1
                while(leave[customerIdToLeave])
                    customerLeaving.await(); // Wait for customer to leave the list
            }
        } catch(InterruptedException ex){
            System.err.println(ex.toString());
        } finally{
            rl.unlock();
        }
    }
    /**
     * End the simulation.
     */
    @Override
    public void end() {
        try{
            rl.lock();
            end = true;
            for (int i = 0; i < stay.length; i++){
                leave[i] = true;
                stay[i].signal();
            }
        } finally{
            rl.unlock();
        }    
    }
}
