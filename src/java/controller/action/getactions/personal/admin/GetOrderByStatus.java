/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal.admin;

import controller.ConfigManager;
import controller.action.getactions.ConcreteLink;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import model.entity.Admin;
import model.entity.Order;
import model.entity.Order.OrderStatus;
import model.entity.User;

/**
 * Get order by status 
 * @author Sasha
 */
public class GetOrderByStatus extends AdminGetAction {
    
    /** title string key value */
    private final static String TITLE = "orders.text.title";

    /**
     * Constructor
     */
    public GetOrderByStatus() {
        super(TITLE);
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

    /**
     * Get all orders with concrete status
     * @return property key value
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected String doExecute() throws ServletException, IOException {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
//            sendRedirect(null, "login.errormessage.loginplease", "home");
            setMessages(null, "login.errormessage.loginplease");
            return ConfigManager.getProperty("path.page.home");
        }
        String orderStatusString = request.getParameter("orderStatus"); 
        if (orderStatusString == null) {
//            sendRedirect(null, "administration.orders.errormessage.wrongorderstatus");
            setMessages(null, "administration.orders.errormessage.wrongorderstatus");
            return request.getHeader("Referer");
        }
        OrderStatus orderStatus = OrderStatus.valueOf(orderStatusString);
        List<Order> orders = getOrdersByStatus(orderStatus);
        if (orders == null || orders.size() < 1) {
            request.setAttribute("message", "administration.orders.errormessage.noorderswithstatus");
        }
        request.setAttribute("orders", orders);
        List<User> users = getAllUsers();
        if (users == null || users.size() < 1) {
            request.setAttribute("message", "administration.users.message.nousers");
        } else {
            Map<Integer, User> userMap = createUserMap(users);
            request.setAttribute("userMap", userMap);
        }
        request.setAttribute("status", orderStatusString);
//        goToPage("administration.orders.text.title", "/view/person/admin/allorders.jsp");
        return ConfigManager.getProperty("path.page.admin.getallorders");
    }
    
}
