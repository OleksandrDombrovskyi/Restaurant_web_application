/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions.personal.admin;

import controller.action.postactions.PostAction;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import model.dao.OrderCreator;
import model.dao.ServerOverloadedException;
import model.entity.Admin;
import model.entity.Order.OrderStatus;

/**
 *
 * @author Sasha
 */
public class SendToKitchen extends PostAction {
    
    @Override
    protected void doExecute() throws ServletException, IOException {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            sendRedirect(null, "login.errormessage.loginplease", "home");
            return;
        }
        String orderIdString = request.getParameter("orderId");
        if (orderIdString == null) {
            sendRedirect(null, "administration.user.order.errormessage.wrongorderid", "getAllOrders");
            return;
        }
        int orderId = Integer.parseInt(orderIdString);
        if (!setStatus(orderId, OrderStatus.ACCEPTED)) {
            return;
        }
        
        String path = request.getHeader("Referer");
        if (path != null) {
            response.sendRedirect(path);
            return;
        }
        response.sendRedirect(request.getContextPath() + "/");
    }
    
    /**
     * Set required order status
     * @param orderId order id
     * @param orderStatus order status
     * @return true if status setting was performed successfully
     * @throws ServletException
     * @throws IOException 
     */
    private boolean setStatus(int orderId, OrderStatus orderStatus) throws ServletException, 
            IOException {
        OrderCreator orderCreator = new OrderCreator();
        try {
            if (orderCreator.setOrderStatus(orderId, orderStatus) == 0) {
                sendRedirect("administration.user.order.errormessage.notaccepted", null, "getAllOrders");
                return false;
            }
        } catch (SQLException ex) {
            sendRedirect(null, "exception.errormessage.sqlexception", "getAllOrders");
            return false;
        } catch (ServerOverloadedException ex) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", 
                    "getAllOrders");
            return false;
        }
        return true;
    }

}
