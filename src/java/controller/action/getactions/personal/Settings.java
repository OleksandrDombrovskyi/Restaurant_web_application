/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal;

import controller.action.getactions.ConcreteLink;
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
    
    /** key for basket page path */
    private final static String BASKET_PATH = "path.page.settings";
    
    /** title string key value */
    private final static String TITLE = "settings.text.title";
    
    /**
     * Constructor
     */
    public Settings() {
        super(TITLE);
    }

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
            return configManager.getProperty(BASKET_PATH);
        }
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin != null) {
            setParameters(admin);
            return configManager.getProperty(BASKET_PATH);
        }
        setMessages(null, LOGIN_PLEASE);
        return configManager.getProperty(HOME_PAGE);
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
        String linkValue = configManager.getProperty(SETTINGS);
        String linkName = TITLE;
        ConcreteLink concreteLink = new ConcreteLink(linkValue, linkName);
        links.add(concreteLink);
        return links;
    }
    
}
