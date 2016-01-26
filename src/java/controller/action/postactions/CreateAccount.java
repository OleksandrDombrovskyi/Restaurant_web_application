/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions;

import controller.ConfigManager;
import controller.action.Validator;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import model.dao.ServerOverloadedException;
import model.dao.UserCreator;
import model.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Account creator
 * @author Sasha
 */
public class CreateAccount extends PostAction {

    /**
     * Create new account if all required fildes are filled correctly
     * 
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public String doExecute() throws ServletException, IOException {
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
//            sendRedirect(null, errorMessage, "signUp");
            setMessages(null, errorMessage);
            return ConfigManager.getProperty("path.page.signup");
        }
        try {
            if (userCreator.getUserByEmail(email) != null) {
                saveFieldValues(name, lastName, email);
//                sendRedirect(null, "signup.errormessage.existinguser", "signUp");
                setMessages(null, "signup.errormessage.existinguser");
                return ConfigManager.getProperty("path.page.signup");
            }
            String hexPassword = DigestUtils.shaHex(password);
            newUser = new User(name, lastName, email, hexPassword);
            if (!userCreator.insertUser(newUser)) {
                throw new SQLException();
            }
            dbUser = (User) userCreator.getUserByEmail(email);
            if (dbUser == null) {
                throw new SQLException();
            }
        } catch (SQLException ex) {
            saveFieldValues(name, lastName, email);
//            sendRedirect(null, "exception.errormessage.sqlexception", "signUp");
            setMessages(null, "exception.errormessage.sqlexception");
            return ConfigManager.getProperty("path.page.signup");
        } catch (ServerOverloadedException ex) {
            saveFieldValues(name, lastName, email);
//            sendRedirect(null, "exception.errormessage.serveroverloaded", "signUp");
            setMessages(null, "exception.errormessage.serveroverloaded");
            return ConfigManager.getProperty("path.page.signup");
        }
        session.setAttribute("user", dbUser);
        sendRedirect(null, null, "profile");
        return null;
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
     * Save previous fields values 
     * @param name user name
     * @param lastName user last name
     * @param email user email
     * @param request http servlet request
     */
    private void saveFieldValues(String name, String lastName, String email) {
        session.setAttribute("previousName", name);
        session.setAttribute("previousLastName", lastName);
        session.setAttribute("previousEmail", email);
    }
    
}
