package com.jgonet.jdbc;

import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: maofw
 * Date: 12-4-25
 * Time: 下午2:59
 * To change this template use File | Settings | File Templates.
 */
public class JdbcBaseTemplate extends JdbcTemplate {

    public <T> T excute(PreparedStatementCaller psc, PreparedStatementCallback<T> statementCallback) throws DataException, SQLException {
        try {
            return excute(DBUtil.getConnection(), psc, statementCallback);
        } catch (Exception e) {
            throw new DataException("获得c3p0数据库连接异常!", e);
        }
    }
}
