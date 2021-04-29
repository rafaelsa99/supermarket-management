
package SAPaymentHall;

import Common.STCashier;

/**
 * Interface of the cashier for the payment hall.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public interface IPaymentHall_Cashier {
/**
     * Accept a customer waiting on the payment hall.
     * @return PAYMENT_BOX, the next state of the cashier
     */
    public STCashier accept();
}
