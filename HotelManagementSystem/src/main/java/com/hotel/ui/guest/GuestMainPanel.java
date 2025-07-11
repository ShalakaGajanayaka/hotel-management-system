/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package main.java.com.hotel.ui.guest;

/**
 *
 * @author shalaka
 */
public class GuestMainPanel extends javax.swing.JPanel {

    /**
     * Creates new form GuestMainPanel
     */
    public GuestMainPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        guestsTabs = new javax.swing.JTabbedPane();
        guestManagementTab = new javax.swing.JPanel();
        guestManagementPanel1 = new main.java.com.hotel.ui.guest.GuestManagementPanel();
        newGuestTab = new javax.swing.JPanel();
        newGuestPanel1 = new main.java.com.hotel.ui.guest.NewGuestPanel();
        guestFeedbackTab = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(930, 660));

        javax.swing.GroupLayout guestManagementTabLayout = new javax.swing.GroupLayout(guestManagementTab);
        guestManagementTab.setLayout(guestManagementTabLayout);
        guestManagementTabLayout.setHorizontalGroup(
            guestManagementTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, guestManagementTabLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(guestManagementPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        guestManagementTabLayout.setVerticalGroup(
            guestManagementTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(guestManagementPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        guestsTabs.addTab("Guest Management", guestManagementTab);

        javax.swing.GroupLayout newGuestTabLayout = new javax.swing.GroupLayout(newGuestTab);
        newGuestTab.setLayout(newGuestTabLayout);
        newGuestTabLayout.setHorizontalGroup(
            newGuestTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, newGuestTabLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(newGuestPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        newGuestTabLayout.setVerticalGroup(
            newGuestTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(newGuestPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
        );

        guestsTabs.addTab("New Guest", newGuestTab);

        javax.swing.GroupLayout guestFeedbackTabLayout = new javax.swing.GroupLayout(guestFeedbackTab);
        guestFeedbackTab.setLayout(guestFeedbackTabLayout);
        guestFeedbackTabLayout.setHorizontalGroup(
            guestFeedbackTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 930, Short.MAX_VALUE)
        );
        guestFeedbackTabLayout.setVerticalGroup(
            guestFeedbackTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 625, Short.MAX_VALUE)
        );

        guestsTabs.addTab("Guest Feedback", guestFeedbackTab);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(guestsTabs)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(guestsTabs)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel guestFeedbackTab;
    private main.java.com.hotel.ui.guest.GuestManagementPanel guestManagementPanel1;
    private javax.swing.JPanel guestManagementTab;
    private javax.swing.JTabbedPane guestsTabs;
    private main.java.com.hotel.ui.guest.NewGuestPanel newGuestPanel1;
    private javax.swing.JPanel newGuestTab;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JTabbedPane getGuestsTabs() {
        return guestsTabs;
    }
    
    public NewGuestPanel getNewGuestPanel() {
        return newGuestPanel1;
    }

    public GuestManagementPanel getGuestManagementPanel() {
        return guestManagementPanel1;
    }
}
