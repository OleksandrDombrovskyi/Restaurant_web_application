/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import model.dao.ServerOverloadedException;
import model.dao.UserCreator;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public class Profile extends Action {

    @Override
    protected void doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            goToLogin();
            return;
        }
        createPage(user);
    }
    
    private void goToLogin() throws IOException {
        request.setAttribute("errorMessage", "login.errormessage.loginplease");
        response.sendRedirect(request.getContextPath() + "?action=loginRequest");
    }
    
    private void createPage(User user) throws ServletException, IOException {
        request.setAttribute("title", "profile.text.title");
        new LanguageBlock().execute(request, response);
        new SetAuthorizationBlock().execute(request, response);
        request.getRequestDispatcher("/view/user/profile.jsp").
                include(request, response);
    }
    
}
