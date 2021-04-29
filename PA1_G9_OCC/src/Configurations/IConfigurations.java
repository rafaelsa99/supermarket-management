
package Configurations;

/**
 * Interface of the methods to set the configurations for the shopping simulation.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public interface IConfigurations {

    /**
     * Sets the number of customers for the simulation.
     * @param totalNumberOfCostumers number of customers in ms
     */
    public void setTotalNumberOfCostumers(int totalNumberOfCostumers);
    /**
     * Sets the customer movement timeout.
     * @param movementTimeOut movement timeout in ms
     */
    public void setMovementTimeOut(int movementTimeOut);
    /**
     * Sets the manager operating mode.
     * @param operatingMode true if auto. false if manual.
     */
    public void setOperatingMode(boolean operatingMode);
    /**
     * Sets the manager operation timeout.
     * @param operatingTimeOut manager timeout in ms
     */
    public void setOperatingTimeOut(int operatingTimeOut);
    /**
     * Sets the customer payment timeout.
     * @param timeToPay payment timeout in ms
     */
    public void setTimeToPay(int timeToPay);
}
