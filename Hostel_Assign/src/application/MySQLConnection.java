package application;

import java.sql.Connection; //interface
import java.sql.DriverManager;

public class MySQLConnection {

	final static String SERVER = "localhost";
	final static String USER = "root";
	final static String PASS = "Official@00";
	final static Integer PORT = 3306;
	final static String dDB_NAME = "My_DB";
	final static String DRIVER = "com.mysql.cj.jdbc.Driver";
	final static String URL = "jdbc:mysql://" + SERVER + ":" + PORT + "/" + dDB_NAME;

	public static void main(String[] args) {
		// Declare
		Connection conn;
		try {
			// input
			// process
			// output
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USER, PASS);
			System.out.println("Connect Database server successfully");
			conn.close();
			System.out.println("Close Database connection successfully");
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage());
		}
	}
}