/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal.user;

import controller.ConfigManager;
import controller.action.getactions.ConcreteLink;
import controller.action.getactions.personal.AbstractOrders;
import controller.action.getactions.personal.Profile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import model.entity.Order;
import model.entity.User;

/**
 * Get orders for user
 * @author Sasha
 */
public class Orders extends AbstractOrders {
    
    /** title string key value */
    private final static String TITLE = "orders.text.title";

    /**
     * Constructor
     */
    public Orders() {
        super(TITLE);
    }

    /**
     * Show all users' orders
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected String doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
//            sendRedirect(null, "login.errormessage.loginplease", "home");
            setMessages(null, "login.errormessage.loginplease");
            return ConfigManager.getProperty("path.home");
        }
        int userId = user.getId();
        List<Order> orders = getOrdersByUserId(userId);
        if (orders == null || orders.size() < 1) {
            request.setAttribute("message", "orders.text.noorders");
        } else {
            request.setAttribute("orders", orders);
        }
//        goToPage("orders.text.title", "/view/person/user/orders.jsp");
        return ConfigManager.getProperty("path.page.user.orders");
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
        String linkValue = ConfigManager.getProperty("link.orders");
        String linkName = "orders.text.title";
        ConcreteLink concreteLink = new ConcreteLink(linkValue, linkName);
        links.add(concreteLink);
        return links;
    }
    
}
