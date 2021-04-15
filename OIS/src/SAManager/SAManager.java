
package SAManager;

import Common.STManager;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class SAManager implements IManager_Control,
                                  IManager_Customer,
                                  IManager_Manager {

   
    private final ReentrantLock rl;
    private final Condition idle;
    int emptySpacesEntranceHall; // Number of spaces available in the Entrance Hall
    private final int[] emptySpacesCorridorHall; // Number of spaces available in the Corridor Halls
    private int numCustomersOutsideHall; //Number of customers left to accept in the Outside Hall
    private int numCustomersEntranceHall; // Number of customers left to accept in the Entrance Hall
    private boolean entranceHallHasEmptySpace; // Flag to indicate if there is space in Entrance Hall
    private boolean corridorHallHasEmptySpace; // Flag to indicate if there is space in any Corridor Hall
    private boolean isSuspended;
    private boolean stop;
    private boolean end;
    private boolean isAuto;
    private int sizeEntranceHall;
    private int sizeCorridorHall;

    public SAManager(int nCorridors, int sizeEntranceHall, int sizeCorridorHall) {
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
    }
    
    
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
        } catch(Exception ex){}
        finally{
            rl.unlock();
        }
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
        try{
            rl.lock();
            isSuspended = false;
            idle.signal();
        } catch(Exception ex){}
        finally{
            rl.unlock();
        }
    }

    @Override
    public void stop() {
        try{
            rl.lock();
            stop = true;
            isSuspended = false;
            entranceHallHasEmptySpace = true;
            numCustomersOutsideHall = 1;
            idle.signal();
        } catch(Exception ex){}
        finally{
            rl.unlock();
        }
    }

    @Override
    public void end() {
        try{
            rl.lock();
            end = true;
            isSuspended = false;
            entranceHallHasEmptySpace = true;
            numCustomersOutsideHall = 1;
            idle.signal();
        } catch(Exception ex){}
        finally{
            rl.unlock();
        }
    }

    @Override
    public void entranceHall_freeSlot() {
        try{
            rl.lock();
            emptySpacesEntranceHall += 1; // Update number of available spaces
            entranceHallHasEmptySpace = true;
            idle.signal();
        } catch(Exception ex){}
        finally{
            rl.unlock();
        }
    }

    @Override
    public void corridorHall_freeSlot(int numCorridor) {
        try{
            rl.lock();
            corridorHallHasEmptySpace = true;
            emptySpacesCorridorHall[numCorridor] += 1; // Update number of available spaces
            idle.signal();
        } catch(Exception ex){}
        finally{
            rl.unlock();
        }
    }

    @Override
    public STManager idle() {
        STManager stManager = STManager.IDLE;
        try{
            rl.lock();
            while(((!entranceHallHasEmptySpace || numCustomersOutsideHall == 0) && // Wait for available space
                  (!corridorHallHasEmptySpace || numCustomersEntranceHall == 0)) ||
                   isSuspended || stop) 
                idle.await();
            if(end)
                stManager = STManager.END;
            else if(entranceHallHasEmptySpace && numCustomersOutsideHall > 0){ // Check if the Entrance Hall has space
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

        } catch(Exception ex){}
        finally{
            rl.unlock();
        }
        return stManager;
    }
    
}
