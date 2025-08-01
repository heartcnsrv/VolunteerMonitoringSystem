package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class handles the connection to the MySQL database.
 * It provides methods to establish and close a connection to the database.
 */
public class DatabaseConnection {

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/volunteer?useSSL=false&serverTimezone=UTC"; // Database URL
    private static final String USER = "root"; // Username for database administrator
    private static final String PASSWORD = ""; // Password for database administrator

    /**
     * Establishes a connection to the MySQL database.
     *
     * @return Connection object representing the established database connection, or null if the connection fails
     */
    public static Connection connect() {
        Connection connection = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Attempt to establish a connection to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection established successfully!");
        } catch (SQLException e) {
            // Print error message if the connection fails due to SQL issues
            System.out.println("Connection failed: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            // Print error message if the JDBC driver is not found
            System.out.println("JDBC Driver not found: " + e.getMessage());
        }

        return connection;
    }

    /**
     * Closes the provided database connection.
     *
     * @param connection the connection to close
     */
    public static void close(Connection connection) {
        // Check if the connection is not null before attempting to close it
        if (connection != null) {
            try {
                // Close the database connection
                connection.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                // Print error message if there is an issue closing the connection
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    /**
     * Main method to test the database connection.
     *
     * @param args command-line arguments (not used in this method)
     */
    public static void main(String[] args) {
        // Test the connection
        Connection connection = connect();

        // Close the connection after use
        close(connection);
    }
}