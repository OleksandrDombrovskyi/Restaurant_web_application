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
import model.dao.Dao;
import model.dao.DaoEnum;
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
    
    /** key for no users message */
    protected final static String NO_USERS = 
            "administration.users.message.nousers";
    
    /** path for page with orders for admin */
    protected final static String ADMIN_GET_ALL_ORDERS = 
            "path.page.admin.getallorders";
    
    /** path to rhe administration page for admin */
    protected final static String ADMINISTRATION = 
            "/servlet?getAction=administration";
    
    /** key for wrong user id */
    protected final static String WRONG_USER_ID = 
            "administration.users.errormessage.wronguserid";
    
    /** key for get users action for admin */
    protected final static String GET_USERS = "path.page.admin.getusers";

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
        Dao userCreator = daoFactory.getCreator(DaoEnum.USER_CREATOR);
        try {
            return (List<User>) ((UserCreator) userCreator).getAllEntities();
        } catch (SQLException e) {
            logger.info(e.getMessage());
            sendRedirect(null, SQL_EXCEPTION, ADMINISTRATION);
            return null;
        } catch (ServerOverloadedException e) {
            logger.info(e.getMessage());
            sendRedirect(null, SERVER_OVERLOADED_EXCEPTION, ADMINISTRATION);
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
        Dao orderCreator = daoFactory.getCreator(DaoEnum.ORDER_CREATOR);
        try {
            return (List<Order>) ((OrderCreator) orderCreator).getAllEntities();
        } catch (SQLException e) {
            logger.info(e.getMessage());
            sendRedirect(null, SQL_EXCEPTION, ADMINISTRATION);
            return null;
        } catch (ServerOverloadedException e) {
            logger.info(e.getMessage());
            sendRedirect(null, SERVER_OVERLOADED_EXCEPTION, ADMINISTRATION);
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
