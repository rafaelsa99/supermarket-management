package ActiveEntity;


import Idle.IIdle_Customer;
import Idle.SAIdle;
import SAOutsideHall.IOutsideHall_Customer;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Não pretende implementar a entidade activa Customer. Serve apenas para dar pistas como o
 * Thread Custumer deve recorrer a àreas partilhadas para gerir as transições de estado.
 * @author omp
 */
public class AECustomer extends Thread { 
    
    // id do customer
    private final int customerId;
    
    // árae partilhada Idle
    private final IIdle_Customer idle;
    // área partilhada OutsideHall
    private final IOutsideHall_Customer outsideHall;
    
    public AECustomer( int customerId, IIdle_Customer idle, IOutsideHall_Customer outsideHall  /* mais args */ ) {
        this.customerId = customerId;
        this.idle = idle;
        this.outsideHall = outsideHall;
    }
    @Override
    public void run() {
        while ( true ) {
            // thread avança para Idle
            idle.idle(customerId );
            // se simulação activa (não suspend, não stop, não end), thread avança para o outsideHall
            outsideHall.enter( customerId );
            // mais
        }
    }
}
