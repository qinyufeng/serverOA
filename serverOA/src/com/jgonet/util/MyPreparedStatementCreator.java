package com.jgonet.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementCreator;

public class MyPreparedStatementCreator implements PreparedStatementCreator {
	private Object[] objs = null;
	private String sql = null;
	public MyPreparedStatementCreator(String sql,Object[] o){
		this.sql = sql;
		this.objs = o;
	}


	public PreparedStatement createPreparedStatement(Connection connection)
			throws SQLException {
		PreparedStatement ps = connection.prepareStatement(sql,
				new String[]{"id"});
		int i = 0;
		for(Object o : objs){
			ps.setString(++i, o.toString());
		}
		return ps;
	}

}
