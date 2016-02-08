/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal.admin;

import controller.ConfigManager;
import controller.action.getactions.ConcreteLink;
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
    
    /** title string key value */
    private final static String TITLE = "administration.users.text.title";

    /**
     * Constructor
     */
    public GetUsers() {
        super(TITLE);
    }

    /**
     * Get all users from data base and show them
     * @return property key value
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected String doExecute() throws ServletException, IOException {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            setMessages(null, LOGIN_PLEASE);
            return configManager.getProperty(HOME_PAGE);
        }
        List<User> users = getAllUsers();
        if (users == null || users.size() < 1) {
            request.setAttribute("message", NO_USERS);
        } else {
            request.setAttribute("users", users);
        }
        return configManager.getProperty(GET_USERS);
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
        String linkValue = configManager.getProperty("link.getusers");
        String linkName = TITLE;
        ConcreteLink concreteLink = new ConcreteLink(linkValue, linkName);
        links.add(concreteLink);
        return links;
    }
    
}
