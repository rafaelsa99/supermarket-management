
package ActiveEntity;

import SACashier.ICashier_Cashier;
import SAPaymentBox.IPaymentBox_Cashier;
import SAPaymentHall.IPaymentHall_Cashier;

/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class AECashier extends Thread{
    
    // área partilhada PaymentHall
    private final IPaymentHall_Cashier iPaymentHall;
    // área partilhada PaymentBox
    private final IPaymentBox_Cashier iPaymentBox;
    // área partilhada Cashier
    private final ICashier_Cashier iCashier;

    public AECashier(IPaymentHall_Cashier iPaymentHall, IPaymentBox_Cashier iPaymentBox, 
                     ICashier_Cashier iCashier) {
        this.iPaymentHall = iPaymentHall;
        this.iPaymentBox = iPaymentBox;
        this.iCashier = iCashier;
    }
    
    
}
