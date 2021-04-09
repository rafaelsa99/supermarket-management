/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SACorridor;

/**
 *
 * @author omp
 */
public interface ICorridor_Customer {

    public void enter(int customerId);

    public void freeSlot();

    public void move(int customerId);
}
