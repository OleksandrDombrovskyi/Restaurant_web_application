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
import static model.dao.EntityCreator.CONNECTION_POOL;
import model.entity.Order;
import model.entity.Order.OrderStatus;
import model.entity.OrderItem;

/**
 *
 * @author Sasha
 */
public class OrderCreator extends EntityCreator {
    
    /** sql value of order table name */
    private final static String ORDER_TABLE = "order";
    
    /** sql value of order id name */
    private final static String ORDER_ID = "order_id";
    
    /** sql query for inserting meal into the main menu table in the data base */
    private static final String SQL_FOR_INSERTING_ORDER = "INSERT INTO restaurantdatabase.order (status, user_id, total_price) VALUES (?, ?, ?)";
    //private static final String SQL_FOR_INSERTING_ORDER = "INSERT INTO `restaurantdatabase`.`order` (`status`, `user_id`, `total_price`) VALUES (?, ?, ?)";
    
    private static final String SQL_FOR_ITEMS_INSERTING = "INSERT INTO order_items (order_id, meal_id, number, price) VALUES (?, ?, ?, ?)";
    
    /**
     * Constructor 
     */
    public OrderCreator() {
        super(ORDER_TABLE, ORDER_ID);
    }

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
            try (PreparedStatement ps = wrapperConnection.prepareStatement(
                    SQL_FOR_INSERTING_ORDER, Statement.RETURN_GENERATED_KEYS)){
                ps.setInt(2, order.getUserId());
                ps.setBigDecimal(3, order.getTotalPrice());
                ps.setString(1, order.getStatus().name());
                int affectedRows = ps.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException();
                }
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        order.setId((int) generatedKeys.getLong(1));
                        insertItems(order, wrapperConnection);
                    } else {
                        throw new SQLException();
                    }
                }
                flag = true;
            }
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
        return flag;
    }
    
    private void insertItems(Order order, 
            WrapperConnectionProxy wrapperConnection) throws SQLException, 
            ServerOverloadedException{
        try (PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_ITEMS_INSERTING)) {
            for (OrderItem item : order.getOrderItems()) {
                ps.setInt(1, order.getId());
                ps.setInt(2, item.getMeal().getId());
                ps.setInt(3, item.getMealAmount());
                ps.setBigDecimal(4, item.getTotalPrice());
                ps.executeUpdate();
            }
        }
    }
    
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
