/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;
import java.util.ResourceBundle;

/**
 *
 * @author Sasha
 */
public class ConnectionPool {
    
    /** queue of connections */
    private Queue<Connection> connections;
    
    /** max number of connections in the queue */
    private int maxConnections;
    
    /** maximum number of connetions by default */
    private static final int MAX_CONNECTIONS_DEFAULT = 1;

    /**
     * Constructor
     */
    public ConnectionPool() {
        connections = new LinkedList<>();
        maxConnections = MAX_CONNECTIONS_DEFAULT;
    }
    
    /** 
     * Get max nubmer of connections
     * @return int max number of connections
     */
    public synchronized int getMaxConnections() {
        return maxConnections;
    }
    
    /**
     * Set maxium number of connections in the queue
     * @param maxConnections 
     */
    public synchronized void setMaxConnection(int maxConnections) {
        this.maxConnections = maxConnections;
    }
    
    /**
     * Get free connection if exists or create new connection
     * @return 
     * @throws SQLException 
     * @throws ServerOverloadedException
     */
    public synchronized WrapperConnectionProxy getConnection() throws SQLException, ServerOverloadedException {
        Connection freeConnection;
        if (connections.size() > 0) {
            freeConnection = connections.poll();
        } else if (connections.size() < maxConnections) {
            Properties prop = new Properties();
            ResourceBundle bundle = ResourceBundle.getBundle("model/dao/restaurantprop");
            String url = bundle.getString("db.url");
            String login = bundle.getString("db.login");
            String pass = bundle.getString("db.password");
            prop.put("user", login);
            prop.put("password", pass);
            prop.put("autoReconnect", "true");
            prop.put("characterEncoding", "UTF-8");
            prop.put("useUnicode", "true");
            freeConnection = DriverManager.getConnection(url, prop);
        } else {
            throw new ServerOverloadedException();
        }
        return new WrapperConnectionProxy(freeConnection, this);
    }
    
    /**
     * Return connection to the queue (pool)
     * @param connection connection is returned
     */
    public synchronized void putConnection(Connection connection) {
        if (connection != null) {
            if (connections.size() < maxConnections) {
                connections.add(connection);
            } else {
                try {
                    connection.close();
                }catch (SQLException e) {
                    System.out.println("connection was not be closed!!! " + e.getMessage());
                }
            }
        }
    }
}
