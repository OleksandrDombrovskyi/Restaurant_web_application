/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal;

import controller.ConfigManager;
import controller.action.getactions.personal.Profile;
import controller.action.ConcreteLink;
import controller.action.getactions.GetAction;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import model.entity.Admin;
import model.entity.Person;
import model.entity.User;

/**
 * Show profile settings
 * @author Sasha
 */
public class Settings extends GetAction {

    /**
     * Check if user or admin are in the current session and show corresponding
     * setting page, or back to home page if there is no person in the session
     * 
     * @return property key value
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected String doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            setParameters(user);
//            goToPage("settings.text.title", "/view/person/settings.jsp");
            return ConfigManager.getProperty("path.page.settings");
        }
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin != null) {
            setParameters(admin);
//            goToPage("settings.text.title", "/view/person/settings.jsp");
            return ConfigManager.getProperty("path.page.settings");
        }
//        sendRedirect(null, "login.errormessage.loginplease", "home");
        setMessages(null, "login.errormessage.loginplease");
        return ConfigManager.getProperty("path.page.home");
    }
    
    /**
     * Set all users' parameters into request
     * @param user user object with old information
     * @throws ServletException
     * @throws IOException 
     */
    private void setParameters(Person person) throws ServletException, 
            IOException {
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        String email = person.getEmail();
        request.setAttribute("firstName", firstName);
        request.setAttribute("lastName", lastName);
        request.setAttribute("email", email);
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
        links.addAll(new Profile().getLink());
        String linkValue = ConfigManager.getProperty("link.settings");
        String linkName = "settings.text.title";
        ConcreteLink concreteLink = new ConcreteLink(linkValue, linkName);
        links.add(concreteLink);
        return links;
    }
    
}
