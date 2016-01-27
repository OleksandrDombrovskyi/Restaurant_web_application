/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions.personal.admin;

import controller.action.postactions.personal.ChangePassword;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import model.dao.AdminCreator;
import model.dao.ServerOverloadedException;
import model.entity.Admin;
import model.entity.Person;

/**
 *
 * @author Sasha
 */
public class AdminChangePassword extends ChangePassword {
    
    /**
     * Change password. If it is successful, set admin with new password to 
     * current session
     * 
     * @param person person needs to change password
     * @param hexNewPassword new password
     * @return true if password cganging was seccessful and false otherwise (in 
     * this case redirection will be performed in this method)
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    protected boolean changePassword(Person person, String hexNewPassword) throws 
            ServletException, IOException {
        Admin admin = (Admin) person;
        AdminCreator adminCreator = new AdminCreator();
        try {
            if (!adminCreator.changePassword(admin, hexNewPassword)) {
                sendRedirect(null, "settings.errormessage.passwordnotchanged", "link.settings");
                return false;
            }
            return setAdminToSession(person.getEmail());
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
            sendRedirect(null, "exception.errormessage.sqlexception", "link.settings");
            return false;
        } catch (ServerOverloadedException e) {
            LOGGER.info(e.getMessage());
            sendRedirect(null, "exception.errormessage.serveroverloaded", "link.settings");
            return false;
        }
    }
    
}
