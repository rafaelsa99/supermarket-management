
package SAManager;

import Common.STManager;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Shared area of the manager.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class SAManager implements IManager_Control,
                                  IManager_Customer,
                                  IManager_Manager {

    /** Reentrant Lock for synchronization */
    private final ReentrantLock rl;
    /** condition to wait on state idle */
    private final Condition idle;
    /** Number of spaces available in the Entrance Hall */
    private int emptySpacesEntranceHall;
    /** Number of spaces available in the Corridor Halls */
    private final int[] emptySpacesCorridorHall;
    /** Number of customers left to accept in the Outside Hall */
    private int numCustomersOutsideHall;
    /** Number of customers left to accept in the Entrance Hall */
    private int numCustomersEntranceHall; 
    /** Flag to indicate if there is space in Entrance Hall */
    private boolean entranceHallHasEmptySpace;
    /** Flag to indicate if there is space in any Corridor Hall */
    private boolean corridorHallHasEmptySpace;
    /** flag indicating that the simulation is suspended */
    private boolean isSuspended;
    /** flag indicating that the simulation has stopped */
    private boolean stop;
    /** flag indicating that the simulation has ended */
    private boolean end;
    /** flag indicating that the operation mode is auto */
    private boolean isAuto;
    /** flag indicating that the manager can accept the next customer in manual */
    private boolean step;
    /** timeout for the manager if in auto */
    private int timeout;
    /** size of the entrance hall */
    private final int sizeEntranceHall;
    /** size of the corridor halls */
    private final int sizeCorridorHall;

    /**
     * Shared area manager instantiation.
     * @param nCorridors
     * @param sizeEntranceHall
     * @param sizeCorridorHall
     * @param timeout 
     */
    public SAManager(int nCorridors, int sizeEntranceHall, int sizeCorridorHall, int timeout) {
        rl = new ReentrantLock(true);
        idle = rl.newCondition();
        emptySpacesCorridorHall = new int[nCorridors];
        this.sizeCorridorHall = sizeCorridorHall;
        this.sizeEntranceHall = sizeEntranceHall;
        emptySpacesEntranceHall = sizeEntranceHall;
        entranceHallHasEmptySpace = true;
        corridorHallHasEmptySpace = true;
        for (int i = 0; i < nCorridors; i++)
            emptySpacesCorridorHall[i] = sizeCorridorHall;
        numCustomersOutsideHall = 0;
        numCustomersEntranceHall = 0;
        isSuspended = false;
        stop = false;
        end = false;
        this.timeout = timeout;
        this.isAuto = true;
        this.step = false;
    }
    /**
     * Start the simulation.
     * @param nCustomers number of customers of the simulation
     */
    @Override
    public void start(int nCustomers) {
        try{
            rl.lock();
            emptySpacesEntranceHall = sizeEntranceHall;
            entranceHallHasEmptySpace = true;
            corridorHallHasEmptySpace = true;
            for (int i = 0; i < emptySpacesCorridorHall.length; i++)
                emptySpacesCorridorHall[i] = sizeCorridorHall;
            numCustomersEntranceHall = 0;
            isSuspended = false;
            stop = false;
            numCustomersOutsideHall = nCustomers; // Updates the number of Customers in the Outside Hall
            idle.signal();
        } finally{
            rl.unlock();
        }
    }
    /**
     * Suspend the simulation.
     */
    @Override
    public void suspend() {
        try{
            rl.lock();
            isSuspended = true;
        } finally{
            rl.unlock();
        }
    }
    /**
     * Resume the simulation.
     */
    @Override
    public void resume() {
        try{
            rl.lock();
            isSuspended = false;
            idle.signal();
        } finally{
            rl.unlock();
        }
    }
    /**
     * Stop the simulation.
     */
    @Override
    public void stop() {
        try{
            rl.lock();
            stop = true;
            isSuspended = false;
            entranceHallHasEmptySpace = true;
            numCustomersOutsideHall = 1;
            idle.signal();
        } finally{
            rl.unlock();
        }
    }
    /**
     * End the simulation.
     */
    @Override
    public void end() {
        try{
            rl.lock();
            end = true;
            isSuspended = false;
            entranceHallHasEmptySpace = true;
            numCustomersOutsideHall = 1;
            idle.signal();
        } finally{
            rl.unlock();
        }
    }
    /**
     * Indicates that a new slot is available on the entrance hall.
     */
    @Override
    public void entranceHall_freeSlot() {
        try{
            rl.lock();
            emptySpacesEntranceHall += 1; // Update number of available spaces
            entranceHallHasEmptySpace = true;
            idle.signal();
        } finally{
            rl.unlock();
        }
    }
    /**
     * Indicates that a new slot is available on a corridor hall.
     * @param numCorridor corridor hall number
     */
    @Override
    public void corridorHall_freeSlot(int numCorridor) {
        try{
            rl.lock();
            corridorHallHasEmptySpace = true;
            emptySpacesCorridorHall[numCorridor] += 1; // Update number of available spaces
            idle.signal();
        } finally{
            rl.unlock();
        }
    }
    /**
     * Manager is in idle.
     * Waits for simulation to begin, for authorization for accept customer,
     * or for free slots to accept customers.
     * @return ENTRANCE_HALL or one of the CORRIDOR_HALL, indicating the destination of the next customer to accept.
     */
    @Override
    public STManager idle() {
        STManager stManager = STManager.IDLE;
        try{
            rl.lock();
            while(((!entranceHallHasEmptySpace || numCustomersOutsideHall == 0) && // Wait for available space
                  (!corridorHallHasEmptySpace || numCustomersEntranceHall == 0)) ||
                   isSuspended || stop || (isAuto == false && step == false)) 
                idle.await();
            if(end)
                stManager = STManager.END;
            else{ 
                if(!isAuto)
                    step = false;
                else{
                    try {
                        Thread.sleep(timeout);
                    } catch (InterruptedException ex) {
                        System.err.println(ex.toString());
                    }
                }
                if(entranceHallHasEmptySpace && numCustomersOutsideHall > 0){ // Check if the Entrance Hall has space
                    emptySpacesEntranceHall -= 1; // Update number of available spaces
                    if(emptySpacesEntranceHall == 0) //If entrance hall is full, update flag
                        entranceHallHasEmptySpace = false;
                    numCustomersOutsideHall -= 1; // Update number of customers in the Outside Hall
                    numCustomersEntranceHall += 1; // Update number of customers in the Entrance Hall
                    stManager = STManager.ENTRANCE_HALL;
                } else {
                    for (int i = 0; i < emptySpacesCorridorHall.length; i++) { // Loop over the Corridor Halls
                        if(emptySpacesCorridorHall[i] > 0){ // Check if one of the Corridor Halls has space
                            emptySpacesCorridorHall[i] -= 1; // Update number of available spaces
                            numCustomersEntranceHall -= 1; // Update number of customers in the Entrance Hall
                            switch(i){
                                case 0: stManager = STManager.CORRIDOR_HALL_1;
                                    break;
                                case 1: stManager = STManager.CORRIDOR_HALL_2;
                                    break;
                                case 2: stManager = STManager.CORRIDOR_HALL_3;
                                    break;
                            }
                            break;
                        }   
                    }
                    // Updates the flag that indicates if there is any free space on the Corridor Hall
                    corridorHallHasEmptySpace = false;
                    for (int i = 0; i < emptySpacesCorridorHall.length; i++)
                        if(emptySpacesCorridorHall[i] > 0){
                            corridorHallHasEmptySpace = true;
                            break;
                        }
                }
            }
        } catch(InterruptedException ex){
            System.err.println(ex.toString());
        } finally{
            rl.unlock();
        }
        return stManager;
    }
    /**
     * Set the operation mode to auto.
     * @param timeout operation timeout
     */
    @Override
    public void auto(int timeout) {
        try{
            rl.lock();
            isAuto = true;
            this.timeout = timeout;
            idle.signal();
        } finally{
            rl.unlock();
        }
    }
    /**
     * Set the operation mode to manual.
     */
    @Override
    public void manual() {
        try{
            rl.lock();
            isAuto = false;
            step = false;
            idle.signal();
        } finally{
            rl.unlock();
        }
    }
    /**
     * Authorization to accept the next customer on manual.
     */
    @Override
    public void step() {
        try{
            rl.lock();
            step = true;
            idle.signal();
        } finally{
            rl.unlock();
        }
    }
}
