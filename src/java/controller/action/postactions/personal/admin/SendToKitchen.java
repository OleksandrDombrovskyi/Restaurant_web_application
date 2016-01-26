/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions.personal.admin;

import controller.ConfigManager;
import controller.action.postactions.personal.SetOrderStatus;
import java.io.IOException;
import javax.servlet.ServletException;
import model.entity.Admin;
import model.entity.Order.OrderStatus;

/**
 * Send order to kitchen by admin (change order status to ACCEPTED)
 * @author Sasha
 */
public class SendToKitchen extends SetOrderStatus {
    
    /**
     * Set ACCEPTED status to concrete order 
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doExecute() throws ServletException, IOException {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            sendRedirect(null, "login.errormessage.loginplease", "link.home");
            return;
//            setMessages(null, "login.errormessage.loginplease");
//            return ConfigManager.getProperty("path.page.home");
        }
        String orderIdString = request.getParameter("orderId");
        if (orderIdString == null) {
            sendRedirect(null, "administration.user.order.errormessage.wrongorderid");
            return;
        }
        int orderId = Integer.parseInt(orderIdString);
        if (!setStatus(orderId, OrderStatus.ACCEPTED)) {
            return;
        }
        sendRedirect(null, null);
//        return null;
    }

}
