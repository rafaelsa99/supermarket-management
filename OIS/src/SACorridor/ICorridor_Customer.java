
package SACorridor;

import Common.STCustomer;

/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public interface ICorridor_Customer {

    public STCustomer enter(int customerId);

    public STCustomer move(int customerId);
}
