
package Main;

import ActiveEntity.AECashier;
import ActiveEntity.AEControl;
import ActiveEntity.AECustomer;
import ActiveEntity.AEManager;
import Common.Configurations;
import Common.STCustomer;
import SACashier.ICashier_Cashier;
import SACashier.ICashier_Customer;
import SACashier.SACashier;
import SACorridor.ICorridor_Customer;
import SACorridor.SACorridor;
import SACorridorHall.ICorridorHall_Customer;
import SACorridorHall.SACorridorHall;
import SACustomer.SACustomer;
import SAOutsideHall.IOutsideHall_Customer;
import SAOutsideHall.SAOutsideHall;
import SACustomer.ICustomer_Customer;
import SAEntranceHall.IEntranceHall_Customer;
import SAEntranceHall.IEntranceHall_Manager;
import SAEntranceHall.SAEntranceHall;
import SAManager.IManager_Customer;
import SAManager.IManager_Manager;
import SAManager.SAManager;
import SAOutsideHall.IOutsideHall_Manager;
import SAPaymentBox.IPaymentBox_Cashier;
import SAPaymentBox.IPaymentBox_Customer;
import SAPaymentBox.SAPaymentBox;
import SAPaymentHall.IPaymentHall_Cashier;
import SAPaymentHall.IPaymentHall_Customer;
import SAPaymentHall.SAPaymentHall;

/*
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class OIS extends javax.swing.JFrame {

    /**
     * Creates new form OIS
     */
    public OIS() {
        initComponents();
        this.setVisible(true);
        initOIS();
    }

    private void initOIS() {        
        final STCustomer[] corridorNumbers = {STCustomer.CORRIDOR_1, 
                                             STCustomer.CORRIDOR_2, 
                                             STCustomer.CORRIDOR_3};
        final SACustomer saCustomer = new SACustomer(Configurations.MAX_CUSTOMERS);
        final SAManager saManager = new SAManager(Configurations.N_CORRIDOR, 
                                                  Configurations.SIZE_ENTRANCE_HALL, 
                                                  Configurations.SIZE_CORRIDOR_HALL,
                                                  Configurations.TIMEOUT_AUTHORIZATION);
        final SACashier saCashier = new SACashier(Configurations.SIZE_PAYMENT_HALL);
        final SAOutsideHall saOutsideHall = new SAOutsideHall(Configurations.MAX_CUSTOMERS);
        final SAEntranceHall saEntranceHall = new SAEntranceHall(Configurations.SIZE_ENTRANCE_HALL);
        final SACorridorHall[] saCorridorHall = new SACorridorHall[Configurations.N_CORRIDOR];
        final SACorridor[] saCorridor = new SACorridor[Configurations.N_CORRIDOR];
        for (int i = 0; i < Configurations.N_CORRIDOR; i++) {
            saCorridorHall[i] = new SACorridorHall(Configurations.SIZE_CORRIDOR_HALL, corridorNumbers[i], Configurations.SIZE_CORRIDOR);
            saCorridor[i] = new SACorridor(Configurations.SIZE_CORRIDOR, Configurations.SIZE_PAYMENT_HALL,
                                           Configurations.CORRIDOR_STEPS, Configurations.TIMEOUT_MOVEMENT, 
                                           Configurations.N_CORRIDOR, corridorNumbers[i], Configurations.MAX_CUSTOMERS);
        }
        final SAPaymentHall saPaymentHall = new SAPaymentHall(Configurations.SIZE_PAYMENT_HALL);
        final SAPaymentBox sAPaymentBox = new SAPaymentBox(Configurations.TIMEOUT_PAYMENT);

        final AECustomer[] aeCustomer = new AECustomer[Configurations.MAX_CUSTOMERS];
        for (int i = 0; i < Configurations.MAX_CUSTOMERS; i++) {
            aeCustomer[i] = new AECustomer(i, (ICustomer_Customer) saCustomer,
                    (IOutsideHall_Customer) saOutsideHall, (IEntranceHall_Customer) saEntranceHall,
                    (ICorridorHall_Customer[]) saCorridorHall, (ICorridor_Customer[]) saCorridor,
                    (IPaymentHall_Customer) saPaymentHall, (IPaymentBox_Customer) sAPaymentBox,
                    (ICashier_Customer) saCashier, (IManager_Customer) saManager);
            aeCustomer[i].start();
        }
        final AEManager aeManager = new AEManager((IManager_Manager) saManager, (IOutsideHall_Manager) saOutsideHall,
                                                   (IEntranceHall_Manager) saEntranceHall);
        aeManager.start();
        final AECashier aeCashier = new AECashier((IPaymentHall_Cashier) saPaymentHall, 
                                                  (ICashier_Cashier) saCashier,
                                                  (IPaymentBox_Cashier) sAPaymentBox);
        aeCashier.start();
        final AEControl aeControl = new AEControl(saCustomer, saManager, saCashier,
                                                  saOutsideHall, saEntranceHall, saCorridorHall, 
                                                  saCorridor, saPaymentHall, sAPaymentBox);
        aeControl.start();
        
        try {
            aeManager.join();
            aeCashier.join();
            for (int i = 0; i < Configurations.MAX_CUSTOMERS; i++) {
                aeCustomer[i].join();
            }
            aeControl.join();
        } catch (InterruptedException ex) {
            System.err.println(ex.toString());
        }
        System.out.println("MAIN: ALL ENDED");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OIS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OIS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OIS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OIS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OIS();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
