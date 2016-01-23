/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal.admin;

import controller.action.getactions.GetAction;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import model.dao.ServerOverloadedException;
import model.dao.UserCreator;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public abstract class AdminGetAction extends GetAction {
    
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
            sendRedirect(null, "exception.errormessage.sqlexception", "administration");
            return null;
        } catch (ServerOverloadedException ex) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "administration");
            return null;
        }
    }
    
    /**
     * Get user by id
     * @param userId user id
     * @return user object
     * @throws ServletException
     * @throws IOException 
     */
    protected User getUserById(int userId) throws ServletException, IOException {
        UserCreator userCreator = new UserCreator();
        try {
            return (User) userCreator.getEntityById(userId);
        } catch (SQLException e) {
            sendRedirect(null, "exception.errormessage.sqlexception", "getAllOrders");
        } catch (ServerOverloadedException ex) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "getAllOrders");
        }
        return null;
    }
    
}
