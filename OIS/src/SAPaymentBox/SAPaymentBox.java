
package SAPaymentBox;

import Common.STCashier;
import Common.STCustomer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Shared area for the Payment Box.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class SAPaymentBox implements IPaymentBox_Cashier,
                                     IPaymentBox_Control,
                                     IPaymentBox_Customer {
    /** Reentrant Lock for synchronization */
    private final ReentrantLock rl;
    /** Condition for cashier wait for payment */
    private final Condition payment;
    /** Condition for waiting the resume of the simulation */
    private final Condition suspend;
    /** flag indicating if the customer has payed */
    private boolean isPayed;
    /** flag indicating if simulation is suspended */
    private boolean isSuspended;
    /** timeout for the customer to pay */
    private int timeoutPayment;
    /** flag indicating if the simulation has stopped */
    private boolean stop;
    /** flag indicating if the customer has ended */
    private boolean end;

    /**
     * Shared area for the Payment Box.
     * @param timeoutPayment timeout of the customer to pay
     */
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
    /**
     * Set the customer timeout to pay
     * @param timeoutPayment customer timeout
     */
    @Override
    public void setTimeoutPayment(int timeoutPayment) {
        this.timeoutPayment = timeoutPayment;
    }
    /**
     * Cashier waits for the payment to be done.
     * @return IDLE, the next state of the cashier
     */
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
            System.out.println(ex.toString());
        } finally{
            rl.unlock();
        }
        return STCashier.IDLE;
    }
    /**
     * Customer enters on payment hall and simulates payment.
     * @param costumerId customer id
     * @return IDLE, the next state of the customer
     */
    @Override
    public STCustomer enter(int costumerId) {
        try {
            Thread.sleep(timeoutPayment);
        } catch (InterruptedException ex) {
            System.out.println(ex.toString());
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
            System.out.println(ex.toString());
        } finally{
            rl.unlock();
        }
        return STCustomer.IDLE;
    }
    /**
     * Suspend the simulation.
     */
    @Override
    public void suspend() {
        try{
            rl.lock();
            isSuspended = true;
        } finally{
            rl.unlock();
        }
    }
    /**
     * Resume the simulation.
     */
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
    /**
     * Stop the simulation.
     */
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
    /**
     * End of the simulation.
     */
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
    /**
     * Start the simulation.
     */
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
