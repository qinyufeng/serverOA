package com.jgonet.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
public class ConnectionManager {

    //private static ConnectionManager instance;
    private ComboPooledDataSource ds;

    private static ConnectionManagerPool connManagerPool = new ConnectionManagerPool();

    public static final String DEFAULT_CONNECTION = "jsmccClient";

    /*
    private ConnectionManager() throws Exception {
        ds = new ComboPooledDataSource() ;
    }
    */

    private ConnectionManager(String name) throws Exception {
        if (DEFAULT_CONNECTION.equals(name)) {
            ds = new ComboPooledDataSource();
        } else {
            ds = new ComboPooledDataSource(name);
        }
    }


    public static final ConnectionManager getInstance() {
        return getInstance(DEFAULT_CONNECTION);
    }


    public static final ConnectionManager getInstance(String name) {
        ConnectionManager instance = null;
        if (!connManagerPool.isContainManager(name)) {
            synchronized (connManagerPool) {
                if (!connManagerPool.isContainManager(name)) {
                    try {
                        instance = new ConnectionManager(name);
                        connManagerPool.registConnectionManager(name, instance);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    instance = connManagerPool.getConnectionManager(name);
                }
            }
        } else {
            instance = connManagerPool.getConnectionManager(name);
        }
        return instance;
    }


    public synchronized final Connection getConnection() {
        Connection cnn = null;
        if (ds != null) {
            try {
                cnn = ds.getConnection();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        /*
        if (cnn == null) {
            try {
                ds = new ComboPooledDataSource("jsmccClient");
                return ds.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        */
        return cnn;
    }


    protected void finalize() throws Throwable {
        DataSources.destroy(ds);
        super.finalize();
    }


    public DataSource getDataSource() {
        return ds;
    }

}
