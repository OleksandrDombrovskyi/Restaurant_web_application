/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import java.io.IOException;
import javax.servlet.ServletException;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public class Settings extends Action {

    @Override
    protected void doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            new Redirection().goToLogin(request, response);
            return;
        }
        createPage(user);
    }
    
    private void createPage(User user) throws ServletException, IOException {
        request.setAttribute("title", "settings.text.title");
        new LanguageBlock().execute(request, response);
        new SetAuthorizationBlock().execute(request, response);
        String name = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        request.setAttribute("firstName", name);
        request.setAttribute("lastName", lastName);
        request.setAttribute("email", email);
        request.getRequestDispatcher("/view/user/settings.jsp").
                include(request, response);
    }
    
}
