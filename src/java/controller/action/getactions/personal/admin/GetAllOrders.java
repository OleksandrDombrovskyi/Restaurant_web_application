/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal.admin;

import controller.ConfigManager;
import controller.action.getactions.ConcreteLink;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import model.entity.Admin;
import model.entity.Order;
import model.entity.User;

/**
 * Get all orders for admin
 * @author Sasha
 */
public class GetAllOrders extends AdminGetAction {
    
    /** title string key value */
    private final static String TITLE = "administration.orders.text.title";
    
    /** key for no orders message */
    private final static String NO_ORDERS = 
            "administration.orders.message.noorders";

    /**
     * Constructor
     */
    public GetAllOrders() {
        super(TITLE);
    }

    /**
     * Get all orders by admin request
     * @return 
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected String doExecute() throws ServletException, IOException {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            setMessages(null, LOGIN_PLEASE);
            return configManager.getProperty(HOME_PAGE);
        }
        List<Order> orders = getAllOrders();
        if (orders == null || orders.size() < 1) {
            request.setAttribute("message", NO_ORDERS);
        } else {
            request.setAttribute("orders", orders);
        }
        List<User> users = getAllUsers();
        if (users == null || users.size() < 1) {
            request.setAttribute("message", NO_USERS);
        } else {
            Map<Integer, User> userMap = createUserMap(users);
            request.setAttribute("userMap", userMap);
        }
        return configManager.getProperty(ADMIN_GET_ALL_ORDERS);
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
        links.addAll(new Administration().getLink());
        String linkValue = configManager.getProperty("link.getallorders");
        String linkName = TITLE;
        ConcreteLink concreteLink = new ConcreteLink(linkValue, linkName);
        links.add(concreteLink);
        return links;
    }
    
}
