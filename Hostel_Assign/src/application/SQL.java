package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQL {

    // Database connection details
    final static String SERVER = "localhost";
    final static String USER = "root";
    final static String PASS = "Official@00";
    final static Integer PORT = 3306;
    final static String dDB_NAME = "My_DB";
    final static String DRIVER = "com.mysql.cj.jdbc.Driver";
    final static String URL = "jdbc:mysql://" + SERVER + ":" + PORT + "/" + dDB_NAME + "?useSSL=false&serverTimezone=UTC";
	public static final String Connection = null;

    /**
     * Establishes a connection to the MySQL database.
     *
     * @return A Connection object representing the database connection.
     * @throws SQLException If a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Load the MySQL JDBC driver
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            throw new SQLException("MySQL JDBC Driver not found.");
        }

        // Establish and return the connection
        return DriverManager.getConnection(URL, USER, PASS);
    }

    /**
     * Tests the database connection.
     */
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Connected to the database successfully!");
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            System.err.println("Error connecting to the database:");
            e.printStackTrace();
        }
    }
}