/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions.personal.user;

import controller.action.postactions.personal.SaveChanges;
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
public class UserSaveChanges extends SaveChanges {
    
    /**
     * Get user from the session
     * 
     * @return user object if it is in the session and null otherwise (in this 
     * case redirection will be performed by this method)
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected Person getPersonFromSession() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            sendRedirect(null, "login.errormessage.loginplease", "home");
            return null;
        }
        return user;
    }
    
    /**
     * Update users' private information (first name, last name and email). If
     * updating is successful, get the same user from data base by its' email 
     * with updated information and set it to the current session
     * 
     * @param userId user id
     * @param firstName users first name
     * @param lastName user last name
     * @param email user email
     * @return true if user update was seccessful and false otherwise (in this 
     * case redirection will be performed in this method)
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected boolean updatePerson(int userId, String firstName, String lastName, 
            String email) throws ServletException, IOException {
        User newUser = new User(firstName, lastName, email, "unusedPassword");
        newUser.setId(userId);
        UserCreator userCreator = new UserCreator();
        try {
            if (!userCreator.updateUser(newUser)) {
                sendRedirect(null, "settings.errormessage.changesnotsaved", "settings");
                return false;
            }
            return setUserToSession(newUser.getEmail());
        } catch (SQLException ex) {
            sendRedirect(null, "exception.errormessage.sqlexception", "settings");
            return false;
        } catch (ServerOverloadedException ex) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "settings");
            return false;
        }
    }
    
}
