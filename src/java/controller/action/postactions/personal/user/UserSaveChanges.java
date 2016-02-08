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
import model.dao.DaoEnum;
import model.dao.ServerOverloadedException;
import model.dao.UserCreator;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public class UserSaveChanges extends SaveChanges {
    
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
        UserCreator userCreator = 
                (UserCreator) daoFactory.getCreator(DaoEnum.USER_CREATOR);
        try {
            if (!userCreator.updateUser(newUser)) {
                sendRedirect(null, CHANGES_NOT_SAVED, 
                        SETTINGS);
                return false;
            }
            return setUserToSession(newUser.getEmail());
        } catch (SQLException e) {
            logger.info(e.getMessage());
            sendRedirect(null, SQL_EXCEPTION, 
                    SETTINGS);
            return false;
        } catch (ServerOverloadedException e) {
            logger.info(e.getMessage());
            sendRedirect(null, SERVER_OVERLOADED_EXCEPTION, 
                    SETTINGS);
            return false;
        }
    }
    
}
