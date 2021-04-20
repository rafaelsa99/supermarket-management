
package SAOutsideHall;

import Common.STCustomer;
import FIFO.FIFO;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class SAOutsideHall implements IOutsideHall_Manager,
                                      IOutsideHall_Customer,
                                      IOutsideHall_Control {
   
    private final ReentrantLock rl;
    private final FIFO fifo;
    private boolean stop;
    private boolean end;
    
    public SAOutsideHall( int maxCustomers ) {
        this.fifo = new FIFO(maxCustomers, true);  
        rl = new ReentrantLock(true);
        stop = false;
        end = false;
    }

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

    @Override
    public void accept() {
        fifo.out();
    }

    @Override
    public void suspend() {
        this.fifo.suspend();
    }

    @Override
    public void resume() {
        this.fifo.resume();
    }

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
