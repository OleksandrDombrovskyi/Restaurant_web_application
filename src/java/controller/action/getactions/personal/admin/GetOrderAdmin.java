/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal.admin;

import controller.ConfigManager;
import controller.action.ConcreteLink;
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
//            sendRedirect(null, "login.errormessage.loginplease", "home");
//            return;
            setMessages(null, "login.errormessage.loginplease");
            return ConfigManager.getProperty("path.page.home");
        }
        String orderIdString = request.getParameter("orderId");
        if (orderIdString == null) {
//            sendRedirect(null, "administration.user.orders.errormessage.wrongparameterorderid", "getAllOrders");
            setMessages(null, "administration.user.orders.errormessage.wrongparameterorderid");
            return ConfigManager.getProperty("path.page.admin.getallorders");
        }
        int orderId = Integer.parseInt(orderIdString);
        Order order = getOrderById(orderId);
        if (order == null) {
//            sendRedirect(null, "order.errormessage.nosuchorder", "getAllOrders");
            setMessages(null, "order.errormessage.nosuchorder");
            return ConfigManager.getProperty("path.page.admin.getallorders");
        }
        int userId = order.getUserId();
        User concreteUser = getUserById(userId);
        if (concreteUser == null) {
//            sendRedirect(null, "administration.users.errormessage.wronguserid", "getAllOrders");
            setMessages(null, "administration.users.errormessage.wronguserid");
            return ConfigManager.getProperty("path.page.admin.getallorders");
        }
        request.setAttribute("concreteUser", concreteUser);
        request.setAttribute("order", order);
//        goToPage("administration.user.order.text.title", "/view/person/admin/order.jsp");
        return ConfigManager.getProperty("path.page.admin.getorder");
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
