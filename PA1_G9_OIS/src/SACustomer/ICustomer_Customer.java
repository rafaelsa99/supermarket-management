
package SACustomer;

import Common.STCustomer;

/**
 * Interface of the customer to the SA customer.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public interface ICustomer_Customer {
    /**
     * Customers wait to be called for the shopping simulation,
     * @param customerId customer id
     * @return OUTSIDE_HALL, the next state of the customer
     */
    public STCustomer idle( int customerId );
}
