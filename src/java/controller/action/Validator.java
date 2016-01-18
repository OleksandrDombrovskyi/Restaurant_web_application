/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Sasha
 */
public class Validator {
    
    /**
     * Check whether is email correct by regular expression
     * 
     * @param email string email
     * @return boolean true if email is correct and false otherwise
     */
    public boolean checkEmail(String email) {
        Pattern regexEmail = 
                Pattern.compile("^[a-zA-Z]{1}[a-zA-Z\\d\\u002E\\u005F]*@"
                        + "([a-zA-Z]+\\u002E){1,2}((net)|(com)|(org)|(ua))");
        Matcher emailMatcher = regexEmail.matcher(email);
        return emailMatcher.find();
    }
    
    /**
     * Check if password if quite difficult
     * 
     * @param password string password
     * @return true if password has uppercase and lowercase letters and digits 
     *         with length not less then 6 symbols or false otherwise
     */
    public boolean checkPassword(String password) {
        Pattern regexPassword = 
                Pattern.compile("[[A-Z]+[a-z]+[0-9]+]{6,}");
        Matcher emailMatcher = regexPassword.matcher(password);
        return emailMatcher.find();
    }
    
}
