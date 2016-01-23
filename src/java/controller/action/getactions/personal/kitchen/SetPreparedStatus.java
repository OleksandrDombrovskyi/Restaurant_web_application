/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal.kitchen;

import controller.action.ConcreteLink;
import controller.action.postactions.personal.SetOrderStatus;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import model.entity.Kitchen;
import model.entity.Order.OrderStatus;

/**
 *
 * @author Sasha
 */
public class SetPreparedStatus extends SetOrderStatus {

    @Override
    protected void doExecute() throws ServletException, IOException {
        Kitchen kitchen = (Kitchen) session.getAttribute("kitchen");
        if (kitchen == null) {
            sendRedirect(null, "login.errormessage.loginplease", "home");
            return;
        }
        String orderIdString = request.getParameter("orderId");
        if (orderIdString == null) {
            sendRedirect(null, "kitchen.acceptedorders.errormessage.nosuchorder");
            return;
        }
        int orderId = Integer.parseInt(orderIdString);
        if (!setStatus(orderId, OrderStatus.PREPARED)) {
            return;
        }
        sendRedirect(null, null);
    }
    
}
