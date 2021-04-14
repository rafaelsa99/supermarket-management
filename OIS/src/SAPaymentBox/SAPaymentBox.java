
package SAPaymentBox;

import Common.STCashier;
import Common.STCustomer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class SAPaymentBox implements IPaymentBox_Cashier,
                                     IPaymentBox_Control,
                                     IPaymentBox_Customer {

    ReentrantLock rl;
    Condition payment;
    boolean isPayed;
    boolean isSuspended;
    int timeoutPayment;

    public SAPaymentBox(int timeoutPayment) {
        this.rl = new ReentrantLock(true);
        this.payment = rl.newCondition();
        this.isPayed = false;
        this.timeoutPayment = timeoutPayment;
    }
    
    @Override
    public STCashier payment() {
        try{
            rl.lock();
            while(!isPayed)
                payment.await();
            isPayed = false;
        } catch (Exception ex){}
        finally{
            rl.unlock();
        }
        return STCashier.IDLE;
    }

    @Override
    public STCustomer enter(int costumerId) {
        try {
            Thread.sleep(timeoutPayment);
        } catch (InterruptedException ex) {}
        try{
            rl.lock();
            isPayed = true;
            payment.signal();
        } catch (Exception ex){}
        finally{
            rl.unlock();
        }
        return STCustomer.IDLE;
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
