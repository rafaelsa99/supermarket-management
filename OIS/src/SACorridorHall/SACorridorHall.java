
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
    Condition emptySpace;
    FIFO fifo;
    STCustomer corridorNumber; // Number of the corridor to which the corridor hall is associated
    boolean emptySpaceCorridor; // Flag to indicate if there is space in the Corridor
    boolean isSuspended;
    int emptySpacesCorridor; // Number of spaces available in the Entrance Hall

    public SACorridorHall(int maxCostumers, STCustomer corridorNumber, int sizeCorridor) {
        this.fifo = new FIFO(maxCostumers);
        this.corridorNumber = corridorNumber;
        this.rl = new ReentrantLock(true);
        this.emptySpace = rl.newCondition();
        this.emptySpaceCorridor = true;
        this.emptySpacesCorridor = sizeCorridor;
    }
    
    @Override
    public STCustomer enter(int customerId) {
        
        try{
            rl.lock();
            if(!emptySpaceCorridor) //If there is no space in the corridor, wait in FIFO
                this.fifo.in(customerId);
            emptySpacesCorridor -= 1; // Updates the available space in the corridor
            if(emptySpacesCorridor == 0) // Check if the corridor has become full
                emptySpaceCorridor = false;
        } catch(Exception ex){}
        finally{
            rl.unlock();
        }
        return corridorNumber;
    }

    @Override
    public void freeSlot() {
        try{
            rl.lock();
            emptySpacesCorridor += 1; // Updates the available space in the corridor
            if(emptySpaceCorridor) // If there was already space in the corridor, doesn't take anyone out of the FIFO
                return;
            emptySpaceCorridor = true; // Updates the flag
        } catch(Exception ex){}
        finally{
            rl.unlock();
        }
        this.fifo.out(); // Call the next customer to enter in the corridor
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

}
