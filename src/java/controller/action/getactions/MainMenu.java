/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions;

import controller.action.ConcreteLink;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import model.dao.MealCreator;
import model.dao.ServerOverloadedException;
import model.entity.Meal;

/**
 *
 * @author Sasha
 */
public class MainMenu extends GetAction {

    /**
     * Show main menu page
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public void doExecute() throws ServletException, IOException {
        List<Meal> meals = getAllMeals();
        if (meals == null || meals.size() < 1) {
            sendRedirect(null, "mainmenu.errormessage.nomeals", "home");
        }
        request.setAttribute("meals", meals);
        goToPage("mainmenu.text.title", "view/mainmenu.jsp");
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
        String linkValue = "/servlet?getAction=mainMenu";
        String linkName = "mainmenu.text.title";
        ConcreteLink concreteLink = new ConcreteLink(linkValue, linkName);
        links.add(concreteLink);
        return links;
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
    
}
