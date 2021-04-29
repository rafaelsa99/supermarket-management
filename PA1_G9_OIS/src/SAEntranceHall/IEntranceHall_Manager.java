
package SAEntranceHall;

import Common.STManager;

/**
 * Interface of the manager for the SA entrance hall.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public interface IEntranceHall_Manager {
    /**
     * Accept the next customer waiting in the FIFO.
     * @param numCorridor corridor number for the customer
     */
    public void accept(STManager numCorridor);
}
