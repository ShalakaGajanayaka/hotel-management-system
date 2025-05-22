
package main.java.com.hotel.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection;

    public static void createConnection() throws Exception {
        if (connection == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hotel_management_system", "root", "12345678");
        }
    }

    public static ResultSet executeSearch(String query) throws Exception {
        createConnection();
        return connection.createStatement().executeQuery(query);
    }

    public static Integer executeIUD(String query) throws Exception {
        createConnection();
        return connection.createStatement().executeUpdate(query);
    }

    public static Connection getConnection() throws SQLException, Exception {
        createConnection();  // Ensure the connection is created
        return connection;    // Return the connection
    }
    
    // Test connection method
    public static boolean testConnection() {
        try {
            createConnection();
            return connection != null && !connection.isClosed();
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e.getMessage());
            return false;
        }
    }
    
    // Close connection method
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null;
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}