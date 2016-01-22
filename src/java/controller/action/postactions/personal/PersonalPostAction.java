/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions.personal;

import controller.action.postactions.PostAction;
import java.io.IOException;
import javax.servlet.ServletException;
import model.entity.Admin;
import model.entity.Person;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public abstract class PersonalPostAction extends PostAction {
    
    /**
     * Get person from the session
     * 
     * @return person object if it is in the session and null otherwise (in this 
     * case redirection will be performed by this method)
     * @throws ServletException
     * @throws IOException 
     */
    protected Person getPersonFromSession() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return user; 
        }
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin != null) {
            return admin;
        }
        sendRedirect(null, "login.errormessage.loginplease", "home");
        return null;
    }
    
}
