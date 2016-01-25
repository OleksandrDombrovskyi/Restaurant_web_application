/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.dao.OrderCreator;
import model.dao.ServerOverloadedException;
import model.dao.UserCreator;
import model.entity.Order;
import model.entity.User;

/**
 * Action abstract class
 * @author Sasha
 */
public abstract class Action {
    
    /** http servlet request */
    protected HttpServletRequest request;
    
    /** http servlet response */
    protected HttpServletResponse response;
    
    /** http session */
    protected HttpSession session;
    
    /**
     * Initialization required variables and run doExecute method
     * 
     * @param request http servlet reuest
     * @param response http servlet response
     * @throws ServletException
     * @throws IOException 
     */
    public void execute(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        this.request = request;
        this.response = response;
        session = request.getSession();
        doExecute();
    }
    
    /**
     * Execute required action
     * 
     * @throws ServletException
     * @throws IOException 
     */
    protected abstract void doExecute() throws ServletException, IOException;
    
    /**
     * Send redirect to some get action
     * @param message message if it is required in some case
     * @param errorMessage error message if it is required in some case
     * @param action specific servlet get action
     * @throws ServletException 
     * @throws IOException 
     */
    protected void sendRedirect(String message, String errorMessage, String action) 
            throws ServletException, IOException {
        setMessages(message, errorMessage);
        if (action != null && !action.equals("")) {
            response.sendRedirect(request.getContextPath() + "/servlet?getAction=" + action);
        } else {
            response.sendRedirect(request.getContextPath() + "/servlet?getAction=home");
        }
    }
    
    /**
     * Send redirect to the same page
     * @param message message if it is required in some case
     * @param errorMessage error message if it is required in some case
     * @throws ServletException 
     * @throws IOException 
     */
    protected void sendRedirect(String message, String errorMessage)
            throws ServletException, IOException {
        setMessages(message, errorMessage);
        String path = request.getHeader("Referer");
        if (path != null) {
            response.sendRedirect(path);
            return;
        }
        response.sendRedirect(request.getContextPath() + "/");
    }
    
    /**
     * Set messages if they exist
     * @param message message
     * @param errorMessage error message
     */
    private void setMessages(String message, String errorMessage) {
        if (message != null && !message.equals("")) {
            session.setAttribute("message", message);
        }
        if (errorMessage != null && !errorMessage.equals("")) {
            session.setAttribute("errorMessage", errorMessage);
        }
    }
    
    /**
     * Get order by id
     * 
     * @param orderId order id
     * @return order object
     * @throws ServletException
     * @throws IOException 
     */
    protected Order getOrderById(int orderId) throws ServletException, IOException {
        OrderCreator orderCreator = new OrderCreator();
        try {
            return (Order) orderCreator.getEntityById(orderId);
        } catch (SQLException e) {
            sendRedirect(null, "exception.errormessage.sqlexception");
        } catch (ServerOverloadedException ex) {
            sendRedirect(null, "exception.errormessage.serveroverloaded");
        }
        return null;
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
