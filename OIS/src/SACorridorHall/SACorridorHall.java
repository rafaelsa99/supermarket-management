
package SACorridorHall;

import FIFO.FIFO;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class SACorridorHall implements ICorridorHall_Control,
                                       ICorridorHall_Customer {
    
    ReentrantLock rl;
    Condition emptySpac;
    FIFO fifo;
    boolean emptySpaceCorridor;
    boolean isSuspended;

    public SACorridorHall(int maxCostumers) {
        this.fifo = new FIFO(maxCostumers);
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

    @Override
    public void enter(int customerId) {
        this.fifo.in(customerId);
    }

    @Override
    public void freeSlot() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
