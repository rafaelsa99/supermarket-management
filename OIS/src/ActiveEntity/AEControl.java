

package ActiveEntity;

import SACashier.ICashier_Control;
import SACorridor.ICorridor_Control;
import SACorridorHall.ICorridorHall_Control;
import java.net.Socket;
import SACustomer.ICustomer_Control;
import SAEntranceHall.IEntranceHall_Control;
import SAManager.IManager_Control;
import SAOutsideHall.IOutsideHall_Control;
import SAPaymentBox.IPaymentBox_Control;
import SAPaymentHall.IPaymentHall_Control;

/**
 * 
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */

/**
 * Represents the Control Thread
 * Responsable for executing the commands received by the OCC
 * Ex: start, stop, end, etc....
 */
public class AEControl extends Thread implements IControl{

    // área partilhada Customer
    private final ICustomer_Control iCustomer;
    // área partilhada Manager
    private final IManager_Control iManager;
    // área partilhada Cashier
    private final ICashier_Control iCashier;
    // área partilhada OutsideHall
    private final IOutsideHall_Control iOutsideHall;
    // área partilhada EntranceHall
    private final IEntranceHall_Control iEntranceHall;
    // área partilhada CorridorHall
    private final ICorridorHall_Control[] iCorridorHall;
    // área partilhada Corridor
    private final ICorridor_Control[] iCorridor;
    // área partilhada PaymentHall
    private final IPaymentHall_Control iPaymentHall;
    // área partilhada PaymentBox
    private final IPaymentBox_Control iPaymentBox;
    // Comunication Server 
    
    public AEControl(ICustomer_Control iCustomer, IManager_Control iManager, ICashier_Control iCashier, 
                     IOutsideHall_Control iOutsideHall, IEntranceHall_Control iEntranceHall, 
                     ICorridorHall_Control[] iCorridorHall, ICorridor_Control[] iCorridor, 
                     IPaymentHall_Control iPaymentHall, IPaymentBox_Control iPaymentBox) {
        this.iCustomer = iCustomer;
        this.iManager = iManager;
        this.iCashier = iCashier;
        this.iOutsideHall = iOutsideHall;
        this.iEntranceHall = iEntranceHall;
        this.iCorridorHall = iCorridorHall;
        this.iCorridor = iCorridor;
        this.iPaymentHall = iPaymentHall;
        this.iPaymentBox = iPaymentBox;
    }
    
    
    /**
    * Start a simulation based on the params received by the OCC
    * @param nCustomers The number of Costumers to be used in the simulation
    * @param socket The socket object to be used in the simulation
    * 
    */
    @Override
    public void startSimulation( int nCustomers, Socket socket ) {
        iOutsideHall.start();
        iEntranceHall.start();
        for (int i = 0; i < iCorridorHall.length; i++) {
            iCorridorHall[i].start();
            iCorridor[i].start();   
        }
        iPaymentHall.start();
        iPaymentBox.start();
        iCustomer.start(nCustomers);
        iManager.start(nCustomers);
        iCashier.start();
        System.out.println("CONTROL: Start");
    }
    
    /**
    * Stops the Simulation
    */
    @Override
    public void stopSimulation() {
        iManager.stop();
        iCashier.stop();
        iOutsideHall.stop();
        iEntranceHall.stop();
        for (int i = 0; i < iCorridorHall.length; i++) {
            iCorridorHall[i].stop();
            iCorridor[i].stop();   
        }
        iPaymentHall.stop();
        iPaymentBox.stop();
        System.out.println("CONTROL: Stop");
    }
    
    /**
    * Ends the Simulation
    */
    @Override
    public void endSimulation() {
        // terminar restantes Customers e outras AE
        iManager.end();
        iCashier.end();
        iOutsideHall.end();
        iEntranceHall.end();
        for (int i = 0; i < iCorridorHall.length; i++) {
            iCorridorHall[i].end();
            iCorridor[i].end();   
        }
        iPaymentHall.end();
        iPaymentBox.end();
        // terminar Customers em idle
        iCustomer.end();
        System.out.println("CONTROL: End");
    }
    
    /**
    * Suspends the Simulation
    */
    @Override
    public void suspendSimulation(){
        iManager.suspend();
        iCashier.suspend();
        iOutsideHall.suspend();
        iEntranceHall.suspend();
        for (int i = 0; i < iCorridorHall.length; i++) {
            iCorridorHall[i].suspend();
            iCorridor[i].suspend();   
        }
        iPaymentHall.suspend();
        iPaymentBox.suspend();
        System.out.println("CONTROL: Suspend");
    }
    
    /**
    * Resumes the Simulation
    */
    @Override
    public void resumeSimulation(){
        iManager.resume();
        iCashier.resume();
        iOutsideHall.resume();
        iEntranceHall.resume();
        for (int i = 0; i < iCorridorHall.length; i++) {
            iCorridorHall[i].resume();
            iCorridor[i].resume();   
        }
        iPaymentHall.resume();
        iPaymentBox.resume();
        System.out.println("CONTROL: Resume");
    }
    
    /**
    * Run the thread simulation, responsable to change the state of the simulation
    */
    @Override
    public void run() {
        // ver qual a msg recebida, executar comando e responder
        
        /*PARA TESTE*/    
        try {  
            sleep(500, 500);
        } catch (InterruptedException ex) {
        }
        
        startSimulation(50, null);
        try { 
            sleep(1500, 1500);
        } catch (InterruptedException ex) {
        }
        
        suspendSimulation();
        try { 
            sleep(1500, 1500);
        } catch (InterruptedException ex) {
        }
        
        resumeSimulation();
        try { 
            sleep(3000, 3000);
        } catch (InterruptedException ex) {
        }
        //endSimulation();
        stopSimulation();
        try { 
            sleep(3000, 3000);
        } catch (InterruptedException ex) {
        }
        
        startSimulation(40, null);
        /*FIM TESTE*/
    }
}
