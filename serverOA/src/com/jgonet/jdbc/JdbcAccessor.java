package com.jgonet.jdbc;

import javax.sql.DataSource;

public abstract class JdbcAccessor {
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
