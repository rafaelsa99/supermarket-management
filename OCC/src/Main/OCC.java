
package Main;

import Configurations.Configurations;
import javax.swing.table.DefaultTableModel;
import Communication.CClient;
import Communication.CServer;
import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;

/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class OCC extends javax.swing.JFrame {

    String simulationState;
    String[] costumersState;
    String costumerState; 
    String managerState;
    String cashierState;
    DefaultTableModel model;
    static Configurations confs = new Configurations();
    CClient cclient = null;
    CServer cserver;

    /**
     * Creates new form OCC
     * @param serverPort server port
     */
    public OCC(int serverPort) {
        initComponents();
        initOCC(serverPort);
    }

    private void initOCC(int serverPort) {
        this.simulationState = "END";
        cserver = new CServer(serverPort);
        cserver.openServer();
        cserver.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jnumberOfCostumers = new javax.swing.JSpinner();
        jLabelNCostumers = new javax.swing.JLabel();
        jLabelCMTimeOut = new javax.swing.JLabel();
        jcostumerMovementTimeOut = new javax.swing.JComboBox<>();
        jLabelSupervisoMode = new javax.swing.JLabel();
        jSupervisorMode = new javax.swing.JComboBox<>();
        jLabelPaymentTime = new javax.swing.JLabel();
        jSupervisorTimeOut = new javax.swing.JComboBox<>();
        jPaymentTimeOut = new javax.swing.JComboBox<>();
        jButtonResume = new javax.swing.JButton();
        jButtonStart = new javax.swing.JButton();
        jButtonSuspend = new javax.swing.JButton();
        jButtonStop = new javax.swing.JButton();
        jButtonEnd = new javax.swing.JButton();
        jTabbedStatus = new javax.swing.JTabbedPane();
        jScrollPaneCostumers = new javax.swing.JScrollPane();
        jTableCostumers = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableManager = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableCashier = new javax.swing.JTable();
        jManualSupervisor = new javax.swing.JButton();
        jLabelPort = new javax.swing.JLabel();
        jPortInput = new javax.swing.JTextField();
        jHostInput = new javax.swing.JTextField();
        jLabelHost = new javax.swing.JLabel();
        jButtonConnect = new javax.swing.JButton();
        jLabelConnectionStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        setSize(new java.awt.Dimension(0, 0));

        jnumberOfCostumers.setModel(new javax.swing.SpinnerNumberModel(10, 0, 99, 1));

        jLabelNCostumers.setText("Number Of Costumers");

        jLabelCMTimeOut.setText("Costumer Movement TimeOut");

        jcostumerMovementTimeOut
                .setModel(new javax.swing.DefaultComboBoxModel<>(Configurations.getMovementTimeOutPossibilities()));

        jcostumerMovementTimeOut.setSelectedIndex(1);

        jLabelSupervisoMode.setText("Supervisor Mode");

        jSupervisorMode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Auto", "Manual" }));

        jLabelPaymentTime.setText("Costumer Time Payment");

        jSupervisorTimeOut
                .setModel(new javax.swing.DefaultComboBoxModel<>(Configurations.getOperatingTimeOutPossibilities()));
        jcostumerMovementTimeOut.setSelectedIndex(1);

        jPaymentTimeOut
                .setModel(new javax.swing.DefaultComboBoxModel<>(Configurations.getMovementTimeOutPossibilities()));
        jcostumerMovementTimeOut.setSelectedIndex(1);

        jButtonResume.setText("Resume");

        jButtonStart.setText("Start");

        jButtonSuspend.setText("Suspend");

        jButtonStop.setText("Stop");

        jButtonEnd.setText("End");
        
        jManualSupervisor.setText("Next Costumer");
        
        jLabelPort.setText("Port");

        jPortInput.setText("6669");

        jHostInput.setText("localhost");

        jLabelHost.setText("Host");

        jButtonConnect.setText("Connect");

        jLabelConnectionStatus.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jManualSupervisor.setEnabled(false);
        jButtonResume.setEnabled(false);
        jButtonSuspend.setEnabled(false);
        jButtonStop.setEnabled(false);
        jButtonStart.setEnabled(false);
        
        jButtonResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResumeActionPerformed(evt);
            }
        });


        jButtonStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStartActionPerformed(evt);
            }
        });

        jButtonSuspend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSuspendActionPerformed(evt);
            }
        });

        jButtonStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStopActionPerformed(evt);
            }
        });

        jButtonEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEndActionPerformed(evt);
            }
        });


        jManualSupervisor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jManualSupervisorActionPerformed(evt);
            }
        });
        
        jSupervisorMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSupervisorModeActionPerformed(evt);
            }
        });

        jButtonConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConnectActionPerformed(evt);
            }
        });

        jTableCostumers.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] { },
                new String[] { "Identification", "State" }));
        jScrollPaneCostumers.setViewportView(jTableCostumers);

        jTabbedStatus.addTab("Costumers", jScrollPaneCostumers);

        jTableManager.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] { },
                new String[] { "Identification", "State" }));
        jScrollPane2.setViewportView(jTableManager);

        jTabbedStatus.addTab("Manager", jScrollPane2);

        jTableCashier.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] { },
                new String[] { "Identification", "State" }));
        jScrollPane3.setViewportView(jTableCashier);

        jTabbedStatus.addTab("Cashier", jScrollPane3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabelCMTimeOut)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jcostumerMovementTimeOut, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelConnectionStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jButtonConnect))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabelSupervisoMode)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jSupervisorMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jSupervisorTimeOut, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jManualSupervisor))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jButtonStart, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jButtonResume, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jButtonSuspend, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jButtonStop, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jButtonEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabelPaymentTime)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jPaymentTimeOut, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabelNCostumers)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jnumberOfCostumers, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelHost)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jHostInput, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(12, 12, 12)
                            .addComponent(jLabelPort)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jPortInput, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jnumberOfCostumers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelNCostumers)
                        .addComponent(jLabelPort)
                        .addComponent(jPortInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jHostInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelHost))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelCMTimeOut)
                        .addComponent(jcostumerMovementTimeOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonConnect)
                        .addComponent(jLabelConnectionStatus))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelPaymentTime)
                        .addComponent(jPaymentTimeOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(16, 16, 16)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jManualSupervisor, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelSupervisoMode)
                        .addComponent(jSupervisorMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSupervisorTimeOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonResume, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonStart, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonSuspend, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonStop, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addComponent(jTabbedStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                    .addContainerGap())
            );

        pack();
    }// </editor-fold>                                             

    private void jManualSupervisorActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jManualSupervisorActionPerformed
        // TODO add your handling code here:
        //System.out.println("Next");
        this.cclient.sendMessage("NX");
    }// GEN-LAST:event_jManualSupervisorActionPerformed

    private void jButtonStartActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonStartActionPerformed
        // TODO add your handling code here:
        this.simulationState = "START";
        //System.out.println("Start");
        // Socket Server that update 
        
        jButtonStart.setEnabled(false);
        jButtonSuspend.setEnabled(true);
        jButtonStop.setEnabled(true);
        
        confs.setTotalNumberOfCostumers(Integer.parseInt(jnumberOfCostumers.getValue().toString()));
        confs.setMovementTimeOut(Integer.parseInt(jcostumerMovementTimeOut.getSelectedItem().toString()));
        confs.setTimeToPay(Integer.parseInt(jPaymentTimeOut.getSelectedItem().toString()));
        confs.setOperatingTimeOut(Integer.parseInt(jSupervisorTimeOut.getSelectedItem().toString()));
        
        if(jSupervisorMode.getSelectedItem().toString().equals("Manual")){
            confs.setOperatingMode(false);
        }else{
            confs.setOperatingMode(true);
        }
        
        System.out.println(confs.getConfigurations());       
        for(int i = 0; i < confs.getTotalNumberOfCostumers(); i++){
            initializeState("CT", i, "Idle" );
        }
        initializeState("MA", 0, "Idle" );
        initializeState("CH", 0, "Idle" );         
        this.cclient.sendMessage(confs.getConfigurations());
        
    }// GEN-LAST:event_jButtonStartActionPerformed

    private void jButtonResumeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonResumeActionPerformed
        // TODO add your handling code here:
        this.simulationState = "RESUME";
        System.out.println("Resume");
        jButtonResume.setEnabled(false);
        jButtonSuspend.setEnabled(true);
        this.cclient.sendMessage("RE");
    }// GEN-LAST:event_jButtonResumeActionPerformed
    
    
    private void jButtonSuspendActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonSuspendActionPerformed
        // TODO add your handling code here:
        this.simulationState = "SUSPEND";
        System.out.println("Suspend");
        jButtonResume.setEnabled(true);
        jButtonSuspend.setEnabled(false);
        this.cclient.sendMessage("SU");
    }// GEN-LAST:event_jButtonSuspendActionPerformed
    
    
    private void jButtonStopActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonStopActionPerformed
        // TODO add your handling code here:
        this.simulationState = "STOP";
        System.out.println("Stop");
        jButtonStop.setEnabled(false);
        jButtonResume.setEnabled(false);
        jButtonSuspend.setEnabled(false);
        jButtonStart.setEnabled(true); 
        cleanTables();
        this.cclient.sendMessage("ST");
        //updateState("Costumer", 0, managerState.CORRIDOR_HALL_3 );
    }// GEN-LAST:event_jButtonStopActionPerformed
    
    private void jButtonEndActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonEndActionPerformed
        // TODO add your handling code here:
        this.simulationState = "END";
        System.out.println("End");                 
        this.cclient.sendMessage("ED");
        this.cserver.closeServer();  
        System.exit(0);
    }// GEN-LAST:event_jButtonEndActionPerformed
    
    private void jButtonConnectActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonEndActionPerformed
        // TODO add your handling code here:
        this.cclient = new CClient(jHostInput.getText(), Integer.parseInt(jPortInput.getText()));
        if(this.cclient.openServer()){
            jButtonStart.setEnabled(true);
            jButtonConnect.setEnabled(false);
            jLabelConnectionStatus.setText("Connected!");
        }else{
            jLabelConnectionStatus.setText("Failed to Connect!"); 
        }
    }// GEN-LAST:event_jButtonEndActionPerformed

   public static void updateState(String tab, String state, int id){
        switch(tab){
            case "CT":
                try {
                    SwingUtilities.invokeAndWait(() -> {
                        jTableCostumers.setValueAt(state, id, 1);
                    });
                } catch (InterruptedException | InvocationTargetException ex) {
                    System.out.println(ex.toString());
                }
                break;
            case "MA":
                try {
                    SwingUtilities.invokeAndWait(() -> {
                        jTableManager.setValueAt(state, id, 1);
                    });
                } catch (InterruptedException | InvocationTargetException ex) {
                    System.out.println(ex.toString());   
                }
                break;
            case "CH":
                try {
                    SwingUtilities.invokeAndWait(() -> {
                        jTableCashier.setValueAt(state, id, 1);
                    });
                } catch (InterruptedException | InvocationTargetException ex) {
                    System.out.println(ex.toString());   
                }
            default:
                break;
        }
   }
   
    public static void updateState(String tab, String state){
        switch(tab){
            case "CT":
                try {
                    SwingUtilities.invokeAndWait(() -> {
                    jTableCostumers.setValueAt(state, 0, 1);
                    });
                } catch (InterruptedException | InvocationTargetException ex) {
                    System.out.println(ex.toString());   
                }
                
                break;
            case "MA":
                try {
                    SwingUtilities.invokeAndWait(() -> {
                    jTableManager.setValueAt(state, 0, 1);
                    });
                } catch (InterruptedException | InvocationTargetException ex) {
                    System.out.println(ex.toString());   
                }

                break;
            case "CH":
                try {
                    SwingUtilities.invokeAndWait(() -> {
                    jTableCashier.setValueAt(state, 0, 1);
                    });
                } catch (InterruptedException | InvocationTargetException ex) {
                    System.out.println(ex.toString());   
                }
                break;
            default:
                break;
        }
   }
   
    public void initializeState(String tab, int id, Object state){
        switch(tab){
            case "CT":
                model = (DefaultTableModel) jTableCostumers.getModel();
                model.addRow(new Object[]{id,state});
                break;
            case "MA":
                model = (DefaultTableModel) jTableManager.getModel();
                model.addRow(new Object[]{id,state});
                break;
            case "CH":
                model = (DefaultTableModel) jTableCashier.getModel();
                model.addRow(new Object[]{id,state});
                break;
            default:
                break;
        }
   }
    
   public void cleanTables(){
        model = (DefaultTableModel) jTableCostumers.getModel();
        model.setRowCount(0);
        model = (DefaultTableModel) jTableManager.getModel();
        model.setRowCount(0);
        model = (DefaultTableModel) jTableCashier.getModel();
        model.setRowCount(0);
   }
   
    
    private void jSupervisorModeActionPerformed(java.awt.event.ActionEvent evt){
        if(jSupervisorMode.getSelectedItem().toString().equals("Manual")){
            jSupervisorTimeOut.setEnabled(false);
            jManualSupervisor.setEnabled(true);
        }else{
            jSupervisorTimeOut.setEnabled(true);
            jManualSupervisor.setEnabled(false);
        }
        if(!this.simulationState.equals("END")){
            this.cclient.sendMessage(confs.getOperatingMode());
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code">
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OCC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OCC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OCC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OCC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                int serverPort = Configurations.SERVER_PORT;
                if(args.length == 1){
                    try{
                        serverPort = Integer.parseInt(args[0]);
                    }catch (NumberFormatException ex){
                        System.out.println("Invalid parameter!\nParameters: [Optional: serverPort]\n");
                    }
                } else if (args.length > 1){
                    System.out.println("Invalid parameters!\nParameters: [Optional: serverPort]\n");
                }
                new OCC(serverPort).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton jButtonConnect;
    private javax.swing.JButton jButtonEnd;
    private javax.swing.JButton jButtonResume;
    private javax.swing.JButton jButtonStart;
    private javax.swing.JButton jButtonStop;
    private javax.swing.JButton jButtonSuspend;
    private javax.swing.JTextField jHostInput;
    private javax.swing.JLabel jLabelCMTimeOut;
    private javax.swing.JLabel jLabelConnectionStatus;
    private javax.swing.JLabel jLabelHost;
    private javax.swing.JLabel jLabelPort;
    private javax.swing.JLabel jLabelNCostumers;
    private javax.swing.JLabel jLabelPaymentTime;
    private javax.swing.JLabel jLabelSupervisoMode;
    private javax.swing.JButton jManualSupervisor;
    private javax.swing.JComboBox<Integer> jPaymentTimeOut;
    private javax.swing.JTextField jPortInput;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPaneCostumers;
    private javax.swing.JComboBox<String> jSupervisorMode;
    private javax.swing.JComboBox<Integer> jSupervisorTimeOut;
    private javax.swing.JTabbedPane jTabbedStatus;
    private static javax.swing.JTable jTableCashier;
    private static javax.swing.JTable jTableCostumers;
    private static javax.swing.JTable jTableManager;
    private javax.swing.JComboBox<Integer> jcostumerMovementTimeOut;
    private javax.swing.JSpinner jnumberOfCostumers;
    // End of variables declaration
}
