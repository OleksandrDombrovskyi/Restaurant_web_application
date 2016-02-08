/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal.kitchen;

import controller.ConfigManager;
import controller.action.getactions.ConcreteLink;
import controller.action.getactions.personal.AbstractOrders;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import model.entity.Kitchen;
import model.entity.Order;

/**
 * Get order for kitchen
 * @author Sasha
 */
public class GetOrderKitchen extends AbstractOrders {
    
    /** title string key value */
    private final static String TITLE = "kitchen.order.text.title";
    
    private final static String KITCHEN_NO_SUCH_ORDER = 
            "kitchen.acceptedorders.errormessage.nosuchorder";

    /**
     * Constructor
     */
    public GetOrderKitchen() {
        super(TITLE);
    }

    /**
     * Get selected order for kitchen
     * @return page path key
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected String doExecute() throws ServletException, IOException {
        Kitchen kitchen = (Kitchen) session.getAttribute("kitchen");
        if (kitchen == null) {
            setMessages(null, LOGIN_PLEASE);
            return configManager.getProperty(HOME_PAGE);
        }
        String orderIdString = request.getParameter("orderId");
        if (orderIdString == null) {
            setMessages(null, KITCHEN_NO_SUCH_ORDER);
            return request.getHeader("Referer");
        }
        int orderId = Integer.parseInt(orderIdString);
        Order order = getOrderById(orderId);
        if (order == null) {
            setMessages(null, KITCHEN_NO_SUCH_ORDER);
            return request.getHeader("Referer");
        }
        request.setAttribute("order", order);
        return configManager.getProperty("path.page.kitchen.getorder");
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
        String linkValue = configManager.getProperty("link.getorderkitchen");
        String linkName = TITLE;
        ConcreteLink concreteLink = new ConcreteLink(linkValue, linkName);
        links.add(concreteLink);
        return links;
    }
    
}
