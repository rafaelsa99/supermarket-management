
package ActiveEntity;

import Common.STManager;
import SAEntranceHall.IEntranceHall_Manager;
import SAManager.IManager_Manager;
import SAOutsideHall.IOutsideHall_Manager;


/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class AEManager extends Thread{
    
    // área partilhada Manager
    private final IManager_Manager iManager;
    // área partilhada OutsideHall
    private final IOutsideHall_Manager iOutsideHall;
    // área partilhada EntranceHall
    private final IEntranceHall_Manager iEntranceHall;

    public AEManager(IManager_Manager iManager, IOutsideHall_Manager iOutsideHall, 
                     IEntranceHall_Manager iEntranceHall) {
        this.iManager = iManager;
        this.iOutsideHall = iOutsideHall;
        this.iEntranceHall = iEntranceHall;
    }

    @Override
    public void run() {
        STManager stManager = STManager.IDLE;
        
        while(true){
            // thread avança para Idle
            stManager = iManager.idle();
            switch(stManager){
                case ENTRANCE_HALL: 
                    System.out.println("MANAGER: OUTSIDE_HALL");
                    iOutsideHall.accept();
                    break;
                case CORRIDOR_HALL_1:
                case CORRIDOR_HALL_2:
                case CORRIDOR_HALL_3:
                    System.out.println("MANAGER: ENTRANCE_HALL");
                    iEntranceHall.accept(stManager);
                    break;
            }
            
        }
    }
    
    
}
