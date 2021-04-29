
package SAOutsideHall;

import Common.STCustomer;
import FIFO.FIFO;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Shared area for the outside hall.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class SAOutsideHall implements IOutsideHall_Manager,
                                      IOutsideHall_Customer,
                                      IOutsideHall_Control {
    
    /** Reentrant Lock for synchronization */
    private final ReentrantLock rl;
    /** FIFO for customers */
    private final FIFO fifo;
    /** flag indicating that the simulation has stopped */
    private boolean stop;
    /** flag indicating that the simulation has ended */
    private boolean end;
    
    /**
     * Shared area outside hall instantiation.
     * @param maxCustomers outside hall size
     */
    public SAOutsideHall( int maxCustomers ) {
        this.fifo = new FIFO(maxCustomers, true);  
        rl = new ReentrantLock(true);
        stop = false;
        end = false;
    }

    /**
     * Customer enters the outside hall and waits for authorization.
     * @param customerId customer id
     * @return ENTRANCE_HALL, the next state of the customer
     */
    @Override
    public STCustomer enter(int customerId) {
        fifo.in(customerId);
        try{
            rl.lock();
            if(stop)
                return STCustomer.STOP;
            if(end)
                return STCustomer.END;
        } finally{
            rl.unlock();
        }
        return STCustomer.ENTRANCE_HALL;
    }
    /**
     * Accept the next customer waiting in the FIFO.
     */
    @Override
    public void accept() {
        fifo.out();
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
