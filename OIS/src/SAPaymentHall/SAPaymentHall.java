
package SAPaymentHall;

import Common.STCashier;
import Common.STCustomer;
import FIFO.FIFO;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class SAPaymentHall implements IPaymentHall_Cashier, 
                                      IPaymentHall_Control, 
                                      IPaymentHall_Customer {
    
    ReentrantLock rl;
    Condition authorization;
    FIFO fifo;
    boolean isAuthorized;
    boolean isSuspended;
    boolean stop;
    boolean end;

    public SAPaymentHall(int maxCustomers) {
        this.fifo = new FIFO(maxCustomers);
        rl = new ReentrantLock(true);
        stop = false;
        end = false;
    }

    @Override
    public STCustomer enter(int costumerId) {
        this.fifo.in(costumerId);
        try{
            rl.lock();
            if(stop)
                return STCustomer.STOP;
            if(end)
                return STCustomer.END;
        } catch (Exception ex){}
        finally{
            rl.unlock();
        }
        return STCustomer.PAYMENT_BOX;
    }
    
    @Override
    public STCashier accept() {
        this.fifo.out();
        try{
            rl.lock();
            if(stop)
                return STCashier.STOP;
            if(end)
                return STCashier.END;
        } catch (Exception ex){}
        finally{
            rl.unlock();
        }
        return STCashier.PAYMENT_BOX;
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
