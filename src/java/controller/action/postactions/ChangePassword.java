/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions;

import controller.action.Validator;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import model.dao.ServerOverloadedException;
import model.dao.UserCreator;
import model.entity.User;

/**
 * Changes users' password
 * @author Sasha
 */
public class ChangePassword extends PostAction {

    /**
     * Perform password changing
     * 
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            sendRedirect(null, "login.errormessage.loginplease", "home");
            return;
        }
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        if (!checkPasswords(user, oldPassword, newPassword, confirmPassword) 
                || !changePassword(user, newPassword) 
                || !setUserToSession(user.getEmail())) {
            return;
        }
        sendRedirect("settings.message.passwordchanged", null, "settings");
    }
    
    /**
     * Check input password for correctness and for matching with existing one.
     * If it is successful, change password and set user with new password in 
     * current session
     * 
     * @param user user needs to change password
     * @param oldPassword existing password
     * @param newPassword new password
     * @param confirmPassword password confirmation
     * @return boolean true if all fields are correct and false otherwise (in 
     * this case redirection will be performed in this method)
     */
    private boolean checkPasswords(User user, String oldPassword, 
            String newPassword, String confirmPassword) throws 
            ServletException, IOException {
        if ((oldPassword == null || oldPassword.equals("")) 
                || (newPassword == null || newPassword.equals("")) 
                || (confirmPassword == null || confirmPassword.equals(""))) {
            sendRedirect(null, "settings.errormessage.easypasword", "settings");
            return false;
        }
        if (!oldPassword.equals(user.getPassword())) {
            sendRedirect(null, "settings.errormessage.wrongpassword", "settings");
            return false;
        }
        if (!newPassword.equals(confirmPassword)) {
            sendRedirect(null, "settings.errormessage.paswordnotmatched", "settings");
            return false;
        }
        Validator validator = new Validator();
        if (!validator.checkPassword(newPassword)) {
            sendRedirect(null, "settings.errormessage.easypasword", "settings");
            return false;
        }
        return true;
    }

    /**
     * Change password. If it is successful, set user with new password to 
     * current session
     * 
     * @param user user needs to change password
     * @param newPassword new password
     * @return true if password cganging was seccessful and false otherwise (in 
     * this case redirection will be performed in this method)
     */
    private boolean changePassword(User user, String newPassword) throws 
            ServletException, IOException {
        UserCreator userCreator = new UserCreator();
        try {
            if (!userCreator.changePassword(user, newPassword)) {
                sendRedirect(null, "settings.errormessage.passwordnotchanged", "settings");
                return false;
            }
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
