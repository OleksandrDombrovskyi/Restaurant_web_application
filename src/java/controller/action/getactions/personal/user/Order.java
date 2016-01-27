/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal.user;

import controller.ConfigManager;
import controller.action.getactions.ConcreteLink;
import controller.action.getactions.personal.AbstractOrders;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import model.entity.Admin;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public class Order extends AbstractOrders {
    
    /** title string key value */
    private final static String TITLE = "order.text.title";

    /**
     * Constructor
     */
    public Order() {
        super(TITLE);
    }

    /**
     * Show selected order
     * 
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected String doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        Admin admin = (Admin) session.getAttribute("admin");
        if (user == null) {
            if (admin == null) {
                setMessages(null, "login.errormessage.loginplease");
                return ConfigManager.getProperty("path.page.home");
            }
        }
        String orderIdString = request.getParameter("orderId");
        if (orderIdString == null) {
            return ConfigManager.getProperty("path.page.home");
        }
        int orderId = Integer.parseInt(orderIdString);
        model.entity.Order order = getOrderById(orderId);
        if (order == null) {
            setMessages(null, "order.errormessage.nosuchorder");
            return ConfigManager.getProperty("path.page.user.orders");
        }
        if (admin != null || checkUserValidation(user, order)) {
            request.setAttribute("order", order);
            request.setAttribute("items", order.getOrderItems());
            return ConfigManager.getProperty("path.page.user.getorder");
        } else {
            setMessages(null, "login.errormessage.loginplease");
            return ConfigManager.getProperty("path.page.home");
        }
    }

    /**
     * Check validation of user according to this order
     * @param user user object
     * @param order order object
     * @return true is this order really belongs to this user and false otherwise
     */
    private boolean checkUserValidation(User user, model.entity.Order order) {
        int userId = user.getId();
        int orderUserId = order.getUserId();
        return userId == orderUserId;
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
        return new Orders().getLink();
    }
    
}
