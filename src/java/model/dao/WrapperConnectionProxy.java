/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Sasha
 */
public class WrapperConnectionProxy {
    
    /** connection pool from which connection is added */
    ConnectionPool connectionPool;
    
    /** free connection from connection pool */
    private Connection connection;
    
    /**
     * Constructor
     * @param connection free connection from connection pool
     * @param connectionPool connection pool with connections
     */
    public WrapperConnectionProxy(Connection connection, ConnectionPool connectionPool) {
        this.connection = connection;
        this.connectionPool = connectionPool;
    }
    
    /**
     * Create new sql statement
     * @return statement
     * @throws SQLException 
     */
    public Statement createStatement() throws SQLException {
        if (connection != null) {
            return connection.createStatement();
        }
        return null;
    }
    
    /**
     * Return connection to the connection pool
     */
    public void close() {
        if (connection != null) {
            connectionPool.putConnection(connection);
        }
    }
    
    /**
     * Prepare statement
     * @param sql sql query
     * @return prepared statement
     * @throws SQLException 
     */
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }
    
}
