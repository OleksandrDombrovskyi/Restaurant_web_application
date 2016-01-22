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
import model.entity.Person;
import model.entity.User;

/**
 * Saving all changes of users' private information (name, last name, email)
 * @author Sasha
 */
public class SaveChanges extends PostAction {

    /**
     * Save all changes of users' private information (name, last name, email)
     * @throws ServletException
     * @throws IOException 
     */    
    @Override
    protected void doExecute() throws ServletException, IOException {
        Person person = getPersonFromSession();
        if (person == null) {
            return;
        }
        
//        Admin admin = (Admin) session.getAttribute("admin");
//        if (admin == null) {
//            sendRedirect(null, "login.errormessage.loginplease", "home");
//            return;
//        }
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        saveFieldValues(firstName, lastName, email);
        
        if (!checkFields(firstName, lastName, email)) {
            return;
        }
        int personId = person.getId();
        if (!updatePerson(personId, firstName, lastName, email)) {
            return;
        }
        sendRedirect("settings.message.changeswassaved", null, "settings");
    }
    
    /**
     * Check all input fields
     * @param firstName users' first name
     * @param lastName users' last name
     * @param email users' email
     * @return boolean true if all fields are correct and false otherwise (in 
     * this case redirection will be performed in this method)
     */
    private boolean checkFields(String firstName, String lastName, 
            String email) throws ServletException, IOException {
        if (firstName == null || firstName.equals("")) {
            sendRedirect(null, "settings.errormessage.emptyname", "settings");
            return false;
        }
        if (lastName == null || lastName.equals("")) {
            sendRedirect(null, "settings.errormessage.emptylastname", "settings");
            return false;
        }
        if (email == null || email.equals("")) {
            sendRedirect(null, "settings.errormessage.emptyemail", "settings");
            return false;
        }
        Validator validator = new Validator();
        if (!validator.checkEmail(email)) {
            sendRedirect(null, "settings.errormessage.uncorrectemail", "settings");
            return false;
        }
        return true;
    }

    /**
     * Update users' private information (first name, last name and email). If
     * updating is successful, get the same user from data base by its' email 
     * with updated information and set it to the current session
     * 
     * @param newUser user object with updated private information
     * @return true if user update was seccessful and false otherwise (in this 
     * case redirection will be performed in this method)
     * @throws ServletException
     * @throws IOException 
     */
    private boolean updatePerson(int userId, String firstName, String lastName, 
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
    
    /**
     * Save previous fields values 
     * @param name user name
     * @param lastName user last name
     * @param email user email
     * @param request http servlet request
     */
    private void saveFieldValues(String firstName, String lastName, String email) {
        request.setAttribute("firstName", firstName);
        request.setAttribute("lastName", lastName);
        request.setAttribute("email", email);
    }

    /**
     * Get user from the session
     * 
     * @return user object if it is in the session and null otherwise (in this 
     * case redirection will be performed by this method)
     * @throws ServletException
     * @throws IOException 
     */
    private Person getPersonFromSession() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            sendRedirect(null, "login.errormessage.loginplease", "home");
            return null;
        }
        return user;
    }
    
}
