/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sasha
 */
public class Redirection {
    
    /**
     * Back to login
     * 
     * @param request http servlet request
     * @param response http servlet response
     * @throws IOException 
     */
    public void goToLogin(HttpServletRequest request, 
            HttpServletResponse response) throws IOException {
        request.setAttribute("errorMessage", "login.errormessage.loginplease");
        response.sendRedirect(request.getContextPath() + "?action=loginRequest");
    }
}
