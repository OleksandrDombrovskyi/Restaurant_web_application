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
import model.entity.Admin;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public class Profile extends GetAction {

    /**
     * Output user profile page
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            createPage();
            request.getRequestDispatcher("/view/user/profile.jsp").
                include(request, response);
            return;
        }
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin != null) {
            createPage();
            request.getRequestDispatcher("/view/admin/profile.jsp").
                include(request, response);
            return;
        }
        goToHome("login.errormessage.loginplease");
    }
    
    /**
     * Create next page
     * 
     * @param user user object
     * @throws ServletException
     * @throws IOException 
     */
    private void createPage() throws ServletException, IOException {
        request.setAttribute("title", "profile.text.title");
        new LanguageBlock().execute(request, response);
        new SetAuthorizationBlock().execute(request, response);
        setNavigationBlock();
    }

    /**
     * Get all links before profile and profile link inclusive
     * @return list of link objects
     */
    @Override
    protected List<ConcreteLink> getLink() {
        List<ConcreteLink> links = new ArrayList<>();
        links.addAll(new HomePage().getLink());
        String linkValue = "/servlet?getAction=profile";
        String linkName = "profile.text.title";
        ConcreteLink concreteLink = new ConcreteLink(linkValue, linkName);
        links.add(concreteLink);
        return links;
    }
    
}
