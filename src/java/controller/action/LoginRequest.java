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
public class LoginRequest extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title", "login.text.title");
        String from = request.getParameter("from");
        request.getSession().setAttribute("from", from);
        new LanguageBlock().execute(request, response);
        request.getRequestDispatcher("/view/login.jsp").include(request, response);
//        try (PrintWriter out = response.getWriter()) {
//            out.print("request.getParameter(from): " + request.getParameter("from")); // input this expression to the jsp file
//        }
    }
    
}
