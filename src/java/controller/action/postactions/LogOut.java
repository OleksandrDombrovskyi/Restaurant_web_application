/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions;

import controller.action.postactions.PostAction;
import java.io.IOException;
import javax.servlet.ServletException;

/**
 * Log out
 * @author Sasha
 */
public class LogOut extends PostAction {

    /**
     * Log out user or admin (go to user role)
     * @return property key value
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public void doExecute() throws ServletException, IOException {
//        session.invalidate();
        session.removeAttribute("user");
        session.removeAttribute("admin");
        session.removeAttribute("kitchen");
        response.sendRedirect(request.getHeader("Referer"));
    }
    
}
