/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions;

import controller.action.Action;
import controller.action.LanguageBlock;
import controller.action.SetAuthorizationBlock;
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

    @Override
    public void doExecute() throws ServletException, IOException {
        request.setAttribute("title", "mainmenu.text.title");
        new LanguageBlock().execute(request, response);
        new SetAuthorizationBlock().execute(request, response);
        setNavigationBlock();
        List<Meal> meals = null;
        MealCreator mealCreator = new MealCreator();
        try {
            meals = (List<Meal>) mealCreator.getAllEntities();
            if (meals == null) {
                request.setAttribute("errorMessage", 
                    "mainmenu.errormessage.nomeals");
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", 
                    "exception.errormessage.sqlexception");
        } catch (ServerOverloadedException e) {
            request.setAttribute("errorMessage", 
                    "exception.errormessage.serveroverloaded");
        }
        request.setAttribute("meals", meals);
        request.getRequestDispatcher("view/mainmenu.jsp").include(request, 
                response);
    }
    
    /**
     * Get all links before settings and aettings link inclusive
     * @return list of links objects
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
    
}
