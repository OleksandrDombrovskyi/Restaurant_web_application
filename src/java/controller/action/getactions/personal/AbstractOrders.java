/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal;

import controller.action.ConcreteLink;
import controller.action.getactions.GetAction;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import model.dao.OrderCreator;
import model.dao.ServerOverloadedException;
import model.entity.Order;

/**
 *
 * @author Sasha
 */
public abstract class AbstractOrders extends GetAction {
    
    /**
     * Get all orders by user id
     * @param userId user id
     * @return orders if they exist in data base or null otherwise
     * @throws ServletException
     * @throws IOException 
     */
    protected List<Order> getOrdersByUserId(int userId) 
            throws ServletException, IOException {
        OrderCreator orderCreator = new OrderCreator();
        try {
            return (List<Order>) orderCreator.getOrdersByUserId(userId);
        } catch (SQLException e) {
            sendRedirect(null, "exception.errormessage.sqlexception", "profile");
            return null;
        } catch (ServerOverloadedException e) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "profile");
            return null;
        }
    }
    
}
