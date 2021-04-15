
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
    Condition suspend;
    boolean isPayed;
    boolean isSuspended;
    int timeoutPayment;

    public SAPaymentBox(int timeoutPayment) {
        this.rl = new ReentrantLock(true);
        this.payment = rl.newCondition();
        this.suspend = rl.newCondition();
        this.isPayed = false;
        this.timeoutPayment = timeoutPayment;
        this.isSuspended = false;
    }
    
    @Override
    public STCashier payment() {
        try{
            rl.lock();
            while(!isPayed || isSuspended)
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
            while(isSuspended)
                suspend.await();
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
        try{
            rl.lock();
            isSuspended = true;
        } catch(Exception ex){}
        finally{
            rl.unlock();
        }
    }

    @Override
    public void resume() {
        try{
            rl.lock();
            isSuspended = false;
            payment.signal();
            suspend.signal();
        } catch(Exception ex){}
        finally{
            rl.unlock();
        }
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
