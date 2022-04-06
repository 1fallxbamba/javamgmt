package config;

import java.sql.*;

public class Database {

	private static Database self = new Database();
	private static final String URL = "jdbc:postgresql://localhost/classmgmt";
	private static final String USER = "postgres";
	private static final String PASSWORD = "root";
	private static final String DRIVER_CLASS = "org.postgresql.Driver";

	private Database() {
		try {
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Connection createConnection() {

		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			System.out.println("ERROR: Unable to Connect to Database.");
		}
		return connection;
	}
	
    public static Connection getConnection() {
        return self.createConnection();
    }

}
