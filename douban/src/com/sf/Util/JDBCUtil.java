package com.sf.Util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JDBCUtil {
	
	
	public static final  String url = "jdbc:mysql://localhost:3306/capackage";
	public static final  String user = "shunfeng";
	public static final  String password = "900523";
	public static final  String driverClass = "com.mysql.jdbc.Driver";
	
	

	static {
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
	}

	public static Connection getConnection() {

		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return conn;
	}

	public static void close(Connection conn, Statement st) {
		try {
			if (st != null) {
				st.close();
			}

			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public static void close(Connection conn, Statement st, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			} 

			if (st != null) {
				st.close();
			}

			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
}
