
package SAPaymentHall;

import FIFO.FIFO;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class SAPaymentHall implements IPaymentHall_Cashier, 
                                      IPaymentHall_Control, 
                                      IPaymentHall_Customer {
    
    ReentrantLock rl;
    Condition authorization;
    FIFO fifo;
    boolean isAuthorized;
    boolean isSuspended;

    public SAPaymentHall(int maxCustomers) {
        this.fifo = new FIFO(maxCustomers);
    }

    @Override
    public void accept() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    @Override
    public void enter(int costumerId) {
        this.fifo.in(costumerId);
    }
    
}
