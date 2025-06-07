/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package main.java.com.hotel.ui.employee;

import java.awt.Container;
import main.java.com.hotel.ui.guest.GuestMainPanel;
import main.java.com.hotel.config.DatabaseConnection;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Vector;

/**
 *
 * @author shalaka
 */
public class EmployeeManagementPanel extends javax.swing.JPanel {

    private DefaultTableModel tableModel;
    private int selectedEmployeeId = -1;

    /**
     * Creates new form EmployeeManagementPanel
     */
    public EmployeeManagementPanel() {
        initComponents();
        initializeComponents();
        loadEmployeeData();
        setupTableSelectionListener();
    }

    private void initializeComponents() {
        // Initialize table model with proper column names
        String[] columnNames = {"ID", "Employee Code", "Name", "Department", "Position", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        employeeListTable.setModel(tableModel);

        // Set table selection mode
        employeeListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Load departments and statuses
        loadDepartments();
        loadEmploymentStatuses();

        // Clear employee details initially
        clearEmployeeDetails();
    }

    private void loadDepartments() {
        try {
            department_combobox.removeAllItems();
            department_combobox.addItem("All");

            String query = "SELECT department_name FROM department ORDER BY department_name";
            ResultSet rs = DatabaseConnection.executeSearch(query);

            while (rs.next()) {
                department_combobox.addItem(rs.getString("department_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading departments: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadEmploymentStatuses() {
        status_combobox.removeAllItems();
        status_combobox.addItem("All");
        status_combobox.addItem("Full-time");
        status_combobox.addItem("Part-time");
        status_combobox.addItem("Contract");
        status_combobox.addItem("Intern");
        status_combobox.addItem("Terminated");
    }

    private void loadEmployeeData() {
        try {
            // Clear existing data
            tableModel.setRowCount(0);

            // Build query based on filters
            StringBuilder query = new StringBuilder();
            query.append("SELECT e.employee_id, e.employee_code, ");
            query.append("CONCAT(e.first_name, ' ', e.last_name) AS full_name, ");
            query.append("d.department_name, e.employment_status, e.contact_number, ");
            query.append("e.email, e.hire_date ");
            query.append("FROM employee e ");
            query.append("LEFT JOIN department d ON e.department_id = d.department_id ");
            query.append("WHERE 1=1 ");

            Vector<Object> parameters = new Vector<>();

            // Add search filter
            String searchText = search_field.getText().trim();
            if (!searchText.isEmpty()) {
                query.append("AND (e.first_name LIKE ? OR e.last_name LIKE ? OR e.employee_code LIKE ? OR e.email LIKE ?) ");
                String searchPattern = "%" + searchText + "%";
                parameters.add(searchPattern);
                parameters.add(searchPattern);
                parameters.add(searchPattern);
                parameters.add(searchPattern);
            }

            // Add department filter
            String selectedDept = (String) department_combobox.getSelectedItem();
            if (selectedDept != null && !selectedDept.equals("All")) {
                query.append("AND d.department_name = ? ");
                parameters.add(selectedDept);
            }

            // Add status filter
            String selectedStatus = (String) status_combobox.getSelectedItem();
            if (selectedStatus != null && !selectedStatus.equals("All")) {
                query.append("AND e.employment_status = ? ");
                parameters.add(selectedStatus);
            }

            query.append("ORDER BY e.employee_code");

            // Execute query
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query.toString());

            // Set parameters
            for (int i = 0; i < parameters.size(); i++) {
                pstmt.setObject(i + 1, parameters.get(i));
            }

            ResultSet rs = pstmt.executeQuery();

            // Populate table
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("employee_id"),
                    rs.getString("employee_code"),
                    rs.getString("full_name"),
                    rs.getString("department_name"),
                    rs.getString("employment_status"),
                    getStatusDisplayText(rs.getString("employment_status"))
                };
                tableModel.addRow(row);
            }

            // Update table display
            employeeListTable.revalidate();
            employeeListTable.repaint();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading employee data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getStatusDisplayText(String status) {
        if ("Terminated".equals(status)) {
            return "Inactive";
        }
        return "Active";
    }

    private void setupTableSelectionListener() {
        employeeListTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = employeeListTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        selectedEmployeeId = (Integer) tableModel.getValueAt(selectedRow, 0);
                        loadEmployeeDetails(selectedEmployeeId);
                    } else {
                        clearEmployeeDetails();
                    }
                }
            }
        });
    }

    private void loadEmployeeDetails(int employeeId) {
        try {
            String query = "SELECT e.employee_id, e.employee_code, "
                    + "CONCAT(e.first_name, ' ', e.last_name) AS full_name, "
                    + "d.department_name, e.employment_status, e.contact_number, "
                    + "e.email, e.hire_date, e.salary "
                    + "FROM employee e "
                    + "LEFT JOIN department d ON e.department_id = d.department_id "
                    + "WHERE e.employee_id = ?";

            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, employeeId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Update employee details labels
                employeeDetails_id.setText(rs.getString("employee_code"));
                employeeDetails_name.setText(rs.getString("full_name"));
                employeeDetails_department.setText(rs.getString("department_name"));
                employeeDetails_position.setText(rs.getString("employment_status")); // Using employment status as position for now
                employeeDetails_contact.setText(rs.getString("contact_number"));
                employeeDetails_email.setText(rs.getString("email"));

                // Format hire date
                java.sql.Date hireDate = rs.getDate("hire_date");
                if (hireDate != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    employeeDetails_hireDate.setText(sdf.format(hireDate));
                } else {
                    employeeDetails_hireDate.setText("N/A");
                }

                // Set status
                String status = rs.getString("employment_status");
                employeeDetails_Status.setText(getStatusDisplayText(status));

                // Enable action buttons
                employeeDetails_ViewDetails_button.setEnabled(true);
                employeeDetails_Edit_button.setEnabled(true);
                employeeDetails_manageAccess_button.setEnabled(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading employee details: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearEmployeeDetails() {
        employeeDetails_id.setText("-");
        employeeDetails_name.setText("-");
        employeeDetails_department.setText("-");
        employeeDetails_position.setText("-");
        employeeDetails_contact.setText("-");
        employeeDetails_email.setText("-");
        employeeDetails_hireDate.setText("-");
        employeeDetails_Status.setText("-");

        // Disable action buttons
        employeeDetails_ViewDetails_button.setEnabled(false);
        employeeDetails_Edit_button.setEnabled(false);
        employeeDetails_manageAccess_button.setEnabled(false);

        selectedEmployeeId = -1;
    }

    // Add combo box change listeners
    private void addComboBoxListeners() {
        department_combobox.addActionListener(e -> loadEmployeeData());
        status_combobox.addActionListener(e -> loadEmployeeData());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        addEmployee_button = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        search_field = new javax.swing.JTextField();
        search_button = new javax.swing.JButton();
        reset_button = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        department_combobox = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        status_combobox = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        employeeListTable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        employeeDetails_id = new javax.swing.JLabel();
        employeeDetails_name = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        employeeDetails_position = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        employeeDetails_department = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        employeeDetails_email = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        employeeDetails_hireDate = new javax.swing.JLabel();
        employeeDetails_contact = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        employeeDetails_Status = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        employeeDetails_ViewDetails_button = new javax.swing.JButton();
        employeeDetails_Edit_button = new javax.swing.JButton();
        employeeDetails_manageAccess_button = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        shedulecalenderpanel = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        schedule_editSchedule = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(930, 660));

        jPanel1.setPreferredSize(new java.awt.Dimension(930, 950));

        jLabel1.setText("Employee Management");

        addEmployee_button.setText("+ Add Employee");
        addEmployee_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEmployee_buttonActionPerformed(evt);
            }
        });

        jLabel2.setText("Search :");

        search_button.setText("search");
        search_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_buttonActionPerformed(evt);
            }
        });

        reset_button.setText("Reset");
        reset_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reset_buttonActionPerformed(evt);
            }
        });

        jLabel3.setText("Department :");

        department_combobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All" }));
        department_combobox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                department_comboboxItemStateChanged(evt);
            }
        });

        jLabel4.setText("Status :");

        status_combobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All" }));
        status_combobox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                status_comboboxItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(search_field, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(search_button)
                        .addGap(18, 18, 18)
                        .addComponent(reset_button))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(department_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(status_combobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(search_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(search_button)
                    .addComponent(reset_button))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(department_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(status_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setText("Employee List");

        employeeListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Department", "Position", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(employeeListTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 904, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 831, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel9.setText("Employee Details");

        jLabel11.setText("ID :");

        employeeDetails_id.setText("E01");

        employeeDetails_name.setText("Jane Davis");

        jLabel14.setText("Name :");

        jLabel15.setText("Position :");

        employeeDetails_position.setText("Receptionist");

        jLabel17.setText("Department :");

        employeeDetails_department.setText("Front Desk");

        jLabel19.setText("Email :");

        employeeDetails_email.setText("jave@gmail.com");

        jLabel21.setText("Hire Date :");

        employeeDetails_hireDate.setText("02/03/2025");

        employeeDetails_contact.setText("555-3456");

        jLabel24.setText("Contact :");

        employeeDetails_Status.setText("Active");

        jLabel26.setText("Status :");

        employeeDetails_ViewDetails_button.setText("View Details");
        employeeDetails_ViewDetails_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeDetails_ViewDetails_buttonActionPerformed(evt);
            }
        });

        employeeDetails_Edit_button.setText("Edit");
        employeeDetails_Edit_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeDetails_Edit_buttonActionPerformed(evt);
            }
        });

        employeeDetails_manageAccess_button.setText("Manage Access");
        employeeDetails_manageAccess_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeDetails_manageAccess_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(employeeDetails_id))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(employeeDetails_name))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(employeeDetails_position))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(employeeDetails_department))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(employeeDetails_email))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(employeeDetails_hireDate))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(employeeDetails_contact))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(employeeDetails_Status))
                    .addComponent(employeeDetails_ViewDetails_button)
                    .addComponent(employeeDetails_Edit_button)
                    .addComponent(employeeDetails_manageAccess_button))
                .addContainerGap(270, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(employeeDetails_id))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(employeeDetails_name))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(employeeDetails_department))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(employeeDetails_position))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(employeeDetails_contact))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(employeeDetails_email))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(employeeDetails_hireDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(employeeDetails_Status))
                .addGap(18, 18, 18)
                .addComponent(employeeDetails_ViewDetails_button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(employeeDetails_Edit_button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(employeeDetails_manageAccess_button)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel10.setText("Shedule");

        shedulecalenderpanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout shedulecalenderpanelLayout = new javax.swing.GroupLayout(shedulecalenderpanel);
        shedulecalenderpanel.setLayout(shedulecalenderpanelLayout);
        shedulecalenderpanelLayout.setHorizontalGroup(
            shedulecalenderpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        shedulecalenderpanelLayout.setVerticalGroup(
            shedulecalenderpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel27.setText("Legend :");

        jLabel28.setText("M - Morning Shift");

        jLabel29.setText("A - Afternoon Shift");

        jLabel30.setText("N - Night Shift");

        jLabel31.setText("O - Off Day");

        schedule_editSchedule.setText("Edit Schedule");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(shedulecalenderpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28)
                            .addComponent(jLabel29)
                            .addComponent(jLabel30)
                            .addComponent(jLabel31)
                            .addComponent(schedule_editSchedule))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(shedulecalenderpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel31)
                .addGap(18, 18, 18)
                .addComponent(schedule_editSchedule)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addEmployee_button))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addEmployee_button)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 930, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addEmployee_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEmployee_buttonActionPerformed
        // Get the parent EmployeeMainPanel
        Container parent = this.getParent();
        while (parent != null && !(parent instanceof EmployeeMainPanel)) {
            parent = parent.getParent();
        }

        if (parent instanceof EmployeeMainPanel) {
            EmployeeMainPanel employeeMainPanel = (EmployeeMainPanel) parent;
            // Switch to the New Employee tab (index 1)
            employeeMainPanel.getEmployeesTabs().setSelectedIndex(1);
        }
    }//GEN-LAST:event_addEmployee_buttonActionPerformed

    private void search_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_buttonActionPerformed
        loadEmployeeData();
    }//GEN-LAST:event_search_buttonActionPerformed

    private void reset_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reset_buttonActionPerformed
        // Clear search field
        search_field.setText("");

        // Reset combo boxes
        department_combobox.setSelectedIndex(0); // "All"
        status_combobox.setSelectedIndex(0); // "All"

        // Reload data
        loadEmployeeData();

        // Clear selection and details
        employeeListTable.clearSelection();
        clearEmployeeDetails();
    }//GEN-LAST:event_reset_buttonActionPerformed

    private void employeeDetails_ViewDetails_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeDetails_ViewDetails_buttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_employeeDetails_ViewDetails_buttonActionPerformed

    private void employeeDetails_Edit_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeDetails_Edit_buttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_employeeDetails_Edit_buttonActionPerformed

    private void employeeDetails_manageAccess_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeDetails_manageAccess_buttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_employeeDetails_manageAccess_buttonActionPerformed

    private void status_comboboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_status_comboboxItemStateChanged
        // Only process when item is selected (not deselected)
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            // Load employee data with new filter
            loadEmployeeData();

            // Clear employee details since selection might change
            employeeListTable.clearSelection();
            clearEmployeeDetails();

            // Optional: Show status message
            String selectedStatus = (String) status_combobox.getSelectedItem();
            if (selectedStatus != null && !selectedStatus.equals("All")) {
                System.out.println("Filtering employees by status: " + selectedStatus);
            }
        }
    }//GEN-LAST:event_status_comboboxItemStateChanged

    private void department_comboboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_department_comboboxItemStateChanged
        // Only process when item is selected (not deselected)
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            // Load employee data with new filter
            loadEmployeeData();

            // Clear employee details since selection might change
            employeeListTable.clearSelection();
            clearEmployeeDetails();

            // Optional: Show status message
            String selectedDept = (String) department_combobox.getSelectedItem();
            if (selectedDept != null && !selectedDept.equals("All")) {
                System.out.println("Filtering employees by department: " + selectedDept);
            }
        }
    }//GEN-LAST:event_department_comboboxItemStateChanged

    // Public method to refresh data (useful when called from other panels)
    public void refreshEmployeeData() {
        loadEmployeeData();
    }

// Method to get selected employee ID (useful for other operations)
    public int getSelectedEmployeeId() {
        return selectedEmployeeId;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addEmployee_button;
    private javax.swing.JComboBox<String> department_combobox;
    private javax.swing.JButton employeeDetails_Edit_button;
    private javax.swing.JLabel employeeDetails_Status;
    private javax.swing.JButton employeeDetails_ViewDetails_button;
    private javax.swing.JLabel employeeDetails_contact;
    private javax.swing.JLabel employeeDetails_department;
    private javax.swing.JLabel employeeDetails_email;
    private javax.swing.JLabel employeeDetails_hireDate;
    private javax.swing.JLabel employeeDetails_id;
    private javax.swing.JButton employeeDetails_manageAccess_button;
    private javax.swing.JLabel employeeDetails_name;
    private javax.swing.JLabel employeeDetails_position;
    private javax.swing.JTable employeeListTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton reset_button;
    private javax.swing.JButton schedule_editSchedule;
    private javax.swing.JButton search_button;
    private javax.swing.JTextField search_field;
    private javax.swing.JPanel shedulecalenderpanel;
    private javax.swing.JComboBox<String> status_combobox;
    // End of variables declaration//GEN-END:variables
}
