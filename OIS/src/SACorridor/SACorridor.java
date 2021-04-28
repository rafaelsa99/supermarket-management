
package SACorridor;

import Common.STCustomer;
import FIFO.FIFO;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class SACorridor implements ICorridor_Control,
                                   ICorridor_Customer {
    
    private final ReentrantLock rl;
    private final Condition[] movement;
    private final Condition full;
    private final Condition suspend;
    private final STCustomer corridorNumber; // Number of the corridor 
    private int timeoutMovement;
    private final boolean[] canMove;
    private final int[] customerPosition;
    private final int[] customerIdx;
    private final int stepsSize;
    private boolean isSuspended;
    private boolean stop;
    private boolean end;
    private int numCustomersOnCorridor;
    private static ReentrantLock rls;
    private static boolean emptySpacePaymentHall;
    private static FIFO fifo;
    private static int emptySpacesPaymentHall;
    private static int sizePaymentHall;
    
    
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

    public void setTimeoutMovement(int timeoutMovement) {
        this.timeoutMovement = timeoutMovement;
    }
    
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
            if(customerPosition[cId] == (stepsSize - 1)){ // Check if reached the end of the corridor
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
