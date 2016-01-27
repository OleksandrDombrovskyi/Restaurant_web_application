/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions;

import controller.ConfigManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import model.dao.MealCreator;
import model.dao.ServerOverloadedException;
import model.entity.Meal;

/**
 * Main menu 
 * @author Sasha
 */
public class MainMenu extends GetAction {
    
    /** title string key value */
    private final static String TITLE = "mainmenu.text.title";

    /**
     * Constructor
     */
    public MainMenu() {
        super(TITLE);
    }
    
    /**
     * Show main menu page
     * @return property key value
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public String doExecute() throws ServletException, IOException {
        List<Meal> meals = getAllMeals();
        if (meals == null || meals.size() < 1) {
            setMessages(null, "mainmenu.errormessage.nomeals");
            return ConfigManager.getProperty("path.page.homepage");
        }
        request.setAttribute("meals", meals);
//        goToPage("mainmenu.text.title", "view/mainmenu.jsp");
        return ConfigManager.getProperty("path.page.mainmenu");
    }

    /**
     * Get all meal from the data base
     * @return array list of all meals 
     * @throws ServletException
     * @throws IOException 
     */
    private List<Meal> getAllMeals() throws ServletException, IOException {
        MealCreator mealCreator = new MealCreator();
        try {
            return (List<Meal>) mealCreator.getAllEntities();
        } catch (SQLException e) {
            sendRedirect(null, "exception.errormessage.sqlexception", "home");
            return null;
        } catch (ServerOverloadedException e) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "home");
            return null;
        }
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
        String linkValue = ConfigManager.getProperty("link.mainmenu");
        String linkName = "mainmenu.text.title";
        ConcreteLink concreteLink = new ConcreteLink(linkValue, linkName);
        links.add(concreteLink);
        return links;
    }
    
}
