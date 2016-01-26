/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions.personal;

import controller.ConfigManager;
import controller.action.Validator;
import java.io.IOException;
import javax.servlet.ServletException;
import model.entity.Person;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Changes users' password
 * @author Sasha
 */
public abstract class ChangePassword extends PersonalPostAction {

    /**
     * Perform password changing
     * 
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected String doExecute() throws ServletException, IOException {
        Person person = getPersonFromSession();
        if (person == null) {
            return null;
        }
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        if (!checkForNotNull(oldPassword, newPassword, confirmPassword)) {
//            sendRedirect(null, "settings.errormessage.easypasword", "settings");
            setMessages(null, "settings.errormessage.easypasword");
            return ConfigManager.getProperty("path.page.settings");
        }
        if (!isValid(newPassword)) {
//            sendRedirect(null, "settings.errormessage.easypasword", "settings");
            setMessages(null, "settings.errormessage.easypasword");
            return ConfigManager.getProperty("path.page.settings");
        }
        String hexOldPassword = DigestUtils.shaHex(oldPassword);
        String hexNewPassword = DigestUtils.shaHex(newPassword);
        String hexConfirmPassword = DigestUtils.shaHex(confirmPassword);
        if (!checkPasswords(person, hexOldPassword, hexNewPassword, hexConfirmPassword) 
                || !changePassword(person, hexNewPassword)) {
            return null;
        }
//        sendRedirect("settings.message.passwordchanged", null, "settings");
        setMessages("settings.message.passwordchanged", null);
        return ConfigManager.getProperty("path.page.settings");
    }
    
    /**
     * Check are fields null or no
     * @param oldPassword old person password
     * @param newPassword new person password
     * @param confirmPassword password confirmation
     * @return true if all fields non null and false if at last one of them is 
     * null or empty text field
     */
    private boolean checkForNotNull(String oldPassword, String newPassword, 
            String confirmPassword) {
        if ((oldPassword == null || oldPassword.equals("")) 
                || (newPassword == null || newPassword.equals("")) 
                || (confirmPassword == null || confirmPassword.equals(""))) { 
            return false;
        }
        return true;
    }
    
    /**
     * Check new password for validation
     * @param newPassword ne person password
     * @return true if password is valid and fale otherwise
     */
    private boolean isValid(String newPassword) {
        Validator validator = new Validator();
        if (!validator.checkPassword(newPassword)) {
            return false;
        }
        return true;
    }
    
    /**
     * Check input password for correctness and for matching with existing one.
     * If it is successful, change password and set user with new password in 
     * current session
     * 
     * @param user user needs to change password
     * @param hexOldPassword existing password
     * @param hexNewPassword new password
     * @param hexConfirmPassword password confirmation
     * @return boolean true if all fields are correct and false otherwise (in 
     * this case redirection will be performed in this method)
     */
    private boolean checkPasswords(Person person, String hexOldPassword, 
            String hexNewPassword, String hexConfirmPassword) throws 
            ServletException, IOException {
        if (!hexOldPassword.equals(person.getPassword())) {
            sendRedirect(null, "settings.errormessage.wrongpassword", "settings");
            return false;
        }
        if (!hexNewPassword.equals(hexConfirmPassword)) {
            sendRedirect(null, "settings.errormessage.paswordnotmatched", "settings");
            return false;
        }
        return true;
    }

    /**
     * Change password. If it is successful, set user with new password to 
     * current session
     * 
     * @param person person needs to change password
     * @param hexNewPassword new password
     * @return true if password cganging was seccessful and false otherwise (in 
     * this case redirection will be performed in this method)
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected abstract boolean changePassword(Person person, String hexNewPassword) throws 
            ServletException, IOException;
    
}
