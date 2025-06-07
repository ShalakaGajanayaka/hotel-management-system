/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package main.java.com.hotel.ui.employee;

import java.awt.Container;
import main.java.com.hotel.config.DatabaseConnection;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author shalaka
 */
public class NewEmployeePanel extends javax.swing.JPanel {

    private int currentEmployeeId = -1;

    /**
     * Creates new form NewEmployeePanel
     */
    public NewEmployeePanel() {
        initComponents();
        update_button.setEnabled(false);
        initializeComboBoxes();
    }

    public void loadEmployeeData(EmployeeManagementPanel.EmployeeData employee) {
        try {
            // Personal Information
            personalInformation_firstName.setText(employee.firstName);
            personalInformation_lastName.setText(employee.lastName);

            // Gender
            if ("Male".equals(employee.gender)) {
                personalInformation_genderMale.setSelected(true);
            } else if ("Female".equals(employee.gender)) {
                personalInformation_genderFemale.setSelected(true);
            } else if ("Other".equals(employee.gender)) {
                personalInformation_genderOther.setSelected(true);
            }

            // Date of Birth
            try {
                if (employee.dateOfBirth != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date dob = sdf.parse(employee.dateOfBirth);
                    personalInformation_dateOfBirth.setDate(dob);
                }
            } catch (Exception e) {
                personalInformation_dateOfBirth.setDate(null);
            }

            // Employment Details
            employmentDetails_employeeCode.setText(employee.employeeCode);

            // Set department
            if (employee.departmentName != null) {
                for (int i = 0; i < employmentDetails_department.getItemCount(); i++) {
                    if (employmentDetails_department.getItemAt(i).equals(employee.departmentName)) {
                        employmentDetails_department.setSelectedIndex(i);
                        break;
                    }
                }
            }

            // Set employment status
            if (employee.employmentStatus != null) {
                for (int i = 0; i < employmentDetails_employeeStatus.getItemCount(); i++) {
                    if (employmentDetails_employeeStatus.getItemAt(i).equals(employee.employmentStatus)) {
                        employmentDetails_employeeStatus.setSelectedIndex(i);
                        break;
                    }
                }
            }

            // Hire Date
            try {
                if (employee.hireDate != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date hireDate = sdf.parse(employee.hireDate);
                    employmentDetails_hireDate.setDate(hireDate);
                }
            } catch (Exception e) {
                employmentDetails_hireDate.setDate(null);
            }

            // Salary
            employmentDetails_salary.setText(employee.salary);

            // Contact Information
            contactInformation_phoneNumber.setText(employee.contactNumber);
            contactInformation_email.setText(employee.email);

            // Address - split the address if it contains multiple lines
            if (employee.address != null) {
                String[] addressParts = employee.address.split("\n", 2);
                contactInformation_addressLine1.setText(addressParts[0].trim());
                if (addressParts.length > 1) {
                    contactInformation_addressLine2.setText(addressParts[1].trim());
                }
            }

            contactInformation_city.setText(employee.city);
            contactInformation_state.setText(employee.state);
            contactInformation_country.setText(employee.country);
            contactInformation_postalcode.setText(employee.postalCode);

            // Store the employee ID for update
            this.currentEmployeeId = employee.employeeId;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading employee data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

// setSaveButtonEnabled method add කරන්න
    public void setSaveButtonEnabled(boolean enabled) {
        save_button.setEnabled(enabled);
    }

// setUpdateButtonEnabled method add කරන්න  
    public void setUpdateButtonEnabled(boolean enabled) {
        update_button.setEnabled(enabled);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        personalInformation_firstName = new javax.swing.JTextField();
        personalInformation_lastName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        personalInformation_genderMale = new javax.swing.JRadioButton();
        personalInformation_genderFemale = new javax.swing.JRadioButton();
        personalInformation_genderOther = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        personalInformation_dateOfBirth = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        employmentDetails_employeeCode = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        employmentDetails_department = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        employmentDetails_position = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        employmentDetails_hireDate = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        employmentDetails_employeeStatus = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        employmentDetails_salary = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        contactInformation_phoneNumber = new javax.swing.JTextField();
        contactInformation_email = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        contactInformation_addressLine1 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        contactInformation_addressLine2 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        contactInformation_city = new javax.swing.JTextField();
        contactInformation_state = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        contactInformation_country = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        contactInformation_postalcode = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        systemAccess_createUserAccount = new javax.swing.JCheckBox();
        systemAccess_username = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        systemAccess_password = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        systemAccess_confirmPassword = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        systemAccess_role = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        systemAccess_accessRights_bookings = new javax.swing.JCheckBox();
        systemAccess_accessRights_guests = new javax.swing.JCheckBox();
        systemAccess_accessRights_payments = new javax.swing.JCheckBox();
        systemAccess_accessRights_rooms = new javax.swing.JCheckBox();
        systemAccess_accessRights_reports = new javax.swing.JCheckBox();
        cancel_button = new javax.swing.JButton();
        save_button = new javax.swing.JButton();
        update_button = new javax.swing.JButton();

        jLabel1.setText("New Employee");

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setPreferredSize(new java.awt.Dimension(387, 372));

        jLabel2.setText("Personal Information");

        jLabel3.setText("First Name :");

        jLabel4.setText("Last Name :");

        jLabel5.setText("Gender :");

        buttonGroup1.add(personalInformation_genderMale);
        personalInformation_genderMale.setText("Male");

        buttonGroup1.add(personalInformation_genderFemale);
        personalInformation_genderFemale.setText("Female");

        buttonGroup1.add(personalInformation_genderOther);
        personalInformation_genderOther.setText("Other");

        jLabel6.setText("Date of Birth :");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(personalInformation_genderMale)
                            .addComponent(personalInformation_firstName)
                            .addComponent(personalInformation_lastName, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                            .addComponent(personalInformation_genderFemale)
                            .addComponent(personalInformation_genderOther)
                            .addComponent(personalInformation_dateOfBirth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(personalInformation_firstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(personalInformation_lastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(personalInformation_genderMale)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(personalInformation_genderFemale)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(personalInformation_genderOther)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(personalInformation_dateOfBirth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setPreferredSize(new java.awt.Dimension(387, 372));

        jLabel7.setText("Employment Details");

        jLabel8.setText("Employee Code :");

        jLabel9.setText("Department :");

        employmentDetails_department.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel10.setText("Position :");

        jLabel11.setText("Hire Date :");

        jLabel12.setText("Employee Status :");

        employmentDetails_employeeStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel13.setText("Salary :");

        jLabel14.setText("$");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(employmentDetails_salary))
                            .addComponent(employmentDetails_employeeCode)
                            .addComponent(employmentDetails_department, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(employmentDetails_position)
                            .addComponent(employmentDetails_hireDate, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                            .addComponent(employmentDetails_employeeStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employmentDetails_employeeCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employmentDetails_department, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employmentDetails_position, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employmentDetails_hireDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employmentDetails_employeeStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(employmentDetails_salary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel15.setText("Contact Information");

        jLabel16.setText("Phone Number :");

        jLabel17.setText("Email :");

        jLabel18.setText("Address :");

        jLabel19.setText("line 1");

        jLabel28.setText("line 2");

        jLabel29.setText("City :");

        jLabel30.setText("State/Province :");

        jLabel31.setText("Country :");

        jLabel32.setText("Postal Code :");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(jLabel29)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(contactInformation_phoneNumber)
                                .addComponent(contactInformation_email, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(contactInformation_addressLine1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(contactInformation_addressLine2, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(contactInformation_city, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(contactInformation_state, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(contactInformation_country, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(contactInformation_postalcode, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel30)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32))
                .addContainerGap(202, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contactInformation_phoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contactInformation_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(contactInformation_addressLine1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(contactInformation_addressLine2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contactInformation_city, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contactInformation_state, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contactInformation_country, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contactInformation_postalcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel7.setPreferredSize(new java.awt.Dimension(387, 456));

        jLabel20.setText("System Access");

        jLabel21.setText("Create User Account :");

        jLabel22.setText("Username :");

        jLabel23.setText("Password :");

        jLabel24.setText("Confirm Password :");

        jLabel25.setText("Role :");

        systemAccess_role.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel26.setText("Access Rights :");

        systemAccess_accessRights_bookings.setText("Bookings");

        systemAccess_accessRights_guests.setText("Guests");

        systemAccess_accessRights_payments.setText("Payments");

        systemAccess_accessRights_rooms.setText("Rooms");

        systemAccess_accessRights_reports.setText("Reports");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(systemAccess_createUserAccount))
                    .addComponent(jLabel22)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(systemAccess_accessRights_bookings)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(systemAccess_username)
                                .addComponent(systemAccess_password, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                                .addComponent(systemAccess_confirmPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                                .addComponent(systemAccess_role, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(systemAccess_accessRights_guests)
                            .addComponent(systemAccess_accessRights_payments)
                            .addComponent(systemAccess_accessRights_rooms)
                            .addComponent(systemAccess_accessRights_reports))))
                .addContainerGap(249, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(systemAccess_createUserAccount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(systemAccess_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(systemAccess_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(systemAccess_confirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(systemAccess_role, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(systemAccess_accessRights_bookings)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(systemAccess_accessRights_guests)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(systemAccess_accessRights_rooms)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(systemAccess_accessRights_payments)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(systemAccess_accessRights_reports)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cancel_button.setText("Cancel");
        cancel_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_buttonActionPerformed(evt);
            }
        });

        save_button.setText("Save");
        save_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save_buttonActionPerformed(evt);
            }
        });

        update_button.setText("Update");
        update_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cancel_button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(update_button)
                        .addGap(18, 18, 18)
                        .addComponent(save_button)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancel_button)
                    .addComponent(save_button)
                    .addComponent(update_button))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void save_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_buttonActionPerformed
        if (validateFields()) {
            try {
                saveEmployee();
                JOptionPane.showMessageDialog(this, "Employee saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error saving employee: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_save_buttonActionPerformed

    private boolean validateFields() {
        // Personal Information validation
        if (personalInformation_firstName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "First Name is required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            personalInformation_firstName.requestFocus();
            return false;
        }

        if (personalInformation_lastName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Last Name is required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            personalInformation_lastName.requestFocus();
            return false;
        }

        if (!personalInformation_genderMale.isSelected()
                && !personalInformation_genderFemale.isSelected()
                && !personalInformation_genderOther.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select a gender!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (personalInformation_dateOfBirth.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Date of Birth is required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Employment Details validation
        if (employmentDetails_employeeCode.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Employee Code is required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            employmentDetails_employeeCode.requestFocus();
            return false;
        }

        if (employmentDetails_department.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Please select a department!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (employmentDetails_position.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Position is required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            employmentDetails_position.requestFocus();
            return false;
        }

        if (employmentDetails_hireDate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Hire Date is required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (employmentDetails_employeeStatus.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Please select employment status!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Contact Information validation
        if (contactInformation_phoneNumber.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Phone Number is required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            contactInformation_phoneNumber.requestFocus();
            return false;
        }

        if (contactInformation_email.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email is required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            contactInformation_email.requestFocus();
            return false;
        }

        if (!isValidEmail(contactInformation_email.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            contactInformation_email.requestFocus();
            return false;
        }

        if (contactInformation_addressLine1.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Address Line 1 is required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            contactInformation_addressLine1.requestFocus();
            return false;
        }

        if (contactInformation_city.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "City is required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            contactInformation_city.requestFocus();
            return false;
        }

        if (contactInformation_country.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Country is required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            contactInformation_country.requestFocus();
            return false;
        }

        // Check if employee code already exists
        if (isEmployeeCodeExists(employmentDetails_employeeCode.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Employee Code already exists! Please use a different code.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            employmentDetails_employeeCode.requestFocus();
            return false;
        }

        // Check if email already exists
        if (isEmailExists(contactInformation_email.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Email already exists! Please use a different email.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            contactInformation_email.requestFocus();
            return false;
        }

        // System Access validation (if creating user account)
        if (systemAccess_createUserAccount.isSelected()) {
            if (systemAccess_username.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username is required when creating user account!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                systemAccess_username.requestFocus();
                return false;
            }

            if (systemAccess_password.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Password is required when creating user account!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                systemAccess_password.requestFocus();
                return false;
            }

            if (!systemAccess_password.getText().equals(systemAccess_confirmPassword.getText())) {
                JOptionPane.showMessageDialog(this, "Password and Confirm Password do not match!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                systemAccess_confirmPassword.requestFocus();
                return false;
            }

            if (systemAccess_password.getText().length() < 6) {
                JOptionPane.showMessageDialog(this, "Password must be at least 6 characters long!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                systemAccess_password.requestFocus();
                return false;
            }

            if (systemAccess_role.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Please select a role when creating user account!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            // Check if username already exists
            if (isUsernameExists(systemAccess_username.getText().trim())) {
                JOptionPane.showMessageDialog(this, "Username already exists! Please choose a different username.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                systemAccess_username.requestFocus();
                return false;
            }
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private boolean isEmployeeCodeExists(String employeeCode) {
        try {
            String query = "SELECT COUNT(*) FROM employee WHERE employee_code = ?";
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, employeeCode);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isEmailExists(String email) {
        try {
            String query = "SELECT COUNT(*) FROM employee WHERE email = ?";
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isUsernameExists(String username) {
        try {
            String query = "SELECT COUNT(*) FROM user WHERE username = ?";
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void saveEmployee() throws Exception {
        Connection conn = DatabaseConnection.getConnection();
        conn.setAutoCommit(false); // Start transaction

        try {
            Integer userId = null;

            // Step 1: Create user account if requested
            if (systemAccess_createUserAccount.isSelected()) {
                userId = createUserAccount(conn);
            }

            // Step 2: Create employee record
            int employeeId = createEmployeeRecord(conn, userId);

            // Step 3: Add user role if user account was created
            if (userId != null) {
                createUserRole(conn, userId);
            }

            conn.commit(); // Commit transaction

        } catch (Exception e) {
            conn.rollback(); // Rollback on error
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    private Integer createUserAccount(Connection conn) throws Exception {
        String query = "INSERT INTO user (username, password, email, first_name, last_name, is_active) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

        pstmt.setString(1, systemAccess_username.getText().trim());
        pstmt.setString(2, hashPassword(systemAccess_password.getText())); // Hash the password
        pstmt.setString(3, contactInformation_email.getText().trim());
        pstmt.setString(4, personalInformation_firstName.getText().trim());
        pstmt.setString(5, personalInformation_lastName.getText().trim());
        pstmt.setBoolean(6, true);

        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected > 0) {
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        throw new Exception("Failed to create user account");
    }

    private int createEmployeeRecord(Connection conn, Integer userId) throws Exception {
        String query = "INSERT INTO employee (user_id, department_id, employee_code, first_name, last_name, gender, "
                + "date_of_birth, contact_number, email, address, city, state, country, postal_code, "
                + "hire_date, employment_status, salary, emergency_contact_name, emergency_contact_number) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

        pstmt.setObject(1, userId); // Can be null
        pstmt.setInt(2, getDepartmentId()); // Get department ID from combo box
        pstmt.setString(3, employmentDetails_employeeCode.getText().trim());
        pstmt.setString(4, personalInformation_firstName.getText().trim());
        pstmt.setString(5, personalInformation_lastName.getText().trim());
        pstmt.setString(6, getSelectedGender());
        pstmt.setDate(7, new java.sql.Date(personalInformation_dateOfBirth.getDate().getTime()));
        pstmt.setString(8, contactInformation_phoneNumber.getText().trim());
        pstmt.setString(9, contactInformation_email.getText().trim());

        // Combine address lines
        String fullAddress = contactInformation_addressLine1.getText().trim();
        if (!contactInformation_addressLine2.getText().trim().isEmpty()) {
            fullAddress += "\n" + contactInformation_addressLine2.getText().trim();
        }
        pstmt.setString(10, fullAddress);

        pstmt.setString(11, contactInformation_city.getText().trim());
        pstmt.setString(12, contactInformation_state.getText().trim().isEmpty() ? null : contactInformation_state.getText().trim());
        pstmt.setString(13, contactInformation_country.getText().trim());
        pstmt.setString(14, contactInformation_postalcode.getText().trim().isEmpty() ? null : contactInformation_postalcode.getText().trim());
        pstmt.setDate(15, new java.sql.Date(employmentDetails_hireDate.getDate().getTime()));
        pstmt.setString(16, getSelectedEmploymentStatus());

        // Handle salary
        if (!employmentDetails_salary.getText().trim().isEmpty()) {
            try {
                pstmt.setDouble(17, Double.parseDouble(employmentDetails_salary.getText().trim()));
            } catch (NumberFormatException e) {
                pstmt.setNull(17, java.sql.Types.DECIMAL);
            }
        } else {
            pstmt.setNull(17, java.sql.Types.DECIMAL);
        }

        pstmt.setNull(18, java.sql.Types.VARCHAR); // emergency_contact_name - not in form
        pstmt.setNull(19, java.sql.Types.VARCHAR); // emergency_contact_number - not in form

        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected > 0) {
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        throw new Exception("Failed to create employee record");
    }

    private void createUserRole(Connection conn, int userId) throws Exception {
        String query = "INSERT INTO user_role (user_id, role_name) VALUES (?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(query);

        pstmt.setInt(1, userId);
        pstmt.setString(2, getSelectedRole());

        pstmt.executeUpdate();
    }

    private String getSelectedGender() {
        if (personalInformation_genderMale.isSelected()) {
            return "Male";
        }
        if (personalInformation_genderFemale.isSelected()) {
            return "Female";
        }
        if (personalInformation_genderOther.isSelected()) {
            return "Other";
        }
        return "Other";
    }

    private int getDepartmentId() {
        // You need to map the combo box selection to department ID
        // This assumes you've loaded departments into the combo box with their IDs
        // For now, returning a default value - you should implement proper mapping
        return employmentDetails_department.getSelectedIndex() + 1;
    }

    private String getSelectedEmploymentStatus() {
        // Map combo box selection to employment status
        String[] statuses = {"Full-time", "Part-time", "Contract", "Intern"};
        int index = employmentDetails_employeeStatus.getSelectedIndex();
        return (index >= 0 && index < statuses.length) ? statuses[index] : "Full-time";
    }

    private String getSelectedRole() {
        // Map combo box selection to role
        String[] roles = {"STAFF", "MANAGER", "ADMIN"};
        int index = systemAccess_role.getSelectedIndex();
        return (index >= 0 && index < roles.length) ? roles[index] : "STAFF";
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return password; // Fallback to plain text (not recommended)
        }
    }

    private void clearFields() {
        // Personal Information
        personalInformation_firstName.setText("");
        personalInformation_lastName.setText("");
        buttonGroup1.clearSelection();
        personalInformation_dateOfBirth.setDate(null);

        // Employment Details
        employmentDetails_employeeCode.setText("");
        employmentDetails_department.setSelectedIndex(-1);
        employmentDetails_position.setText("");
        employmentDetails_hireDate.setDate(null);
        employmentDetails_employeeStatus.setSelectedIndex(-1);
        employmentDetails_salary.setText("");

        // Contact Information
        contactInformation_phoneNumber.setText("");
        contactInformation_email.setText("");
        contactInformation_addressLine1.setText("");
        contactInformation_addressLine2.setText("");
        contactInformation_city.setText("");
        contactInformation_state.setText("");
        contactInformation_country.setText("");
        contactInformation_postalcode.setText("");

        // System Access
        systemAccess_createUserAccount.setSelected(false);
        systemAccess_username.setText("");
        systemAccess_password.setText("");
        systemAccess_confirmPassword.setText("");
        systemAccess_role.setSelectedIndex(-1);

        // Clear all checkboxes
        systemAccess_accessRights_bookings.setSelected(false);
        systemAccess_accessRights_guests.setSelected(false);
        systemAccess_accessRights_payments.setSelected(false);
        systemAccess_accessRights_rooms.setSelected(false);
        systemAccess_accessRights_reports.setSelected(false);

        // Set focus to first field
        personalInformation_firstName.requestFocus();
    }

// Method to load departments into combo box - call this in constructor or when form loads
    private void loadDepartments() {
        try {
            employmentDetails_department.removeAllItems();
            String query = "SELECT department_name FROM department ORDER BY department_name";
            ResultSet rs = DatabaseConnection.executeSearch(query);

            while (rs.next()) {
                employmentDetails_department.addItem(rs.getString("department_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading departments: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

// Method to load employment statuses into combo box
    private void loadEmploymentStatuses() {
        employmentDetails_employeeStatus.removeAllItems();
        employmentDetails_employeeStatus.addItem("Full-time");
        employmentDetails_employeeStatus.addItem("Part-time");
        employmentDetails_employeeStatus.addItem("Contract");
        employmentDetails_employeeStatus.addItem("Intern");
    }

// Method to load roles into combo box
    private void loadRoles() {
        systemAccess_role.removeAllItems();
        systemAccess_role.addItem("STAFF");
        systemAccess_role.addItem("MANAGER");
        systemAccess_role.addItem("ADMIN");
    }

// Call these methods in your constructor after initComponents()
    public void initializeComboBoxes() {
        loadDepartments();
        loadEmploymentStatuses();
        loadRoles();
    }

    private void update_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_buttonActionPerformed
        try {
            // Validate all form data
            if (validateFields()) {
                // Update employee data in database
                updateEmployeeData();

                JOptionPane.showMessageDialog(this,
                        "Employee information updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                // Refresh the employee management panel
                refreshEmployeeManagementPanel();

                // Clear form and reset buttons
                clearFields();
                setSaveButtonEnabled(true);
                setUpdateButtonEnabled(false);
                currentEmployeeId = -1;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error updating employee data: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_update_buttonActionPerformed

    private void cancel_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_buttonActionPerformed
        int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to cancel? All unsaved data will be lost.",
                "Confirm Cancel",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            clearFields();
        }
    }//GEN-LAST:event_cancel_buttonActionPerformed

    
    private void updateEmployeeData() throws Exception {
    Connection conn = DatabaseConnection.getConnection();
    conn.setAutoCommit(false); // Start transaction

    try {
        // Update employee record
        updateEmployeeRecord(conn);

        // Update user account if it exists
        updateUserAccount(conn);

        conn.commit(); // Commit transaction

    } catch (Exception e) {
        conn.rollback(); // Rollback on error
        throw e;
    } finally {
        conn.setAutoCommit(true);
    }
}

// updateEmployeeRecord method add කරන්න
private void updateEmployeeRecord(Connection conn) throws Exception {
    String query = "UPDATE employee SET department_id=?, employee_code=?, first_name=?, last_name=?, "
            + "gender=?, date_of_birth=?, contact_number=?, email=?, address=?, city=?, state=?, "
            + "country=?, postal_code=?, employment_status=?, salary=? "
            + "WHERE employee_id=?";

    PreparedStatement pstmt = conn.prepareStatement(query);

    pstmt.setInt(1, getDepartmentIdByName((String) employmentDetails_department.getSelectedItem()));
    pstmt.setString(2, employmentDetails_employeeCode.getText().trim());
    pstmt.setString(3, personalInformation_firstName.getText().trim());
    pstmt.setString(4, personalInformation_lastName.getText().trim());
    pstmt.setString(5, getSelectedGender());
    pstmt.setDate(6, new java.sql.Date(personalInformation_dateOfBirth.getDate().getTime()));
    pstmt.setString(7, contactInformation_phoneNumber.getText().trim());
    pstmt.setString(8, contactInformation_email.getText().trim());

    // Combine address lines
    String fullAddress = contactInformation_addressLine1.getText().trim();
    if (!contactInformation_addressLine2.getText().trim().isEmpty()) {
        fullAddress += "\n" + contactInformation_addressLine2.getText().trim();
    }
    pstmt.setString(9, fullAddress);

    pstmt.setString(10, contactInformation_city.getText().trim());
    pstmt.setString(11, contactInformation_state.getText().trim().isEmpty() ? null : contactInformation_state.getText().trim());
    pstmt.setString(12, contactInformation_country.getText().trim());
    pstmt.setString(13, contactInformation_postalcode.getText().trim().isEmpty() ? null : contactInformation_postalcode.getText().trim());
    pstmt.setString(14, getSelectedEmploymentStatus());

    // Handle salary
    if (!employmentDetails_salary.getText().trim().isEmpty()) {
        try {
            pstmt.setDouble(15, Double.parseDouble(employmentDetails_salary.getText().trim()));
        } catch (NumberFormatException e) {
            pstmt.setNull(15, java.sql.Types.DECIMAL);
        }
    } else {
        pstmt.setNull(15, java.sql.Types.DECIMAL);
    }

    pstmt.setInt(16, currentEmployeeId);

    int rowsAffected = pstmt.executeUpdate();

    if (rowsAffected == 0) {
        throw new SQLException("Updating employee failed, no rows affected.");
    }
}

// updateUserAccount method add කරන්න
private void updateUserAccount(Connection conn) throws Exception {
    // Check if employee has a user account
    String checkQuery = "SELECT user_id FROM employee WHERE employee_id = ? AND user_id IS NOT NULL";
    PreparedStatement checkPstmt = conn.prepareStatement(checkQuery);
    checkPstmt.setInt(1, currentEmployeeId);
    ResultSet rs = checkPstmt.executeQuery();

    if (rs.next()) {
        int userId = rs.getInt("user_id");
        
        // Update user account
        String updateQuery = "UPDATE user SET email=?, first_name=?, last_name=? WHERE user_id=?";
        PreparedStatement updatePstmt = conn.prepareStatement(updateQuery);
        updatePstmt.setString(1, contactInformation_email.getText().trim());
        updatePstmt.setString(2, personalInformation_firstName.getText().trim());
        updatePstmt.setString(3, personalInformation_lastName.getText().trim());
        updatePstmt.setInt(4, userId);
        
        updatePstmt.executeUpdate();
    }
}

// getDepartmentIdByName method add කරන්න
private int getDepartmentIdByName(String departmentName) throws Exception {
    String query = "SELECT department_id FROM department WHERE department_name = ?";
    Connection conn = DatabaseConnection.getConnection();
    PreparedStatement pstmt = conn.prepareStatement(query);
    pstmt.setString(1, departmentName);
    ResultSet rs = pstmt.executeQuery();
    
    if (rs.next()) {
        return rs.getInt("department_id");
    }
    
    // Default to first department if not found
    return 1;
}

// refreshEmployeeManagementPanel method add කරන්න
private void refreshEmployeeManagementPanel() {
    // Get the parent EmployeeMainPanel
    Container parent = this.getParent();
    while (parent != null && !(parent instanceof EmployeeMainPanel)) {
        parent = parent.getParent();
    }

    if (parent instanceof EmployeeMainPanel) {
        EmployeeMainPanel employeeMainPanel = (EmployeeMainPanel) parent;
        // Switch back to the Employee Management tab
        employeeMainPanel.getEmployeesTabs().setSelectedIndex(0);
        // Refresh the employee list
        employeeMainPanel.getEmployeeManagementPanel().refreshEmployeeData();
    }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cancel_button;
    private javax.swing.JTextField contactInformation_addressLine1;
    private javax.swing.JTextField contactInformation_addressLine2;
    private javax.swing.JTextField contactInformation_city;
    private javax.swing.JTextField contactInformation_country;
    private javax.swing.JTextField contactInformation_email;
    private javax.swing.JTextField contactInformation_phoneNumber;
    private javax.swing.JTextField contactInformation_postalcode;
    private javax.swing.JTextField contactInformation_state;
    private javax.swing.JComboBox<String> employmentDetails_department;
    private javax.swing.JTextField employmentDetails_employeeCode;
    private javax.swing.JComboBox<String> employmentDetails_employeeStatus;
    private com.toedter.calendar.JDateChooser employmentDetails_hireDate;
    private javax.swing.JTextField employmentDetails_position;
    private javax.swing.JTextField employmentDetails_salary;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser personalInformation_dateOfBirth;
    private javax.swing.JTextField personalInformation_firstName;
    private javax.swing.JRadioButton personalInformation_genderFemale;
    private javax.swing.JRadioButton personalInformation_genderMale;
    private javax.swing.JRadioButton personalInformation_genderOther;
    private javax.swing.JTextField personalInformation_lastName;
    private javax.swing.JButton save_button;
    private javax.swing.JCheckBox systemAccess_accessRights_bookings;
    private javax.swing.JCheckBox systemAccess_accessRights_guests;
    private javax.swing.JCheckBox systemAccess_accessRights_payments;
    private javax.swing.JCheckBox systemAccess_accessRights_reports;
    private javax.swing.JCheckBox systemAccess_accessRights_rooms;
    private javax.swing.JTextField systemAccess_confirmPassword;
    private javax.swing.JCheckBox systemAccess_createUserAccount;
    private javax.swing.JTextField systemAccess_password;
    private javax.swing.JComboBox<String> systemAccess_role;
    private javax.swing.JTextField systemAccess_username;
    private javax.swing.JButton update_button;
    // End of variables declaration//GEN-END:variables
}
