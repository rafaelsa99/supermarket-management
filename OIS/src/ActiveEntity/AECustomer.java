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
 * Não pretende implementar a entidade activa Customer. Serve apenas para dar
 * pistas como o Thread Custumer deve recorrer a àreas partilhadas para gerir as
 * transições de estado.
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class AECustomer extends Thread {

    // id do customer
    private final int customerId;

    // área partilhada Customer
    private final ICustomer_Customer iCustomer;
    // área partilhada Manager
    private final IManager_Customer iManager;
    // área partilhada Cashier
    private final ICashier_Customer iCashier;
    // área partilhada OutsideHall
    private final IOutsideHall_Customer iOutsideHall;
    // área partilhada EntranceHall
    private final IEntranceHall_Customer iEntranceHall;
    // área partilhada CorridorHall
    private final ICorridorHall_Customer[] iCorridorHall;
    // área partilhada Corridor
    private final ICorridor_Customer[] iCorridor;
    // área partilhada PaymentHall
    private final IPaymentHall_Customer iPaymentHall;
    // área partilhada PaymentBox
    private final IPaymentBox_Customer iPaymentBox;
    //Graphical ID
    private String graphicalID;
    //Communication Client
    private final CClient cClient;

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

    @Override
    public void run() {
        STCustomer stCustomer;
        int corridorPos;
        int corridorNumber;
        while (true) {
            // thread avança para Idle
            cClient.sendMessage("CA|" + customerId + ":Idle");
            stCustomer = iCustomer.idle(customerId);
            System.out.println("CUSTOMER " + customerId + ": " + stCustomer);
            if(stCustomer == STCustomer.END)
                return;
            // se simulação activa (não suspend, não stop, não end), thread avança para o outsideHall
            if(stCustomer == STCustomer.OUTSIDE_HALL){
                cClient.sendMessage("CA|" + customerId + ":Outside Hall");
                graphicalID = OIS.appendCostumerToInterface(OIS.jListOutsideHall, customerId);
                stCustomer = iOutsideHall.enter(customerId);
            }
            System.out.println("CUSTOMER " + customerId + ": " + stCustomer);
            if(stCustomer == STCustomer.STOP){
                OIS.removeCustomerFromInterface(OIS.jListOutsideHall, graphicalID);
                continue;
            }
            if(stCustomer == STCustomer.END)
                return;
            // se simulação activa (não suspend, não stop, não end), thread avança para o entranceHall
            if(stCustomer == STCustomer.ENTRANCE_HALL){
                cClient.sendMessage("CA|" + customerId + ":Entrance Hall");
                graphicalID = OIS.moveCostumer(OIS.jListOutsideHall, OIS.jListEntranceHall, graphicalID, customerId);
                stCustomer = iEntranceHall.enter(customerId);
            }
            corridorNumber = stCustomer.getValue();
            System.out.println("CUSTOMER " + customerId + ": " + stCustomer);
            //notifica manager que saiu do entrance hall e há espaço livre
            iManager.entranceHall_freeSlot();
            if(stCustomer == STCustomer.STOP){
                OIS.removeCustomerFromInterface(OIS.jListEntranceHall, graphicalID);
                continue;
            }
            if(stCustomer == STCustomer.END)
                return;
            //Entra no CorridorHall que lhe foi atribuido
            if(stCustomer == STCustomer.CORRIDOR_HALL_1 || 
               stCustomer == STCustomer.CORRIDOR_HALL_2 ||
               stCustomer == STCustomer.CORRIDOR_HALL_3){
                cClient.sendMessage("CA|" + customerId + ":Corridor Hall " + corridorNumber);
                graphicalID = OIS.moveCostumer(OIS.jListEntranceHall, OIS.corridoHall[corridorNumber], graphicalID, customerId);
                stCustomer = iCorridorHall[corridorNumber].enter(customerId);
            }
            iManager.corridorHall_freeSlot(corridorNumber);
            System.out.println("CUSTOMER " + customerId + ": " + stCustomer);
            if(stCustomer == STCustomer.STOP){
                OIS.removeCustomerFromInterface(OIS.corridoHall[corridorNumber], graphicalID);
                continue;
            }
            if(stCustomer == STCustomer.END)
                return;
            corridorPos = 0;
            //Entra no Corridor que lhe foi atribuido
            if(stCustomer == STCustomer.CORRIDOR_1 || 
               stCustomer == STCustomer.CORRIDOR_2 ||
               stCustomer == STCustomer.CORRIDOR_3){
                cClient.sendMessage("CA|" + customerId + ":Corridor " + corridorNumber + " in position " + corridorPos);
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
                    cClient.sendMessage("CA|" + customerId + ":Corridor " + corridorNumber + " in position " + corridorPos);
                    graphicalID = OIS.moveCostumer(OIS.corridor[corridorNumber][corridorPos - 1], OIS.corridor[corridorNumber][corridorPos], graphicalID, customerId);
                }
                System.out.println("CUSTOMER " + customerId + ": MOVEMENT " + stCustomer);
            }
            iCorridorHall[corridorNumber].freeSlot();
            if(stCustomer == STCustomer.STOP){
                OIS.removeCustomerFromInterface(OIS.corridor[corridorNumber][corridorPos], graphicalID);
                continue;
            }
            if(stCustomer == STCustomer.END)
                return;
            if(stCustomer == STCustomer.PAYMENT_HALL){
                cClient.sendMessage("CA|" + customerId + ":Payment Hall");
                iCashier.paymentHall_customerIn();
                graphicalID = OIS.moveCostumer(OIS.corridor[corridorNumber][corridorPos], OIS.jListPaymentHall, graphicalID, customerId);
                stCustomer = iPaymentHall.enter(customerId);
                iCashier.paymentHall_freeSlot();
                SACorridor.freeSlot();
            }
            System.out.println("CUSTOMER " + customerId + ": " + stCustomer);
            if(stCustomer == STCustomer.STOP){
                OIS.removeCustomerFromInterface(OIS.jListPaymentHall, graphicalID);
                continue;
            }
            if(stCustomer == STCustomer.END)
                return;
            if(stCustomer == STCustomer.PAYMENT_BOX){
                cClient.sendMessage("CA|" + customerId + ":Payment Box");
                graphicalID = OIS.moveCostumer(OIS.jListPaymentHall, OIS.jListPaymentBox, graphicalID, customerId);
                stCustomer = iPaymentBox.enter(customerId);
            }
            if(stCustomer == STCustomer.STOP){
                OIS.removeCustomerFromInterface(OIS.jListPaymentBox, graphicalID);
                continue;
            }
            if(stCustomer == STCustomer.END)
                return;
            OIS.removeCustomerFromInterface(OIS.jListPaymentBox, graphicalID);
        }
    }
}
