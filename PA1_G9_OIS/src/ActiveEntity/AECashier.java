
package ActiveEntity;

import Common.STCashier;
import Communication.CClient;
import Main.OIS;
import SACashier.ICashier_Cashier;
import SAPaymentHall.IPaymentHall_Cashier;
import SAPaymentBox.IPaymentBox_Cashier;
        
/**
 * Represents the Cashier Thread.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */

public class AECashier extends Thread{
    /**
    * Shared Area PaymentHall
    */
    private final IPaymentHall_Cashier iPaymentHall;
    /**
    * Shared Area Cashier
    */
    private final ICashier_Cashier iCashier;
    /**
    * Shared Area PaymentBox
    */
    private final IPaymentBox_Cashier iPaymentBox;
    /**
    * Grafical Interface ID
    */
    private String graphicalID;
    /**
    * Communication Client Object
    */
    private final CClient cClient;
    
    /**
    * Entity Constructor
    * @param iPaymentHall Payment Hall Shared Area Interface
    * @param iCashier Cashier Shared Area Interface
    * @param iPaymentBox Payemnt Box Shared Area Interface
    * @param cc Client Communication Object
    */
    public AECashier(IPaymentHall_Cashier iPaymentHall, ICashier_Cashier iCashier,
                     IPaymentBox_Cashier iPaymentBox, CClient cc) {
        this.iPaymentHall = iPaymentHall;
        this.iCashier = iCashier;
        this.iPaymentBox = iPaymentBox;
        this.cClient = cc;
        graphicalID = OIS.appendCashierToInterface(OIS.jListIdle);
    }

    
    /**
   * Life cycle of the Cashier Thread.
   */
    @Override
    public void run() {
        STCashier stCashier;
        while(true){
            stCashier = iCashier.idle();
            if(stCashier == STCashier.END)
                return;
            if(stCashier == STCashier.PAYMENT_HALL){
                cClient.sendMessage("CA|Payment Hall");
                graphicalID = OIS.moveCashier(OIS.jListIdle, OIS.jListPaymentHall, graphicalID);
                stCashier = iPaymentHall.accept();
                if(stCashier == STCashier.STOP){
                    graphicalID = OIS.moveCashier(OIS.jListPaymentHall, OIS.jListIdle, graphicalID);
                    continue;
                }
                if(stCashier == STCashier.END)
                    return;
                if(stCashier == STCashier.PAYMENT_BOX){
                    cClient.sendMessage("CA|Payment Box");
                    graphicalID = OIS.moveCashier(OIS.jListPaymentHall, OIS.jListPaymentBox, graphicalID);
                    stCashier = iPaymentBox.payment();
                    graphicalID = OIS.moveCashier(OIS.jListPaymentBox, OIS.jListIdle, graphicalID);
                    if(stCashier == STCashier.STOP)
                        continue;
                    if(stCashier == STCashier.END)
                        return;
                }
            }
            cClient.sendMessage("CA|Idle");
        }
    }
    
}
