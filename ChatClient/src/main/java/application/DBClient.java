package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBClient {
	private final static String GET_CLIENT = "SELECT nameClient from clients where nameClient=";
	private final static String GET_CLIENTHISTORY = "SELECT nameClient,historyLine from historyClient where nameClient=";
	private final static String CREATE_CLIENT = "INSERT INTO clients (nameClient)  VALUES(";
	private final static String SET_HISTORYLINE = "INSERT INTO historyClient (nameClient,historyLine)  VALUES(";
	static Connection conn;

	public void createClient(String name) {
		Statement stmt = getStatement();
		ResultSet rs = null;
		try {
			stmt.execute(CREATE_CLIENT + "'" + name + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void setHistoryLine(String name, String line) {
		Statement stmt = getStatement();
		ResultSet rs = null;
		try {
			stmt.execute(SET_HISTORYLINE + "'" + name + "','" + line + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public String getUser(String name) {
		Statement stmt = getStatement();
		ResultSet rs = null;
		String stringName = "";
		try {
			rs = stmt.executeQuery(GET_CLIENT + "'" + name + "'");
			while (rs.next()) {
				stringName = rs.getString("nameClient");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return stringName;

	}

	public void getClientHistory(String name) {
		Statement stmt = getStatement();
		ResultSet rs = null;
		String stringName = "";
		try {
			rs = stmt.executeQuery(GET_CLIENTHISTORY + "'" + name + "'");
			while (rs.next()) {
				System.out.println(rs.getString("nameClient") + " : " + rs.getString("historyLine"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private static Statement getStatement() {
		conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/chatclient?" + "user=root&password=");
			stmt = conn.createStatement();
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

		return stmt;
	}

}
