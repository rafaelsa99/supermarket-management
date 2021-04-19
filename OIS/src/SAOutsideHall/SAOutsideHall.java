
package SAOutsideHall;

import Common.STCustomer;
import FIFO.FIFO;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class SAOutsideHall implements IOutsideHall_Manager,
                                      IOutsideHall_Customer,
                                      IOutsideHall_Control {
   
    ReentrantLock rl;
    Condition authorization;
    private final FIFO fifo;
    boolean isAuthorized;
    boolean isSuspended;
    private boolean stop;
    private boolean end;
    
    public SAOutsideHall( int maxCustomers ) {
        this.fifo = new FIFO(maxCustomers);  
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
        } catch (Exception ex){}
        finally{
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
        } catch(Exception ex){}
        finally{
            rl.unlock();
        }
        fifo.removeAll();
    }

    @Override
    public void end() {
        try{
            rl.lock();
            end = true;
        } catch(Exception ex){}
        finally{
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
        } catch(Exception ex){}
        finally{
            rl.unlock();
        }
    }
}
