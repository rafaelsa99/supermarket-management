
package Configurations;

/**
 * Configurations for the simulation and shopping simulation.
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class Configurations implements IConfigurations {

    /** Options for the movement timeout (in milliseconds). */
    final static Integer[] movementTimeOutPossibilities = {0, 100, 250, 500, 1000};
    /** Options for the manager auto mode timeout (in milliseconds). */
    final static Integer[] operatingTimeOutPossibilities = {0, 100, 250, 500, 1000, 2500, 5000};
    /** Default server port */
    public static final int SERVER_PORT = 6666;
    /** Number of customers for the shopping simulation. */
    private int totalNumberOfCostumers;
    /** Timeout for the customer movement. */
    private int movementTimeOut;
    /** Manager operating mode. */
    private boolean operatingMode;
    /** Timeout for the manager operation mode auto. */
    private int operatingTimeOut;
    /** Timeout for the payment. */
    private int timeToPay;

    /**
     * Configurations instantiation.
     */
    public Configurations() {
        this.totalNumberOfCostumers = 10;
        this.movementTimeOut = 100;
        this.operatingMode = true;
        this.operatingTimeOut = 100;
        this.timeToPay = 100;
    }

    /**
     * Sets the number of customers for the simulation.
     * @param totalNumberOfCostumers number of customers in ms
     */
    @Override
    public void setTotalNumberOfCostumers(int totalNumberOfCostumers) {
        this.totalNumberOfCostumers = totalNumberOfCostumers;
    }

    /**
     * Sets the customer movement timeout.
     * @param movementTimeOut movement timeout in ms
     */
    @Override
    public void setMovementTimeOut(int movementTimeOut) {
        this.movementTimeOut = movementTimeOut;
    }

    /**
     * Sets the manager operating mode.
     * @param operatingMode true if auto. false if manual.
     */
    @Override
    public void setOperatingMode(boolean operatingMode) {
        this.operatingMode = operatingMode;
    }
    /**
     * Sets the manager operation timeout.
     * @param operatingTimeOut manager timeout in ms
     */
    @Override
    public void setOperatingTimeOut(int operatingTimeOut) {
        this.operatingTimeOut = operatingTimeOut;
    }
    /**
     * Sets the customer payment timeout.
     * @param timeToPay payment timeout in ms
     */
    @Override
    public void setTimeToPay(int timeToPay) {
        this.timeToPay = timeToPay;
    }
    /**
     * Get options for the movement timeout (in milliseconds).
     * @return array of the options
     */
    public static Integer[] getMovementTimeOutPossibilities() {
        return movementTimeOutPossibilities;
    }
    /**
     * Get options for the manager auto mode timeout (in milliseconds).
     * @return array of the options
     */
    public static Integer[] getOperatingTimeOutPossibilities() {
        return operatingTimeOutPossibilities;
    }
    /**
     * Get configurations for the simulation.
     * Ex: NC:10|CT:100|PT:0|OM:true|OT:0
     * @return string with the configurations
     */
    public String getConfigurations(){
        return "NC:" + this.totalNumberOfCostumers + "|CT:" + this.movementTimeOut + "|PT:" + this.timeToPay + "|OM:" + Boolean.toString(this.operatingMode) + "|OT:" + this.operatingTimeOut;
    }
    /**
     * Get number of customers for the simulation.
     * @return number of customers
     */
    public int getTotalNumberOfCostumers() {
        return totalNumberOfCostumers;
    }
    /**
     * Get manager operation mode and timeout.
     * Ex: OM:true|OT:0
     * @return string with the mode and timeout
     */
    public String getOperatingMode() {
        return "OM:" + Boolean.toString(this.operatingMode) + "|OT:" + this.operatingTimeOut;
    }
}
