
package SAManager;

/**
 * Interface of the customer for the SA manager.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public interface IManager_Customer {
    /**
     * Indicates that a new slot is available on the entrance hall.
     */
    public void entranceHall_freeSlot();
    /**
     * Indicates that a new slot is available on a corridor hall.
     * @param numCorridor corridor hall number
     */
    public void corridorHall_freeSlot(int numCorridor);
}
