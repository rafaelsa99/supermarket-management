
package SAPaymentHall;

import Common.STCustomer;

/**
 * Interface of the customer for the payment hall.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public interface IPaymentHall_Customer {
    /**
     * Customer enters on payment hall and waits for authorization.
     * @param costumerId id of the customer
     * @return PAYMENT_BOX, the next state of the customer
     */
    public STCustomer enter(int costumerId);
}
