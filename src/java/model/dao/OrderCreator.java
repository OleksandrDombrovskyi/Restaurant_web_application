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
    
    private static final String SQL_FOR_ITEMS_UPDATING = 
            "UPDATE restaurantdatabase.order_items SET number = ?, price = ? "
            + "WHERE order_id = ? AND meal_id = ?";
    
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
    
    /** sql query for all users' orders except that has status NOT_CONFIRMED */
    private static final String SQL_FOR_ORDERS_BY_USER_ID = 
            "SELECT * FROM restaurantdatabase.order "
            + "WHERE user_id = ? AND status <> 'NOT_CONFIRMED'";
    
    /** sql query for getting not confirmed order that is uders' basket */
    private static final String SQL_FOR_GETTING_BASKET_ORDER = 
            "SELECT * FROM restaurantdatabase.order "
            + "WHERE user_id = ? AND status = ?";
    
    private static final String SQL_FOR_INSERTING_BASKET = 
            "INSERT INTO restaurantdatabase.order "
            + "(user_id) VALUES (?)";
    
    /** sql query to set status CREATED and set current time to new order */
    private static final String SQL_FOR_BASKET_CONFIRMATION = 
            "UPDATE restaurantdatabase.order SET status = ?, date = ? "
            + "WHERE user_id = ? AND status = ?";
    
    private static final String SQL_FOR_PRICE_UPDATING = 
            "UPDATE restaurantdatabase.order SET total_price = "
            + "(SELECT SUM(price) FROM restaurantdatabase.order_items "
            + "WHERE order_id = ?)";
    
    /** set required order status */
    private static final String SQL_FOR_ORDER_STATUS_SETTING = 
            "UPDATE restaurantdatabase.order SET status = ? "
            + "WHERE order_id = ?";
    
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
    
    private void insertItem(OrderItem orderItem, int orderId, PreparedStatement ps) 
            throws SQLException, ServerOverloadedException {
        ps.setInt(1, orderId);
        ps.setInt(2, orderItem.getMeal().getId());
        ps.setInt(3, orderItem.getMealAmount());
        ps.setBigDecimal(4, orderItem.getTotalPrice());
        ps.executeUpdate();
    }
    
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

    public void createBasketOrder(int userId) throws SQLException, 
            ServerOverloadedException {
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            try (PreparedStatement ps = wrapperConnection.
                    prepareStatement(SQL_FOR_INSERTING_BASKET)){
                ps.setInt(1, userId);
                ps.executeUpdate();
            }
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
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
    
    private void updateOrderTotalPrice(int orderId) throws SQLException, 
            ServerOverloadedException{
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            try (PreparedStatement ps = wrapperConnection.
                    prepareStatement(SQL_FOR_PRICE_UPDATING)) {
                ps.setInt(1, orderId);
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
    
}
