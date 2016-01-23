/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal.admin;

import controller.action.ConcreteLink;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import model.dao.OrderCreator;
import model.dao.ServerOverloadedException;
import model.entity.Admin;
import model.entity.Order;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public class GetOrderAdmin extends AdminGetAction {

    @Override
    protected void doExecute() throws ServletException, IOException {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            sendRedirect(null, "login.errormessage.loginplease", "home");
            return;
        }
        String orderIdString = request.getParameter("orderId");
        if (orderIdString == null) {
            sendRedirect(null, "administration.user.orders.errormessage.wrongparameterorderid", "getAllOrders");
            return;
        }
        int orderId = Integer.parseInt(orderIdString);
        Order order = getOrderById(orderId);
        if (order == null) {
            sendRedirect(null, "order.errormessage.nosuchorder", "getAllOrders");
            return;
        }
        int userId = order.getUserId();
        User concreteUser = getUserById(userId);
        if (concreteUser == null) {
            sendRedirect(null, "administration.users.errormessage.wronguserid", "getAllOrders");
            return;
        }
        request.setAttribute("concreteUser", concreteUser);
        request.setAttribute("order", order);
        goToPage("administration.user.order.text.title", "/view/person/admin/order.jsp");
    }
    
    /**
     * Get order by id
     * 
     * @param orderId order id
     * @return order object
     * @throws ServletException
     * @throws IOException 
     */
    private Order getOrderById(int orderId) throws ServletException, IOException {
        OrderCreator orderCreator = new OrderCreator();
        try {
            return (Order) orderCreator.getEntityById(orderId);
        } catch (SQLException e) {
            sendRedirect(null, "exception.errormessage.sqlexception", "getAllOrders");
        } catch (ServerOverloadedException ex) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "getAllOrders");
        }
        return null;
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
