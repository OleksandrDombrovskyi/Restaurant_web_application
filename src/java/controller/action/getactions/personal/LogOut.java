/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal;

import controller.action.Action;
import java.io.IOException;
import javax.servlet.ServletException;

/**
 *
 * @author Sasha
 */
public class LogOut extends Action {

    /**
     * Log out user or admin (go to user role)
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public void doExecute() throws ServletException, IOException {
        session.removeAttribute("user");
        session.removeAttribute("admin");
        session.removeAttribute("kitchen");
        response.sendRedirect(request.getHeader("Referer"));
    }
    
}
