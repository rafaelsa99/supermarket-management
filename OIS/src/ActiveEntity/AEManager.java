
package ActiveEntity;

import Common.STManager;
import Communication.CClient;
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
    //Communication Client
    private final CClient cClient;

    public AEManager(IManager_Manager iManager, IOutsideHall_Manager iOutsideHall, 
                     IEntranceHall_Manager iEntranceHall, CClient cc) {
        this.iManager = iManager;
        this.iOutsideHall = iOutsideHall;
        this.iEntranceHall = iEntranceHall;
        graphicalID = OIS.appendManagerToInterface(OIS.jListIdle);
        this.cClient = cc;
    }

    @Override
    public void run() {
        STManager stManager;
        
        while(true){
            // thread avança para Idle
            cClient.sendMessage("MA|Idle");
            stManager = iManager.idle();
            if(stManager == STManager.END){
                System.out.println("MANAGER: END");
                return;
            }
            switch(stManager){
                case ENTRANCE_HALL: 
                    System.out.println("MANAGER: OUTSIDE_HALL");
                    cClient.sendMessage("MA|Outside Hall");
                    graphicalID = OIS.moveManager(OIS.jListIdle, OIS.jListOutsideHall, graphicalID);
                    iOutsideHall.accept();
                    graphicalID = OIS.moveManager(OIS.jListOutsideHall, OIS.jListIdle, graphicalID);
                    break;
                case CORRIDOR_HALL_1:
                case CORRIDOR_HALL_2:
                case CORRIDOR_HALL_3:
                    System.out.println("MANAGER: ENTRANCE_HALL");
                    cClient.sendMessage("MA|Entrance Hall");
                    graphicalID = OIS.moveManager(OIS.jListIdle, OIS.jListPaymentHall, graphicalID);
                    iEntranceHall.accept(stManager);
                    graphicalID = OIS.moveManager(OIS.jListPaymentHall, OIS.jListIdle, graphicalID);
                    break;
            }
        }
    }    
}
