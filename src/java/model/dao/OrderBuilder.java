/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.entity.DBEntity;
import model.entity.Order;
import model.entity.Order.OrderStatus;

/**
 *
 * @author Sasha
 */
public class OrderBuilder extends EntityBuilder {
    
     /** sql query for all orders */
    private static final String SQL_FOR_ALL_ORDERS = "SELECT * FROM order";
    
    /** sql query to get order by id */
    private static final String SQL_FOR_ORDER_BY_ID = "SELECT * FROM order WHERE order_id = ?";

    /** sql query for inserting meal into the main menu table in the data base */
    private static final String SQL_FOR_INSERTING_ORDER = "INSERT INTO order (status, user_id, total_price) VALUES (?, ?, ?)";
    
    /** sql query for deleting entity from the data base table */
    private static final String SQL_FOR_DELETING_BY_ID = "DELETE FROM order WHERE order_id = ?";

    /**
     * Get sql query for all orders 
     * @param wrapperConnection wrapper connection from the connection pool
     * @return list of orders
     * @throws SQLException
     */
    @Override
    protected List<DBEntity> getAllEntities(
            WrapperConnectionProxy wrapperConnection) throws SQLException {
        List<DBEntity> orders = new ArrayList<>();
        Statement st = wrapperConnection.createStatement();
        ResultSet rs = st.executeQuery(SQL_FOR_ALL_ORDERS);
        while (rs.next()) {
            orders.add(getOrder(rs));
        }
        return orders;
    }
    
    /**
     * Get one order by result set
     * @param rs result set of sql query
     * @return DBEntity object
     * @throws SQLException
     */
    private DBEntity getOrder(ResultSet rs) throws SQLException {
        int id = rs.getInt("order_id");
        int userId = rs.getInt("user_id");
        OrderStatus status = OrderStatus.valueOf(rs.getString("status"));
        BigDecimal price = rs.getBigDecimal("total_price");
        return new Order(id, userId, status, price);
    }

    /**
     * Get order by id
     * @param wrapperConnection wrapper connection regarding to which prepared 
     *  statement might be created
     * @param id order id
     * @return DBEntity order or null if it won't be found
     * @throws SQLException
     */
    @Override
    protected DBEntity getEntityById(
            WrapperConnectionProxy wrapperConnection, int id) throws SQLException {
        PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_ORDER_BY_ID);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return getOrder(rs);
        }
        return null;
    }

    /**
     * Insert order into the data base from the object oriented entity
     * @param wrapperConnection wrapper connection from the connection pool
     * @param entity order might be inserting into the data base
     * @throws SQLException 
     */
    @Override
    protected void insertEntity(WrapperConnectionProxy wrapperConnection, 
            DBEntity entity) throws SQLException {
        PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_INSERTING_ORDER);
        Order order = (Order) entity;
        ps.setString(1, order.getStatus().toString());
        ps.setInt(2, order.getUserId());
        ps.setBigDecimal(3, order.getTotalPrice());
        ps.executeUpdate();
    }

    /**
     * Delete order from the data base by id
     * @param wrapperConnection wrapper conection 
     * @param id order id 
     * @throws SQLException 
     */
    @Override
    protected void deleteById(WrapperConnectionProxy wrapperConnection, int id) 
            throws SQLException {
        PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_DELETING_BY_ID);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
    
}
