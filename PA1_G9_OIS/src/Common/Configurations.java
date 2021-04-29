
package Common;

/**
 * Contains the configurations of the simulation
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class Configurations {
    /**
    * Max Number of Costumers
    */
    public static final int MAX_CUSTOMERS = 100;
    /**
    * Number of Corridors
    */
    public static final int N_CORRIDOR = 3;
    /**
    * Entrance Hall size
    */
    public static final int SIZE_ENTRANCE_HALL = 6;
    /**
    * Corridor Hall size
    */
    public static final int SIZE_CORRIDOR_HALL = 3;
    /**
    * Corridor size
    */
    public static final int SIZE_CORRIDOR = 2;
    /**
    * Payment Hall size
    */
    public static final int SIZE_PAYMENT_HALL = 2;
    /**
    * Corridor Steps
    */
    public static final int CORRIDOR_STEPS = 10;
    /**
    * Movement Time Out
    */
    public static final int TIMEOUT_MOVEMENT = 100;
    /**
    * Payment Time Out
    */
    public static final int TIMEOUT_PAYMENT = 100;
    /**
    * Authorization Time Out
    */
    public static final int TIMEOUT_AUTHORIZATION = 100;
    /**
    * Socket Port
    */
    public static final int SERVER_PORT = 6669;
    /**
    * Configurations (Avoid Instantiation)
    */
    private Configurations() {};
}
