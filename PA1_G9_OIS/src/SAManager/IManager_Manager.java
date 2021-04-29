
package SAManager;

import Common.STManager;

/**
 * Interface of the manager for the SA manager.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public interface IManager_Manager {
    /**
     * Manager is in idle.
     * Waits for simulation to begin, for authorization for accept customer,
     * or for free slots to accept customers.
     * @return ENTRANCE_HALL or one of the CORRIDOR_HALL, indicating the destination of the next customer to accept.
     */
    public STManager idle();
}
