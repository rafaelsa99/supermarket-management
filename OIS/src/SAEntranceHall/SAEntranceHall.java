
package SAEntranceHall;

import Common.STCustomer;
import Common.STManager;
import FIFO.FIFO;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Shared area for the entrance hall.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class SAEntranceHall implements IEntranceHall_Customer,
                                       IEntranceHall_Manager,
                                       IEntranceHall_Control {
    /** Reentrant Lock for synchronization */
    private final ReentrantLock rl;
    /** FIFO for customers */
    private final FIFO fifo;
    /** flag indicating that the simulation has stopped */
    private boolean stop;
    /** flag indicating that the simulation has ended */
    private boolean end;
    /** corridor hall assigned for the next customer to leave */
    private STCustomer corridorHallAssigned;

    /**
     * Shared area instantiation.
     * @param maxCostumers entrance hall size
     */
    public SAEntranceHall(int maxCostumers) {
        this.fifo = new FIFO(maxCostumers);
        rl = new ReentrantLock(true);
        stop = false;
        end = false;
    }

    /**
     * Customer enters the entrance hall and waits for authorization.
     * @param costumerId customer id
     * @return the CORRIDOR_HALL assigned for the customer
     */
    @Override
    public STCustomer enter(int costumerId) {
        STCustomer stCustomer = STCustomer.IDLE;
        this.fifo.in(costumerId);
        try{
            rl.lock();
            if(stop)
                return STCustomer.STOP;
            if(end)
                return STCustomer.END;
            stCustomer = corridorHallAssigned;
        } finally{
            rl.unlock();
        }
        return stCustomer;
    }
    /**
     * Accept the next customer waiting in the FIFO.
     * @param numCorridor corridor number for the customer
     */
    @Override
    public void accept(STManager numCorridor) { 
        try{
            rl.lock();
            switch(numCorridor){
                case CORRIDOR_HALL_1: 
                    corridorHallAssigned = STCustomer.CORRIDOR_HALL_1; break;
                case CORRIDOR_HALL_2: 
                    corridorHallAssigned = STCustomer.CORRIDOR_HALL_2; break;
                case CORRIDOR_HALL_3: 
                    corridorHallAssigned = STCustomer.CORRIDOR_HALL_3; break;
            }
        } finally{
            rl.unlock();
        }
        this.fifo.out();
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
