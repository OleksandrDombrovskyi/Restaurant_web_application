/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions;

import controller.action.Action;
import java.io.IOException;
import javax.servlet.ServletException;

/**
 * Change language
 * @author Sasha
 */
public class ChangeLanguage extends Action {

    /**
     * Set languge block and set the language settings
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public void doExecute() throws ServletException, IOException {
        session.setAttribute("language", request.getParameter("language"));
        String path = request.getHeader("Referer");
        if (path != null) {
            response.sendRedirect(path);
            return;
        }
        response.sendRedirect(request.getContextPath() + "/");
    }
    
}
