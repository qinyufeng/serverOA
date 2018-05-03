package com.jgonet.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;


@SuppressWarnings("unchecked")
public class DBUtil {

    // private static final ThreadLocal threadLocal = new ThreadLocal();

    private static Map<String, ThreadLocal> threadLocalMap = new HashMap<String, ThreadLocal>();


    private DBUtil() {
    }


    public static DataSource getDataSource(String name) {
        ConnectionManager connectionManager = ConnectionManager.getInstance(name);
        return connectionManager == null ? null : connectionManager.getDataSource();
    }


    public synchronized static Connection getConnection() throws Exception {
        return getConnection(ConnectionManager.DEFAULT_CONNECTION);
    }

    public synchronized static Connection getConnection(String name) throws Exception {

        ThreadLocal threadLocal = threadLocalMap.get(name);
        if (threadLocal == null) {
            threadLocal = new ThreadLocal();
            threadLocalMap.put(name, threadLocal);
        }

        Connection conn = (Connection) threadLocal.get();
        try {
            if (conn == null || conn.isClosed()) {
                conn = ConnectionManager.getInstance(name).getConnection();//使用C3P0
                //conn = ConnectionProvider.getConnection();

                if (conn == null || conn.isClosed()) {
                    System.out.println("DBUtil.getConnection()时发生异常，连接可能已经关闭，再试一次....");
                    conn = ConnectionManager.getInstance(name).getConnection();
                    System.out.println("DBUtil.getConnection()时发生异常，连接可能已经关闭，再试一次....conn==" + conn);
                }

                conn.setAutoCommit(true);
                threadLocal.set(conn);
            }
        } catch (Exception e) {
            throw new Exception("数据库访问失败..");
        }

        return conn;
    }

    private   synchronized static Connection getConnection(ConnectionManager connectionManager) throws Exception {
        Connection conn = null ;
        if(connectionManager!=null){

        }
        return conn ;

    }

    public static Connection getExistConnection(String name) {
        ThreadLocal threadLocal = threadLocalMap.get(name);
        return threadLocal == null ? null : (Connection) threadLocal.get();

    }


    public static void closeConnection() throws Exception {
        closeConnection(ConnectionManager.DEFAULT_CONNECTION);
    }

    public static void closeConnection(String name) throws Exception {
        ThreadLocal threadLocal = threadLocalMap.get(name);
        Connection conn = threadLocal == null ? null : (Connection) threadLocal.get();
        if (conn != null && !conn.isClosed()) {
            try {
                conn.close();
            } catch (Exception e) {
                throw new Exception("关闭数据库连接失败..");
            }
            threadLocal.set(null);
        }
    }

    public static void commit() throws Exception {
        commit(ConnectionManager.DEFAULT_CONNECTION);
    }

    public static void commit(String name) throws Exception {
        ThreadLocal threadLocal = threadLocalMap.get(name);
        Connection conn = threadLocal == null ? null : (Connection) threadLocal.get();
        if (conn != null && !conn.isClosed()) {
            try {
                conn.commit();
            } catch (Exception e) {
                throw new Exception("提交数据库连接失败..");
            }
        }
    }

    public static void rollback() throws Exception {
        roolback(ConnectionManager.DEFAULT_CONNECTION);
    }

    public static void roolback(String name) throws Exception {
        ThreadLocal threadLocal = threadLocalMap.get(name);
        Connection conn = threadLocal == null ? null : (Connection) threadLocal.get();
        if (conn != null && !conn.isClosed()) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
