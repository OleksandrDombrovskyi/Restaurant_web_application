/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions;

import controller.ConfigManager;
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
        return ConfigManager.getProperty("path.page.loginrequest");
    }

    @Override
    public List<ConcreteLink> getLink() {
        List<ConcreteLink> links = new ArrayList<>();
        links.addAll(new HomePage().getLink());
        String linkValue = ConfigManager.getProperty("link.loginrequest");
        String linkName = "login.button.login";
        ConcreteLink concreteLink = new ConcreteLink(linkValue, linkName);
        links.add(concreteLink);
        return links;
    }
    
}
