
package SACorridorHall;

import Common.STCustomer;

/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public interface ICorridorHall_Customer {

    public STCustomer enter(int customerId);
    public void freeSlot();
}
