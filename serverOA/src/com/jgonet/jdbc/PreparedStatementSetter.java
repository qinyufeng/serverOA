package com.jgonet.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementSetter {
	void setValues(PreparedStatement ps) throws SQLException;
}
