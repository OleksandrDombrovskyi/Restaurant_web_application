/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import java.io.IOException;
import javax.servlet.ServletException;

/**
 *
 * @author Sasha
 */
public class LoginRequest extends Action {

    @Override
    public void doExecute() throws ServletException, IOException {
        saveURIForRedirect();
        request.setAttribute("title", "login.text.title");
        new LanguageBlock().execute(request, response);
        request.getRequestDispatcher("/view/login.jsp").include(request, response);
//        try (PrintWriter out = response.getWriter()) {
//            out.println("from: " + from);
//            out.println("request.getSession().getAttribute(\"from\"): " + request.getSession().getAttribute("from")); // input this expression to the jsp file
//        }
    }
    
    private void saveURIForRedirect() {
        String from = request.getParameter("from");
        request.getSession().setAttribute("from", from);
    }
    
}
