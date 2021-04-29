
package SACorridor;

import Common.STCustomer;
import FIFO.FIFO;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Shared area for the corridor.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class SACorridor implements ICorridor_Control,
                                   ICorridor_Customer {
    
    /** Reentrant Lock for synchronization */
    private final ReentrantLock rl;
    /** array of conditions for customers wait for the movement */
    private final Condition[] movement;
    /** condition if fifo is corridor is full */
    private final Condition full;
    /** condition if simulation is suspended */
    private final Condition suspend;
    /** Number of the corridor */
    private final STCustomer corridorNumber;
    /** timeout for the movement of the customer */
    private int timeoutMovement;
    /** array of flags indicating if each customer can move */
    private final boolean[] canMove;
    /** array indicating the position of each customer in the corridor */
    private final int[] customerPosition;
    /** auxiliary array to indicating the mapping of the customer id with the index in the class arrays */
    private final int[] customerIdx;
    /** number of steps in the corridor */
    private final int stepsSize;
    /** flag indicating that the simulation is suspended */ 
    private boolean isSuspended;
    /** flag indicating that the simulation has stopped */ 
    private boolean stop;
    /** flag indicating that the simulation has ended */ 
    private boolean end;
    /** number of customers in the corridor */ 
    private int numCustomersOnCorridor;
    /** Reentrant Lock for synchronization with the static variables */
    private static ReentrantLock rls;
    /** flag indicating if there is space in the payment hall */
    private static boolean emptySpacePaymentHall;
    /** FIFO for customers wait for space in payment hall */
    private static FIFO fifo;
    /** number of empty spaces in the payment hall */
    private static int emptySpacesPaymentHall;
    /** size of the payment hall */
    private static int sizePaymentHall;
    
    /**
     * Shared area corridor instantiation.
     * @param maxCostumers maximum number of customers in the corridor
     * @param sizePaymentHall size of the payment hall
     * @param stepsSize number of steps in the corridor
     * @param timeoutMovement timeout for the customer movement
     * @param nCorridors number of corridors in the simulation
     * @param corridorNumber corridor number
     * @param totalCustomers total of customers in the simulation
     */
    public SACorridor(int maxCostumers, int sizePaymentHall, int stepsSize, int timeoutMovement, int nCorridors, STCustomer corridorNumber, int totalCustomers) {
        this.corridorNumber = corridorNumber;
        this.rl = new ReentrantLock(true);
        this.full = rl.newCondition();
        this.suspend = rl.newCondition();
        this.movement = new Condition[maxCostumers];
        this.timeoutMovement = timeoutMovement;
        customerIdx = new int[totalCustomers];
        this.canMove = new boolean[maxCostumers];
        this.stepsSize = stepsSize;
        this.customerPosition = new int[maxCostumers];
        for (int i = 0; i < maxCostumers; i++){
            movement[i] = rl.newCondition();
            canMove[i] = false;
            customerPosition[i] = -1;
        }
        this.isSuspended = false;
        this.stop = false;
        this.end = false;
        this.numCustomersOnCorridor = 0;
        SACorridor.sizePaymentHall = sizePaymentHall;
        SACorridor.emptySpacesPaymentHall = sizePaymentHall;
        SACorridor.emptySpacePaymentHall = true;
        SACorridor.rls = new ReentrantLock(true);
        SACorridor.fifo = new FIFO(nCorridors * maxCostumers);
        
    }
     /**
      * Customer enters the corridor in the position 0.
      * @param customerId customer id
      * @return the number of the corridor
      */
    @Override
    public STCustomer enter(int customerId) {
        try{
            rl.lock();
            while(numCustomersOnCorridor == customerPosition.length) // Check if the corridor is full
               full.await();
            if(stop)
                return STCustomer.STOP;
            else if(end)
                return STCustomer.END;
            for (int i = 0; i < customerPosition.length; i++)
                if(customerPosition[i] == -1){
                    customerIdx[customerId] = i;
                    numCustomersOnCorridor += 1;
                    break;
                }
            if(numCustomersOnCorridor == 1)
                canMove[customerIdx[customerId]] = true;
            customerPosition[customerIdx[customerId]] = 0;
        } catch(InterruptedException ex){
            System.err.println(ex.toString());
        } finally{
            rl.unlock();
        }
        return corridorNumber;
    }
    
    /**
     * Set the customer movement timeout.
     * @param timeoutMovement movement timeout
     */
    @Override
    public void setTimeoutMovement(int timeoutMovement) {
        this.timeoutMovement = timeoutMovement;
    }
    
    /**
     * Customer waits for his turn and moves to the next position in the corridor.
     * If reached the end, check if there is space in payment hall.
     * If there is leave the corridor, otherwise waits.
     * @param customerId customer id
     * @return corridor number to continue in the corridor ; PAYMENT_HALL to leave the corridor
     */
    @Override
    public STCustomer move(int customerId) {
        try{
            rl.lock();
            int cId = customerIdx[customerId];
            while(!canMove[cId] || isSuspended)
                movement[cId].await();
            if(stop || end){
                canMove[cId] = false;
                customerPosition[cId] = -1;
                numCustomersOnCorridor -= 1;
                if(stop)
                    return STCustomer.STOP;
                else if(end)
                    return STCustomer.END;
            }
            try {
                Thread.sleep(timeoutMovement);
            } catch (InterruptedException ex) {
                System.err.println(ex.toString());
            }
            while(isSuspended)
                suspend.await();
            if(stop || end){
                canMove[cId] = false;
                customerPosition[cId] = -1;
                numCustomersOnCorridor -= 1;
                if(stop)
                    return STCustomer.STOP;
                else if(end)
                    return STCustomer.END;
            }
            customerPosition[cId] += 1; // Go to next position
            if(numCustomersOnCorridor > 1){ // If there is more customers in the corridor, wakes up the next one
                int nextCId = cId + 1;
                if((cId + 1) == numCustomersOnCorridor)
                    nextCId = 0;
                canMove[cId] = false;
                canMove[nextCId] = true;
                movement[nextCId].signal();
            }
            if(customerPosition[cId] == stepsSize){ // Check if reached the end of the corridor
                canMove[cId] = false;
                customerPosition[cId] = -1;
                numCustomersOnCorridor -= 1;
                rl.unlock();
                SACorridor.rls.lock();
                try {
                    if(!SACorridor.emptySpacePaymentHall){ // If there is no space in the payment hall, wait in FIFO
                        SACorridor.rls.unlock();
                        SACorridor.fifo.in(customerId);
                        SACorridor.rls.lock();
                    }
                    SACorridor.emptySpacesPaymentHall -= 1; // Updates the number of available spaces in the paymentHall
                    if(SACorridor.emptySpacesPaymentHall == 0)
                        SACorridor.emptySpacePaymentHall = false;
                } finally {
                    SACorridor.rls.unlock();
                }
                rl.lock();
                if(stop)
                    return STCustomer.STOP;
                if(end)
                    return STCustomer.END;
                if((numCustomersOnCorridor + 1) == customerPosition.length) // Check if the corridor was full
                    full.signal();
                return STCustomer.PAYMENT_HALL;
            }
        } catch(InterruptedException ex){
            System.err.println(ex.toString());
        } finally{
            rl.unlock();
        }
        return corridorNumber;
    }
    /**
     * Indicates there is a new slot available in the payment hall.
     */
    public static void freeSlot() {
        try{
            SACorridor.rls.lock();
            SACorridor.emptySpacesPaymentHall += 1;
            SACorridor.emptySpacePaymentHall = true;
            if(SACorridor.emptySpacesPaymentHall > 1 || SACorridor.fifo.isEmpty())
                return;
        } finally{
            SACorridor.rls.unlock();
        }
        SACorridor.fifo.out();
    }
    /**
     * Suspend the simulation.
     */
    @Override
    public void suspend() {
        SACorridor.fifo.suspend();
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
        try {
            rl.lock();
            isSuspended = false;
            full.signal();
            suspend.signal();
            for (Condition cMovement : movement)
                cMovement.signal();
        } finally {
            rl.unlock();
        }
        SACorridor.fifo.resume();
    }
    /**
     * Stop the simulation.
     */
    @Override
    public void stop() {
        try {
            rl.lock();
            stop = true;
            isSuspended = false;
            suspend.signal();
            for (int i = 0; i < movement.length; i++){
                canMove[i] = true;
                movement[i].signal();
            }
            full.signal();
        } finally {
            rl.unlock();
        }
        SACorridor.fifo.removeAll();
    }
    /**
     * End the simulation.
     */
    @Override
    public void end() {
        try {
            rl.lock();
            end = true;
            isSuspended = false;
            suspend.signal();
            for (int i = 0; i < movement.length; i++){
                canMove[i] = true;
                movement[i].signal();
            }
            full.signal();
        } finally {
            rl.unlock();
        }
        SACorridor.fifo.removeAll();
    }
    /**
     * Start the simulation.
     */
    @Override
    public void start() {
        try{
            rl.lock();
            for (int i = 0; i < canMove.length; i++){
                canMove[i] = false;
                customerPosition[i] = -1;
            }
            this.isSuspended = false;
            this.stop = false;
            numCustomersOnCorridor = 0;
        } finally{
            rl.unlock();
        }
        try{
            SACorridor.rls.lock();
            SACorridor.emptySpacesPaymentHall = SACorridor.sizePaymentHall;
            SACorridor.emptySpacePaymentHall = true;
        } finally{
            SACorridor.rls.unlock();
        }
        SACorridor.fifo.resetFIFO();
    }
}
