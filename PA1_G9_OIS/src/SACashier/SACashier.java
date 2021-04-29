
package SACashier;

import Common.STCashier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Cashier Shared Area
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class SACashier implements ICashier_Cashier,
                                  ICashier_Customer,
                                  ICashier_Control {
    
    private final ReentrantLock rl;
    private final Condition idle;
    private int numCustomersPaymentHall;
    private int emptySpacesPaymentHall;
    private boolean paymentHallHasEmptySpace;
    private boolean isSuspended;
    private boolean stop;
    private boolean end;
    private final int sizePaymentHall;

    
    /**
    * Cashier Shared Area Constructor
    */
    public SACashier(int sizePaymentHall) {
        this.rl = new ReentrantLock(true);
        this.idle = rl.newCondition();
        this.sizePaymentHall = sizePaymentHall;
        this.end = false;
        this.numCustomersPaymentHall = 0;
        this.emptySpacesPaymentHall = sizePaymentHall;
        this.paymentHallHasEmptySpace = true;
        this.isSuspended = false;
        this.stop = false;
    }

    /**
    * Cashier Idle State
    */
    @Override
    public STCashier idle() {
        STCashier stCashier = STCashier.IDLE;
        try{
            rl.lock();
            while((!paymentHallHasEmptySpace || numCustomersPaymentHall == 0) || isSuspended || stop)
                idle.await();
            if(end)
                stCashier = STCashier.END;
            else if(paymentHallHasEmptySpace && numCustomersPaymentHall > 0){
                emptySpacesPaymentHall -= 1;
                if(emptySpacesPaymentHall == 0)
                    paymentHallHasEmptySpace = false;
                numCustomersPaymentHall -= 1;
                stCashier = STCashier.PAYMENT_HALL;
            }
        } catch(InterruptedException ex){
            System.err.println(ex.toString());
        } finally{
            rl.unlock();
        }
        return stCashier;
    }

    /**
    * Cashier Let Costumer In
    */
    @Override
    public void paymentHall_customerIn() {
        try{
            rl.lock();
            numCustomersPaymentHall += 1;
            idle.signal();
        } finally{
            rl.unlock();
        }
    }

    /**
    * Payment Hall Release Slot
    */
    @Override
    public void paymentHall_freeSlot() {
        try{
            rl.lock();
            paymentHallHasEmptySpace = true;
            emptySpacesPaymentHall += 1; // Update number of available spaces
            idle.signal();
        } finally{
            rl.unlock();
        }
    }
    
    /**
    * Start Cashier Shared Area
    */
    @Override
    public void start() {
        try{
            rl.lock();
            this.numCustomersPaymentHall = 0;
            this.emptySpacesPaymentHall = sizePaymentHall;
            this.paymentHallHasEmptySpace = true;
            this.isSuspended = false;
            this.stop = false;
            this.idle.signal();
        } finally{
            rl.unlock();
        }
    }

    /**
    * Suspend Cashier Shared Area
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
    * Resume Cashier Shared Area
    */
    @Override
    public void resume() {
        try{
            rl.lock();
            isSuspended = false;
            idle.signal();
        } finally{
            rl.unlock();
        }
    }

    /**
    * Stop Cashier Shared Area
    */
    @Override
    public void stop() {
        try{
            rl.lock();
            stop = true;
            isSuspended = false;
            paymentHallHasEmptySpace = true;
            numCustomersPaymentHall = 1;
            idle.signal();
        } finally{
            rl.unlock();
        }
    }

    /**
    * End Cashier Shared Area
    */
    @Override
    public void end() {
        try{
            rl.lock();
            end = true;
            isSuspended = false;
            paymentHallHasEmptySpace = true;
            numCustomersPaymentHall = 1;
            idle.signal();
        } finally{
            rl.unlock();
        }
    }
}
