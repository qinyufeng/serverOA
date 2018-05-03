package com.jgonet.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementCallback<T> {
	T doPreparedInStatement(PreparedStatement ps) throws SQLException, DataException;
}
