
package SACorridor;

import Common.STCustomer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class SACorridor implements ICorridor_Control,
                                   ICorridor_Customer {
    
    ReentrantLock rl;
    Condition[] movement;
    Condition full;
    STCustomer corridorNumber; // Number of the corridor 
    final int timeoutMovement;
    boolean[] canMove;
    int[] customerPosition;
    Map<Integer, Integer> customerIdx;
    int stepsSize;

    
    public SACorridor(int maxCostumers, int sizePaymentHall, int stepsSize, int timeoutMovement, int totalCustomers, STCustomer corridorNumber) {
        this.corridorNumber = corridorNumber;
        this.rl = new ReentrantLock(true);
        this.full = rl.newCondition();
        this.movement = new Condition[maxCostumers];
        customerIdx = new HashMap<>();
        this.canMove = new boolean[maxCostumers];
        this.stepsSize = stepsSize;
        this.customerPosition = new int[maxCostumers];
        for (int i = 0; i < maxCostumers; i++){
            movement[i] = rl.newCondition();
            canMove[i] = false;
            customerPosition[i] = -1;
        }
        this.timeoutMovement = timeoutMovement;
    }

    @Override
    public STCustomer enter(int customerId) {
        try{
            rl.lock();
            if(customerIdx.size() == customerPosition.length) // Check if the corridor is full
               full.await();
            for (int i = 0; i < customerPosition.length; i++)
                if(customerPosition[i] == -1)
                    customerIdx.put(customerId, i);
            if(customerIdx.size() == 1)
                canMove[customerIdx.get(customerId)] = true;
            //TODO: Verificar se posição 0 está livre (será mesmo necessário?)
            customerPosition[customerIdx.get(customerId)] = 0;
        } catch(Exception ex){}
        finally{
            rl.unlock();
        }
        return corridorNumber;
    }
    
    @Override
    public STCustomer move(int customerId) {
        try{
            rl.lock();
            int cId = customerIdx.get(customerId);
            while(!canMove[cId])
                movement[cId].await();
            try {
                Thread.sleep(timeoutMovement);
            } catch (InterruptedException ex) {}
            customerPosition[cId] += 1; // Go to next position
            if(customerIdx.size() > 1){ // If there is more customers in the corridor, wakes up the next one
                int nextCId = cId + 1;;
                if((cId + 1) == customerIdx.size())
                    nextCId = 0;
                canMove[cId] = false;
                canMove[nextCId] = true;
                movement[nextCId].signal();
            }
            if(customerPosition[cId] == (stepsSize - 1)){ // Check if reached the end of the corridor
                canMove[cId] = false;
                customerPosition[cId] = -1;
                if(customerIdx.size() == customerPosition.length) // Check if the corridor is full
                    full.signal();
                customerIdx.remove(customerId);
                return STCustomer.PAYMENT_HALL;
            }
        } catch(Exception ex){}
        finally{
            rl.unlock();
        }
        return corridorNumber;
    }
    
    @Override
    public void freeSlot() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
