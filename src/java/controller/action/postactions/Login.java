/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import model.dao.AdminCreator;
import model.dao.ServerOverloadedException;
import model.dao.UserCreator;
import model.entity.Admin;
import model.entity.User;

/**
 * Set log in
 * @author Sasha
 */
public class Login extends PostAction {

    /**
     * Log in
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public void doExecute() 
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (isEmpty(email) || isEmpty(password)) {
            sendRedirect(null, "login.errormessage.empty", "loginRequest");
            return;
        }
        if (!userAuthorization(email, password)) {
            if (!adminAuthorization(email, password)) {
                sendRedirect(null, "login.errormessage.nosuchuser", "loginRequest");
            }
        }
    }
    
    /**
     * Authorization of user if there is one in the data base and in case of 
     * paswords matching
     * 
     * @param email users' email
     * @param password users' password
     * @return true if user was at least founded in the data base, does not 
     * matter whether do passwords match or not, and false if there is no user 
     * with such email
     * 
     * @throws ServletException
     * @throws IOException 
     */
    private boolean userAuthorization(String email, String password) 
            throws ServletException, IOException {
        UserCreator creator = new UserCreator();
        User user = null;
        try {
            user = (User) creator.getUserByEmail(email);
            if (user == null) {
                return false;
            }
            if (user.getPassword().equals(password)) {
                session.setAttribute("user", user);
                showLastPage();
            } else {
                sendRedirect(null, "login.errormessage.invalidpassword", "loginRequest");
            }
        } catch (ServerOverloadedException e) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "loginRequest");
        } catch (SQLException e) {
            sendRedirect(null, "exception.errormessage.sqlexception", "loginRequest");
        }
        return true;
    }

    /**
     * Authorization of admin if there is one in the data base and in case of 
     * paswords matching
     * 
     * @param email admins' email
     * @param password admins' password
     * @return true if admin was at least founded in the data base, does not 
     * matter whether do passwords match or not, and false if there is no admin 
     * with such email
     * 
     * @throws ServletException
     * @throws IOException 
     */
    private boolean adminAuthorization(String email, String password) 
            throws ServletException, IOException {
        AdminCreator creator = new AdminCreator();
        Admin admin = null;
        try {
            admin = (Admin) creator.getAdminByEmail(email);
            if (admin == null) {
                return false;
            }
            if (admin.getPassword().equals(password)) {
                session.setAttribute("admin", admin);
                showLastPage();
            } else {
                sendRedirect(null, "login.errormessage.invalidpassword", "loginRequest");
            }
        } catch (ServerOverloadedException e) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "loginRequest");
        } catch (SQLException e) {
            sendRedirect(null, "exception.errormessage.sqlexception", "loginRequest");
        }
        return true;
    }
    
    /**
     * Go to page was opened before login was requested of to hme page if last 
     * action equals null in the session
     * @throws ServletException
     * @throws IOException 
     */
    private void showLastPage() throws ServletException, IOException {
        String lastAction = (String) session.getAttribute("lastAction");
        if (lastAction != null) {
            sendRedirect(null, null, lastAction);
//            response.sendRedirect(request.getContextPath() + "/servlet?getAction=" + lastAction);
            return;
        }
        sendRedirect(null, null, "home");
    }

    /**
     * Check whether is string variable equals null or empty string
     * @param field string variable
     * @return true if string field is empty of equals null
     */
    private boolean isEmpty(String field) {
        return field == null || field.equals("");
    }
    
}
