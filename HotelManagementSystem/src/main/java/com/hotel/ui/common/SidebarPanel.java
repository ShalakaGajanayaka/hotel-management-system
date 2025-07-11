/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package main.java.com.hotel.ui.common;

import main.java.com.hotel.ui.booking.BookingsMainPanel;
import main.java.com.hotel.ui.dashboard.DashboardFrame;
import main.java.com.hotel.ui.room.RoomMainPanel;

/**
 *
 * @author shalaka
 */
public class SidebarPanel extends javax.swing.JPanel {

    private DashboardFrame parent;

    /**
     * Creates new form SidebarPanel
     */
    public SidebarPanel() {
        initComponents();
    }

    public SidebarPanel(DashboardFrame parent) {
        this.parent = parent;
        initComponents();
    }

    public void setParent(DashboardFrame parent) {
        this.parent = parent;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dashboardBtnPanel = new javax.swing.JPanel();
        dashboardBtnLabel = new javax.swing.JLabel();
        bookingBtnPanel = new javax.swing.JPanel();
        bookingBtnLabel = new javax.swing.JLabel();
        roomsBtnPanel = new javax.swing.JPanel();
        roomsBtnLabel = new javax.swing.JLabel();
        servicesBtnPanel = new javax.swing.JPanel();
        servicesBtnLabel = new javax.swing.JLabel();
        employeesBtnPanel = new javax.swing.JPanel();
        employeesBtnLabel = new javax.swing.JLabel();
        guestBtnPanel = new javax.swing.JPanel();
        guestsBtnLabel = new javax.swing.JLabel();
        reportsBtnPanel = new javax.swing.JPanel();
        reportsBtnLabel = new javax.swing.JLabel();
        settingsBtnPannel = new javax.swing.JPanel();
        settingsBtnLabel = new javax.swing.JLabel();
        paymentsBtnPanel = new javax.swing.JPanel();
        paymentsBtnLabel = new javax.swing.JLabel();
        inventoryBtnPanel = new javax.swing.JPanel();
        inventoryBtnLabel = new javax.swing.JLabel();
        maintenanceBtnPanel = new javax.swing.JPanel();
        maintenanceBtnLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(220, 0));

        dashboardBtnPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        dashboardBtnLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        dashboardBtnLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        dashboardBtnLabel.setText("Dashboard");
        dashboardBtnLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashboardBtnLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout dashboardBtnPanelLayout = new javax.swing.GroupLayout(dashboardBtnPanel);
        dashboardBtnPanel.setLayout(dashboardBtnPanelLayout);
        dashboardBtnPanelLayout.setHorizontalGroup(
            dashboardBtnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardBtnPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dashboardBtnLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        dashboardBtnPanelLayout.setVerticalGroup(
            dashboardBtnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardBtnPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dashboardBtnLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bookingBtnPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        bookingBtnLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        bookingBtnLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        bookingBtnLabel.setText("Bookings");
        bookingBtnLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bookingBtnLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout bookingBtnPanelLayout = new javax.swing.GroupLayout(bookingBtnPanel);
        bookingBtnPanel.setLayout(bookingBtnPanelLayout);
        bookingBtnPanelLayout.setHorizontalGroup(
            bookingBtnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bookingBtnPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bookingBtnLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                .addContainerGap())
        );
        bookingBtnPanelLayout.setVerticalGroup(
            bookingBtnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bookingBtnPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bookingBtnLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        roomsBtnPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        roomsBtnLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        roomsBtnLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        roomsBtnLabel.setText("Rooms");
        roomsBtnLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                roomsBtnLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout roomsBtnPanelLayout = new javax.swing.GroupLayout(roomsBtnPanel);
        roomsBtnPanel.setLayout(roomsBtnPanelLayout);
        roomsBtnPanelLayout.setHorizontalGroup(
            roomsBtnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roomsBtnPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roomsBtnLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                .addContainerGap())
        );
        roomsBtnPanelLayout.setVerticalGroup(
            roomsBtnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roomsBtnPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roomsBtnLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        servicesBtnPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        servicesBtnLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        servicesBtnLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        servicesBtnLabel.setText("Services");
        servicesBtnLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                servicesBtnLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout servicesBtnPanelLayout = new javax.swing.GroupLayout(servicesBtnPanel);
        servicesBtnPanel.setLayout(servicesBtnPanelLayout);
        servicesBtnPanelLayout.setHorizontalGroup(
            servicesBtnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(servicesBtnPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(servicesBtnLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                .addContainerGap())
        );
        servicesBtnPanelLayout.setVerticalGroup(
            servicesBtnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(servicesBtnPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(servicesBtnLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        employeesBtnPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        employeesBtnLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        employeesBtnLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        employeesBtnLabel.setText("Employees");
        employeesBtnLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                employeesBtnLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout employeesBtnPanelLayout = new javax.swing.GroupLayout(employeesBtnPanel);
        employeesBtnPanel.setLayout(employeesBtnPanelLayout);
        employeesBtnPanelLayout.setHorizontalGroup(
            employeesBtnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(employeesBtnPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(employeesBtnLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                .addContainerGap())
        );
        employeesBtnPanelLayout.setVerticalGroup(
            employeesBtnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(employeesBtnPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(employeesBtnLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        guestBtnPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        guestsBtnLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        guestsBtnLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        guestsBtnLabel.setText("Guests");
        guestsBtnLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                guestsBtnLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout guestBtnPanelLayout = new javax.swing.GroupLayout(guestBtnPanel);
        guestBtnPanel.setLayout(guestBtnPanelLayout);
        guestBtnPanelLayout.setHorizontalGroup(
            guestBtnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(guestBtnPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(guestsBtnLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        guestBtnPanelLayout.setVerticalGroup(
            guestBtnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(guestBtnPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(guestsBtnLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        reportsBtnPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        reportsBtnLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        reportsBtnLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        reportsBtnLabel.setText("Reports");
        reportsBtnLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportsBtnLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout reportsBtnPanelLayout = new javax.swing.GroupLayout(reportsBtnPanel);
        reportsBtnPanel.setLayout(reportsBtnPanelLayout);
        reportsBtnPanelLayout.setHorizontalGroup(
            reportsBtnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reportsBtnPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(reportsBtnLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                .addContainerGap())
        );
        reportsBtnPanelLayout.setVerticalGroup(
            reportsBtnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reportsBtnPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(reportsBtnLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        settingsBtnPannel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        settingsBtnLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        settingsBtnLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        settingsBtnLabel.setText("Settings");
        settingsBtnLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                settingsBtnLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout settingsBtnPannelLayout = new javax.swing.GroupLayout(settingsBtnPannel);
        settingsBtnPannel.setLayout(settingsBtnPannelLayout);
        settingsBtnPannelLayout.setHorizontalGroup(
            settingsBtnPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsBtnPannelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(settingsBtnLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                .addContainerGap())
        );
        settingsBtnPannelLayout.setVerticalGroup(
            settingsBtnPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsBtnPannelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(settingsBtnLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        paymentsBtnPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        paymentsBtnLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        paymentsBtnLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        paymentsBtnLabel.setText("Payments");
        paymentsBtnLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                paymentsBtnLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout paymentsBtnPanelLayout = new javax.swing.GroupLayout(paymentsBtnPanel);
        paymentsBtnPanel.setLayout(paymentsBtnPanelLayout);
        paymentsBtnPanelLayout.setHorizontalGroup(
            paymentsBtnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paymentsBtnPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(paymentsBtnLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                .addContainerGap())
        );
        paymentsBtnPanelLayout.setVerticalGroup(
            paymentsBtnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paymentsBtnPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(paymentsBtnLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        inventoryBtnPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        inventoryBtnLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        inventoryBtnLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        inventoryBtnLabel.setText("Inventory");
        inventoryBtnLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inventoryBtnLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout inventoryBtnPanelLayout = new javax.swing.GroupLayout(inventoryBtnPanel);
        inventoryBtnPanel.setLayout(inventoryBtnPanelLayout);
        inventoryBtnPanelLayout.setHorizontalGroup(
            inventoryBtnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inventoryBtnPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(inventoryBtnLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                .addContainerGap())
        );
        inventoryBtnPanelLayout.setVerticalGroup(
            inventoryBtnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inventoryBtnPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(inventoryBtnLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        maintenanceBtnPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        maintenanceBtnLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        maintenanceBtnLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        maintenanceBtnLabel.setText("Maintenance");
        maintenanceBtnLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                maintenanceBtnLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout maintenanceBtnPanelLayout = new javax.swing.GroupLayout(maintenanceBtnPanel);
        maintenanceBtnPanel.setLayout(maintenanceBtnPanelLayout);
        maintenanceBtnPanelLayout.setHorizontalGroup(
            maintenanceBtnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(maintenanceBtnPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(maintenanceBtnLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                .addContainerGap())
        );
        maintenanceBtnPanelLayout.setVerticalGroup(
            maintenanceBtnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(maintenanceBtnPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(maintenanceBtnLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dashboardBtnPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bookingBtnPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roomsBtnPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(servicesBtnPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(employeesBtnPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(guestBtnPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(reportsBtnPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(settingsBtnPannel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(paymentsBtnPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inventoryBtnPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(maintenanceBtnPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dashboardBtnPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bookingBtnPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(roomsBtnPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(guestBtnPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(employeesBtnPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inventoryBtnPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(paymentsBtnPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(servicesBtnPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(maintenanceBtnPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(reportsBtnPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(settingsBtnPannel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void dashboardBtnLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboardBtnLabelMouseClicked
        if (parent != null) {
            // Create the dashboard overview panel
            DashboardOverviewPanel dashboardPanel = new DashboardOverviewPanel();
            dashboardPanel.setParent(parent);

            // Set the dashboard panel as the viewport view of the scroll pane
            parent.jScrollPane1.setViewportView(dashboardPanel);

            // Refresh the layout
            parent.jScrollPane1.revalidate();
            parent.jScrollPane1.repaint();
        }
    }//GEN-LAST:event_dashboardBtnLabelMouseClicked

    private void bookingBtnLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookingBtnLabelMouseClicked
        if (parent != null) {
            // Create the booking management panel
            BookingsMainPanel bookingMainPanel
                    = new BookingsMainPanel();

            // Set the booking panel as the viewport view of the scroll pane
            parent.jScrollPane1.setViewportView(bookingMainPanel);

            // Refresh the layout
            parent.jScrollPane1.revalidate();
            parent.jScrollPane1.repaint();
        }
    }//GEN-LAST:event_bookingBtnLabelMouseClicked

    private void roomsBtnLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_roomsBtnLabelMouseClicked
        if (parent != null) {
            // Create the booking management panel
            RoomMainPanel roomMainPanel
                    = new RoomMainPanel();

            // Set the booking panel as the viewport view of the scroll pane
            parent.jScrollPane1.setViewportView(roomMainPanel);

            // Refresh the layout
            parent.jScrollPane1.revalidate();
            parent.jScrollPane1.repaint();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_roomsBtnLabelMouseClicked

    private void guestsBtnLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guestsBtnLabelMouseClicked
        if (parent != null) {
            // Create the guest management panel
            main.java.com.hotel.ui.guest.GuestMainPanel guestMainPanel = new main.java.com.hotel.ui.guest.GuestMainPanel();

            // Set the guest panel as the viewport view of the scroll pane
            parent.jScrollPane1.setViewportView(guestMainPanel);

            // Refresh the layout
            parent.jScrollPane1.revalidate();
            parent.jScrollPane1.repaint();
        }
    }//GEN-LAST:event_guestsBtnLabelMouseClicked

    private void employeesBtnLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeesBtnLabelMouseClicked
        if (parent != null) {
            // Create the employee management panel
            main.java.com.hotel.ui.employee.EmployeeMainPanel employeeMainPanel = new main.java.com.hotel.ui.employee.EmployeeMainPanel();

            // Set the employee panel as the viewport view of the scroll pane
            parent.jScrollPane1.setViewportView(employeeMainPanel);

            // Refresh the layout
            parent.jScrollPane1.revalidate();
            parent.jScrollPane1.repaint();
        }
    }//GEN-LAST:event_employeesBtnLabelMouseClicked

    private void paymentsBtnLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paymentsBtnLabelMouseClicked
       if (parent != null) {
            // Create the employee management panel
            main.java.com.hotel.ui.payment.PaymentMainPanel paymentMainPanel = new main.java.com.hotel.ui.payment.PaymentMainPanel();

            // Set the employee panel as the viewport view of the scroll pane
            parent.jScrollPane1.setViewportView(paymentMainPanel);

            // Refresh the layout
            parent.jScrollPane1.revalidate();
            parent.jScrollPane1.repaint();
        }
    }//GEN-LAST:event_paymentsBtnLabelMouseClicked

    private void servicesBtnLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_servicesBtnLabelMouseClicked
          if (parent != null) {
            // Create the employee management panel
            main.java.com.hotel.ui.service.ServicesMainPanel serviceMainPanel = new main.java.com.hotel.ui.service.ServicesMainPanel();

            // Set the employee panel as the viewport view of the scroll pane
            parent.jScrollPane1.setViewportView(serviceMainPanel);

            // Refresh the layout
            parent.jScrollPane1.revalidate();
            parent.jScrollPane1.repaint();
        }
    }//GEN-LAST:event_servicesBtnLabelMouseClicked

    private void inventoryBtnLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventoryBtnLabelMouseClicked
         if (parent != null) {
            // Create the employee management panel
            main.java.com.hotel.ui.inventory.InventoryMainPanel inventoryMainPanel = new main.java.com.hotel.ui.inventory.InventoryMainPanel();

            // Set the employee panel as the viewport view of the scroll pane
            parent.jScrollPane1.setViewportView(inventoryMainPanel);

            // Refresh the layout
            parent.jScrollPane1.revalidate();
            parent.jScrollPane1.repaint();
        }
    }//GEN-LAST:event_inventoryBtnLabelMouseClicked

    private void maintenanceBtnLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_maintenanceBtnLabelMouseClicked
        if (parent != null) {
            // Create the employee management panel
            main.java.com.hotel.ui.maintenance.MaintenanceMainPanel maintenanceMainPanel = new main.java.com.hotel.ui.maintenance.MaintenanceMainPanel();

            // Set the employee panel as the viewport view of the scroll pane
            parent.jScrollPane1.setViewportView(maintenanceMainPanel);

            // Refresh the layout
            parent.jScrollPane1.revalidate();
            parent.jScrollPane1.repaint();
        }
    }//GEN-LAST:event_maintenanceBtnLabelMouseClicked

    private void reportsBtnLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportsBtnLabelMouseClicked
        if (parent != null) {
            // Create the employee management panel
            main.java.com.hotel.ui.reports.ReportsMainPanel reportMainPanel = new main.java.com.hotel.ui.reports.ReportsMainPanel();

            // Set the employee panel as the viewport view of the scroll pane
            parent.jScrollPane1.setViewportView(reportMainPanel);

            // Refresh the layout
            parent.jScrollPane1.revalidate();
            parent.jScrollPane1.repaint();
        }
    }//GEN-LAST:event_reportsBtnLabelMouseClicked

    private void settingsBtnLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsBtnLabelMouseClicked
        if (parent != null) {
            // Create the employee management panel
            main.java.com.hotel.ui.settings.SettingsMainPanel settingMainPanel = new main.java.com.hotel.ui.settings.SettingsMainPanel();

            // Set the employee panel as the viewport view of the scroll pane
            parent.jScrollPane1.setViewportView(settingMainPanel);

            // Refresh the layout
            parent.jScrollPane1.revalidate();
            parent.jScrollPane1.repaint();
        }
    }//GEN-LAST:event_settingsBtnLabelMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bookingBtnLabel;
    private javax.swing.JPanel bookingBtnPanel;
    private javax.swing.JLabel dashboardBtnLabel;
    private javax.swing.JPanel dashboardBtnPanel;
    private javax.swing.JLabel employeesBtnLabel;
    private javax.swing.JPanel employeesBtnPanel;
    private javax.swing.JPanel guestBtnPanel;
    private javax.swing.JLabel guestsBtnLabel;
    private javax.swing.JLabel inventoryBtnLabel;
    private javax.swing.JPanel inventoryBtnPanel;
    private javax.swing.JLabel maintenanceBtnLabel;
    private javax.swing.JPanel maintenanceBtnPanel;
    private javax.swing.JLabel paymentsBtnLabel;
    private javax.swing.JPanel paymentsBtnPanel;
    private javax.swing.JLabel reportsBtnLabel;
    private javax.swing.JPanel reportsBtnPanel;
    private javax.swing.JLabel roomsBtnLabel;
    private javax.swing.JPanel roomsBtnPanel;
    private javax.swing.JLabel servicesBtnLabel;
    private javax.swing.JPanel servicesBtnPanel;
    private javax.swing.JLabel settingsBtnLabel;
    private javax.swing.JPanel settingsBtnPannel;
    // End of variables declaration//GEN-END:variables
}
