/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Sasha
 */
public class CreateAccount extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String lastName = request.getParameter("lastname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmpassword");
        
        String errorMessage = chechAllFields(name, lastName, email, password, confirmPassword);
        if (errorMessage != null) {
            startOver(request, response, errorMessage);
            return;
        }
        
        HttpSession session = request.getSession();
        session.setAttribute("userName", name);
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
    private String chechAllFields(String name, String lastName, String email, 
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
            return "signup.errormessage.password";
        }
        if (!confirmPasswords(password, confirmPassword)) {
            return "signup.errormessage.password";
        }
        if (!checkPassword(password)) {
            return "signup.errormessage.password";
        }
        return null;
    }
    
    /**
     * Check is string empty or no
     * @param string string variable
     * @return boolean true if string is empty and false otherwise
     */
    private boolean isStringEmpty(String string) {
        return string == null || string.equals("");
    }
    
    /**
     * Check whether is email correct by regular expression
     * @param email string email
     * @return boolean true if email is correct and false otherwise
     */
    private boolean checkEmail(String email) {
        //TODO: regular expression for email
        return true;
    }
    
    /**
     * Check whether password and confirm password are the same and not null
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
    
    private boolean checkPassword(String password) {
        //TODO: regular expression for password
        return true;
    }
    
    /**
     * Back to filling the form couse of uncorrect and sending correspond error message
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param errorMessage text value of text property file which corresponds to the error message
     * @throws ServletException
     * @throws IOException 
     */
    private void startOver(HttpServletRequest request, HttpServletResponse response, 
            String errorMessage) throws ServletException, IOException {
        request.setAttribute("errormessage", errorMessage);
        new SignUp().execute(request, response);
    }
    
}
