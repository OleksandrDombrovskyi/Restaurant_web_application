/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal.user;

import controller.action.getactions.personal.Profile;
import controller.action.getactions.ConcreteLink;
import controller.action.getactions.GetAction;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import model.dao.Dao;
import model.dao.DaoEnum;
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
    
    /** key for empty basket message */
    private final static String EMPTY_BUSKET = "basket.message.emptybasket";

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
            setMessages(null, LOGIN_PLEASE);
            return configManager.getProperty(HOME_PAGE);
        }
        int userId = user.getId();
        model.entity.Order basketOrder = getBasketOrder(userId);
        if (basketOrder == null || basketOrder.getOrderItems().size() < 1) {
            request.setAttribute("message", EMPTY_BUSKET);
        }
        request.setAttribute("basketOrder", basketOrder);
        return configManager.getProperty("path.page.user.basket");
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
        Dao orderCreator = daoFactory.getCreator(DaoEnum.ORDER_CREATOR);
        try {
            basketOrder =  ((OrderCreator) orderCreator).getNotConfirmedOrder(userId);
            if (basketOrder == null) {
                request.setAttribute("message", EMPTY_BUSKET);
                return null;
            } else {
                return basketOrder;
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
            sendRedirect(null, SQL_EXCEPTION, PROFILE);
            return null;
        } catch (ServerOverloadedException e) {
            logger.info(e.getMessage());
            sendRedirect(null, SERVER_OVERLOADED_EXCEPTION, PROFILE);
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
        String linkValue = configManager.getProperty(BASKET);
        String linkName = TITLE;
        ConcreteLink concreteLink = new ConcreteLink(linkValue, linkName);
        links.add(concreteLink);
        return links;
    }
    
}
