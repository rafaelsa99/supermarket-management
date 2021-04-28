
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

    private final ReentrantLock rl;
    private final Condition payment;
    private final Condition suspend;
    private boolean isPayed;
    private boolean isSuspended;
    private int timeoutPayment;
    private boolean stop;
    private boolean end;

    public SAPaymentBox(int timeoutPayment) {
        this.rl = new ReentrantLock(true);
        this.payment = rl.newCondition();
        this.suspend = rl.newCondition();
        this.isPayed = false;
        this.timeoutPayment = timeoutPayment;
        this.isSuspended = false;
        this.stop = false;
        this.end = false;
    }

    @Override
    public void setTimeoutPayment(int timeoutPayment) {
        this.timeoutPayment = timeoutPayment;
    }
    
    @Override
    public STCashier payment() {
        try{
            rl.lock();
            while(!isPayed || isSuspended)
                payment.await();
            isPayed = false;
            if(stop)
                return STCashier.STOP;
            if(end)
                return STCashier.END;
        } catch (InterruptedException ex){
            System.err.println(ex.toString());
        } finally{
            rl.unlock();
        }
        return STCashier.IDLE;
    }

    @Override
    public STCustomer enter(int costumerId) {
        try {
            Thread.sleep(timeoutPayment);
        } catch (InterruptedException ex) {
            System.err.println(ex.toString());
        }
        try{
            rl.lock();
            while(isSuspended)
                suspend.await();
            if(stop)
                return STCustomer.STOP;
            if(end)
                return STCustomer.END;
            isPayed = true;
            payment.signal();
        } catch (InterruptedException ex){
            System.err.println(ex.toString());
        } finally{
            rl.unlock();
        }
        return STCustomer.IDLE;
    }
    
    @Override
    public void suspend() {
        try{
            rl.lock();
            isSuspended = true;
        } finally{
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
        } finally{
            rl.unlock();
        }
    }

    @Override
    public void stop() {
        try{
            rl.lock();
            stop = true;
            isSuspended = false;
            isPayed = true;
            payment.signal();
            suspend.signal();
        } finally{
            rl.unlock();
        }
    }

    @Override
    public void end() {
        try{
            rl.lock();
            end = true;
            isSuspended = false;
            isPayed = true;
            payment.signal();
            suspend.signal();
        } finally{
            rl.unlock();
        }
    }
    
    @Override
    public void start() {
        try{
            rl.lock();
            isPayed = false;
            stop = false;
        } finally{
            rl.unlock();
        }
    }
}
