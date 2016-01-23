/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal.kitchen;

import controller.action.ConcreteLink;
import controller.action.getactions.personal.AbstractOrders;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import model.entity.Kitchen;
import model.entity.Order;
import model.entity.Order.OrderStatus;

/**
 *
 * @author Sasha
 */
public class GetAcceptedOrders extends AbstractOrders {

    /**
     * Get and output all accepted by admin orders
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doExecute() throws ServletException, IOException {
        Kitchen kitchen = (Kitchen) session.getAttribute("kitchen");
        if (kitchen == null) {
            sendRedirect(null, "login.errormessage.loginplease", "home");
            return;
        }
        List<Order> orders = getOrdersByStatus(OrderStatus.ACCEPTED);
        if (orders == null || orders.size() < 1) {
            session.setAttribute("message", "kitchen.message.noacceptedorders");
        } else {
            request.setAttribute("orders", orders);
        }
        goToPage("kitchen.acceptedorders.text.title", "/view/kitchen/acceptedorders.jsp");
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
        String linkValue = "/servlet?getAction=showAcceptedOrders";
        String linkName = "kitchen.authorization.link.showorders";
        ConcreteLink concreteLink = new ConcreteLink(linkValue, linkName);
        links.add(concreteLink);
        return links;
    }
    
}
