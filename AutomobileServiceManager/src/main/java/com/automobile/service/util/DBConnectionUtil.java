package com.automobile.service.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Database connection utility class for the Automobile Service Manager
 * Provides methods to establish and manage database connections
 */
public class DBConnectionUtil {
    
    // Default database configuration
    private static final String DEFAULT_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/automobile_service";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "password";
    
    // Connection instance for singleton pattern
    private static Connection connection = null;
    
    /**
     * Private constructor to prevent instantiation
     */
    private DBConnectionUtil() {
        // Private constructor to enforce singleton pattern
    }
    
    /**
     * Get a database connection using default credentials
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Load the JDBC driver
                Class.forName(DEFAULT_DRIVER);
                
                // Create the connection
                connection = DriverManager.getConnection(
                    DEFAULT_URL, 
                    DEFAULT_USER, 
                    DEFAULT_PASSWORD
                );
            } catch (ClassNotFoundException e) {
                throw new SQLException("Database driver not found: " + e.getMessage());
            }
        }
        return connection;
    }
    
    /**
     * Get a database connection using properties file
     * @param propertiesFilePath path to the properties file
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection(String propertiesFilePath) throws SQLException {
        if (connection == null || connection.isClosed()) {
            Properties props = new Properties();
            
            try (InputStream input = new FileInputStream(propertiesFilePath)) {
                // Load properties file
                props.load(input);
                
                // Get properties
                String driver = props.getProperty("jdbc.driver", DEFAULT_DRIVER);
                String url = props.getProperty("jdbc.url", DEFAULT_URL);
                String user = props.getProperty("jdbc.user", DEFAULT_USER);
                String password = props.getProperty("jdbc.password", DEFAULT_PASSWORD);
                
                // Load the JDBC driver
                Class.forName(driver);
                
                // Create the connection
                connection = DriverManager.getConnection(url, user, password);
                
            } catch (IOException e) {
                throw new SQLException("Error loading database properties: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new SQLException("Database driver not found: " + e.getMessage());
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
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            } finally {
                connection = null;
            }
        }
    }
}
