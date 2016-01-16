/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.dao.DAODirector;
import model.dao.ENUMEntity;
import model.dao.ServerOverloadedException;
import model.dao.UserBuilder;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public class CreateAccount extends Action {

    /**
     * Create new account if all required fildes are filled correctly
     * @param request http servlet request
     * @param response http serrvlet response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String lastName = request.getParameter("lastname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        String errorMessage = checkAllFields(name, lastName, email, password, confirmPassword);
        if (errorMessage != null) {
            saveFieldValues(name, lastName, email, request);
            startOver(request, response, errorMessage);
            return;
        }
        UserBuilder builder = new UserBuilder();
        try {
            if (builder.getUserByEmail(email) != null) {
                saveFieldValues(name, lastName, email, request);
                startOver(request, response, "signup.errormessage.existinguser");
                return;
            }
            User newUser = new User(name, lastName, email, password);
            DAODirector director = new DAODirector(ENUMEntity.USER);
            director.insertEntity(newUser);
        } catch (SQLException ex) {
            saveFieldValues(name, lastName, email, request);
            startOver(request, response, "exception.errormessage.sqlexception");
            return;
        } catch (ServerOverloadedException ex) {
            saveFieldValues(name, lastName, email, request);
            startOver(request, response, "exception.errormessage.serveroverloaded");
            return;
        }
        HttpSession session = request.getSession();
        session.setAttribute("userName", name);
        session.setAttribute("userEmail", email);
        response.sendRedirect(request.getParameter("from"));
    }
    
    /**
     * Check all fields. If some field is uncorrect, return corresponding error message, 
     * in case when all fields are correct, return null
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
        if (!checkEmail(email)) {
            return "signup.errormessage.uncorrectemail";
        }
        if (!confirmPasswords(password, confirmPassword)) {
            return "signup.errormessage.password";
        }
        if (!checkPassword(password)) {
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
    
    /**
     * Check whether is email correct by regular expression
     * 
     * @param email string email
     * @return boolean true if email is correct and false otherwise
     */
    private boolean checkEmail(String email) {
        Pattern regexEmail = 
                Pattern.compile("^[a-zA-Z]{1}[a-zA-Z\\d\\u002E\\u005F]*@([a-zA-Z]+\\u002E){1,2}((net)|(com)|(org)|(ua))");
        Matcher emailMatcher = regexEmail.matcher(email);
        return emailMatcher.find();
    }
    
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
    
    /**
     * Check if password if quite difficult
     * 
     * @param password string password
     * @return true if password has uppercase and lowercase letters and digits 
     *         with length not less then 6 symbols or false otherwise
     */
    private boolean checkPassword(String password) {
        Pattern regexPassword = 
                Pattern.compile("[[A-Z]+[a-z]+[0-9]+]{6,}");
        Matcher emailMatcher = regexPassword.matcher(password);
        return emailMatcher.find();
    }
    
    /**
     * Save previous fields values 
     * @param name user name
     * @param lastName user last name
     * @param email user email
     * @param request http servlet request
     */
    private void saveFieldValues(String name, String lastName, String email, HttpServletRequest request) {
        request.setAttribute("previousName", name);
        request.setAttribute("previousLastName", lastName);
        request.setAttribute("previousEmail", email);
    }
    
    /**
     * Back to filling the form couse of uncorrect field filling and sending 
     * correspond error message
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param errorMessage text value of text property file which corresponds to the error message
     * @throws ServletException
     * @throws IOException 
     */
    private void startOver(HttpServletRequest request, HttpServletResponse response, 
            String errorMessage) throws ServletException, IOException {
        request.setAttribute("errorMessage", errorMessage);
        new SignUp().execute(request, response);
    }
    
}
