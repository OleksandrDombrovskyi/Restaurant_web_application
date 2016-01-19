/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions;

import controller.action.Action;
import java.io.IOException;
import javax.servlet.ServletException;

/**
 *
 * @author Sasha
 */
public abstract class PostAction extends Action {
    
    /**
     * Send redirect to some get action
     * @param message message if it is required in some case
     * @param errorMessage error message if it is required in some case
     * @param action specific servlet get action
     * @throws ServletException 
     * @throws IOException 
     */
    protected void sendRedirect(String message, String errorMessage, String action) 
            throws ServletException, IOException {
        if (message != null && !message.equals("")) {
            session.setAttribute("message", message);
        }
        if (errorMessage != null && !errorMessage.equals("")) {
            session.setAttribute("errorMessage", errorMessage);
        }
        if (action != null && !action.equals("")) {
            response.sendRedirect(request.getContextPath() + "/servlet?getAction=" + action);
            return;
        }
        response.sendRedirect(request.getContextPath() + "/servlet?getAction=home");
    }
    
}
