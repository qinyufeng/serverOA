package com.jgonet.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tanghaibo
 * Date: 12-7-23
 * Time: 下午5:26
 * To change this template use File | Settings | File Templates.
 */
public class BatchPreparedStatementSetter implements PreparedStatementSetter {
    private List<Object[]> paramList;

    public BatchPreparedStatementSetter(List<Object[]> list) {
        this.paramList = list;
    }

    @Override
    public void setValues(PreparedStatement ps) throws SQLException {
        if (this.paramList != null) {
            for (Object[] obj : paramList) {
                for (int i = 0; i < obj.length; i++) {
                    ps.setObject(i + 1, obj[i]);
                }
                ps.addBatch();
            }
        }
    }
}
