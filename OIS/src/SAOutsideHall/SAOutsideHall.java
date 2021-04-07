/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OutsideHall;

import FIFO.FIFO;

/**
 *
 * @author omp 
 */
public class SAOutsideHall implements IOutsideHall_Manager,
                                      IOutsideHall_Customer,
                                      IOutsideHall_Control {

    public SAOutsideHall( int maxCustomers ) {
    } 
    @Override
    public void call() {
    }

    @Override
    public void in(int customerId) {
    }
}
