package cn.zju.edu.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection {
	public static final String DBDRIVER = "com.mysql.jdbc.Driver";
	public static final String DBURL = "jdbc:mysql://localhost:3306/test";
	public static final String DBUSER = "root";
	public static final String DBPASS = "";
	private static Connection conn = null;

	public static Connection getConnection() {
		try {
			Class.forName(DBDRIVER);
			conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return conn;

	}

	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
