/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import controller.action.getactions.HomePage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.dao.ServerOverloadedException;
import model.dao.UserCreator;
import model.entity.User;

/**
 *
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
        if (message != null && !message.equals("")) {
            session.setAttribute("message", message);
        }
        if (errorMessage != null && !errorMessage.equals("")) {
            session.setAttribute("errorMessage", errorMessage);
        }
        if (action != null && !action.equals("")) {
            response.sendRedirect(request.getContextPath() + "/servlet?getAction=" + action);
        } else {
            response.sendRedirect(request.getContextPath() + "/servlet?getAction=home");
        }
    }
    
}
