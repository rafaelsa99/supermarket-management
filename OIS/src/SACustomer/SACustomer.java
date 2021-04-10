
package SACustomer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class SACustomer implements ICustomer_Customer,
                               ICustomer_Control {

    ReentrantLock rl;
    Condition simulation;
    boolean isOnSimulation;
    
    public SACustomer(int maxCustomers) {
    }
    @Override
    public void idle( int customerId ) {
    }
    @Override
    public void start( int nCustmers ) {
    }
    @Override
    public void end() {   
    }
   
}
