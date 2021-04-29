package ActiveEntity;

import Common.STCustomer;
import Communication.CClient;
import Main.OIS;
import SACashier.ICashier_Customer;
import SACorridor.ICorridor_Customer;
import SACorridor.SACorridor;
import SACorridorHall.ICorridorHall_Customer;
import SAOutsideHall.IOutsideHall_Customer;
import SACustomer.ICustomer_Customer;
import SAEntranceHall.IEntranceHall_Customer;
import SAManager.IManager_Customer;
import SAPaymentBox.IPaymentBox_Customer;
import SAPaymentHall.IPaymentHall_Customer;

/**
 * Represents the Customer Thread.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class AECustomer extends Thread {
    /**
    * id do customer
    */
    private final int customerId;
    /**
    * Customer Shared Area
    */
    private final ICustomer_Customer iCustomer;
    /**
    * Manager Shared Area
    */
    private final IManager_Customer iManager;
    /**
    * Cashier Shared Area
    */
    private final ICashier_Customer iCashier;
    /**
    * OutsideHall Shared Area
    */
    private final IOutsideHall_Customer iOutsideHall;
    /**
    * EntranceHall Shared Area
    */
    private final IEntranceHall_Customer iEntranceHall;
    /**
    * CorridorHall Shared Area List
    */
    private final ICorridorHall_Customer[] iCorridorHall;
    /**
    * Corridor Shared Area List
    */
    private final ICorridor_Customer[] iCorridor;
    /**
    * PaymentHall Shared Area
    */
    private final IPaymentHall_Customer iPaymentHall;
    /**
    * PaymentBox Shared Area
    */
    private final IPaymentBox_Customer iPaymentBox;
    /**
    * Grafical Interface ID
    */
    private String graphicalID;
    /**
    * Communication Socket Client Object
    */
    private final CClient cClient;

    
    /**
    * Entity Constructor
    * @param customerId Customer ID
    * @param customer Customer Shared Area Interface
    * @param outsideHall Outside Hall Shared Area Interface
    * @param entranceHall Entrance Hall Shared Area Interface   
    * @param corridorHall Corridor Hall List Shared Area Interfaces
    * @param corridor Corridor List Shared Area Interface
    * @param paymentHall Payment Hall Shared Area Interface
    * @param paymentBox Payemnt Box Shared Area Interface
    * @param cashier Cashier Shared Area Interface
    * @param manager Manager Shared Area Interface
    * @param cc Socket Client Communication Object
    */
    public AECustomer(int customerId, ICustomer_Customer customer, IOutsideHall_Customer outsideHall, 
                      IEntranceHall_Customer entranceHall, ICorridorHall_Customer[] corridorHall,
                      ICorridor_Customer[] corridor, IPaymentHall_Customer paymentHall,
                      IPaymentBox_Customer paymentBox, ICashier_Customer cashier, IManager_Customer manager, CClient cc) {
        this.customerId = customerId;
        this.iCustomer = customer;
        this.iManager = manager;
        this.iCashier = cashier;
        this.iOutsideHall = outsideHall;
        this.iEntranceHall = entranceHall;
        this.iCorridorHall = corridorHall;
        this.iCorridor = corridor;
        this.iPaymentHall = paymentHall;
        this.iPaymentBox = paymentBox;
        this.cClient = cc;
    }

    /**
     * Life cycle of the customer.
     */
    @Override
    public void run() {
        STCustomer stCustomer;
        int corridorPos;
        int corridorNumber;
        while (true) {
            stCustomer = iCustomer.idle(customerId);
            if(stCustomer == STCustomer.END)
                return;
            /**
            * If Simulation Active (Not suspend, Not stop, Not end), thread go to outsideHall
            */
            if(stCustomer == STCustomer.OUTSIDE_HALL){
                cClient.sendMessage("CT|" + customerId + ":Outside Hall");
                graphicalID = OIS.appendCostumerToInterface(OIS.jListOutsideHall, customerId);
                stCustomer = iOutsideHall.enter(customerId);
            }
            if(stCustomer == STCustomer.STOP){
                OIS.removeCustomerFromInterfaceInvoke(OIS.jListOutsideHall, graphicalID);
                continue;
            }
            if(stCustomer == STCustomer.END)
                return;
            /**
            * If Simulation Active (Not suspend, Not stop, Not end), thread go to entranceHall
            */
            if(stCustomer == STCustomer.ENTRANCE_HALL){
                cClient.sendMessage("CT|" + customerId + ":Entrance Hall");
                graphicalID = OIS.moveCostumer(OIS.jListOutsideHall, OIS.jListEntranceHall, graphicalID, customerId);
                stCustomer = iEntranceHall.enter(customerId);
            }
            corridorNumber = stCustomer.getValue();
            /**
            * Notificate manager of empty space in entrance hall
            */
            iManager.entranceHall_freeSlot();
            if(stCustomer == STCustomer.STOP){
                OIS.removeCustomerFromInterfaceInvoke(OIS.jListEntranceHall, graphicalID);
                continue;
            }
            if(stCustomer == STCustomer.END)
                return;
            /**
            * Enter in the attributed CorridorHall
            */
            if(stCustomer == STCustomer.CORRIDOR_HALL_1 || 
               stCustomer == STCustomer.CORRIDOR_HALL_2 ||
               stCustomer == STCustomer.CORRIDOR_HALL_3){
                cClient.sendMessage("CT|" + customerId + ":Corridor Hall " + corridorNumber);
                graphicalID = OIS.moveCostumer(OIS.jListEntranceHall, OIS.corridoHall[corridorNumber], graphicalID, customerId);
                stCustomer = iCorridorHall[corridorNumber].enter(customerId);
            }
            iManager.corridorHall_freeSlot(corridorNumber);
            if(stCustomer == STCustomer.STOP){
                OIS.removeCustomerFromInterfaceInvoke(OIS.corridoHall[corridorNumber], graphicalID);
                continue;
            }
            if(stCustomer == STCustomer.END)
                return;
            corridorPos = 0;
            /**
            * Enter in the attributed Corridor
            */
            if(stCustomer == STCustomer.CORRIDOR_1 || 
               stCustomer == STCustomer.CORRIDOR_2 ||
               stCustomer == STCustomer.CORRIDOR_3){
                cClient.sendMessage("CT|" + customerId + ":Corridor " + corridorNumber + " in position " + corridorPos);
                graphicalID = OIS.moveCostumer(OIS.corridoHall[corridorNumber], OIS.corridor[corridorNumber][corridorPos], graphicalID, customerId);
                stCustomer = iCorridor[corridorNumber].enter(customerId);
            }
            while(stCustomer == STCustomer.CORRIDOR_1 || 
               stCustomer == STCustomer.CORRIDOR_2 ||
               stCustomer == STCustomer.CORRIDOR_3){
                stCustomer = iCorridor[corridorNumber].move(customerId);
                if(stCustomer == STCustomer.CORRIDOR_1 || 
                stCustomer == STCustomer.CORRIDOR_2 ||
                stCustomer == STCustomer.CORRIDOR_3){
                    corridorPos++;
                    cClient.sendMessage("CT|" + customerId + ":Corridor " + corridorNumber + " in position " + corridorPos);
                    graphicalID = OIS.moveCostumer(OIS.corridor[corridorNumber][corridorPos - 1], OIS.corridor[corridorNumber][corridorPos], graphicalID, customerId);
                }
            }
            iCorridorHall[corridorNumber].freeSlot();
            if(stCustomer == STCustomer.STOP){
                OIS.removeCustomerFromInterfaceInvoke(OIS.corridor[corridorNumber][corridorPos], graphicalID);
                continue;
            }
            if(stCustomer == STCustomer.END)
                return;
            if(stCustomer == STCustomer.PAYMENT_HALL){
                cClient.sendMessage("CT|" + customerId + ":Payment Hall");
                iCashier.paymentHall_customerIn();
                graphicalID = OIS.moveCostumer(OIS.corridor[corridorNumber][corridorPos], OIS.jListPaymentHall, graphicalID, customerId);
                stCustomer = iPaymentHall.enter(customerId);
                iCashier.paymentHall_freeSlot();
                SACorridor.freeSlot();
            }
            if(stCustomer == STCustomer.STOP){
                OIS.removeCustomerFromInterfaceInvoke(OIS.jListPaymentHall, graphicalID);
                continue;
            }
            if(stCustomer == STCustomer.END)
                return;
            if(stCustomer == STCustomer.PAYMENT_BOX){
                cClient.sendMessage("CT|" + customerId + ":Payment Box");
                graphicalID = OIS.moveCostumer(OIS.jListPaymentHall, OIS.jListPaymentBox, graphicalID, customerId);
                stCustomer = iPaymentBox.enter(customerId);
            }
            if(stCustomer == STCustomer.STOP){
                OIS.removeCustomerFromInterfaceInvoke(OIS.jListPaymentBox, graphicalID);
                continue;
            }
            if(stCustomer == STCustomer.END)
                return;
            OIS.removeCustomerFromInterfaceInvoke(OIS.jListPaymentBox, graphicalID);
            cClient.sendMessage("CT|" + customerId + ":Idle");
        }
    }
}
