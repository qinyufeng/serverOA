package com.jgonet.jdbc;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class JdbcDataSource implements DataSource {
	private String driverName = "oracle.jdbc.driver.OracleDriver";
	private String url;
	private String username;
	private String password;

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public JdbcDataSource() {

	}

	/**
	 * 获得数据库连接公共方法
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 * @throws java.sql.SQLException
	 */
	private Connection getConnection(String url, String username, String password) throws SQLException {
		// 加载oracle驱动
		Connection conn = null;
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, password);
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public Connection getConnection() throws SQLException {
		return getConnection(url, username, password);
	}

	public Connection getConnection(String username, String password) throws SQLException {
		return getConnection(url, username, password);
	}

	public PrintWriter getLogWriter() throws SQLException {
		return DriverManager.getLogWriter();
	}

	public int getLoginTimeout() throws SQLException {
		return DriverManager.getLoginTimeout();
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		DriverManager.setLogWriter(out);

	}

	public void setLoginTimeout(int seconds) throws SQLException {
		DriverManager.setLoginTimeout(seconds);
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		
		return false;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

}
