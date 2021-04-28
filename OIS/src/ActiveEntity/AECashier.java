
package ActiveEntity;

import Common.STCashier;
import Communication.CClient;
import Main.OIS;
import SACashier.ICashier_Cashier;
import SAPaymentHall.IPaymentHall_Cashier;
import SAPaymentBox.IPaymentBox_Cashier;
        
/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class AECashier extends Thread{
    
    // área partilhada PaymentHall
    private final IPaymentHall_Cashier iPaymentHall;
    // área partilhada Cashier
    private final ICashier_Cashier iCashier;
    // área partilhada PaymentBox
    private final IPaymentBox_Cashier iPaymentBox;
    //Graphical ID
    private int graphicalID;
    //Communication Client
    private final CClient cClient;
    
    public AECashier(IPaymentHall_Cashier iPaymentHall, ICashier_Cashier iCashier,
                     IPaymentBox_Cashier iPaymentBox, CClient cc) {
        this.iPaymentHall = iPaymentHall;
        this.iCashier = iCashier;
        this.iPaymentBox = iPaymentBox;
        this.cClient = cc;
        graphicalID = OIS.appendCashierToInterface(OIS.jListIdle);
    }

    @Override
    public void run() {
        STCashier stCashier;
        while(true){
            cClient.sendMessage("CA|Idle");
            stCashier = iCashier.idle();
            System.out.println("CASHIER: " + stCashier);
            if(stCashier == STCashier.END)
                return;
            if(stCashier == STCashier.PAYMENT_HALL){
                cClient.sendMessage("CA|Payment Hall");
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
                    cClient.sendMessage("CA|Payment Box");
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
