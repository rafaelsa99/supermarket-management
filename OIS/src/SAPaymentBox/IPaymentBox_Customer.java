
package SAPaymentBox;

import Common.STCustomer;

/**
 * Interface of the customer for the payment box.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public interface IPaymentBox_Customer {
    /**
     * Customer enters on payment hall and simulates payment.
     * @param costumerId customer id
     * @return IDLE, the next state of the customer
     */
    public STCustomer enter(int costumerId);
}
