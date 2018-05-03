package com.jgonet.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementCaller {
	PreparedStatement createPreparedStatement(Connection conn) throws SQLException;
}
