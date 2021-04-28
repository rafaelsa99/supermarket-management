

package ActiveEntity;

import Communication.CServer;
import SACashier.ICashier_Control;
import SACorridor.ICorridor_Control;
import SACorridorHall.ICorridorHall_Control;
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
    // communication server
    private final CServer cServer;
    
    public AEControl(ICustomer_Control iCustomer, IManager_Control iManager, ICashier_Control iCashier, 
                     IOutsideHall_Control iOutsideHall, IEntranceHall_Control iEntranceHall, 
                     ICorridorHall_Control[] iCorridorHall, ICorridor_Control[] iCorridor, 
                     IPaymentHall_Control iPaymentHall, IPaymentBox_Control iPaymentBox, CServer cs) {
        this.iCustomer = iCustomer;
        this.iManager = iManager;
        this.iCashier = iCashier;
        this.iOutsideHall = iOutsideHall;
        this.iEntranceHall = iEntranceHall;
        this.iCorridorHall = iCorridorHall;
        this.iCorridor = iCorridor;
        this.iPaymentHall = iPaymentHall;
        this.iPaymentBox = iPaymentBox;
        this.cServer = cs;
    }
    
    
    /**
    * Start a simulation based on the params received by the OCC
    * @param nCustomers The number of Costumers to be used in the simulation
    * @param socket The socket object to be used in the simulation
    * 
    */
    @Override
    public void startSimulation( int nCustomers, int movementTimeout, int paymentTimeout, boolean operationMode, int operationTimeout ) {
        iOutsideHall.start();
        iEntranceHall.start();
        for (int i = 0; i < iCorridorHall.length; i++) {
            iCorridorHall[i].start();
            iCorridor[i].start();   
            iCorridor[i].setTimeoutMovement(movementTimeout);
        }
        iPaymentHall.start();
        iPaymentBox.setTimeoutPayment(paymentTimeout);
        iPaymentBox.start();
        iCustomer.start(nCustomers);
        if(operationMode)
            iManager.auto(operationTimeout);
        else
            iManager.manual();
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
    public void managerStep(){
        iManager.step();
    }
    
    @Override
    public void run() {
        String msg;
        startSimulation(20, 100, 100, true, 100);
        cServer.awaitConnection();
        while(true){
            msg = cServer.awaitMessages();
            if(msg.substring(0, 2).equals("ED"))
                break;
            new Command(msg).start();
        cServer.closeServer();
        }
    }
    
    class Command extends Thread{
        
        private final String command;

        public Command(String command) {
            this.command = command;
        }

        @Override
        public void run() {
            String type = command.substring(0, 2);
            switch(type){
                case "RE": resumeSimulation();
                    break;
                case "SU": suspendSimulation();
                    break;
                case "ST": stopSimulation();
                    break;
                case "NX": managerStep();
                    break;
                case "NC":
                    String[] configs = command.split("\\|");
                    int nc = Integer.parseInt((configs[0].split(":"))[1]);
                    int ct = Integer.parseInt((configs[1].split(":"))[1]);
                    int pt = Integer.parseInt((configs[2].split(":"))[1]);
                    boolean om = Boolean.parseBoolean((configs[3].split(":"))[1]);
                    int ot = Integer.parseInt((configs[4].split(":"))[1]);
                    startSimulation(nc, ct, pt, om, ot);
                    break;
            }
        } 
    }
}
