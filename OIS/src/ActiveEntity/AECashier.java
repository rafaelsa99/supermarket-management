
package ActiveEntity;

import Common.STCashier;
import Main.OIS;
import SACashier.ICashier_Cashier;
import SAPaymentHall.IPaymentHall_Cashier;
import SAPaymentBox.IPaymentBox_Cashier;
        
/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */

/**
 * Represents the Cashier Thread
 */
public class AECashier extends Thread{
    // área partilhada PaymentHall
    private final IPaymentHall_Cashier iPaymentHall;
    // área partilhada Cashier
    private final ICashier_Cashier iCashier;
    // área partilhada PaymentBox
    private final IPaymentBox_Cashier iPaymentBox;
    //Graphical ID
    private String graphicalID;
    
    public AECashier(IPaymentHall_Cashier iPaymentHall, ICashier_Cashier iCashier,
                     IPaymentBox_Cashier iPaymentBox) {
        this.iPaymentHall = iPaymentHall;
        this.iCashier = iCashier;
        this.iPaymentBox = iPaymentBox;
        graphicalID = OIS.appendCashierToInterface(OIS.jListIdle);
    }

    
    /**
   * LyfeCycle of the Cashier Therad
   */

    @Override
    public void run() {
        STCashier stCashier;
        while(true){
            stCashier = iCashier.idle();
            System.out.println("CASHIER: " + stCashier);
            if(stCashier == STCashier.END)
                return;
            if(stCashier == STCashier.PAYMENT_HALL){
                graphicalID = OIS.moveCashier(OIS.jListIdle, OIS.jListPaymentHall, graphicalID);
                stCashier = iPaymentHall.accept();
                System.out.println("CASHIER: " + stCashier);
                if(stCashier == STCashier.STOP){
                    graphicalID = OIS.moveCashier(OIS.jListPaymentHall, OIS.jListIdle, graphicalID);
                    continue;
                }
                if(stCashier == STCashier.END)
                    return;
                if(stCashier == STCashier.PAYMENT_BOX){
                    graphicalID = OIS.moveCashier(OIS.jListPaymentHall, OIS.jListPaymentBox, graphicalID);
                    stCashier = iPaymentBox.payment();
                    System.out.println("CASHIER: " + stCashier);
                    graphicalID = OIS.moveCashier(OIS.jListPaymentBox, OIS.jListIdle, graphicalID);
                    if(stCashier == STCashier.STOP)
                        continue;
                    if(stCashier == STCashier.END)
                        return;
                }
            }
        }
    }
    
}
