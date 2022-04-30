package tn.ridha.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public  class SingletonConnection {

	private static Connection myConnection;

	public static Connection getConnection() {
		return myConnection;
	}

	public static Connection newConnection () {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mariadb://localhost:3306/mydb";
			Connection conn = DriverManager.getConnection(url, "root", "0420");
			return conn;
		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	static {
			myConnection = newConnection();
			if (myConnection != null) {
				System.out.println("connection with DB established");
			}else {
				System.out.println("couldn't connect to DB!");
			}
	}
}
