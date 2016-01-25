/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal.admin;

import controller.action.ConcreteLink;
import controller.action.getactions.personal.AbstractOrders;
import controller.action.getactions.personal.Profile;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import model.dao.ServerOverloadedException;
import model.dao.UserCreator;
import model.entity.Admin;
import model.entity.Order;
import model.entity.User;

/**
 * Get users' orders
 * @author Sasha
 */
public class GetUserOrders extends AbstractOrders {

    /**
     * Get all orders for concrete user
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
        String userIdString = request.getParameter("userId");
        if (userIdString == null) {
            sendRedirect(null, "administration.user.errormessage.wrongparameteruserid", "getUsers");
            return;
        }
        int userId = Integer.parseInt(userIdString);
        User concreteUser = getUserById(userId);
        if (concreteUser == null) {
            sendRedirect(null, "administration.user.errormessage.nosuchuser", "getUsers");
            return;
        }
        List<Order> orders = getOrdersByUserId(userId);
        if (orders == null || orders.size() < 1) {
            request.setAttribute("message", "administration.user.message.noorders");
        } else {
            concreteUser.setOrders(orders);
            request.setAttribute("concreteUser", concreteUser);
        }
        goToPage("administration.user.orders.text.title", "/view/person/admin/userorders.jsp");
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
        String linkValue = "/servlet?getAction=getAllOrders";
        String linkName = "administration.orders.text.title";
        ConcreteLink concreteLink = new ConcreteLink(linkValue, linkName);
        links.add(concreteLink);
        return links;
    }
    
}
