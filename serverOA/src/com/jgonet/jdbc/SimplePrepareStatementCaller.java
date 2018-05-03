package com.jgonet.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SimplePrepareStatementCaller implements PreparedStatementCaller {

	private String sql;

	public SimplePrepareStatementCaller(String sql) {
		this.sql = sql;
	}

	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
		return conn == null ? null : conn.prepareStatement(this.sql);
	}
}
