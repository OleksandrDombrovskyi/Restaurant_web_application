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
import static model.dao.EntityCreator.CONNECTION_POOL;
import model.entity.DBEntity;
import model.entity.Order;
import model.entity.Order.OrderStatus;

/**
 *
 * @author Sasha
 */
public class OrderCreator extends EntityCreator {
    
//     /** sql query for all orders */
//    private static final String SQL_FOR_ALL_ORDERS = "SELECT * FROM order";
//    
//    /** sql query to get order by id */
//    private static final String SQL_FOR_ORDER_BY_ID = "SELECT * FROM order WHERE order_id = ?";

    /** sql value of order table name */
    private final static String ORDER_TABLE = "order";
    
    /** sql value of order id name */
    private final static String ORDER_ID = "order_id";
    
    /** sql query for inserting meal into the main menu table in the data base */
    private static final String SQL_FOR_INSERTING_ORDER = "INSERT INTO order (status, user_id, total_price) VALUES (?, ?, ?)";
    
//    /** sql query for deleting entity from the data base table */
//    private static final String SQL_FOR_DELETING_BY_ID = "DELETE FROM order WHERE order_id = ?";

    /**
     * Constructor 
     */
    public OrderCreator() {
        super(ORDER_TABLE, ORDER_ID);
    }

//    /**
//     * Get all order from the db
//     * 
//     * @return list of data base order
//     * @throws java.sql.SQLException
//     * @throws model.dao.ServerOverloadedException
//     */
//    public List<DBEntity> getAllEntities() throws SQLException, ServerOverloadedException {
//        List<DBEntity> orders = new ArrayList<>();
//        WrapperConnectionProxy wrapperConnection = null;
//        try {
//            wrapperConnection = CONNECTION_POOL.getConnection();
//            Statement st = wrapperConnection.createStatement();
//            ResultSet rs = st.executeQuery(SQL_FOR_ALL_ORDERS);
//            while (rs.next()) {
//                orders.add(getOrder(rs));
//            }
//        } finally {
//            if (wrapperConnection != null) {
//                wrapperConnection.close();
//            }
//        }
//        return orders;
//    }

//    /**
//     * Get order by id
//     * 
//     * @param id id of order
//     * @return data base order
//     * @throws java.sql.SQLException
//     * @throws model.dao.ServerOverloadedException
//     */
//    public Order getOrderById(int id) throws SQLException, ServerOverloadedException {
//        WrapperConnectionProxy wrapperConnection = null;
//        try {
//            wrapperConnection = CONNECTION_POOL.getConnection();
//            PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_ORDER_BY_ID);
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return getOrder(rs);
//            }
//        } finally {
//            if (wrapperConnection != null) {
//                wrapperConnection.close();
//            }
//        }
//        return null;
//    }

    /**
     * Create new order
     * 
     * @param order order migth be updated into the data base
     * @return true if updating is successfull and false otherwise
     * @throws java.sql.SQLException
     * @throws model.dao.ServerOverloadedException
     */
    public boolean insertOrder(Order order) throws SQLException, ServerOverloadedException {
        boolean flag = false;
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_INSERTING_ORDER);
            ps.setString(1, order.getStatus().toString());
            ps.setInt(2, order.getUserId());
            ps.setBigDecimal(3, order.getTotalPrice());
            ps.executeUpdate();
            flag = true;
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
        return flag;
    }

//    /**
//     * Delete order from the data base by id
//     * 
//     * @param id order id
//     * @return true if deleting is successfull or false otherwise
//     * @throws java.sql.SQLException
//     * @throws model.dao.ServerOverloadedException
//     */
//    public boolean deleteById(int id) throws SQLException, ServerOverloadedException {
//        boolean flag = false;
//        WrapperConnectionProxy wrapperConnection = null;
//        try {
//            wrapperConnection = CONNECTION_POOL.getConnection();
//            PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_DELETING_BY_ID);
//            ps.setInt(1, id);
//            ps.executeUpdate();
//            flag = true;
//        } finally {
//            if (wrapperConnection != null) {
//                wrapperConnection.close();
//            }
//        }
//        return flag;
//    }
    
    /**
     * Get one order by result set
     * @param rs result set of sql query
     * @return DBEntity object
     * @throws SQLException
     */
    @Override
    protected Order getEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("order_id");
        int userId = rs.getInt("user_id");
        OrderStatus status = OrderStatus.valueOf(rs.getString("status"));
        BigDecimal price = rs.getBigDecimal("total_price");
        Order newOrder = new Order(userId, status, price);
        newOrder.setId(id);
        return newOrder;
    }
    
}
