/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main.java.com.hotel.ui.auth;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import main.java.com.hotel.config.DatabaseConnection;
import main.java.com.hotel.ui.dashboard.DashboardFrame;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author shalaka
 */
public class LoginFrame extends javax.swing.JFrame {

    /**
     * Creates new form LoginFrame
     */
    public LoginFrame() {
        initComponents(); // NetBeans generated method
        setupLoginFrame(); // Our custom setup method
        addEventListeners(); // Our custom event listeners
    }

    /**
     * Setup additional login frame properties (called after initComponents)
     */
    private void setupLoginFrame() {
        setResizable(false);
//        setTitle("Hotel Management System - Login");

        // Style the login button
        loginBtn.setBackground(new java.awt.Color(70, 130, 180));
        loginBtn.setForeground(java.awt.Color.WHITE);
        loginBtn.setFocusPainted(false);

        // Center the frame
        setLocationRelativeTo(null);

        // Focus on username field initially
        usernameTextField.requestFocus();
    }

    /**
     * Add event listeners to components (NetBeans won't override this)
     */
    private void addEventListeners() {
        // Enter key listeners for both fields
        usernameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    passwordPasswordField.requestFocus();
                }
            }
        });

        passwordPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    attemptLogin();
                }
            }
        });
    }

    /**
     * Attempt to log in with provided credentials
     */
    private void attemptLogin() {
        String username = usernameTextField.getText().trim();
        String password = new String(passwordPasswordField.getPassword());

        // Basic input validation
        if (!validateInput(username, password)) {
            return;
        }

        // Disable login button during authentication
        loginBtn.setEnabled(false);
        loginBtn.setText("Logging in...");

        // Perform authentication in a separate thread to avoid UI freezing
        new Thread(() -> {
            try {
                if (authenticateUser(username, password)) {
                    // Authentication successful
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        // Update last login time
                        updateLastLogin(username);

                        // Show success message
                        JOptionPane.showMessageDialog(LoginFrame.this,
                                "Login successful! Welcome " + username,
                                "Login Success",
                                JOptionPane.INFORMATION_MESSAGE);

                        // Open dashboard
                        openDashboard(username);
                        dispose(); // Close login window
                    });
                } else {
                    // Authentication failed
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(LoginFrame.this,
                                "Invalid username or password.\n\nPlease check your credentials and try again.",
                                "Login Failed",
                                JOptionPane.ERROR_MESSAGE);

                        passwordPasswordField.setText(""); // Clear password field
                        usernameTextField.requestFocus();

                        // Re-enable login button
                        loginBtn.setEnabled(true);
                        loginBtn.setText("Login");
                    });
                }
            } catch (Exception e) {
                // Database error
                javax.swing.SwingUtilities.invokeLater(() -> {
                    showConnectionError(e);

                    // Re-enable login button
                    loginBtn.setEnabled(true);
                    loginBtn.setText("Login");
                });
            }
        }).start();
    }

    /**
     * Validate input fields
     */
    private boolean validateInput(String username, String password) {
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter your username.",
                    "Username Required",
                    JOptionPane.WARNING_MESSAGE);
            usernameTextField.requestFocus();
            return false;
        }

        if (username.length() < 3) {
            JOptionPane.showMessageDialog(this,
                    "Username must be at least 3 characters long.",
                    "Invalid Username",
                    JOptionPane.WARNING_MESSAGE);
            usernameTextField.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter your password.",
                    "Password Required",
                    JOptionPane.WARNING_MESSAGE);
            passwordPasswordField.requestFocus();
            return false;
        }

        if (password.length() < 4) {
            JOptionPane.showMessageDialog(this,
                    "Password must be at least 4 characters long.",
                    "Invalid Password",
                    JOptionPane.WARNING_MESSAGE);
            passwordPasswordField.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Authenticate user against database
     */
  private boolean authenticateUser(String username, String password) {
    try {
        // Test database connection first
        if (!testDatabaseConnection()) {
            throw new Exception("Database connection failed. Please check your database server.");
        }

        // Escape single quotes to prevent basic SQL injection
        String safeUsername = username.replace("'", "''");
        
        // Query to get user details
        String getUserQuery = "SELECT u.user_id, u.username, u.password, u.first_name, u.last_name, u.is_active, " +
                             "ur.role_name " +
                             "FROM user u " +
                             "LEFT JOIN user_role ur ON u.user_id = ur.user_id " +
                             "WHERE u.username = '" + safeUsername + "' AND u.is_active = 1";

        ResultSet rs = DatabaseConnection.executeSearch(getUserQuery);

        if (rs.next()) {
            // User found and active
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String role = rs.getString("role_name");
            
            // Hard-coded password check for development only
            boolean passwordMatches = false;
            
            if (username.equals("admin") && password.equals("admin123")) {
                passwordMatches = true;
            } else if (username.equals("manager") && password.equals("manager123")) {
                passwordMatches = true;
            } else if (username.equals("frontdesk1") && password.equals("frontdesk1")) {
                passwordMatches = true;
            }
            
            if (passwordMatches) {
                System.out.println("Login successful for user: " + firstName + " " + lastName + " (Role: " + role + ")");
                rs.close();
                return true;
            } else {
                System.out.println("Password doesn't match for user: " + username);
                rs.close();
                return false;
            }
        } else {
            // User not found or inactive
            System.out.println("No matching active user found with username: " + username);
            rs.close();
            return false;
        }

    } catch (Exception e) {
        System.err.println("Authentication error: " + e.getMessage());
        e.printStackTrace();
        throw new RuntimeException("Authentication failed: " + e.getMessage(), e);
    }
}

    /**
     * Test database connection
     */
    private boolean testDatabaseConnection() {
        try {
            ResultSet rs = DatabaseConnection.executeSearch("SELECT 1");
            boolean connected = rs.next();
            rs.close();
            return connected;
        } catch (Exception e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Update last login time for user
     */
    private void updateLastLogin(String username) {
        try {
            String safeUsername = username.replace("'", "''");
            String updateQuery = "UPDATE user SET last_login = NOW() WHERE username = '" + safeUsername + "'";
            DatabaseConnection.executeIUD(updateQuery);
            System.out.println("Last login time updated for user: " + username);
        } catch (Exception e) {
            System.err.println("Error updating last login: " + e.getMessage());
            // Don't throw exception as this is not critical for login process
        }
    }

    /**
     * Open the dashboard frame
     */
    private void openDashboard(String username) {
        try {
            // Create and show dashboard
            DashboardFrame dashboard = new DashboardFrame(username);
            dashboard.setVisible(true);

            System.out.println("Dashboard opened for user: " + username);
        } catch (Exception e) {
            System.err.println("Error opening dashboard: " + e.getMessage());
            e.printStackTrace();

            // Show error message and don't close login window
            JOptionPane.showMessageDialog(this,
                    "Error opening dashboard:\n" + e.getMessage()
                    + "\n\nPlease contact system administrator.",
                    "Dashboard Error",
                    JOptionPane.ERROR_MESSAGE);

            // Re-enable login button
            loginBtn.setEnabled(true);
            loginBtn.setText("Login");
        }
    }

    /**
     * Show connection error dialog with retry option
     */
    private void showConnectionError(Exception e) {
        String message = "Unable to connect to the database.\n\n"
                + "Error: " + e.getMessage() + "\n\n"
                + "Please check:\n"
                + "• Database server is running\n"
                + "• Network connection is available\n"
                + "• Database credentials are correct\n\n"
                + "Would you like to try again?";

        int result = JOptionPane.showConfirmDialog(this,
                message,
                "Database Connection Error",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            // Retry login
            attemptLogin();
        }
    }

    /**
     * Load saved username if available (Remember Me functionality)
     */
    public void loadSavedUsername() {
        try {
            java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(LoginFrame.class);
            String savedUsername = prefs.get("savedUsername", "");

            if (!savedUsername.isEmpty()) {
                usernameTextField.setText(savedUsername);
                passwordPasswordField.requestFocus();
                System.out.println("Loaded saved username: " + savedUsername);
            }
        } catch (Exception e) {
            System.err.println("Error loading saved username: " + e.getMessage());
        }
    }

    /**
     * Save username for Remember Me functionality
     */
    private void saveUsername(String username) {
        try {
            java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(LoginFrame.class);
            prefs.put("savedUsername", username);
        } catch (Exception e) {
            System.err.println("Error saving username: " + e.getMessage());
        }
    }

    /**
     * Test method to verify database connection and show test credentials
     */
    public void showTestCredentials() {
        String message = "Test Database Connection and Credentials:\n\n"
                + "Default test users:\n"
                + "• Username: admin, Password: admin123\n"
                + "• Username: manager, Password: manager123\n"
                + "• Username: staff, Password: staff123\n\n"
                + "Make sure these users exist in your database.";

        JOptionPane.showMessageDialog(this,
                message,
                "Test Credentials",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        passwordPasswordField = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        loginBtn = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Username");

        usernameTextField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Password");

        passwordPasswordField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText(":");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText(":");

        loginBtn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        loginBtn.setText("Login");
        loginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(170, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(loginBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(usernameTextField)
                            .addComponent(passwordPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(170, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel2, jLabel3});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(passwordPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(26, 26, 26)
                .addComponent(loginBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel2, jLabel3, passwordPasswordField, usernameTextField});

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/resources/images/icons/logo1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel6)
                .addGap(42, 42, 42)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void loginBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginBtnActionPerformed
        attemptLogin();
    }//GEN-LAST:event_loginBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatMacLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton loginBtn;
    private javax.swing.JPasswordField passwordPasswordField;
    private javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables
}
