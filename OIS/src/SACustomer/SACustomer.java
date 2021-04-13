
package SACustomer;

import Common.STCustomer;
import java.util.TreeSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class SACustomer implements ICustomer_Customer,
                               ICustomer_Control {

    private final ReentrantLock rl;
    private final Condition[] stay;
    private final Condition setEmpty;
    private final Condition customerLeaving;
    private final TreeSet<Integer> set;
    private boolean[] leave;
    
    public SACustomer(int maxCustomers) {
        rl = new ReentrantLock(true);
        set = new TreeSet<>();
        setEmpty = rl.newCondition();
        stay = new Condition[maxCustomers];
        customerLeaving = rl.newCondition();
        leave = new boolean[maxCustomers];
        for ( int i = 0; i < maxCustomers; i++ ) {
            stay[ i ] = rl.newCondition();
            leave[ i ] = false;
        }
    }
    @Override
    public STCustomer idle( int customerId ) {
        STCustomer stCustomer = STCustomer.IDLE;
        try{
            rl.lock();
            set.add(customerId); // Add customer to set
            if(set.size() == 1) // If it is the only element, it means that the set is no longer empty
                setEmpty.signal();
            while(!leave[customerId]) // Loop waiting for the authorization to start the simulation
                stay[customerId].await();
            leave[customerId] = false;
            customerLeaving.signal(); // Notify that it has left the set
            stCustomer = STCustomer.OUTSIDE_HALL;
        } catch(Exception ex){}
        finally{
            rl.unlock();
        }
        return stCustomer;
    }
    @Override
    public void start( int nCustomers ) {
        try{
            rl.lock();
            while(set.isEmpty()) // If set is empty, then wait
                setEmpty.await();
            for (int i = 0; i < nCustomers; i++){
                int customerIdToLeave = set.pollFirst(); // Remove the customer with lower ID
                leave[customerIdToLeave] = true;
                stay[customerIdToLeave].signal(); // Notify customers that the one with the lower ID can leave1
                while(leave[customerIdToLeave])
                    customerLeaving.await(); // Wait for customer to leave the set
            }
        } catch(Exception ex){}
        finally{
            rl.unlock();
        }
    }
    @Override
    public void end() {   
    }
   
}
