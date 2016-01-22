/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions;

import controller.action.ConcreteLink;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import model.dao.OrderCreator;
import model.dao.ServerOverloadedException;
import model.entity.Order;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public class Orders extends GetAction {

    /**
     * Show all users' orders
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            sendRedirect(null, "login.errormessage.loginplease", "home");
            return;
        }
        int userId = user.getId();
        List<Order> orders = getOrdersByUserId(userId);
        if (orders == null || orders.size() < 1) {
            request.setAttribute("message", "orders.text.noorders");
        } else {
            request.setAttribute("orders", orders);
        }
        goToPage("orders.text.title", "/view/user/orders.jsp");
    }
    
    /**
     * Get all orders by user id
     * @param userId user id
     * @return orders if they exist in data base or null otherwise
     * @throws ServletException
     * @throws IOException 
     */
    private List<Order> getOrdersByUserId(int userId) 
            throws ServletException, IOException {
        OrderCreator orderCreator = new OrderCreator();
        try {
            return (List<Order>) orderCreator.getOrdersByUserId(userId);
        } catch (SQLException e) {
            sendRedirect(null, "exception.errormessage.sqlexception", "profile");
            return null;
        } catch (ServerOverloadedException e) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "profile");
            return null;
        }
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
        String linkValue = "/servlet?getAction=orders";
        String linkName = "orders.text.title";
        ConcreteLink concreteLink = new ConcreteLink(linkValue, linkName);
        links.add(concreteLink);
        return links;
    }
    
}
