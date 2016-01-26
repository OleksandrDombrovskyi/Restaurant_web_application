/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions;

import controller.ConfigManager;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import model.dao.AdminCreator;
import model.dao.KitchenCreator;
import model.dao.ServerOverloadedException;
import model.dao.UserCreator;
import model.entity.Admin;
import model.entity.Kitchen;
import model.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Set log in
 * @author Sasha
 */
public class Login extends PostAction {

    /**
     * Log in
     * @return property key value
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public void doExecute() 
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (isEmpty(email) || isEmpty(password)) {
            sendRedirect(null, "login.errormessage.empty", "link.loginrequest");
            return;
//            setMessages(null, "login.errormessage.empty");
//            return ConfigManager.getProperty("path.page.loginrequest");
        }
        String hexPassword = DigestUtils.shaHex(password);
        if (!userAuthorization(email, hexPassword)) {
            if (!adminAuthorization(email, hexPassword)) {
                if (!kitchenAuthorization(email, hexPassword)) {
                    sendRedirect(null, "login.errormessage.nosuchuser", "link.loginrequest");
//                    setMessages(null, "login.errormessage.nosuchuser");
//                    return ConfigManager.getProperty("path.page.loginrequest");
                }
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
//                sendRedirect(null, null, "home");
                showLastPage();
            } else {
                sendRedirect(null, "login.errormessage.invalidpassword", "link.loginrequest");
            }
        } catch (ServerOverloadedException e) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "link.loginrequest");
        } catch (SQLException e) {
            sendRedirect(null, "exception.errormessage.sqlexception", "link.loginrequest");
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
//                sendRedirect(null, null, "home");
                showLastPage();
            } else {
                sendRedirect(null, "login.errormessage.invalidpassword", "link.loginrequest");
            }
        } catch (ServerOverloadedException e) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "link.loginrequest");
        } catch (SQLException e) {
            sendRedirect(null, "exception.errormessage.sqlexception", "link.loginrequest");
        }
        return true;
    }
    
    /**
     * Authorization of kitchen if there is one in the data base and in case of 
     * paswords matching
     * 
     * @param email kitchens' email
     * @param password kitchens' password
     * @return true if kitchen was at least founded in the data base, does not 
     * matter whether do passwords match or not, and false if there is no kitchen 
     * with such email
     * 
     * @throws ServletException
     * @throws IOException 
     */
    private boolean kitchenAuthorization(String email, String password) 
            throws ServletException, IOException {
        KitchenCreator creator = new KitchenCreator();
        Kitchen kitchen = null;
        try {
            kitchen = (Kitchen) creator.getKitchenByEmail(email);
            if (kitchen == null) {
                return false;
            }
            if (kitchen.getPassword().equals(password)) {
                session.setAttribute("kitchen", kitchen);
                sendRedirect(null, null, "link.showacceptedorders");
            } else {
                sendRedirect(null, "login.errormessage.invalidpassword", "link.loginrequest");
            }
        } catch (ServerOverloadedException e) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "link.loginrequest");
        } catch (SQLException e) {
            sendRedirect(null, "exception.errormessage.sqlexception", "link.loginrequest");
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
        if (lastAction == null) {
            lastAction = ConfigManager.getProperty("link.home");
            response.sendRedirect(request.getContextPath() + lastAction);
        } else {
            response.sendRedirect(lastAction);
        }
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
