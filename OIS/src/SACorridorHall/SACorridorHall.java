
package SACorridorHall;

import Common.STCustomer;
import FIFO.FIFO;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class SACorridorHall implements ICorridorHall_Control,
                                       ICorridorHall_Customer {
    
    private final ReentrantLock rl;
    private final FIFO fifo;
    private final STCustomer corridorNumber; // Number of the corridor to which the corridor hall is associated
    private boolean emptySpaceCorridor; // Flag to indicate if there is space in the Corridor
    private int emptySpacesCorridor; // Number of spaces available in the Corridor
    private boolean stop;
    private boolean end;
    private final int sizeCorridor;
    
    public SACorridorHall(int maxCostumers, STCustomer corridorNumber, int sizeCorridor) {
        this.fifo = new FIFO(maxCostumers);
        this.corridorNumber = corridorNumber;
        this.rl = new ReentrantLock(true);
        this.sizeCorridor = sizeCorridor;
        this.emptySpaceCorridor = true;
        this.emptySpacesCorridor = sizeCorridor;
        this.stop = false;
        this.end = false;
    }
    
    @Override
    public STCustomer enter(int customerId) {
        
        try{
            rl.lock();
            if(!emptySpaceCorridor){ //If there is no space in the corridor, wait in FIFO
                rl.unlock();
                this.fifo.in(customerId);
                rl.lock();
            }
            emptySpacesCorridor -= 1; // Updates the available space in the corridor
            if(emptySpacesCorridor == 0) // Check if the corridor has become full
                emptySpaceCorridor = false;
            if(stop)
                return STCustomer.STOP;
            if(end)
                return STCustomer.END;
        } finally{
            rl.unlock();
        }
        return corridorNumber;
    }

    @Override
    public void freeSlot() {
        try{
            rl.lock();
            emptySpacesCorridor += 1; // Updates the available space in the corridor
            emptySpaceCorridor = true; // Updates the flag
            if(emptySpacesCorridor > 1 || fifo.isEmpty()) // If there was already space in the corridor, doesn't take anyone out of the FIFO
                return;
        } finally{
            rl.unlock();
        }
        this.fifo.out(); // Call the next customer to enter in the corridor
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
            this.emptySpaceCorridor = true;
            this.emptySpacesCorridor = sizeCorridor;
            this.stop = false;
            fifo.resetFIFO();
        } finally{
            rl.unlock();
        }
    }
}
