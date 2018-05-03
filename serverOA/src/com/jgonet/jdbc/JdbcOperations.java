package com.jgonet.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * jdbc操作集合接口
 *
 * @author maofw
 */
public interface JdbcOperations {
    public <T> T excute(Connection conn, PreparedStatementCaller psc, PreparedStatementCallback<T> statementCallback) throws DataException, SQLException;

    public <T> T excute(PreparedStatementCaller psc, PreparedStatementCallback<T> statementCallback) throws DataException, SQLException;

    public <T> T query(String sql, PreparedStatementSetter pss, ResultSetExtractor<T> extractor) throws DataException, SQLException;

    public <T> T query(String sql, Object[] params, ResultSetExtractor<T> extractor) throws DataException, SQLException;

    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... params) throws DataException, SQLException;

    public List<Map> queryForList(String sql, Object... params) throws DataException, SQLException;

    public int queryForInt(String sql, Object... params) throws DataException, SQLException;

    public Map queryForMap(String sql, Object... params) throws DataException, SQLException;

    public int update(String sql, PreparedStatementSetter pss) throws DataException, SQLException;

    public int update(String sql, Object... params) throws DataException, SQLException;


    public int[] execBatch(String sql, PreparedStatementSetter pss) throws DataException, SQLException;

    public int[] execBatch(String sql, List<Object[]> list) throws DataException, SQLException;
}
