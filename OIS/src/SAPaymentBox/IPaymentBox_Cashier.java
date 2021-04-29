
package SAPaymentBox;

import Common.STCashier;

/**
 * Interface of the cashier for the payment box.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public interface IPaymentBox_Cashier {
    /**
     * Cashier waits for the payment to be done.
     * @return IDLE, the next state of the cashier
     */
    public STCashier payment();
}
