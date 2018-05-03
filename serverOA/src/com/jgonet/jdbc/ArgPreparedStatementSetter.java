package com.jgonet.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ArgPreparedStatementSetter implements PreparedStatementSetter {

	private final Object[] params;

	public ArgPreparedStatementSetter(Object[] params) {
		this.params = params;
	}

	public void setValues(PreparedStatement ps) throws SQLException {
		if ( this.params != null && ps != null ) {
			for (int i = 0; i < this.params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
		}
	}
}
