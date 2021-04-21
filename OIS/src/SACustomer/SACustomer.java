
package SACustomer;

import Common.STCustomer;
import List.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class SACustomer implements ICustomer_Customer,
                               ICustomer_Control {

    private final ReentrantLock rl;
    private final Condition[] stay;
    private final Condition setEmpty;
    private final Condition customerLeaving;
    private final List list;
    private final boolean[] leave;
    private boolean end;
    private boolean simulationActive;
    
    public SACustomer(int maxCustomers) {
        rl = new ReentrantLock(true);
        list = new List(maxCustomers, true);
        setEmpty = rl.newCondition();
        stay = new Condition[maxCustomers];
        customerLeaving = rl.newCondition();
        leave = new boolean[maxCustomers];
        for ( int i = 0; i < maxCustomers; i++ ) {
            stay[ i ] = rl.newCondition();
            leave[ i ] = false;
        }
        end = false;
        simulationActive = false;
    }
    @Override
    public STCustomer idle( int customerId ) {
        STCustomer stCustomer = STCustomer.IDLE;
        try{
            rl.lock();
            list.in(customerId); 
            if(list.getCount() == stay.length){
                setEmpty.signal();
                if(simulationActive){
                    System.out.println("SHOPPING SIMULATION ENDED"); // Enviar mensagem para o OCC
                    simulationActive = false;
                }
            }
            while(!leave[customerId]) // Loop waiting for the authorization to start the simulation
                stay[customerId].await();
            if(end)
                return STCustomer.END;
            leave[customerId] = false;
            customerLeaving.signal(); // Notify that it has left the list
            stCustomer = STCustomer.OUTSIDE_HALL;
        } catch(InterruptedException ex){
            System.err.println(ex.toString());
        } finally{
            rl.unlock();
        }
        return stCustomer;
    }
    @Override
    public void start( int nCustomers ) {
        try{
            rl.lock();
            while(list.getCount()!= stay.length) // Check if all customers are in idle
                setEmpty.await();
            simulationActive = true;
            for (int i = 0; i < nCustomers; i++){
                int customerIdToLeave = list.outFirst(); // Remove the customer with lower ID
                leave[customerIdToLeave] = true;
                stay[customerIdToLeave].signal(); // Notify customers that the one with the lower ID can leave1
                while(leave[customerIdToLeave])
                    customerLeaving.await(); // Wait for customer to leave the list
            }
        } catch(InterruptedException ex){
            System.err.println(ex.toString());
        } finally{
            rl.unlock();
        }
    }
    @Override
    public void end() {
        try{
            rl.lock();
            end = true;
            for (int i = 0; i < stay.length; i++){
                leave[i] = true;
                stay[i].signal();
            }
        } finally{
            rl.unlock();
        }    
    }
}
