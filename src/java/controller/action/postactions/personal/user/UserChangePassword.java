/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions.personal.user;

import controller.action.postactions.personal.ChangePassword;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import model.dao.ServerOverloadedException;
import model.dao.UserCreator;
import model.entity.Person;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public class UserChangePassword extends ChangePassword {
    
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
    @Override
    protected boolean changePassword(Person person, String hexNewPassword) throws 
            ServletException, IOException {
        User user = (User) person;
        UserCreator userCreator = new UserCreator();
        try {
            if (!userCreator.changePassword(user, hexNewPassword)) {
                sendRedirect(null, "settings.errormessage.passwordnotchanged", "link.settings");
                return false;
            }
            return setUserToSession(person.getEmail());
        } catch (SQLException ex) {
            sendRedirect(null, "exception.errormessage.sqlexception", "link.settings");
            return false;
        } catch (ServerOverloadedException ex) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "link.settings");
            return false;
        }
    }
    
}
