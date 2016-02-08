/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;

/**
 * Login request
 * @author Sasha
 */
public class LoginRequest extends GetAction {
    
    /** title string key value */
    private final static String TITLE = "login.text.title";

    /**
     * Constructor
     */
    public LoginRequest() {
        super(TITLE);
    }
    
    /**
     * Show login page
     * @return property key value
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public String doExecute() throws ServletException, IOException {
        return configManager.getProperty("path.page.loginrequest");
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
        String linkValue = configManager.getProperty(LOGIN_REQUEST_LINK);
        String linkName = "login.button.login";
        ConcreteLink concreteLink = new ConcreteLink(linkValue, linkName);
        links.add(concreteLink);
        return links;
    }
    
}
