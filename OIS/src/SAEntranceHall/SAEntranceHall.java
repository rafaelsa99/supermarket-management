
package SAEntranceHall;

import Common.STCustomer;
import Common.STManager;
import FIFO.FIFO;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class SAEntranceHall implements IEntranceHall_Customer,
                                       IEntranceHall_Manager,
                                       IEntranceHall_Control {
    
    private final ReentrantLock rl;
    private Condition authorization;
    private final FIFO fifo;
    private boolean isAuthorized;
    private boolean isSuspended;
    private STCustomer corridorHallAssigned;

    public SAEntranceHall(int maxCostumers) {
        this.fifo = new FIFO(maxCostumers);
        rl = new ReentrantLock(true);
    }

    @Override
    public STCustomer enter(int costumerId) {
        this.fifo.in(costumerId);
        try{
            rl.lock();
            return corridorHallAssigned;
        } catch (Exception ex){}
        finally{
            rl.unlock();
        }
        return STCustomer.IDLE;
    }

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
        } catch (Exception ex){}
        finally{
            rl.unlock();
        }
        this.fifo.out();
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
