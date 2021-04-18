
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
    Condition suspend;
    STCustomer corridorNumber; // Number of the corridor 
    final int timeoutMovement;
    boolean[] canMove;
    int[] customerPosition;
    Map<Integer, Integer> customerIdx;
    int stepsSize;
    boolean isSuspended;
    boolean stop;
    boolean end;
    
    public SACorridor(int maxCostumers, int sizePaymentHall, int stepsSize, int timeoutMovement, int totalCustomers, STCustomer corridorNumber) {
        this.corridorNumber = corridorNumber;
        this.rl = new ReentrantLock(true);
        this.full = rl.newCondition();
        this.suspend = rl.newCondition();
        this.movement = new Condition[maxCostumers];
        this.timeoutMovement = timeoutMovement;
        customerIdx = new HashMap<>();
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
    }

    @Override
    public STCustomer enter(int customerId) {
        try{
            rl.lock();
            while(customerIdx.size() == customerPosition.length || isSuspended) // Check if the corridor is full
               full.await();
            if(stop)
                return STCustomer.STOP;
            else if(end)
                return STCustomer.END;
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
            while(!canMove[cId] || isSuspended)
                movement[cId].await();
            if(stop || end){
                canMove[cId] = false;
                customerPosition[cId] = -1;
                customerIdx.remove(customerId);
                if(stop)
                    return STCustomer.STOP;
                else if(end)
                    return STCustomer.END;
            }
            try {
                Thread.sleep(timeoutMovement);
            } catch (InterruptedException ex) {}
            while(isSuspended)
                suspend.await();
            if(stop || end){
                canMove[cId] = false;
                customerPosition[cId] = -1;
                customerIdx.remove(customerId);
                if(stop)
                    return STCustomer.STOP;
                else if(end)
                    return STCustomer.END;
            }
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
        try{
            rl.lock();
            isSuspended = true;
        } catch(Exception ex){}
        finally{
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
            for (int i = 0; i < movement.length; i++)
                movement[i].signal();
        } catch ( Exception ex ) {}
        finally {
            rl.unlock();
        }
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
        } catch ( Exception ex ) {}
        finally {
            rl.unlock();
        }
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
        } catch ( Exception ex ) {}
        finally {
            rl.unlock();
        }
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
            customerIdx.clear();
        } catch(Exception ex){}
        finally{
            rl.unlock();
        }
    }
}
