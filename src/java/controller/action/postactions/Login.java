/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions;

import controller.action.Action;
import controller.action.getactions.LoginRequest;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import model.dao.ServerOverloadedException;
import model.dao.UserCreator;
import model.entity.User;

/**
 * Set log in
 * @author Sasha
 */
public class Login extends Action {

    /**
     * Log in
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public void doExecute() 
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password"); 
        UserCreator builder = new UserCreator();
        User user = null;
        try {
            user = (User) builder.getUserByEmail(email);
            if (user == null) {
                startOver("login.errormessage.nosuchuser");
                return;
            }
            if (!user.getPassword().equals(password)) {
                startOver("login.errormessage.invalidpassword");
                return;
            }
        } catch (ServerOverloadedException e) {
            startOver("exception.errormessage.serveroverloaded");
            return;
        } catch (SQLException e) {
            startOver("exception.errormessage.sqlexception");
            return;
        }
        session.setAttribute("user", user);
        makeRedirect();
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
    private void startOver(String errorMessage) throws ServletException, 
            IOException {
        request.setAttribute("errorMessage", errorMessage);
        new LoginRequest().execute(request, response);
    }
    
    /**
     * Make redirect to the previous page
     * @throws ServletException
     * @throws IOException 
     */
    private void makeRedirect() throws ServletException, IOException {
        //String from = (String) session.getAttribute("from");
        String lastAction = (String) session.getAttribute("lastAction");
//        String uri;
//        if (from != null && lastAction != null) {
//            uri = from + "?getAction=" + lastAction;
//        } else {
//            uri = from;
//        }
        if (lastAction == null) {
            response.sendRedirect(request.getContextPath());
        }
        response.sendRedirect(request.getContextPath() + "/servlet?getAction=" + lastAction);
    }
    
}
