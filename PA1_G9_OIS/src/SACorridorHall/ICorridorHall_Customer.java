
package SACorridorHall;

import Common.STCustomer;

/**
 * Interface of the customer for the corridor hall.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public interface ICorridorHall_Customer {
    /**
     * Customer enters and awaits for space in the corridor.
     * @param customerId customer id
     * @return the number of the corridor to go
     */
    public STCustomer enter(int customerId);
    /**
     * Indicates that there is a new slot available in the corridor.
     */
    public void freeSlot();
}
