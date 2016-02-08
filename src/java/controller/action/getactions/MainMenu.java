/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions;

import controller.ConfigManager;
import controller.tagsupport.mainmenutag.MealTagIterator;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import model.dao.Dao;
import model.dao.DaoEnum;
import model.dao.EntityCreator;
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
            setMessages(null, NO_MEALS);
            return configManager.getProperty(HOME_PAGE);
        }
        request.setAttribute("meals", meals);
        MealTagIterator mealIterator = new MealTagIterator(meals);
        request.setAttribute("mealIterator", mealIterator);
        return configManager.getProperty("path.page.mainmenu");
    }

    /**
     * Get all meal from the data base
     * @return array list of all meals 
     * @throws ServletException
     * @throws IOException 
     */
    private List<Meal> getAllMeals() throws ServletException, IOException {
        Dao mealCreator = daoFactory.getCreator(DaoEnum.MEAL_CREATOR);
        try {
            return (List<Meal>) ((EntityCreator) mealCreator).getAllEntities();
        } catch (SQLException e) {
            logger.info(e.getMessage());
            sendRedirect(null, SQL_EXCEPTION, HOME_PAGE);
            return null;
        } catch (ServerOverloadedException e) {
            logger.info(e.getMessage());
            sendRedirect(null, SERVER_OVERLOADED_EXCEPTION, HOME_PAGE);
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
        String linkValue = configManager.getProperty(MAIN_MENU);
        String linkName = TITLE;
        ConcreteLink concreteLink = new ConcreteLink(linkValue, linkName);
        links.add(concreteLink);
        return links;
    }
    
}
