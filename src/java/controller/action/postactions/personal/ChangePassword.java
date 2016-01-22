/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions.personal;

import controller.action.Validator;
import java.io.IOException;
import javax.servlet.ServletException;
import model.entity.Person;

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
    protected void doExecute() throws ServletException, IOException {
        Person person = getPersonFromSession();
        if (person == null) {
            return;
        }
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        if (!checkPasswords(person, oldPassword, newPassword, confirmPassword) 
                || !changePassword(person, newPassword)) {
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
    private boolean checkPasswords(Person person, String oldPassword, 
            String newPassword, String confirmPassword) throws 
            ServletException, IOException {
        if ((oldPassword == null || oldPassword.equals("")) 
                || (newPassword == null || newPassword.equals("")) 
                || (confirmPassword == null || confirmPassword.equals(""))) {
            sendRedirect(null, "settings.errormessage.easypasword", "settings");
            return false;
        }
        if (!oldPassword.equals(person.getPassword())) {
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
     * @param person person needs to change password
     * @param newPassword new password
     * @return true if password cganging was seccessful and false otherwise (in 
     * this case redirection will be performed in this method)
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected abstract boolean changePassword(Person person, String newPassword) throws 
            ServletException, IOException;
    
}
