
package SAEntranceHall;

import Common.STCustomer;

/**
 * Interface of the customer for the SA entrance hall.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public interface IEntranceHall_Customer {
    /**
     * Customer enters the entrance hall and waits for authorization.
     * @param customerId customer id
     * @return the CORRIDOR_HALL assigned for the customer
     */
    public STCustomer enter(int customerId);
}
