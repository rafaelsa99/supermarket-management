
package SACorridor;

import Common.STCustomer;

/**
 * Interface of the customer in the SA corridor.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public interface ICorridor_Customer {
    /**
      * Customer enters the corridor in the position 0.
      * @param customerId customer id
      * @return the number of the corridor
      */
    public STCustomer enter(int customerId);
    /**
     * Customer waits for his turn and moves to the next position in the corridor.
     * If reached the end, check if there is space in payment hall.
     * If there is leave the corridor, otherwise waits.
     * @param customerId customer id
     * @return corridor number to continue in the corridor ; PAYMENT_HALL to leave the corridor
     */
    public STCustomer move(int customerId);
}
