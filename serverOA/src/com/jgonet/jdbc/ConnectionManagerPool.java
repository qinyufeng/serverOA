package com.jgonet.jdbc;

import java.util.HashMap;
import java.util.Map;

/**
 * 连接管理池
 * Created with IntelliJ IDEA.
 * User: tanghaibo
 * Date: 12-6-29
 * Time: 上午10:22
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionManagerPool {
    public Map<String, ConnectionManager> connPoolMap = new HashMap<String, ConnectionManager>();

    public ConnectionManager getConnectionManager(String name) {
        return connPoolMap.get(name);
    }

    public void registConnectionManager(String name, ConnectionManager connectionManager) {
        if (!isContainManager(name)) {
            connPoolMap.put(name, connectionManager);
        }
    }

    public boolean isContainManager(String name) {
        return connPoolMap.containsKey(name);
    }
}
