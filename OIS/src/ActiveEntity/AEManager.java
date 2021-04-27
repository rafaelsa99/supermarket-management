
package ActiveEntity;

import Common.STManager;
import Main.OIS;
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
    //Graphical ID
    private int graphicalID;

    public AEManager(IManager_Manager iManager, IOutsideHall_Manager iOutsideHall, 
                     IEntranceHall_Manager iEntranceHall) {
        this.iManager = iManager;
        this.iOutsideHall = iOutsideHall;
        this.iEntranceHall = iEntranceHall;
        graphicalID = OIS.appendManagerToInterface(OIS.jListIdle);
    }

    @Override
    public void run() {
        STManager stManager;
        
        while(true){
            // thread avança para Idle
            stManager = iManager.idle();
            if(stManager == STManager.END){
                System.out.println("MANAGER: END");
                return;
            }
            switch(stManager){
                case ENTRANCE_HALL: 
                    System.out.println("MANAGER: OUTSIDE_HALL");
                    graphicalID = OIS.moveManager(OIS.jListIdle, OIS.jListOutsideHall, graphicalID);
                    iOutsideHall.accept();
                    graphicalID = OIS.moveManager(OIS.jListOutsideHall, OIS.jListIdle, graphicalID);
                    break;
                case CORRIDOR_HALL_1:
                case CORRIDOR_HALL_2:
                case CORRIDOR_HALL_3:
                    System.out.println("MANAGER: ENTRANCE_HALL");
                    graphicalID = OIS.moveManager(OIS.jListIdle, OIS.jListPaymentHall, graphicalID);
                    iEntranceHall.accept(stManager);
                    graphicalID = OIS.moveManager(OIS.jListPaymentHall, OIS.jListIdle, graphicalID);
                    break;
            }
        }
    }    
}
