/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions;

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
public class Login extends PostAction {

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
                sendRedirect(null, "login.errormessage.nosuchuser", "loginRequest");
                return;
            }
            if (!user.getPassword().equals(password)) {
                sendRedirect(null, "login.errormessage.invalidpassword", "loginRequest");
                return;
            }
        } catch (ServerOverloadedException e) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "loginRequest");
            return;
        } catch (SQLException e) {
            sendRedirect(null, "exception.errormessage.sqlexception", "loginRequest");
            return;
        }
        session.setAttribute("user", user);
        String lastAction = (String) session.getAttribute("lastAction");
        if (lastAction != null) {
            sendRedirect(null, null, lastAction);
//            response.sendRedirect(request.getContextPath() + "/servlet?getAction=" + lastAction);
            return;
        }
        sendRedirect(null, null, "home");
//        response.sendRedirect(request.getContextPath() + "/servlet?getAction=home");
//        makeRedirect();
    }
    
//    /**
//     * Back to filling the form couse of uncorrect field filling and sending 
//     * correspond error message
//     * 
//     * @param request HttpServletRequest
//     * @param response HttpServletResponse
//     * @param errorMessage text value of text property file which corresponds to the error message
//     * @throws ServletException
//     * @throws IOException 
//     */
//    private void startOver(String errorMessage) throws ServletException, 
//            IOException {
//        session.setAttribute("errorMessage", errorMessage);
////        session.setAttribute("lastPath", request.getContextPath() + "/servlet?getAction=loginRequest");
////        new LoginRequest().execute(request, response);
//        response.sendRedirect(request.getContextPath() + 
//                "/servlet?getAction=loginRequest");
//        
//    }
    
//    /**
//     * Make redirect to the previous page
//     * @throws ServletException
//     * @throws IOException 
//     */
//    private void makeRedirect() throws ServletException, IOException {
//        String lastAction = (String) session.getAttribute("lastAction");
//        if (lastAction != null) {
//            response.sendRedirect(request.getContextPath() + "/servlet?getAction=" + lastAction);
//            return;
//        }
//        response.sendRedirect(request.getContextPath() + "/servlet?getAction=home");
//    }
    
}
