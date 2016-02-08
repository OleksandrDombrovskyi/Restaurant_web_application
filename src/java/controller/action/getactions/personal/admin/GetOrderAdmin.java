/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal.admin;

import controller.action.getactions.ConcreteLink;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import model.entity.Admin;
import model.entity.Order;
import model.entity.User;

/**
 * Get order for admin
 * @author Sasha
 */
public class GetOrderAdmin extends AdminGetAction {
    
    /** title string key value */
    private final static String TITLE = "order.text.title";
    
    /** key for wrong order message */
    protected final static String WRONG_ORDER_ID = 
            "administration.user.orders.errormessage.wrongparameterorderid";

    /**
     * Constructor
     */
    public GetOrderAdmin() {
        super(TITLE);
    }

    /**
     * Get order by admin request
     * @return property key value
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
        String orderIdString = request.getParameter("orderId");
        if (orderIdString == null) {
            setMessages(null, WRONG_ORDER_ID);
            return configManager.getProperty(ADMIN_GET_ALL_ORDERS);
        }
        int orderId = Integer.parseInt(orderIdString);
        Order order = getOrderById(orderId);
        if (order == null) {
            setMessages(null, NO_SUCH_ORDER);
            return configManager.getProperty(ADMIN_GET_ALL_ORDERS);
        }
        int userId = order.getUserId();
        User concreteUser = getUserById(userId);
        if (concreteUser == null) {
            setMessages(null, WRONG_USER_ID);
            return configManager.getProperty(ADMIN_GET_ALL_ORDERS);
        }
        request.setAttribute("concreteUser", concreteUser);
        request.setAttribute("order", order);
        return configManager.getProperty("path.page.admin.getorder");
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
        return new GetAllOrders().getLink();
    }
    
}
