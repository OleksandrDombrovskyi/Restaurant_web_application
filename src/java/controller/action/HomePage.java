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
public class HomePage extends Action {

    @Override
    public void doExecute() throws ServletException, IOException {
        request.setAttribute("title", "home.text.title");
        new LanguageBlock().execute(request, response);
        new SetAuthorizationBlock().execute(request, response);
        request.getRequestDispatcher("/view/home.jsp").include(request, response);
//        try (PrintWriter out = response.getWriter()) {
//            out.println("request.getParameter(language): " + request.getParameter("language")); // input this expression to the jsp file
//            out.println("request.getSession().getAttribute(\"action\"): " + request.getSession().getAttribute("action"));
//        }
    }
    
}
