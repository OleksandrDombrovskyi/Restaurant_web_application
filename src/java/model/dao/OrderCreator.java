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
import java.sql.Timestamp;
import static model.dao.EntityCreator.CONNECTION_POOL;
import model.entity.Meal;
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
    private static final String SQL_FOR_INSERTING_ORDER = 
            "INSERT INTO restaurantdatabase.order "
            + "(user_id, total_price, status, date) VALUES (?, ?, ?, ?)";
    
    /** sql query for order items inserting */
    private static final String SQL_FOR_ITEMS_INSERTING = 
            "INSERT INTO order_items (order_id, meal_id, number, price) "
            + "VALUES (?, ?, ?, ?)";
    
    /** sql query to get order item by id */
    private static final String SQL_GET_ITEM_BY_ID = 
            "SELECT * FROM restaurantdatabase.order_items WHERE order_id = ?";
    
    /** sql query to set order status */
    private static final String SQL_SET_ORDER_STATUS = 
            "UPDATE restaurantdatabase.order SET status = ? WHERE order_id = ?";
    
    /** sql query to remove order from data base */ 
    private static final String SQL_FOR_ORDER_DELETING = 
            "DELETE FROM restaurantdatabase.order WHERE order_id = ?";
    
    /** sql query to remove items from data base by order id */
    private static final String SQL_FOR_ITEMS_DELETING = 
            "DELETE FROM restaurantdatabase.order_items WHERE order_id = ?";
    
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
     * @return non zero order id id order was added successfuly and zero 
     * otherwise
     * @throws java.sql.SQLException
     * @throws model.dao.ServerOverloadedException
     */
    public int insertOrder(Order order) throws SQLException, 
            ServerOverloadedException {
        int orderId = 0;
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            try (PreparedStatement ps = wrapperConnection.prepareStatement(
                    SQL_FOR_INSERTING_ORDER, Statement.RETURN_GENERATED_KEYS)){
                ps.setInt(1, order.getUserId());
                ps.setBigDecimal(2, order.getTotalPrice());
                ps.setString(3, order.getStatus().name());
                ps.setTimestamp(4, order.getDate());
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
                orderId = order.getId();
            }
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
        return orderId;
    }
    
    /**
     * Insert order item into the data base
     * @param order order with items
     * @param wrapperConnection wrapper connection
     * @throws SQLException 
     * @throws ServerOverloadedException 
     */
    private void insertItems(Order order,
            WrapperConnectionProxy wrapperConnection) throws SQLException, 
            ServerOverloadedException{
        try (PreparedStatement ps = wrapperConnection.
                prepareStatement(SQL_FOR_ITEMS_INSERTING)) {
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
     * @throws model.dao.ServerOverloadedException
     */
    @Override
    protected Order getEntity(ResultSet rs) throws SQLException, 
            ServerOverloadedException {
        int orderId = rs.getInt("order_id");
        int userId = rs.getInt("user_id");
        OrderStatus status = OrderStatus.valueOf(rs.getString("status"));
        BigDecimal price = rs.getBigDecimal("total_price");
        Timestamp date = rs.getTimestamp("date");
        Order newOrder = new Order(userId, status, price, date);
        newOrder.setId(orderId);
        getItemsByOrderId(newOrder);
        return newOrder;
    }

    /**
     * Get all items by order id
     * @param newOrder order is creating
     * @throws SQLException
     * @throws ServerOverloadedException 
     */
    private void getItemsByOrderId(Order newOrder) throws SQLException, 
            ServerOverloadedException {
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            try (PreparedStatement ps = wrapperConnection.prepareStatement(
                    SQL_GET_ITEM_BY_ID)){
                ps.setInt(1, newOrder.getId());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    newOrder.addOrderItem(getItem(rs));
                }
            }
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
    }
    
    /**
     * Get item from result set
     * @param rs result set
     * @return order item object
     * @throws SQLException
     * @throws ServerOverloadedException 
     */
    private OrderItem getItem(ResultSet rs) throws SQLException, 
            ServerOverloadedException {
        int mealId = rs.getInt("meal_id");
        MealCreator mealCreator = new MealCreator();
        Meal meal = (Meal) mealCreator.getEntityById(mealId);
        int mealAmount = rs.getInt("number");
        BigDecimal totalPrice = rs.getBigDecimal("price");
        return new OrderItem(meal, mealAmount, totalPrice);
    }

    /**
     * Set order status
     * @param orderId id of order which status might be changed
     * @param orderStatus statuc which might be set
     * @throws SQLException
     * @throws ServerOverloadedException 
     */
    public void setStatus(int orderId, OrderStatus orderStatus) 
            throws SQLException, ServerOverloadedException{
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            try (PreparedStatement ps = wrapperConnection.prepareStatement(
                    SQL_SET_ORDER_STATUS)){
                ps.setString(1, orderStatus.name());
                ps.setInt(2, orderId);
                ps.executeUpdate();
            }
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
    }

    public boolean removeOrder(int orderId) throws SQLException, 
            ServerOverloadedException {
        boolean flag = false;
        if (!deleteItems(orderId)) {
            return flag;
        }
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            try (PreparedStatement ps = 
                    wrapperConnection.prepareStatement(SQL_FOR_ORDER_DELETING)) {
                ps.setInt(1, orderId);
                ps.executeUpdate();
                flag = true;
            }
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
        return flag;
    }

    private boolean deleteItems(int orderId) throws SQLException, 
            ServerOverloadedException {
        boolean flag = false;
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            try (PreparedStatement ps = 
                    wrapperConnection.prepareStatement(SQL_FOR_ITEMS_DELETING)) {
                ps.setInt(1, orderId);
                ps.executeUpdate();
                flag = true;
            }
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
        return flag;
    }
    
}
