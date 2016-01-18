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
 * Account creator
 * @author Sasha
 */
public class CreateAccount extends Action {

    /**
     * Create new account if all required fildes are filled correctly
     * 
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public void doExecute() throws ServletException, IOException {
        String name = request.getParameter("name");
        String lastName = request.getParameter("lastname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        UserCreator userCreator = new UserCreator();
        User newUser = null;
        User dbUser = null;
        
        String errorMessage = checkAllFields(name, lastName, email, password, 
                confirmPassword);
        if (errorMessage != null) {
            saveFieldValues(name, lastName, email);
            startOver(errorMessage);
            return;
        }
        try {
            if (userCreator.getUserByEmail(email) != null) {
                saveFieldValues(name, lastName, email);
                startOver("signup.errormessage.existinguser");
                return;
            }
            newUser = new User(name, lastName, email, password);
            if (!userCreator.insertUser(newUser)) {
                throw new SQLException();
            }
            dbUser = (User) userCreator.getUserByEmail(email);
            if (dbUser == null) {
                throw new SQLException();
            }
        } catch (SQLException ex) {
            saveFieldValues(name, lastName, email);
            startOver("exception.errormessage.sqlexception");
            return;
        } catch (ServerOverloadedException ex) {
            saveFieldValues(name, lastName, email);
            startOver("exception.errormessage.serveroverloaded");
            return;
        }
        session.setAttribute("user", dbUser);
        response.sendRedirect(request.getContextPath() + "/servlet?action=profile");
        //response.sendRedirect(request.getParameter("from")); 
        //TODO: redirect to the profile link response.sendRedirect(profileLink);
    }
    
    /**
     * Check all fields. If some field is uncorrect, return corresponding error 
     * message, in case when all fields are correct, return null
     * 
     * @param name user name
     * @param lastName user last name
     * @param email user e-mail
     * @param password user password
     * @param confirmPassword confirmation password
     * @return if some field is uncorrect, return corresponding error message, 
     *         in case when all fields are correct, return null
     */
    private String checkAllFields(String name, String lastName, String email, 
            String password, String confirmPassword) {
        if (isStringEmpty(name)) {
            return "signup.errormessage.emptyname";
        }
        if (isStringEmpty(lastName)) {
            return "signup.errormessage.emptylastname";
        }
        if (isStringEmpty(email)) {
            return "signup.errormessage.emptyemail";
        }
        Validator validator = new Validator();
        if (!validator.checkEmail(email)) {
            return "signup.errormessage.uncorrectemail";
        }
        if (!confirmPasswords(password, confirmPassword)) {
            return "signup.errormessage.password";
        }
        if (!validator.checkPassword(password)) {
            return "signup.errormessage.uncorrectpassword";
        }
        return null;
    }
    
    /**
     * Check is string empty or no
     * 
     * @param string string variable
     * @return boolean true if string is empty and false otherwise
     */
    private boolean isStringEmpty(String string) {
        return string == null || string.equals("");
    }
    
//    /**
//     * Check whether is email correct by regular expression
//     * 
//     * @param email string email
//     * @return boolean true if email is correct and false otherwise
//     */
//    private boolean checkEmail(String email) {
//        Pattern regexEmail = 
//                Pattern.compile("^[a-zA-Z]{1}[a-zA-Z\\d\\u002E\\u005F]*@"
//                        + "([a-zA-Z]+\\u002E){1,2}((net)|(com)|(org)|(ua))");
//        Matcher emailMatcher = regexEmail.matcher(email);
//        return emailMatcher.find();
//    }
    
    /**
     * Check whether password and confirm password are the same and not null
     * 
     * @param password password string
     * @param confirmPassword password confirmation string
     * @return boolean true if both password and confirmation password are 
     *         the same and not null and boolean false otherwise
     */
    private boolean confirmPasswords(String password, String confirmPassword) {
        if (password == null || password.equals("") 
                || confirmPassword == null || confirmPassword.equals("") 
                || !password.equals(confirmPassword)) {
            return false;
        }
        return true;
    }
    
//    /**
//     * Check if password if quite difficult
//     * 
//     * @param password string password
//     * @return true if password has uppercase and lowercase letters and digits 
//     *         with length not less then 6 symbols or false otherwise
//     */
//    private boolean checkPassword(String password) {
//        Pattern regexPassword = 
//                Pattern.compile("[[A-Z]+[a-z]+[0-9]+]{6,}");
//        Matcher emailMatcher = regexPassword.matcher(password);
//        return emailMatcher.find();
//    }
    
    /**
     * Save previous fields values 
     * @param name user name
     * @param lastName user last name
     * @param email user email
     * @param request http servlet request
     */
    private void saveFieldValues(String name, String lastName, String email) {
        request.setAttribute("previousName", name);
        request.setAttribute("previousLastName", lastName);
        request.setAttribute("previousEmail", email);
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
        new SignUp().execute(request, response);
    }
    
}
