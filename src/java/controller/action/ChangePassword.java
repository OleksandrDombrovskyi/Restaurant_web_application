/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

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
public class ChangePassword extends Action {

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
            goToHome("login.errormessage.loginplease");
            return;
        }
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        String errorMessage = checkAndChangePassword(user, oldPassword, 
                newPassword, confirmPassword);
        if (errorMessage != null) {
            startOver(errorMessage);
            return;
        }
        createPage();
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
     * @return error message or null if password passed all checks
     */
    private String checkAndChangePassword(User user, String oldPassword, 
            String newPassword, String confirmPassword) throws 
            ServletException, IOException {
        if ((oldPassword == null || oldPassword.equals("")) 
                && (newPassword == null || newPassword.equals("")) 
                && (confirmPassword == null || confirmPassword.equals(""))) {
            return null;
        }
        if (!oldPassword.equals(user.getPassword())) {
            return "settings.errormessage.wrongpassword";
        }
        if (!newPassword.equals(confirmPassword)) {
            return "settings.errormessage.paswordnotmatched";
        }
        Validator validator = new Validator();
        if (!validator.checkPassword(newPassword)) {
            return "settings.errormessage.easypasword";
        }
        return changePassword(user, newPassword);
    }

    /**
     * Change password. If it is successful, set user with new password to 
     * current session
     * 
     * @param user user needs to change password
     * @param newPassword new password
     * @return error message or null if changing is successful.
     */
    private String changePassword(User user, String newPassword) throws 
            ServletException, IOException {
        UserCreator userCreator = new UserCreator();
        try {
            if (!userCreator.changePassword(user, newPassword)) {
                return "settings.errormessage.passwordnotchanged";
            }
            return setUserToSession(user.getEmail());
        } catch (SQLException ex) {
            return "exception.errormessage.sqlexception";
        } catch (ServerOverloadedException ex) {
            return "exception.errormessage.serveroverloaded";
        }
    }
    
    /**
     * Create next page with all required information
     * 
     * @throws ServletException
     * @throws IOException 
     */
    private void createPage() throws ServletException, IOException {
        request.setAttribute("message", "settings.message.passwordcganged");
        new Settings().execute(request, response);
    }
    
    /**
     * Back to filling the form couse of uncorrect field filling and sending 
     * correspond error message
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param errorMessage text value of text property file which corresponds 
     * to the error message
     * @throws ServletException
     * @throws IOException 
     */
    private void startOver(String errorMessage) throws ServletException, 
            IOException {
        request.setAttribute("errorMessage", errorMessage);
        new Settings().execute(request, response);
    }
    
}
