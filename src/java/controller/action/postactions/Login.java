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
import model.dao.DaoEnum;
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
    
    /** key for message about invalid password */
    private final static String INVALID_PASSWORD = "login.errormessage.invalidpassword";

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
            sendRedirect(null, "login.errormessage.empty", LOGIN_REQUEST_LINK);
            return;
        }
        String hexPassword = DigestUtils.shaHex(password);
        if (!userAuthorization(email, hexPassword)) {
            if (!adminAuthorization(email, hexPassword)) {
                if (!kitchenAuthorization(email, hexPassword)) {
                    sendRedirect(null, NO_SUCH_USER, LOGIN_REQUEST_LINK);
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
        UserCreator userCreator = (UserCreator) daoFactory.getCreator(DaoEnum.USER_CREATOR);
        User user = null;
        try {
            user = (User) userCreator.getUserByEmail(email);
            if (user == null) {
                return false;
            }
            if (user.getPassword().equals(password)) {
                session.setAttribute("user", user);
                showLastPage();
            } else {
                sendRedirect(null, INVALID_PASSWORD, LOGIN_REQUEST_LINK);
            }
        } catch (ServerOverloadedException e) {
            logger.info(e.getMessage());
            sendRedirect(null, SERVER_OVERLOADED_EXCEPTION, LOGIN_REQUEST_LINK);
        } catch (SQLException e) {
            logger.info(e.getMessage());
            sendRedirect(null, SQL_EXCEPTION, LOGIN_REQUEST_LINK);
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
        AdminCreator adminCreator = (AdminCreator) daoFactory.
                getCreator(DaoEnum.ADMIN_CREATOR);
        Admin admin = null;
        try {
            admin = (Admin) adminCreator.getAdminByEmail(email);
            if (admin == null) {
                return false;
            }
            if (admin.getPassword().equals(password)) {
                session.setAttribute("admin", admin);
                showLastPage();
            } else {
                sendRedirect(null, INVALID_PASSWORD, 
                        LOGIN_REQUEST_LINK);
            }
        } catch (ServerOverloadedException e) {
            logger.info(e.getMessage());
            sendRedirect(null, SERVER_OVERLOADED_EXCEPTION, 
                    LOGIN_REQUEST_LINK);
        } catch (SQLException e) {
            logger.info(e.getMessage());
            sendRedirect(null, SQL_EXCEPTION, 
                    LOGIN_REQUEST_LINK);
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
        KitchenCreator kitchenCreator = (KitchenCreator) daoFactory.getCreator(DaoEnum.KITCHEN_CREATOR);
        Kitchen kitchen = null;
        try {
            kitchen = (Kitchen) kitchenCreator.getKitchenByEmail(email);
            if (kitchen == null) {
                return false;
            }
            if (kitchen.getPassword().equals(password)) {
                session.setAttribute("kitchen", kitchen);
                sendRedirect(null, null, "link.showacceptedorders");
            } else {
                sendRedirect(null, INVALID_PASSWORD, 
                        LOGIN_REQUEST_LINK);
            }
        } catch (ServerOverloadedException e) {
            logger.info(e.getMessage());
            sendRedirect(null, SERVER_OVERLOADED_EXCEPTION, 
                    LOGIN_REQUEST_LINK);
        } catch (SQLException e) {
            logger.info(e.getMessage());
            sendRedirect(null, SQL_EXCEPTION, 
                    LOGIN_REQUEST_LINK);
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
            lastAction = configManager.getProperty(HOME_PAGE_LINK);
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
