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
 *
 * @author Sasha
 */
public class SaveChanges extends Action {

    @Override
    protected void doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            new Redirection().goToLogin(request, response);
            return;
        }
        String name = request.getParameter("name");
        String lastName = request.getParameter("lastname");
        String email = request.getParameter("email");
        
        String errorMessage = checkFields(name, lastName, email);
        if (errorMessage != null) {
            saveFieldValues(name, lastName, email);
            startOver(errorMessage);
            return;
        }
        UserCreator userCreator = new UserCreator();
        //TODO: user data updating
    }
    
    private String checkFields(String name, String lastName, String email) {
        if (name == null || name.equals("")) {
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
        return null;
    }
    
    /**
     * Save previous fields values 
     * @param name user name
     * @param lastName user last name
     * @param email user email
     * @param request http servlet request
     */
    private void saveFieldValues(String name, String lastName, String email) {
        request.setAttribute("name", name);
        request.setAttribute("lastName", lastName);
        request.setAttribute("email", email);
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
