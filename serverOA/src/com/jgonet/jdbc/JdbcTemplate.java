package com.jgonet.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

/**
 * jdbc模板类实现
 *
 * @author maofw
 */
public class JdbcTemplate extends JdbcAccessor implements JdbcOperations {

    public JdbcTemplate() {
    }

    public JdbcTemplate(DataSource dataSource) {
        setDataSource(dataSource);
    }

    public <T> T excute(Connection conn, PreparedStatementCaller psc, PreparedStatementCallback<T> statementCallback) throws DataException, SQLException {
        if (conn != null && !conn.isClosed() && psc != null && statementCallback != null) {
            PreparedStatement ps = null;
            try {
                ps = psc.createPreparedStatement(conn);
                return statementCallback.doPreparedInStatement(ps);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                JdbcConnectUtils.closeStatement(ps);
                JdbcConnectUtils.releaseConnection(conn);
            }
        }
        return null;
    }

    public <T> T excute(PreparedStatementCaller psc, PreparedStatementCallback<T> statementCallback) throws DataException, SQLException {
        return excute(JdbcConnectUtils.getConnection(getDataSource()), psc, statementCallback);
    }

    public <T> T query(final String sql, final PreparedStatementSetter pss, final ResultSetExtractor<T> extractor) throws DataException, SQLException {
        return excute(new SimplePrepareStatementCaller(sql), new PreparedStatementCallback<T>() {
            public T doPreparedInStatement(PreparedStatement ps) throws SQLException, DataException {
                ResultSet rs = null;
                try {
                    // 设置参数值
                    if (pss != null) {
                        pss.setValues(ps);
                    }
                    // 查询结果
                    rs = ps.executeQuery();
                    return extractor.extractData(rs);
                } finally {
                    JdbcConnectUtils.closeResultset(rs);
                }
            }

        });
    }

    public <T> T query(String sql, Object[] params, ResultSetExtractor<T> extractor) throws DataException, SQLException {
        return query(sql, new ArgPreparedStatementSetter(params), extractor);
    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... params) throws DataException, SQLException {
        return query(sql, params, new RowMapperResultSetExtractor<T>(rowMapper));
    }

    public List<Map> queryForList(String sql, Object... params) throws DataException, SQLException {
        return query(sql, new ColumnMapRowMapper(), params);
    }


    public <T> T queryForObject(String sql, Object[] params, RowMapper<T> rowMapper) throws DataException, SQLException {
        List<T> list = query(sql, rowMapper, params);
        Object o = null;
        if (list != null && list.size() > 0) {
            o = list.get(0);
        }
        return (T) o;
    }

    public int queryForInt(String sql, Object... params) throws DataException, SQLException {
        Integer numInt = queryForObject(sql, params, new SingleRowMapper<Integer>(Integer.class));
        return numInt == null ? 0 : numInt.intValue();
    }

    public Map queryForMap(String sql, Object... params) throws DataException, SQLException {
        return queryForObject(sql, params, new ColumnMapRowMapper());
    }

    public int update(final String sql, final PreparedStatementSetter pss) throws DataException, SQLException {
        return excute(new SimplePrepareStatementCaller(sql), new PreparedStatementCallback<Integer>() {
            public Integer doPreparedInStatement(PreparedStatement ps) throws SQLException, DataException {
                if (pss != null) {
                    pss.setValues(ps);
                }
                return ps.executeUpdate();
            }
        });
    }

    public int update(String sql, Object... params) throws DataException, SQLException {
        return update(sql, new ArgPreparedStatementSetter(params));
    }


    public int[] execBatch(final String sql, final PreparedStatementSetter pss) throws DataException, SQLException {
        return excute(new SimplePrepareStatementCaller(sql), new PreparedStatementCallback<int[]>() {
            @Override
            public int[] doPreparedInStatement(PreparedStatement ps) throws SQLException, DataException {
                if (pss != null) {
                    pss.setValues(ps);
                }
                return ps.executeBatch();
            }
        });
    }

    @Override
    public int[] execBatch(String sql, List<Object[]> list) throws DataException, SQLException {
        return execBatch(sql, new BatchPreparedStatementSetter(list));
    }

}
