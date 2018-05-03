package com.jgonet.jdbc;

import java.math.BigDecimal;
import java.sql.*;

/**
 * Created by IntelliJ IDEA.
 * User: maofw
 * Date: 12-4-25
 * Time: 下午1:16
 * To change this template use File | Settings | File Templates.
 */
public class SingleRowMapper<T> implements RowMapper<T> {
    private Class<T> clazz;

    public SingleRowMapper(Class<T> clazz) {
        this.clazz = clazz;

    }

    public T mapRow(ResultSet rs, int rowNum) throws SQLException, DataException {
        if (rs != null) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int nrOfColumns = rsmd.getColumnCount();
            if (nrOfColumns != 1) {
                throw new DataException("ResultSet's Columns is " + nrOfColumns + ", over 1!");
            }

            // Extract column value from JDBC ResultSet.
            Object result = getColumnValue(rs, 1, this.clazz);
            /*
            if (result != null && this.clazz != null && !this.clazz.isInstance(result)) {
                // Extracted value does not match already: try to convert it.
                /
                try {
                    return (T) convertValueToRequiredType(result, this.requiredType);
                } catch (IllegalArgumentException ex) {
                    throw new TypeMismatchDataAccessException(
                            "Type mismatch affecting row number " + rowNum + " and column type '" +
                                    rsmd.getColumnTypeName(1) + "': " + ex.getMessage());
                }

            }
            */
            return (T) result;
        }
        return null;
    }

    private Object getColumnValue(ResultSet rs, int index, Class<T> requiredType) throws SQLException {
        if (requiredType == null) {
            // return getResultSetValue(rs, index);
        }

        Object value = null;
        boolean wasNullCheck = false;

        // Explicitly extract typed value, as far as possible.
        if (String.class.equals(requiredType)) {
            value = rs.getString(index);
        } else if (boolean.class.equals(requiredType) || Boolean.class.equals(requiredType)) {
            value = rs.getBoolean(index);
            wasNullCheck = true;
        } else if (byte.class.equals(requiredType) || Byte.class.equals(requiredType)) {
            value = rs.getByte(index);
            wasNullCheck = true;
        } else if (short.class.equals(requiredType) || Short.class.equals(requiredType)) {
            value = rs.getShort(index);
            wasNullCheck = true;
        } else if (int.class.equals(requiredType) || Integer.class.equals(requiredType)) {
            value = rs.getInt(index);
            wasNullCheck = true;
        } else if (long.class.equals(requiredType) || Long.class.equals(requiredType)) {
            value = rs.getLong(index);
            wasNullCheck = true;
        } else if (float.class.equals(requiredType) || Float.class.equals(requiredType)) {
            value = rs.getFloat(index);
            wasNullCheck = true;
        } else if (double.class.equals(requiredType) || Double.class.equals(requiredType) ||
                Number.class.equals(requiredType)) {
            value = rs.getDouble(index);
            wasNullCheck = true;
        } else if (byte[].class.equals(requiredType)) {
            value = rs.getBytes(index);
        } else if (java.sql.Date.class.equals(requiredType)) {
            value = rs.getDate(index);
        } else if (java.sql.Time.class.equals(requiredType)) {
            value = rs.getTime(index);
        } else if (java.sql.Timestamp.class.equals(requiredType) || java.util.Date.class.equals(requiredType)) {
            value = rs.getTimestamp(index);
        } else if (BigDecimal.class.equals(requiredType)) {
            value = rs.getBigDecimal(index);
        } else if (Blob.class.equals(requiredType)) {
            value = rs.getBlob(index);
        } else if (Clob.class.equals(requiredType)) {
            value = rs.getClob(index);
        } else {
            // Some unknown type desired -> rely on getObject.
            value = getResultSetValue(rs, index);
        }

        // Perform was-null check if demanded (for results that the
        // JDBC driver returns as primitives).
        if (wasNullCheck && value != null && rs.wasNull()) {
            value = null;
        }
        return value;
    }

    public static Object getResultSetValue(ResultSet rs, int index) throws SQLException {
        Object obj = rs.getObject(index);
        String className = null;
        if (obj != null) {
            className = obj.getClass().getName();
        }
        if (obj instanceof Blob) {
            obj = rs.getBytes(index);
        } else if (obj instanceof Clob) {
            obj = rs.getString(index);
        } else if (className != null &&
                ("oracle.sql.TIMESTAMP".equals(className) ||
                        "oracle.sql.TIMESTAMPTZ".equals(className))) {
            obj = rs.getTimestamp(index);
        } else if (className != null && className.startsWith("oracle.sql.DATE")) {
            String metaDataClassName = rs.getMetaData().getColumnClassName(index);
            if ("java.sql.Timestamp".equals(metaDataClassName) ||
                    "oracle.sql.TIMESTAMP".equals(metaDataClassName)) {
                obj = rs.getTimestamp(index);
            } else {
                obj = rs.getDate(index);
            }
        } else if (obj != null && obj instanceof java.sql.Date) {
            if ("java.sql.Timestamp".equals(rs.getMetaData().getColumnClassName(index))) {
                obj = rs.getTimestamp(index);
            }
        }
        return obj;
    }
}
