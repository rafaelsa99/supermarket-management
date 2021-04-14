
package Common;

/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public enum STCustomer { 
    IDLE,
    ENTRANCE_HALL,
    OUTSIDE_HALL,
    CORRIDOR_HALL_1(0),
    CORRIDOR_HALL_2(1),
    CORRIDOR_HALL_3(2),
    CORRIDOR_1(0),
    CORRIDOR_2(1),
    CORRIDOR_3(2),
    PAYMENT_HALL,
    PAYMENT_BOX;
        
    private int value;

    private STCustomer(int value) {
        this.value = value;
    }
    
    private STCustomer() {}
    
    /**
     * Get the value of value
     *
     * @return the value of value
     */
    public int getValue() {
        return value;
    }

}
