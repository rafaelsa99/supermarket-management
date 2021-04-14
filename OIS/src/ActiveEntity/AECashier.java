
package ActiveEntity;

import Common.STCashier;
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

    public AECashier(IPaymentHall_Cashier iPaymentHall, ICashier_Cashier iCashier,
                     IPaymentBox_Cashier iPaymentBox) {
        this.iPaymentHall = iPaymentHall;
        this.iCashier = iCashier;
        this.iPaymentBox = iPaymentBox;
    }

    @Override
    public void run() {
        STCashier stCashier = STCashier.IDLE;
        while(true){
            System.out.println("CASHIER: " + stCashier);
            stCashier = iCashier.idle();
            System.out.println("CASHIER: " + stCashier);
            if(stCashier == STCashier.END)
                return;
            if(stCashier == STCashier.PAYMENT_HALL){
                stCashier = iPaymentHall.accept();
                if(stCashier == STCashier.STOP)
                    continue;
                if(stCashier == STCashier.END)
                    return;
                System.out.println("CASHIER: " + stCashier);
                if(stCashier == STCashier.PAYMENT_BOX){
                    stCashier = iPaymentBox.payment();
                    if(stCashier == STCashier.STOP)
                        continue;
                    if(stCashier == STCashier.END)
                        return;
                }
            }
        }
    }
    
}
