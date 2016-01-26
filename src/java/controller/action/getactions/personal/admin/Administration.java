/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal.admin;

import controller.ConfigManager;
import controller.action.ConcreteLink;
import controller.action.getactions.GetAction;
import controller.action.getactions.HomePage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import model.entity.Admin;

/**
 * Administration
 * @author Sasha
 */
public class Administration extends GetAction {

    /**
     * Admin administration
     * @return property key value
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected String doExecute() throws ServletException, IOException {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
//            sendRedirect(null, "login.errormessage.loginplease", "home");
            setMessages(null, "login.errormessage.loginplease");
            return ConfigManager.getProperty("path.page.home");
        }
//        goToPage("administration.text.title", "/view/person/admin/administration.jsp");
        return ConfigManager.getProperty("path.page.admin.administration");
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
        links.addAll(new HomePage().getLink());
        String linkValue = ConfigManager.getProperty("link.administration");
        String linkName = "administration.text.title";
        ConcreteLink concreteLink = new ConcreteLink(linkValue, linkName);
        links.add(concreteLink);
        return links;
    }
    
}
