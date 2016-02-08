/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions.personal.admin;

import controller.action.postactions.personal.SetOrderStatus;
import java.io.IOException;
import javax.servlet.ServletException;
import model.entity.Admin;
import model.entity.Order;

/**
 * Setting order status to READY
 * @author Sasha
 */
public class ConfirmPreparedOrder extends SetOrderStatus {

    /**
     * Set READY order status 
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doExecute() throws ServletException, IOException {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            sendRedirect(null, LOGIN_PLEASE, HOME_PAGE_LINK);
            return;
        }
        String orderIdString = request.getParameter("orderId");
        if (orderIdString == null) {
            sendRedirect(null, "administration.user.order.errormessage.wrongorderid");
        }
        int orderId = Integer.parseInt(orderIdString);
        if (!setStatus(orderId, Order.OrderStatus.READY)) {
            return;
        }
        sendRedirect(null, null);
    }
    
}
