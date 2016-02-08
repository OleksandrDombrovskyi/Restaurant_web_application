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
 * Home page
 * @author Sasha
 */
public class HomePage extends GetAction {
    
    /** title string key value */
    private final static String TITLE = "home.text.title";
    
    /**
     * Constructor
     */
    public HomePage() {
        super(TITLE);
    }

    /**
     * Show home page
     * @return property key value
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public String doExecute() throws ServletException, IOException {
        return configManager.getProperty(HOME_PAGE);
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
        List<ConcreteLink> links = new ArrayList();
        String linkValue = configManager.getProperty(HOME_PAGE_LINK);
        String linkName = TITLE;
        links.add(new ConcreteLink(linkValue, linkName));
        return links;
    }
    
}
