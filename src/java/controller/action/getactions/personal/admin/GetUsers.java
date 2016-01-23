/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal.admin;

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
public class GetUsers extends AdminGetAction {

    /**
     * Get all users from data base and show them
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doExecute() throws ServletException, IOException {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            sendRedirect(null, "login.errormessage.loginplease", "home");
            return;
        }
        List<User> users = getAllUsers();
        if (users == null || users.size() < 1) {
            request.setAttribute("message", "administration.users.message.nousers");
        } else {
            request.setAttribute("users", users);
        }
        goToPage("administration.user.text.title", "/view/person/admin/users.jsp");
    }
    
    /**
     * Get array list of link chain direct to current page (in fact this method 
     * gets link chain of its' previous page, add its' own link and return 
     * created array list)
     * 
     * @return array list of links
     */
    @Override
    public List<ConcreteLink> getLink() {
        List<ConcreteLink> links = new ArrayList<>();
        links.addAll(new Administration().getLink());
        String linkValue = "/servlet?getAction=getUsers";
        String linkName = "administration.users.text.title";
        ConcreteLink concreteLink = new ConcreteLink(linkValue, linkName);
        links.add(concreteLink);
        return links;
    }
    
}
