
package SAPaymentHall;

import Common.STCashier;
import Common.STCustomer;
import FIFO.FIFO;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Shared area for the Payment Hall.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class SAPaymentHall implements IPaymentHall_Cashier, 
                                      IPaymentHall_Control, 
                                      IPaymentHall_Customer {
    /** Reentrant Lock for synchronization */
    private final ReentrantLock rl;
    /** FIFO for customers */
    private final FIFO fifo;
    /** flag indicating that the simulation has stopped */
    private boolean stop;
    /** flag indicating that the simulation has ended */
    private boolean end;

    /**
     * Shared area payment hall instantiation.
     * @param maxCustomers size of the payment hall
     */
    public SAPaymentHall(int maxCustomers) {
        this.fifo = new FIFO(maxCustomers);
        rl = new ReentrantLock(true);
        stop = false;
        end = false;
    }

    /**
     * Customer enters on payment hall and waits for authorization.
     * @param costumerId id of the customer
     * @return PAYMENT_BOX, the next state of the customer
     */
    @Override
    public STCustomer enter(int costumerId) {
        this.fifo.in(costumerId);
        try{
            rl.lock();
            if(stop)
                return STCustomer.STOP;
            if(end)
                return STCustomer.END;
        } finally{
            rl.unlock();
        }
        return STCustomer.PAYMENT_BOX;
    }
    /**
     * Accept a customer waiting on the payment hall.
     * @return PAYMENT_BOX, the next state of the cashier
     */
    @Override
    public STCashier accept() {
        this.fifo.out();
        try{
            rl.lock();
            if(stop)
                return STCashier.STOP;
            if(end)
                return STCashier.END;
        } finally{
            rl.unlock();
        }
        return STCashier.PAYMENT_BOX;
    }
    /**
     * Suspend the simulation.
     */
    @Override
    public void suspend() {
        this.fifo.suspend();
    }
    /**
     * Resume the simulation.
     */
    @Override
    public void resume() {
        this.fifo.resume();
    }
    /**
     * Stop the simulation.
     */
    @Override
    public void stop() {
        try{
            rl.lock();
            stop = true;
        } finally{
            rl.unlock();
        }
        fifo.removeAll();
    }
    /**
     * End the simulation.
     */
    @Override
    public void end() {
        try{
            rl.lock();
            end = true;
        } finally{
            rl.unlock();
        }
        fifo.removeAll();
    }
    /**
     * Start the simulation.
     */
    @Override
    public void start() {
        try{
            rl.lock();
            stop = false;
            fifo.resetFIFO();
        } finally{
            rl.unlock();
        }
    }
}
