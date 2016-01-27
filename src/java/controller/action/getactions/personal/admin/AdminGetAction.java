/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal.admin;

import controller.action.getactions.personal.AbstractOrders;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import model.dao.OrderCreator;
import model.dao.ServerOverloadedException;
import model.dao.UserCreator;
import model.entity.Order;
import model.entity.User;

/**
 * Get action abstract class for admin requests
 * @author Sasha
 */
public abstract class AdminGetAction extends AbstractOrders {

    /**
     * Constructor
     * @param title page title key value
     */
    public AdminGetAction(String title) {
        super(title);
    }
    
    /**
     * Get all users from data base
     * @return list of users
     * @throws ServletException
     * @throws IOException 
     */
    protected List<User> getAllUsers() throws ServletException, IOException {
        UserCreator userCreator = new UserCreator();
        try {
            return (List<User>) userCreator.getAllEntities();
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
            sendRedirect(null, "exception.errormessage.sqlexception", "administration");
            return null;
        } catch (ServerOverloadedException e) {
            LOGGER.info(e.getMessage());
            sendRedirect(null, "exception.errormessage.serveroverloaded", "administration");
            return null;
        }
    }
    
    /**
     * Get all orders from data base
     * @return list oof orders
     * @throws ServletException
     * @throws IOException 
     */
    protected List<Order> getAllOrders() throws ServletException, IOException {
        OrderCreator orderCreator = new OrderCreator();
        try {
            return (List<Order>) orderCreator.getAllEntities();
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
            sendRedirect(null, "exception.errormessage.sqlexception", "administration");
            return null;
        } catch (ServerOverloadedException e) {
            LOGGER.info(e.getMessage());
            sendRedirect(null, "exception.errormessage.serveroverloaded", "administration");
            return null;
        }
    }
    
    /**
     * Create user map: key is user id, value = user object
     * @param users list of users
     * @return hash map with users by user id keys
     */
    protected Map<Integer, User> createUserMap(List<User> users) {
        Map<Integer, User> userMap = new HashMap<>();
        for (User user : users) {
            userMap.put(user.getId(), user);
        }
        return userMap;
    }
    
}
