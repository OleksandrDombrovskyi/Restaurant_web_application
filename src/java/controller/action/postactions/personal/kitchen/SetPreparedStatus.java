/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions.personal.kitchen;

import controller.ConfigManager;
import controller.action.postactions.personal.SetOrderStatus;
import java.io.IOException;
import javax.servlet.ServletException;
import model.entity.Kitchen;
import model.entity.Order.OrderStatus;

/**
 * Set prepared status by kitchen
 * @author Sasha
 */
public class SetPreparedStatus extends SetOrderStatus {

    /**
     * Change status of order to PREPARED by the kitchen
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doExecute() throws ServletException, IOException {
        Kitchen kitchen = (Kitchen) session.getAttribute("kitchen");
        if (kitchen == null) {
            sendRedirect(null, "login.errormessage.loginplease", "link.home");
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
