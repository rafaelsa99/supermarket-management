
package SAOutsideHall;

import Common.STCustomer;

/**
 * Interface of the customer for the outside hall.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public interface IOutsideHall_Customer {
    /**
     * Customer enters the outside hall and waits for authorization.
     * @param customerId customer id
     * @return ENTRANCE_HALL, the next state of the customer
     */
    public STCustomer enter(int customerId);
}
