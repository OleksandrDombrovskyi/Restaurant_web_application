/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal.admin;

import controller.action.ConcreteLink;
import controller.action.getactions.GetAction;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import model.dao.OrderCreator;
import model.dao.ServerOverloadedException;
import model.entity.Admin;
import model.entity.Order;

/**
 *
 * @author Sasha
 */
public class GetAllOrders extends GetAction {

    @Override
    protected void doExecute() throws ServletException, IOException {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            sendRedirect(null, "login.errormessage.loginplease", "home");
            return;
        }
        List<Order> orders = getAllOrders();
        if (orders == null || orders.size() < 1) {
            request.setAttribute("message", "administration.orders.message.noorders");
        } else {
            request.setAttribute("orders", orders);
        }
        goToPage("administration.orders.text.title", "/view/person/orders.jsp");
    }
    
    /**
     * Get all orders from data base
     * @return list oof orders
     * @throws ServletException
     * @throws IOException 
     */
    private List<Order> getAllOrders() throws ServletException, IOException {
        OrderCreator orderCreator = new OrderCreator();
        try {
            return (List<Order>) orderCreator.getAllEntities();
        } catch (SQLException e) {
            sendRedirect(null, "exception.errormessage.sqlexception", "administration");
            return null;
        } catch (ServerOverloadedException ex) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "administration");
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
        links.addAll(new Administration().getLink());
        String linkValue = "/servlet?getAction=getAllOrders";
        String linkName = "administration.orders.text.title";
        ConcreteLink concreteLink = new ConcreteLink(linkValue, linkName);
        links.add(concreteLink);
        return links;
    }
    
}
