package com.jgonet.jdbc;

/**
 * Created by IntelliJ IDEA.
 * User: maofw
 * Date: 12-4-25
 * Time: 上午11:31
 * To change this template use File | Settings | File Templates.
 */
public class JdbcDaoSupport {
    protected JdbcOperations jdbcTemplate;

    public JdbcOperations getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
