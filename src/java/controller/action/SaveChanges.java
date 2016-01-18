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
 * Saving all changes of users' private information (name, last name, email)
 * @author Sasha
 */
public class SaveChanges extends Action {

    /**
     * Save all changes of users' private information (name, last name, email)
     * @throws ServletException
     * @throws IOException 
     */    
    @Override
    protected void doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            new Redirection().goToLogin(request, response);
            return;
        }
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        int userId = user.getId();
        
        String errorMessage = checkAndUpdate(firstName, lastName, email, userId);
        if (errorMessage != null) {
            saveFieldValues(firstName, lastName, email);
            startOver(errorMessage);
            return;
        }
        createPage();
    }
    
    /**
     * Check all input fields. If it is successful, update it
     * @param firstName users' first name
     * @param lastName users' last name
     * @param email users' email
     * @return error message or null if updating was successful
     */
    private String checkAndUpdate(String firstName, String lastName, 
            String email, int userId) throws ServletException, IOException {
        if (firstName == null || firstName.equals("")) {
            return "settings.errormessage.emptyname";
        }
        if (lastName == null || lastName.equals("")) {
            return "settings.errormessage.emptylastname";
        }
        if (email == null || email.equals("")) {
            return "settings.errormessage.emptyemail";
        }
        Validator validator = new Validator();
        if (!validator.checkEmail(email)) {
            return "settings.errormessage.uncorrectemail";
        }
        User newUser = new User(firstName, lastName, email, "unusedPassword");
        newUser.setId(userId);
        return updateUser(newUser);
    }

    /**
     * Update users' private information (first name, last name and email). If
     * updating is successful, get the same user from data base by its' email 
     * with updated information and set it to the current session
     * 
     * @param newUser user object with updated private information
     * @return error message or null if updating was successful
     * @throws ServletException
     * @throws IOException 
     */
    private String updateUser(User newUser) throws ServletException, IOException {
        UserCreator userCreator = new UserCreator();
        try {
            if (!userCreator.updateUser(newUser)) {
                return "settings.errormessage.changesnotsaved";
            }
            return setUserToSession(newUser.getEmail());
        } catch (SQLException ex) {
            return "exception.errormessage.sqlexception";
        } catch (ServerOverloadedException ex) {
            return "exception.errormessage.serveroverloaded";
        }
    }
    
    /**
     * Create next page with all required information
     * @throws ServletException
     * @throws IOException 
     */
    private void createPage() throws ServletException, IOException {
        request.setAttribute("message", "settings.message.changeswassaved");
        new Settings().execute(request, response);
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
     * Back to filling the form couse of uncorrect field filling and sending 
     * correspond error message
     * 
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
