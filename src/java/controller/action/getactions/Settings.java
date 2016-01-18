/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions;

import controller.action.LanguageBlock;
import controller.action.SetAuthorizationBlock;
import controller.action.ConcreteLink;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import model.entity.User;

/**
 * Show profile settings
 * @author Sasha
 */
public class Settings extends GetAction {

    /**
     * Show settings page
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            goToHome("login.errormessage.loginplease");
            return;
        }
        createPage(user);
    }
    
    /**
     * Create settings page
     * @param user user object with old information
     * @throws ServletException
     * @throws IOException 
     */
    private void createPage(User user) throws ServletException, IOException {
        request.setAttribute("title", "settings.text.title");
        new LanguageBlock().execute(request, response);
        new SetAuthorizationBlock().execute(request, response);
        setNavigationBlock();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        request.setAttribute("firstName", firstName);
        request.setAttribute("lastName", lastName);
        request.setAttribute("email", email);
        request.getRequestDispatcher("/view/user/settings.jsp").
                include(request, response);
    }
    
    /**
     * Get all links before settings and aettings link inclusive
     * @return list of links objects
     */
    @Override
    public List<ConcreteLink> getLink() {
        List<ConcreteLink> links = new ArrayList<>();
        links.addAll(new Profile().getLink());
        String linkValue = "/servlet?getAction=settings";
        String linkName = "settings.text.title";
        ConcreteLink concreteLink = new ConcreteLink(linkValue, linkName);
        links.add(concreteLink);
        return links;
    }
    
}
