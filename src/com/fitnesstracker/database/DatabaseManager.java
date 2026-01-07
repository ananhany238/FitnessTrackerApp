package com.fitnesstracker.database;

import java.sql.*;

/**
 * DatabaseManager - SQL Server connection manager

 * @version 1.0
 */
public class DatabaseManager {
    
    private static DatabaseManager instance;
    private Connection connection;
    
    //  SERVER NAME FROM SSMS
    private static final String SERVER_NAME = "ANAN-HANY";
    private static final String DATABASE_NAME = "FitnessTrackerDB";
    private static final String DB_USER = "fitness_user";
    private static final String DB_PASSWORD = "StrongPassword123!";

private static final String CONNECTION_URL =
    String.format(
        "jdbc:sqlserver://%s;databaseName=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=true",
        SERVER_NAME,
        DATABASE_NAME,
        DB_USER,
        DB_PASSWORD
    );
    
    /**
     * Private constructor - Singleton pattern
     */
    private DatabaseManager() {
        try {
            // Load SQL Server JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            System.out.println(" Connecting to SQL Server...");
            System.out.println(" Server: " + SERVER_NAME);
            System.out.println(" Database: " + DATABASE_NAME);
          //  System.out.println(" URL: " + CONNECTION_URL);
            
            // Establish connection
            connection = DriverManager.getConnection(CONNECTION_URL);
            
            System.out.println("\n CONNECTED SUCCESSFULLY! ");
            
            // Test connection
            testConnection();
            
        } catch (ClassNotFoundException e) {
            System.err.println(" SQL Server JDBC Driver not found!");
            System.err.println("Please add mssql-jdbc JAR to project libraries.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println(" Database connection failed!");
            System.err.println("Server: " + SERVER_NAME);
            System.err.println("Database: " + DATABASE_NAME);
            System.err.println("Error: " + e.getMessage());
            System.err.println("\n Troubleshooting:");
            System.err.println("1. Make sure SQL Server service is running");
            System.err.println("2. Make sure database 'FitnessTrackerDB' exists");
            System.err.println("3. Try connecting in SSMS first with server: " + SERVER_NAME);
            e.printStackTrace();
        }
    }
    
    /**
     * Get singleton instance
     * @return DatabaseManager instance
     */
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    
    /**
     * Get active database connection
     * Reconnects if connection is closed
     * 
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(CONNECTION_URL);
            System.out.println(" Database connection re-established");
        }
        return connection;
    }
    
    /**
     * Test database connection and display info
     */
    private void testConnection() {
        try {
            System.out.println("\n Testing connection...\n");
            
            Statement stmt = connection.createStatement();
            
            // Get SQL Server version
            ResultSet versionRs = stmt.executeQuery("SELECT @@VERSION AS Version");
            if (versionRs.next()) {
                String version = versionRs.getString("Version");
                // Get first line only
                String firstLine = version.split("\n")[0];
                System.out.println(" " + firstLine);
            }
            versionRs.close();
            
            // Get current database
            ResultSet dbRs = stmt.executeQuery("SELECT DB_NAME() AS CurrentDB");
            if (dbRs.next()) {
                System.out.println(" Connected to database: " + dbRs.getString("CurrentDB"));
            }
            dbRs.close();
            
            // List existing tables
            ResultSet rs = stmt.executeQuery(
                "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES " +
                "WHERE TABLE_TYPE = 'BASE TABLE' " +
                "ORDER BY TABLE_NAME"
            );
            
            System.out.println("\n Available tables:");
            boolean hasTables = false;
            while (rs.next()) {
                System.out.println("   " + rs.getString("TABLE_NAME"));
                hasTables = true;
            }
            
            if (!hasTables) {
                System.out.println("   No tables found!");
                System.out.println("   Please run the CREATE TABLE scripts in SSMS");
            }
            
            rs.close();
            stmt.close();
            
            System.out.println("\n Connection test completed!\n");
            
        } catch (SQLException e) {
            System.err.println("Error testing connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Close database connection
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
    
    /**
     * Check if connection is active
     * @return true if connected
     */
    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed() && connection.isValid(5);
        } catch (SQLException e) {
            return false;
        }
    }
}