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
import model.entity.Order;
import model.entity.Order.OrderStatus;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public class SingletonPaymentTransaction implements Dao {
    
    /** singleton payment transaction instance */
    private static SingletonPaymentTransaction instance = null;
    
    /**
     * Constructor
     */
    private SingletonPaymentTransaction() {}
    
    /**
     * Get singleton payment transaction object 
     * @return singleton payment transaction object 
     */
    public synchronized static SingletonPaymentTransaction getInstance() {
        if (instance == null) {
            instance = new SingletonPaymentTransaction();
        }
        return instance;
    }
    
    /**
     * Performe currency transfer from user account to admin account
     * 
     * @param user user who performs remittance
     * @param order pay order
     * @return true is transfer is successful and false otherwise
     * @throws SQLException
     * @throws ServerOverloadedException 
     */
    public boolean makePayment(User user, Order order) throws SQLException, 
            ServerOverloadedException {
        boolean flag = false;
        WrapperConnectionProxy wrapperConnection = null; 
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            wrapperConnection.setAutoCommit(false);
            try (PreparedStatement selectFrom = wrapperConnection.
                        prepareStatement(SQL_FOR_USER_BY_EMAIL);
                    PreparedStatement selectTo = wrapperConnection.
                            prepareStatement(SQL_FOR_ADMIN_BY_EMAIL);
                    PreparedStatement updateFrom = wrapperConnection.
                            prepareStatement(SQL_FOR_USER_ACCOUNT_UPDATING);
                    PreparedStatement updateTo = wrapperConnection.
                            prepareStatement(SQL_FOR_ADMIN_ACCOUNT_APDATING);
                    PreparedStatement selectOrder = wrapperConnection.
                            prepareStatement(SQL_FOR_ORDER_BY_ID);
                    PreparedStatement setOrderStatus = wrapperConnection.
                            prepareStatement(SQL_FOR_ORDER_STATUS_SETTING)) {
                
                // get user account
                BigDecimal userAccount = BigDecimal.ZERO;
                selectFrom.setString(1, user.getEmail());
                ResultSet rsFrom = selectFrom.executeQuery();
                if (rsFrom.next()) {
                    userAccount = rsFrom.getBigDecimal("account");
                }
                
                // get admin account
                BigDecimal adminAccount = BigDecimal.ZERO;
                selectTo.setString(1, "admin@gmail.com");
                ResultSet rsTo = selectTo.executeQuery();
                if (rsTo.next()) {
                    adminAccount = rsTo.getBigDecimal("account");
                }
                
                // get order price
                BigDecimal orderPrice = BigDecimal.ZERO;
                selectOrder.setInt(1, order.getId());
                ResultSet rsOrder = selectOrder.executeQuery();
                if (rsOrder.next()) {
                    orderPrice = rsOrder.getBigDecimal("total_price");
                }
                
                // evaluation
                BigDecimal userAccountResult = BigDecimal.ZERO;
                BigDecimal adminAccountResult = BigDecimal.ZERO;
                if (userAccount.compareTo(orderPrice) > 0) {
                    userAccountResult = userAccount.subtract(orderPrice);
                    adminAccountResult = adminAccount.add(orderPrice);
                } else {
                    throw new SQLException();
                }
                
                // updating
                updateFrom.setBigDecimal(1, userAccountResult);
                updateFrom.setInt(2, user.getId());
                updateFrom.executeUpdate();
                updateTo.setBigDecimal(1, adminAccountResult);
                updateTo.setInt(2, 1); // admin id = 1
                updateTo.executeUpdate();
                
                // set order status
                setOrderStatus.setString(1, OrderStatus.PAYED.name());
                setOrderStatus.setInt(2, order.getId());
                setOrderStatus.executeUpdate();
                
                // committing
                wrapperConnection.commit();
                wrapperConnection.setAutoCommit(true);
                flag = true;
            }
        } catch (SQLException e) {
            wrapperConnection.rollback();
            wrapperConnection.setAutoCommit(true);
            throw new SQLException();
        }finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
        return flag;
    }
    
}
