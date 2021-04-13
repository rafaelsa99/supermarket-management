

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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta entidade é responsável por fazer executar os comandos originados no OCC
 * tais como start, stop, end, etc....
 * 
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class AEControl extends Thread {

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
    
    public void start( int nCustomers, Socket socket ) {
        iCustomer.start(nCustomers);
        iManager.start(nCustomers);
    }
    public void end() {
        // terminar Customers em idle
        iCustomer.end();
        // terminar restantes Customers e outras AE
    }
    // mais comandos 
    
    
    @Override
    public void run() {
        // ver qual a msg recebida, executar comando e responder
        
        /*PARA TESTE*/    
        try {  
            sleep(500, 500);
        } catch (InterruptedException ex) {
        }
        System.out.println("CONTROL: Start");
        start(20, null);
        /*FIM TESTE*/
    }
}
