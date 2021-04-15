
package SAOutsideHall;

import Common.STCustomer;
import FIFO.FIFO;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author omp 
 */
public class SAOutsideHall implements IOutsideHall_Manager,
                                      IOutsideHall_Customer,
                                      IOutsideHall_Control {
   
    ReentrantLock rl;
    Condition authorization;
    private final FIFO fifo;
    boolean isAuthorized;
    boolean isSuspended;

    public SAOutsideHall( int maxCustomers ) {
        this.fifo = new FIFO(maxCustomers);   
    }

    @Override
    public STCustomer enter(int customerId) {
        fifo.in(customerId);
        return STCustomer.ENTRANCE_HALL;
    }

    @Override
    public void accept() {
        fifo.out();
    }

    @Override
    public void suspend() {
        this.fifo.suspend();
    }

    @Override
    public void resume() {
        this.fifo.resume();
    }

    @Override
    public void stop() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void end() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
