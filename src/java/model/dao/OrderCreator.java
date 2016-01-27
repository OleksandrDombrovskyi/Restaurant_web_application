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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.entity.Meal;
import model.entity.Order;
import model.entity.Order.OrderStatus;
import model.entity.OrderItem;

/**
 * Order creator
 * @author Sasha
 */
public class OrderCreator extends EntityCreator {
    
    /**
     * Constructor 
     */
    public OrderCreator() {
        super(ORDER_TABLE, ORDER_ID);
    }

    /**
     * Create new order
     * 
     * @param order order migth be inserted into the data base
     * @return non zero order id if order was added successfuly and zero 
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
            wrapperConnection.setAutoCommit(false);
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
                        orderId = order.getId();
                    }
                }
            }
            wrapperConnection.commit();
            wrapperConnection.setAutoCommit(true);
        } catch (SQLException e) {
            wrapperConnection.rollback();
            wrapperConnection.setAutoCommit(true);
            throw new SQLException();
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
            ServerOverloadedException {
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
     * Insert item into the data base
     * @param orderItem order item might be inserted
     * @param orderId order is
     * @param ps prepared statement 
     * @throws SQLException 
     * @throws ServerOverloadedException 
     */
    private void insertItem(OrderItem orderItem, int orderId, PreparedStatement ps) 
            throws SQLException, ServerOverloadedException {
        ps.setInt(1, orderId);
        ps.setInt(2, orderItem.getMeal().getId());
        ps.setInt(3, orderItem.getMealAmount());
        ps.setBigDecimal(4, orderItem.getTotalPrice());
        ps.executeUpdate();
    }
    
    /**
     * Update item in the data base
     * @param orderItem order item
     * @param orderId order id
     * @param ps prepared statement
     * @throws SQLException
     * @throws ServerOverloadedException 
     */
    private void updateItem(OrderItem orderItem, int orderId, PreparedStatement ps) 
            throws SQLException, ServerOverloadedException {
        ps.setInt(1, orderItem.getMealAmount());
        ps.setBigDecimal(2, orderItem.getTotalPrice());
        ps.setInt(3, orderId);
        ps.setInt(4, orderItem.getMeal().getId());
        ps.executeUpdate();
    }
    
    
    /**
     * Get users' orders (by user id) WITHOUT items
     * @param userId
     * @return order array list
     * @throws SQLException
     * @throws ServerOverloadedException 
     */
    public List<Order> getOrdersByUserId(int userId) throws SQLException, 
            ServerOverloadedException {
        List<Order> orders = new ArrayList<>();
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            try (PreparedStatement ps = wrapperConnection.
                    prepareStatement(SQL_FOR_ORDERS_BY_USER_ID)) {
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    orders.add(getOrderWithoutItems(rs));
                }
            }
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
        return orders;
    }
    
    /**
     * Get order by result set WITHOUT items
     * @param rs result set
     * @return order without items
     * @throws SQLException
     * @throws ServerOverloadedException 
     */
    private Order getOrderWithoutItems(ResultSet rs) throws SQLException, 
            ServerOverloadedException {
        int orderId = rs.getInt("order_id");
        int userId = rs.getInt("user_id");
        OrderStatus status = OrderStatus.valueOf(rs.getString("status"));
        BigDecimal price = rs.getBigDecimal("total_price");
        Timestamp date = rs.getTimestamp("date");
        Order newOrder = new Order(userId, status, price, date);
        newOrder.setId(orderId);
        return newOrder;
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
        Order newOrder = getOrderWithoutItems(rs);
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

    /**
     * Remove order from the date base
     * @param orderId order id
     * @return true if updating is successful and false otherwise
     * @throws SQLException
     * @throws ServerOverloadedException 
     */
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

    /**
     * Delete item from the data base
     * @param orderId order id
     * @return true if deleting is successful and false otherwise
     * @throws SQLException
     * @throws ServerOverloadedException 
     */
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

    /**
     * Get order with NOT_CONFIRMED status (basket order) of user with concrete id
     * 
     * @param userId user id
     * @return order with NOT_CONFIRMED status (basket order) or null if it 
     * not exists
     * @throws SQLException
     * @throws ServerOverloadedException 
     */
    public Order getNotConfirmedOrder(int userId) throws SQLException, 
            ServerOverloadedException {
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            try (PreparedStatement ps = wrapperConnection.
                    prepareStatement(SQL_FOR_GETTING_BASKET_ORDER)) {
                ps.setInt(1, userId);
                ps.setString(2, OrderStatus.NOT_CONFIRMED.name());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return getEntity(rs);
                }
            }
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
        return null;
    }

    /**
     * Set CREATED (instead of NOT_CONFIRMED) status to the basket order for 
     * user with concrete id
     * 
     * @param userId id of user for which basket might be confirmed
     * @return 0 if there is no orders with NOT_CONFIRMED status (basket is 
     * empty) and 1 if basket is not empty and status setting was performed 
     * successfully (see more in JavaDocs for PreparedStatement.executeUpdate)
     * (see JavaDocs for PreparedStatement.executeUpdate method)
     * @throws SQLException
     * @throws ServerOverloadedException 
     */
    public int confirmBasket(int userId) throws SQLException, 
            ServerOverloadedException {
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            try (PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_BASKET_CONFIRMATION)) {
                ps.setString(1, OrderStatus.CREATED.name());
                ps.setTimestamp(2, new Timestamp(new Date().getTime()));
                ps.setInt(3, userId);
                ps.setString(4, OrderStatus.NOT_CONFIRMED.name());
                return ps.executeUpdate();
            }
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
    }

    /**
     * Add item to basket
     * 
     * @param basketOrder basket order
     * @param newBasketOrder basket order with new items might be added to the 
     * old basket order
     * @return ture if adding is successful and false otherwise
     * @throws SQLException
     * @throws ServerOverloadedException 
     */
    public boolean addItemsToBasket(Order basketOrder, Order newBasketOrder) 
            throws SQLException, ServerOverloadedException {
        boolean returnFlag = false;
        int orderId = basketOrder.getId();
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            try (PreparedStatement psInsert = wrapperConnection.
                    prepareStatement(SQL_FOR_ITEMS_INSERTING);
                    PreparedStatement psUpdate = wrapperConnection.
                            prepareStatement(SQL_FOR_ITEMS_UPDATING)) {
                boolean flag;
                for (OrderItem newItem : newBasketOrder.getOrderItems()) {
                    flag = false;
                    for (OrderItem item : basketOrder.getOrderItems()) {
                        if (item.getMeal().getId() == newItem.getMeal().getId()) {
                            int amount = item.getMealAmount() + newItem.getMealAmount();
                            BigDecimal totalPrice = item.getTotalPrice().add(newItem.getTotalPrice());
                            OrderItem updatedItem = new OrderItem(item.getMeal(), amount, totalPrice);
                            updateItem(updatedItem, orderId, psUpdate);
                            flag = true;
                            break;
                        } 
                    }
                    if (!flag) {
                        insertItem(newItem, orderId, psInsert);
                    }
                }
                updateOrderTotalPrice(orderId);
                returnFlag = true;
            }
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
        return returnFlag;
    }
    
    /**
     * Update order total price according to all items that are in this order
     * 
     * @param orderId order id
     * @throws SQLException
     * @throws ServerOverloadedException 
     */
    private void updateOrderTotalPrice(int orderId) throws SQLException, 
            ServerOverloadedException{
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            try (PreparedStatement ps = wrapperConnection.
                    prepareStatement(SQL_FOR_PRICE_UPDATING)) {
                ps.setInt(1, orderId);
                ps.setInt(2, orderId);
                ps.executeUpdate();
            }
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
    }
    
    /**
     * Set status of order with concrete id 
     * 
     * @param orderId order id
     * @param orderStatus order status
     * @return 0 if there is no orders with such id and 1 status setting was 
     * performed successfully (see more in JavaDocs for PreparedStatement.executeUpdate)
     * @throws SQLException
     * @throws ServerOverloadedException 
     */
    public int setOrderStatus(int orderId, OrderStatus orderStatus) throws SQLException, 
            ServerOverloadedException {
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            try (PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_ORDER_STATUS_SETTING)) {
                ps.setString(1, orderStatus.name());
                ps.setInt(2, orderId);
                return ps.executeUpdate();
            }
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
    }
    
    /**
     * Get all orders with concrete status (orders without items)
     * 
     * @param orderStatus order status 
     * @return list of orders
     * @throws SQLException
     * @throws ServerOverloadedException 
     */
    public List<Order> getOrdersByStatus(OrderStatus orderStatus)
        throws SQLException, ServerOverloadedException {
        List<Order> orders = new ArrayList<>();
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            try (PreparedStatement ps = wrapperConnection.
                    prepareStatement(SQL_FOR_ORDERS_BY_STATUS)) {
                ps.setString(1, orderStatus.name());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    orders.add(getOrderWithoutItems(rs));
                }
            }
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
        return orders;
    }
    
}
