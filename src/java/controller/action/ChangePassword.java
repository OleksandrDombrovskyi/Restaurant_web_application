/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import java.io.IOException;
import javax.servlet.ServletException;
import model.dao.UserCreator;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public class ChangePassword extends Action {

    @Override
    protected void doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            new Redirection().goToLogin(request, response);
            return;
        }
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        String errorMessage = checkPassword(user, oldPassword, newPassword, 
                confirmPassword);
        if (errorMessage != null) {
            startOver(errorMessage);
            return;
        }
        UserCreator userCreator = new UserCreator();
        //TODO: user password updating
    }
    
    private String checkPassword(User user, String oldPassword, 
            String newPassword, String confirmPassword) 
            throws ServletException, IOException {
        if ((oldPassword == null || oldPassword.equals("")) 
                && (newPassword == null || newPassword.equals("")) 
                && (confirmPassword == null || confirmPassword.equals(""))) {
            return null;
        }
        if (!oldPassword.equals(user.getPassword())) {
            return "settings.errormessage.wrongpassword";
        }
        if (!newPassword.equals(confirmPassword)) {
            return "settings.errormessage.paswordnotmatched";
        }
        Validator validator = new Validator();
        if (!validator.checkPassword(newPassword)) {
            return "settings.errormessage.easypasword";
        }
        return null;
    }
    
    /**
     * Back to filling the form couse of uncorrect field filling and sending 
     * correspond error message
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param errorMessage text value of text property file which corresponds 
     * to the error message
     * @throws ServletException
     * @throws IOException 
     */
    private void startOver(String errorMessage) throws ServletException, 
            IOException {
        request.setAttribute("errorMessage", errorMessage);
        new Settings().execute(request, response);
    }
    
}
