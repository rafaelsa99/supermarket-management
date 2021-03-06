
package ActiveEntity;

import Common.STManager;
import Communication.CClient;
import Main.OIS;
import SAEntranceHall.IEntranceHall_Manager;
import SAManager.IManager_Manager;
import SAOutsideHall.IOutsideHall_Manager;


/**
 * Represents the manager thread.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class AEManager extends Thread{
    
    /**
    * Manager Shared Area
    */
    private final IManager_Manager iManager;
    /**
    * OutsideHall Shared Area
    */
    private final IOutsideHall_Manager iOutsideHall;
    /**
    * EntranceHall Shared Area
    */
    private final IEntranceHall_Manager iEntranceHall;
    /**
    * Grafical Interface ID
    */
    private String graphicalID;
    /**
    * Communication Socket Client Object
    */
    private final CClient cClient;

    public AEManager(IManager_Manager iManager, IOutsideHall_Manager iOutsideHall, 
                     IEntranceHall_Manager iEntranceHall, CClient cc) {
        this.iManager = iManager;
        this.iOutsideHall = iOutsideHall;
        this.iEntranceHall = iEntranceHall;
        graphicalID = OIS.appendManagerToInterface(OIS.jListIdle);
        this.cClient = cc;
    }

    /**
     * Life cycle of the manager.
     */
    @Override
    public void run() {
        STManager stManager;
        
        while(true){
            // thread avança para Idle
            stManager = iManager.idle();
            if(stManager == STManager.END)
                return;
            switch(stManager){
                case ENTRANCE_HALL: 
                    cClient.sendMessage("MA|Outside Hall");
                    graphicalID = OIS.moveManager(OIS.jListIdle, OIS.jListOutsideHall, graphicalID);
                    iOutsideHall.accept();
                    graphicalID = OIS.moveManager(OIS.jListOutsideHall, OIS.jListIdle, graphicalID);
                    break;
                case CORRIDOR_HALL_1:
                case CORRIDOR_HALL_2:
                case CORRIDOR_HALL_3:
                    cClient.sendMessage("MA|Entrance Hall");
                    graphicalID = OIS.moveManager(OIS.jListIdle, OIS.jListEntranceHall, graphicalID);
                    iEntranceHall.accept(stManager);
                    graphicalID = OIS.moveManager(OIS.jListEntranceHall, OIS.jListIdle, graphicalID);
                    break;
            }
            cClient.sendMessage("MA|Idle");
        }
    }    
}
