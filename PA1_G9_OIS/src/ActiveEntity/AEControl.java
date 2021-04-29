

package ActiveEntity;

import Communication.CServer;
import Main.OIS;
import SACashier.ICashier_Control;
import SACorridor.ICorridor_Control;
import SACorridorHall.ICorridorHall_Control;
import SACustomer.ICustomer_Control;
import SAEntranceHall.IEntranceHall_Control;
import SAManager.IManager_Control;
import SAOutsideHall.IOutsideHall_Control;
import SAPaymentBox.IPaymentBox_Control;
import SAPaymentHall.IPaymentHall_Control;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Represents the Control Thread.
 * Responsible for executing the commands received by the OCC.
 * Ex: start, stop, end, etc....
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */

public class AEControl extends Thread implements IControl{

    /**
    * Customer Shared Area
    */
    private final ICustomer_Control iCustomer;
   /**
    * Manager Shared Area
    */
    private final IManager_Control iManager;
    /**
    * Cashier Shared Area
    */
    private final ICashier_Control iCashier;
    /**
    * OutsideHall Shared Area
    */
    private final IOutsideHall_Control iOutsideHall;
    /**
    * EntranceHall Shared Area
    */
    private final IEntranceHall_Control iEntranceHall;
    /**
    * CorridorHall Shared Area
    */
    private final ICorridorHall_Control[] iCorridorHall;
    /**
    * Corridor Shared Area
    */
    private final ICorridor_Control[] iCorridor;
    /**
    * PaymentHall Shared Area
    */
    private final IPaymentHall_Control iPaymentHall;
    /**
    * PaymentBox Shared Area
    */
    private final IPaymentBox_Control iPaymentBox;
    /**
    * Communication Server Object
    */
    private final CServer cServer;
    /**
    *  End thread flag
    */
    private boolean end;
    
    
    /**
    * Entity Constructor
    * @param iCustomer Customer Shared Area Interface
    * @param iManager Manager Shared Area Interface
    * @param iCashier Cashier Shared Area Interface
    * @param iOutsideHall Outside Hall Shared Area Interface
    * @param iEntranceHall Entrance Hall Shared Area Interface
    * @param iCorridorHall Corridor Hall List Shared Area Interfaces
    * @param iCorridor Corridor List Shared Area Interface
    * @param iPaymentHall Payment Hall Shared Area Interface
    * @param iPaymentBox Payemnt Box Shared Area Interface
    * @param cs Socket Server Communication Object
    */
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
        this.end = false;
    }
    
    
    /**
    * Start a simulation based on the parameters received by the OCC
    * @param nCustomers The number of Costumers to be used in the simulation
     * @param movementTimeout customer movement timeout
     * @param paymentTimeout customer payment timeout
     * @param operationMode manager operation mode
     * @param operationTimeout manager operation timeout
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
    }
    
    /**
    * Stops the Simulation.
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
    }
    
    /**
    * Ends the Simulation.
    */
    @Override
    public void endSimulation() {
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
        iCustomer.end();
        OIS.endSimulation();
    }
    
    /**
    * Suspends the Simulation.
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
    }
    
    /**
    * Resumes the Simulation.
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
    }
    
    /**
     * Manager step, if operation mode is manual.
     */
    @Override
    public void managerStep(){
        iManager.step();
    }
    /**
     * Updates the manager operation mode to auto
     * @param timeout manager timeout
     */
    @Override
    public void managerAuto(int timeout){
        iManager.auto(timeout);
    }
    /**
     * Updates the manager operation mode to manual
     */
    @Override
    public void managerManual(){
        iManager.manual();
    }
    /**
    * Run the thread simulation, responsible to change the state of the simulation.
    */
    @Override
    public void run() {
        Socket socket;
        while(!end){
            socket = cServer.awaitMessages();
            new ClientThread(socket).start();
        }
        cServer.closeServer();
    }
    
    
    /**
    * Responsable Process Message received by the OCC
    */
    class ClientThread extends Thread{
        /**
        * Socket Object
        */
        private final Socket socket;
        /**
        * Client Thread Constructor
        * @param socket Receive Client socket Object 
        */
        public ClientThread(Socket socket) {
            this.socket = socket;
        }

        /**
        * Life cycle of the ClientThread Thread.
        */
        @Override
        public void run() {
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                socket) {
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    processCommand(inputLine);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        
        /**
        * Responsable to process input messages and call each task
        */
        private void processCommand(String command){
            String type = command.substring(0, 2);
            switch(type){
                case "ED": end = true;
                    endSimulation();
                    break;
                case "RE": resumeSimulation();
                    break;
                case "SU": suspendSimulation();
                    break;
                case "ST": stopSimulation();
                    break;
                case "NX": managerStep();
                    break;
                case "OM": String[] config = command.split("\\|");
                    boolean omo = Boolean.parseBoolean((config[0].split(":"))[1]);
                    int oti = Integer.parseInt((config[1].split(":"))[1]);
                    if(omo) managerAuto(oti);
                    else managerManual();
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
