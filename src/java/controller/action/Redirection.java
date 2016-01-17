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
    
    /**
     * Show error message only
     * 
     * @param request http servlet request
     * @param response http servlet response
     * @param message error message
     * @throws IOException
     * @throws ServletException 
     */
    public void showMessage(HttpServletRequest request, 
            HttpServletResponse response, String message) throws IOException, 
            ServletException {
        request.setAttribute("title", message);
        new LanguageBlock().execute(request, response);
        new SetAuthorizationBlock().execute(request, response);
        request.setAttribute("errorMessage", message);
        request.getRequestDispatcher("/view/error.jsp").
                include(request, response);
    }
}
