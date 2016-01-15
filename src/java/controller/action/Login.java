/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Sasha
 */
public class Login extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");    
        if (password.equals("admin123")) {
            HttpSession session = request.getSession();
            session.setAttribute("userName", name);
            response.sendRedirect(request.getParameter("from"));
        } else {
            try (PrintWriter out = response.getWriter()) {
                new LoginRequest().execute(request, response);
                out.print("Sorry, user name or password error! Try again."); // input this expression to the jsp file
            }
        }
    }
    
}
