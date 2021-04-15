
package SACashier;

import Common.STCashier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class SACashier implements ICashier_Cashier,
                                  ICashier_Customer,
                                  ICashier_Control {
    
    ReentrantLock rl;
    Condition idle;
    int numCustomersPaymentHall;
    int emptySpacesPaymentHall;
    boolean paymentHallHasEmptySpace;
    boolean isSuspended;

    public SACashier(int sizePaymentHall) {
        this.rl = new ReentrantLock(true);
        this.idle = rl.newCondition();
        this.numCustomersPaymentHall = 0;
        this.emptySpacesPaymentHall = sizePaymentHall;
        this.paymentHallHasEmptySpace = true;
        this.isSuspended = false;
    }

    @Override
    public STCashier idle() {
        STCashier stCashier = STCashier.IDLE;
        try{
            rl.lock();
            while((!paymentHallHasEmptySpace || numCustomersPaymentHall == 0) || isSuspended)
                idle.await();
            if(paymentHallHasEmptySpace && numCustomersPaymentHall > 0){
                emptySpacesPaymentHall -= 1;
                if(emptySpacesPaymentHall == 0)
                    paymentHallHasEmptySpace = false;
                numCustomersPaymentHall -= 1;
                stCashier = STCashier.PAYMENT_HALL;
            }
        } catch(Exception ex){}
        finally{
            rl.unlock();
        }
        return stCashier;
    }

    @Override
    public void paymentHall_customerIn() {
        try{
            rl.lock();
            numCustomersPaymentHall += 1;
            idle.signal();
        } catch(Exception ex){}
        finally{
            rl.unlock();
        }
    }

    @Override
    public void paymentHall_freeSlot() {
        try{
            rl.lock();
            paymentHallHasEmptySpace = true;
            emptySpacesPaymentHall += 1; // Update number of available spaces
            idle.signal();
        } catch(Exception ex){}
        finally{
            rl.unlock();
        }
    }
    
    @Override
    public void start() {
        try{
            rl.lock();
            idle.signal();
        } catch(Exception ex){}
        finally{
            rl.unlock();
        }
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
            idle.signal();
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
