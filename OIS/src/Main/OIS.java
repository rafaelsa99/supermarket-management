
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

        jEntranceHall = new javax.swing.JScrollPane();
        jListEntranceHall = new javax.swing.JList<>();
        jCorridorHall2 = new javax.swing.JScrollPane();
        jListCorridorHall2 = new javax.swing.JList<>();
        jOutsideHall = new javax.swing.JScrollPane();
        jListOutsideHall = new javax.swing.JList<>();
        jCorridorHall1 = new javax.swing.JScrollPane();
        jListCorridorHall1 = new javax.swing.JList<>();
        jCorridorHall3 = new javax.swing.JScrollPane();
        jListCorridorHall3 = new javax.swing.JList<>();
        jCorridor1 = new javax.swing.JScrollPane();
        jListCorridor1 = new javax.swing.JList<>();
        jCorridor2 = new javax.swing.JScrollPane();
        jListCorridor2 = new javax.swing.JList<>();
        jLabelTitle = new javax.swing.JLabel();
        jCorridor3 = new javax.swing.JScrollPane();
        jListCorridor3 = new javax.swing.JList<>();
        jPaymentHall = new javax.swing.JScrollPane();
        jListPaymentHall = new javax.swing.JList<>();
        jPaymentBox = new javax.swing.JScrollPane();
        jListPaymentBox = new javax.swing.JList<>();
        jLabelOutsideHall = new javax.swing.JLabel();
        jLabelEntranceHall = new javax.swing.JLabel();
        jLabelCorridorHall = new javax.swing.JLabel();
        jLabelCorridor = new javax.swing.JLabel();
        jLabelPaymentHall = new javax.swing.JLabel();
        jLabelPaymentBox = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jListEntranceHall.setModel(new javax.swing.AbstractListModel<javax.swing.JLabel>() {
            javax.swing.JLabel[] icons = { };
            public int getSize() { return icons.length; }
            public javax.swing.JLabel getElementAt(int i) { return icons[i]; }
        });
        jEntranceHall.setViewportView(jListEntranceHall);

        jListCorridorHall2.setModel(new javax.swing.AbstractListModel<javax.swing.JLabel>() {
            javax.swing.JLabel[] icons = { };
            public int getSize() { return icons.length; }
            public javax.swing.JLabel getElementAt(int i) { return icons[i]; }
        });
        jCorridorHall2.setViewportView(jListCorridorHall2);

        jListOutsideHall.setModel(new javax.swing.AbstractListModel<javax.swing.JLabel>() {
            javax.swing.JLabel[] icons = { };
            public int getSize() { return icons.length; }
            public javax.swing.JLabel getElementAt(int i) { return icons[i]; }
        });
        jOutsideHall.setViewportView(jListOutsideHall);

        jListCorridorHall1.setModel(new javax.swing.AbstractListModel<javax.swing.JLabel>() {
            javax.swing.JLabel[] icons = { };
            public int getSize() { return icons.length; }
            public javax.swing.JLabel getElementAt(int i) { return icons[i]; }
        });
        jCorridorHall1.setViewportView(jListCorridorHall1);

        jListCorridorHall3.setModel(new javax.swing.AbstractListModel<javax.swing.JLabel>() {
            javax.swing.JLabel[] icons = { };
            public int getSize() { return icons.length; }
            public javax.swing.JLabel getElementAt(int i) { return icons[i]; }
        });
        jCorridorHall3.setViewportView(jListCorridorHall3);

        jListCorridor1.setModel(new javax.swing.AbstractListModel<javax.swing.JLabel>() {
            javax.swing.JLabel[] icons = { };
            public int getSize() { return icons.length; }
            public javax.swing.JLabel getElementAt(int i) { return icons[i]; }
        });
        jCorridor1.setViewportView(jListCorridor1);

        jListCorridor2.setModel(new javax.swing.AbstractListModel<javax.swing.JLabel>() {
            javax.swing.JLabel[] icons = { };
            public int getSize() { return icons.length; }
            public javax.swing.JLabel getElementAt(int i) { return icons[i]; }
        });
        jCorridor2.setViewportView(jListCorridor2);

        jLabelTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelTitle.setText("OurMarket in-Site Market");

        jListCorridor3.setModel(new javax.swing.AbstractListModel<javax.swing.JLabel>() {
            javax.swing.JLabel[] icons = { };
            public int getSize() { return icons.length; }
            public javax.swing.JLabel getElementAt(int i) { return icons[i]; }
        });
        jCorridor3.setViewportView(jListCorridor3);

        jListPaymentHall.setModel(new javax.swing.AbstractListModel<javax.swing.JLabel>() {
            javax.swing.JLabel[] icons = { };
            public int getSize() { return icons.length; }
            public javax.swing.JLabel getElementAt(int i) { return icons[i]; }
        });
        jPaymentHall.setViewportView(jListPaymentHall);

        jListPaymentBox.setModel(new javax.swing.AbstractListModel<javax.swing.JLabel>() {
            javax.swing.JLabel[] icons = { };
            public int getSize() { return icons.length; }
            public javax.swing.JLabel getElementAt(int i) { return icons[i]; }
        });
        jPaymentBox.setViewportView(jListPaymentBox);

        jLabelOutsideHall.setText("Outside Hall");

        jLabelEntranceHall.setText("Entrance Hall");

        jLabelCorridorHall.setText("Corridor Hall");

        jLabelCorridor.setText("Corridor");

        jLabelPaymentHall.setText("Payment Hall");

        jLabelPaymentBox.setText("Payment Box");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jOutsideHall, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jEntranceHall, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCorridorHall3, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCorridorHall1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCorridorHall2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCorridor2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPaymentHall, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPaymentBox, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCorridor1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelTitle)
                            .addComponent(jCorridor3, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabelOutsideHall)
                .addGap(67, 67, 67)
                .addComponent(jLabelEntranceHall)
                .addGap(82, 82, 82)
                .addComponent(jLabelCorridorHall)
                .addGap(167, 167, 167)
                .addComponent(jLabelCorridor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelPaymentHall)
                .addGap(114, 114, 114)
                .addComponent(jLabelPaymentBox)
                .addGap(50, 50, 50))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jEntranceHall, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jOutsideHall, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jCorridorHall1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jCorridorHall2)
                                    .addComponent(jCorridor2)
                                    .addComponent(jPaymentHall)
                                    .addComponent(jPaymentBox)))
                            .addComponent(jCorridor1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCorridorHall3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jCorridor3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelOutsideHall)
                    .addComponent(jLabelEntranceHall)
                    .addComponent(jLabelCorridorHall)
                    .addComponent(jLabelCorridor)
                    .addComponent(jLabelPaymentHall)
                    .addComponent(jLabelPaymentBox))
                .addContainerGap(33, Short.MAX_VALUE))
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
    private javax.swing.JScrollPane jCorridor1;
    private javax.swing.JScrollPane jCorridor2;
    private javax.swing.JScrollPane jCorridor3;
    private javax.swing.JScrollPane jCorridorHall1;
    private javax.swing.JScrollPane jCorridorHall2;
    private javax.swing.JScrollPane jCorridorHall3;
    private javax.swing.JScrollPane jEntranceHall;
    private javax.swing.JLabel jLabelCorridor;
    private javax.swing.JLabel jLabelCorridorHall;
    private javax.swing.JLabel jLabelEntranceHall;
    private javax.swing.JLabel jLabelOutsideHall;
    private javax.swing.JLabel jLabelPaymentBox;
    private javax.swing.JLabel jLabelPaymentHall;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JList<javax.swing.JLabel> jListCorridor1;
    private javax.swing.JList<javax.swing.JLabel> jListCorridor2;
    private javax.swing.JList<javax.swing.JLabel> jListCorridor3;
    private javax.swing.JList<javax.swing.JLabel> jListCorridorHall1;
    private javax.swing.JList<javax.swing.JLabel> jListCorridorHall2;
    private javax.swing.JList<javax.swing.JLabel> jListCorridorHall3;
    private javax.swing.JList<javax.swing.JLabel> jListEntranceHall;
    private javax.swing.JList<javax.swing.JLabel> jListOutsideHall;
    private javax.swing.JList<javax.swing.JLabel> jListPaymentBox;
    private javax.swing.JList<javax.swing.JLabel> jListPaymentHall;
    private javax.swing.JScrollPane jOutsideHall;
    private javax.swing.JScrollPane jPaymentBox;
    private javax.swing.JScrollPane jPaymentHall;
    // End of variables declaration//GEN-END:variables
}
