package com.jgonet.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RowMapperResultSetExtractor<T> implements ResultSetExtractor<List<T>> {
	private final RowMapper<T> rowMapper;

	public RowMapperResultSetExtractor(RowMapper<T> rowMapper) {
		this.rowMapper = rowMapper;
	}

	public List<T> extractData(ResultSet rs) throws SQLException, DataException {
		List<T> list = null;
		if ( rs != null ) {
			list = new ArrayList<T>();
			int rowNum = 0;
			while (rs.next()) {
				list.add(this.rowMapper.mapRow(rs, rowNum++));
			}
		}
		return list;
	}

}
