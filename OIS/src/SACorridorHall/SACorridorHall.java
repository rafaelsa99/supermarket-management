
package SACorridorHall;

import Common.STCustomer;
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
    STCustomer corridorNumber;
    boolean emptySpaceCorridor;
    boolean isSuspended;

    public SACorridorHall(int maxCostumers, STCustomer corridorNumber) {
        this.fifo = new FIFO(maxCostumers);
        this.corridorNumber = corridorNumber;
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
    public STCustomer enter(int customerId) {
        this.fifo.in(customerId);
        return corridorNumber;
    }

    @Override
    public void freeSlot() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
