/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal;

import controller.action.getactions.GetAction;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import model.dao.Dao;
import model.dao.DaoEnum;
import model.dao.OrderCreator;
import model.dao.ServerOverloadedException;
import model.entity.Order;

/**
 * Abstract orders class
 * @author Sasha
 */
public abstract class AbstractOrders extends GetAction {
    
    /** key to orders page */
    protected final static String ORDERS = "path.page.user.orders";

    /**
     * Constructor
     * @param title page title
     */
    public AbstractOrders(String title) {
        super(title);
    }
    
    /**
     * Get all orders by user id
     * @param userId user id
     * @return orders if they exist in data base or null otherwise
     * @throws ServletException
     * @throws IOException 
     */
    protected List<Order> getOrdersByUserId(int userId) 
            throws ServletException, IOException {
        Dao orderCreator = daoFactory.getCreator(DaoEnum.ORDER_CREATOR);
        try {
            return (List<Order>) ((OrderCreator) orderCreator).getOrdersByUserId(userId);
        } catch (SQLException e) {
            logger.info(e.getMessage());
            sendRedirect(null, SQL_EXCEPTION, PROFILE);
            return null;
        } catch (ServerOverloadedException e) {
            logger.info(e.getMessage());
            sendRedirect(null, SERVER_OVERLOADED_EXCEPTION, PROFILE);
            return null;
        }
    }
    
    /**
     * Get all orders with concrete status
     * 
     * @param orderStatus concrete status
     * @return list of orders 
     * @throws ServletException
     * @throws IOException 
     */
    protected List<Order> getOrdersByStatus(Order.OrderStatus orderStatus) throws ServletException, 
            IOException {
        Dao orderCreator = daoFactory.getCreator(DaoEnum.ORDER_CREATOR);
        try {
            return (List<Order>) ((OrderCreator) orderCreator).getOrdersByStatus(orderStatus);
        } catch (SQLException e) {
            logger.info(e.getMessage());
            sendRedirect(null, SQL_EXCEPTION);
            return null;
        } catch (ServerOverloadedException e) {
            logger.info(e.getMessage());
            sendRedirect(null, SERVER_OVERLOADED_EXCEPTION);
            return null;
        }
    }
    
}
