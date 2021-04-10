
package SAOutsideHall;

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
    FIFO fifo;
    boolean isAuthorized;
    boolean isSuspended;

    public SAOutsideHall( int maxCustomers ) {
        this.fifo = new FIFO(maxCustomers);   
    }

    @Override
    public void enter(int customerId) {
        fifo.in(customerId);
    }

    @Override
    public void accept() {
        fifo.out();
    }

    @Override
    public void suspend() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resume() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
