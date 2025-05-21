/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.hotel.config;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {
    
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());
    private static Connection connection = null;
    private static final String CONFIG_FILE = "src/main/resources/database/hotel_db.properties";
    
    // Database configuration
    private static String dbUrl;
    private static String dbUser;
    private static String dbPassword;
    private static String dbDriver;
    
    static {
        loadDatabaseConfig();
    }
    
    /**
     * Private constructor to prevent instantiation
     */
    private DatabaseConnection() {
        // Private constructor to enforce singleton pattern
    }
    
    /**
     * Load database configuration from properties file
     */
    private static void loadDatabaseConfig() {
        Properties props = new Properties();
        
        try {
            // Load properties from file
            props.load(new FileInputStream(CONFIG_FILE));
            
            // Get database configuration
            dbUrl = props.getProperty("db.url");
            dbUser = props.getProperty("db.user");
            dbPassword = props.getProperty("db.password");
            dbDriver = props.getProperty("db.driver");
            
            // Load the JDBC driver
            Class.forName(dbDriver);
            
            LOGGER.log(Level.INFO, "Database configuration loaded successfully");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading database configuration: " + e.getMessage(), e);
            
            // Use default settings if configuration file is not found
            dbUrl = "jdbc:mysql://localhost:3306/hotel_management_system?useSSL=false&serverTimezone=UTC";
            dbUser = "root";
            dbPassword = "12345678";
            dbDriver = "com.mysql.cj.jdbc.Driver";
            
            try {
                Class.forName(dbDriver);
            } catch (ClassNotFoundException ex) {
                LOGGER.log(Level.SEVERE, "Error loading JDBC driver: " + ex.getMessage(), ex);
            }
        }
    }
    
    /**
     * Get a database connection
     * 
     * @return a connection to the database
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                LOGGER.log(Level.INFO, "Database connection established");
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error connecting to database: " + e.getMessage(), e);
                throw e;
            }
        }
        
        return connection;
    }
    
    /**
     * Close the database connection
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                LOGGER.log(Level.INFO, "Database connection closed");
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing database connection: " + e.getMessage(), e);
            }
        }
    }
    
    /**
     * Test the database connection
     * 
     * @return true if connection is successful, false otherwise
     */
    public static boolean testConnection() {
        try {
            Connection testConn = getConnection();
            boolean isValid = testConn.isValid(5); // Test if connection is valid with 5 second timeout
            if (!isValid) {
                closeConnection();
            }
            return isValid;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database connection test failed: " + e.getMessage(), e);
            return false;
        }
    }
}
