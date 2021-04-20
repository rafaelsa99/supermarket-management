
package SAEntranceHall;

import Common.STCustomer;
import Common.STManager;
import FIFO.FIFO;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class SAEntranceHall implements IEntranceHall_Customer,
                                       IEntranceHall_Manager,
                                       IEntranceHall_Control {
    
    private final ReentrantLock rl;
    private final FIFO fifo;
    private boolean stop;
    private boolean end;
    private STCustomer corridorHallAssigned;

    public SAEntranceHall(int maxCostumers) {
        this.fifo = new FIFO(maxCostumers);
        rl = new ReentrantLock(true);
        stop = false;
        end = false;
    }

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
        } catch (Exception ex){}
        finally{
            rl.unlock();
        }
        return stCustomer;
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
        } catch(Exception ex){}
        finally{
            rl.unlock();
        }
        fifo.removeAll();
    }

    @Override
    public void end() {
        try{
            rl.lock();
            end = true;
        } catch(Exception ex){}
        finally{
            rl.unlock();
        }
        fifo.removeAll();
    }
 
    @Override
    public void start() {
        try{
            rl.lock();
            stop = false;
            fifo.resetFIFO();
        } catch(Exception ex){}
        finally{
            rl.unlock();
        }
    }
}
