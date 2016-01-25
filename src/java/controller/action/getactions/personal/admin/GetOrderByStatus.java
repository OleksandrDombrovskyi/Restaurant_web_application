/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal.admin;

import controller.action.ConcreteLink;
import controller.action.getactions.personal.AbstractOrders;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import model.entity.Admin;
import model.entity.Order;
import model.entity.Order.OrderStatus;
import model.entity.User;

/**
 * Get order by status 
 * @author Sasha
 */
public class GetOrderByStatus extends AdminGetAction {

    /**
     * Get all orders with concrete status
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doExecute() throws ServletException, IOException {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            sendRedirect(null, "login.errormessage.loginplease", "home");
            return;
        }
        String orderStatusString = request.getParameter("orderStatus"); 
        if (orderStatusString == null) {
            sendRedirect(null, "administration.orders.errormessage.wrongorderstatus");
            return;
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
        goToPage("administration.orders.text.title", "/view/person/admin/allorders.jsp");
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
