/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import controller.action.getactions.HomePage;
import java.io.IOException;
import java.sql.SQLException;
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
     * Get the same user from data base by its' email with updated information 
     * and set it to current session
     * 
     * @param email users' email
     * @return error message or null if setting was successsful
     * @throws ServletException
     * @throws IOException 
     * @throws java.sql.SQLException 
     * @throws model.dao.ServerOverloadedException 
     */
    protected String setUserToSession(String email) throws ServletException, 
            IOException, SQLException, ServerOverloadedException {
        User user = null;
        UserCreator userCreator = new UserCreator();
        user = (User) userCreator.getUserByEmail(email);
        if (user == null) {
            return "login.errormessage.nosuchuser";
        }
        session.setAttribute("user", user);
        return null;
    }
    
    /**
     * Back to home page and print error message if error message parameter 
     * does not equals null
     * 
     * @param errorMessage error message
     * @throws javax.servlet.ServletException
     * @throws IOException 
     */
    protected void goToHome(String errorMessage) throws ServletException, IOException {
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
        }
        new HomePage().execute(request, response);
    }
    
    /**
     * Show error message only
     * 
     * @param message error message
     * @throws IOException
     * @throws ServletException 
     */
    public void showMessage(String message) throws IOException, 
            ServletException {
        request.setAttribute("title", message);
        new LanguageBlock().execute(request, response);
        new SetAuthorizationBlock().execute(request, response);
        request.setAttribute("errorMessage", message);
        request.getRequestDispatcher("/view/error.jsp").
                include(request, response);
    }
    
}
