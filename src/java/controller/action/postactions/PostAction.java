/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions;

import controller.action.Action;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.dao.AdminCreator;
import model.dao.ServerOverloadedException;
import model.dao.UserCreator;
import model.entity.Admin;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public abstract class PostAction extends Action {
    
    /**
     * Initialization required variables and run doExecute method
     * 
     * @param request http servlet reuest
     * @param response http servlet response
     * @return string value of page property file
     * @throws ServletException
     * @throws IOException 
     */
    public String execute(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        this.request = request;
        this.response = response;
        session = request.getSession();
        return doExecute();
    }
    
    /**
     * Execute required action
     * @return string value of page property file
     * @throws ServletException
     * @throws IOException 
     */
    protected abstract String doExecute() throws ServletException, IOException;
    
    /**
     * Get the same user from data base by its' email with updated information 
     * and set it to current session
     * 
     * @param email users' email
     * @return boolean true if setting was successful and false otherwise (in 
     * this case redirection will be performed by this method)
     * @throws ServletException
     * @throws IOException 
     */
    protected boolean setUserToSession(String email) throws ServletException, 
            IOException {
        try {
            User user = null;
            UserCreator userCreator = new UserCreator();
            user = (User) userCreator.getUserByEmail(email);
            if (user == null) {
                sendRedirect(null, "login.errormessage.nosuchuser", "settings");
                return false;
            }
            session.setAttribute("user", user);
            return true;
        } catch (SQLException ex) {
            sendRedirect(null, "exception.errormessage.sqlexception", "settings");
            return false;
        } catch (ServerOverloadedException ex) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "settings");
            return false;
        }
    }
    
    /**
     * Get the same admin from data base by its' email with updated information 
     * and set it to current session
     * 
     * @param email admin' email
     * @return boolean true if setting was successful and false otherwise (in 
     * this case redirection will be performed by this method)
     * @throws ServletException
     * @throws IOException 
     */
    protected boolean setAdminToSession(String email) throws ServletException, 
            IOException {
        try {
            Admin admin = null;
            AdminCreator adminCreator = new AdminCreator();
            admin = (Admin) adminCreator.getAdminByEmail(email);
            if (admin == null) {
                sendRedirect(null, "login.errormessage.nosuchuser", "settings");
                return false;
            }
            session.setAttribute("admin", admin);
            return true;
        } catch (SQLException ex) {
            sendRedirect(null, "exception.errormessage.sqlexception", "settings");
            return false;
        } catch (ServerOverloadedException ex) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "settings");
            return false;
        }
    }
    
}
