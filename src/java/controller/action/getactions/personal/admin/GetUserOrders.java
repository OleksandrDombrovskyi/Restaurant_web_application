/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal.admin;

import controller.action.getactions.ConcreteLink;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import model.entity.Admin;
import model.entity.Order;
import model.entity.User;

/**
 * Get users' orders
 * @author Sasha
 */
public class GetUserOrders extends AdminGetAction {
    
    /** title string key value */
    private final static String TITLE = "administration.user.orders.text.title";

    /**
     * Constructor
     */
    public GetUserOrders() {
        super(TITLE);
    }

    /**
     * Get all orders for concrete user
     * @return string page path key
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
        String userIdString = request.getParameter("userId");
        if (userIdString == null) {
            setMessages(null, WRONG_USER_ID);
            return configManager.getProperty(GET_USERS);
        }
        int userId = Integer.parseInt(userIdString);
        User concreteUser = getUserById(userId);
        if (concreteUser == null) {
            setMessages(null, WRONG_USER_ID);
            return configManager.getProperty(GET_USERS);
        }
        List<Order> orders = getOrdersByUserId(userId);
        if (orders == null || orders.size() < 1) {
            request.setAttribute("message", "administration.user.message.noorders");
        } else {
            concreteUser.setOrders(orders);
            request.setAttribute("concreteUser", concreteUser);
        }
        return configManager.getProperty("path.page.admin.getuserorders");
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
