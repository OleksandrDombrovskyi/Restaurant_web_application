/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.dao.ServerOverloadedException;
import model.dao.UserBuilder;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public class Login extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password"); 
        UserBuilder builder = new UserBuilder();
        User user = null;
        try {
            user = (User) builder.getUserByEmail(email);
            if (user == null) {
                startOver(request, response, "login.errormessage.nosuchuser");
                return;
            }
            if (!user.getPassword().equals(password)) {
                startOver(request, response, "login.errormessage.invalidpassword");
                return;
            }
        } catch (ServerOverloadedException e) {
            startOver(request, response, "exception.errormessage.serveroverloaded");
            return;
        } catch (SQLException e) {
            startOver(request, response, "exception.errormessage.sqlexception");
            return;
        }
        HttpSession session = request.getSession();
        session.setAttribute("userName", user.getFirstName());
        session.setAttribute("email", email);
        response.sendRedirect(request.getParameter("from"));
    }
    
    /**
     * Back to filling the form couse of uncorrect field filling and sending 
     * correspond error message
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param errorMessage text value of text property file which corresponds to the error message
     * @throws ServletException
     * @throws IOException 
     */
    private void startOver(HttpServletRequest request, HttpServletResponse response, 
            String errorMessage) throws ServletException, IOException {
        request.setAttribute("errorMessage", errorMessage);
        new LoginRequest().execute(request, response);
    }
    
}
