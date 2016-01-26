/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions;

import controller.ConfigManager;
import controller.action.ConcreteLink;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;

/**
 * Home page
 * @author Sasha
 */
public class HomePage extends GetAction {

    /**
     * Show home page
     * @return property key value
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public String doExecute() throws ServletException, IOException {
        return ConfigManager.getProperty("path.page.home");
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
        String linkValue = ConfigManager.getProperty("link.home");
        String linkName = "home.text.title";
        links.add(new ConcreteLink(linkValue, linkName));
        return links;
    }
    
}
