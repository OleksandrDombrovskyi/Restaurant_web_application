/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal.user;

import controller.ConfigManager;
import controller.action.getactions.personal.Profile;
import controller.action.getactions.ConcreteLink;
import controller.action.getactions.GetAction;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import model.dao.OrderCreator;
import model.dao.ServerOverloadedException;
import model.entity.Order;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public class Basket extends GetAction {
    
    /** title string key value */
    private final static String TITLE = "basket.text.title";

    /**
     * Constructor
     */
    public Basket() {
        super(TITLE);
    }

    /**
     * Show page with basket of current user
     * @return property key value
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected String doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            setMessages(null, "login.errormessage.loginplease");
            return ConfigManager.getProperty("path.home");
        }
        int userId = user.getId();
        model.entity.Order basketOrder = getBasketOrder(userId);
        if (basketOrder == null || basketOrder.getOrderItems().size() < 1) {
            request.setAttribute("message", "basket.message.emptybasket");
        }
        request.setAttribute("basketOrder", basketOrder);
        return ConfigManager.getProperty("path.page.user.basket");
    }
    
    /**
     * Get basket ordrer from the data base by user id
     * 
     * @param userId user id
     * @return basket order object if it exists in the data base for current user 
     * @throws ServletException
     * @throws IOException 
     */
    private Order getBasketOrder(int userId) throws ServletException, 
            IOException {
        model.entity.Order basketOrder = null;
        OrderCreator orderCreator = new OrderCreator();
        try {
            basketOrder =  orderCreator.getNotConfirmedOrder(userId);
            if (basketOrder == null) {
                request.setAttribute("message", "basket.message.emptybasket");
                return null;
            } else {
                return basketOrder;
            }
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
            sendRedirect(null, "exception.errormessage.sqlexception", "profile");
            return null;
        } catch (ServerOverloadedException e) {
            LOGGER.info(e.getMessage());
            sendRedirect(null, "exception.errormessage.serveroverloaded", "profile");
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
        links.addAll(new Profile().getLink());
        String linkValue = ConfigManager.getProperty("link.basket");
        String linkName = "basket.text.title";
        ConcreteLink concreteLink = new ConcreteLink(linkValue, linkName);
        links.add(concreteLink);
        return links;
    }
    
}
