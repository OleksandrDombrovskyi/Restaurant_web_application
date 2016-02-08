/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions.personal;

import controller.action.postactions.PostAction;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import model.dao.DaoEnum;
import model.dao.OrderCreator;
import model.dao.ServerOverloadedException;
import model.entity.Order.OrderStatus;

/**
 * Set required order status by order id
 * @author Sasha
 */
public abstract class SetOrderStatus extends PostAction {
    
    /**
     * Set required order status
     * @param orderId order id
     * @param orderStatus order status
     * @return true if status setting was performed successfully
     * @throws ServletException
     * @throws IOException 
     */
    protected boolean setStatus(int orderId, OrderStatus orderStatus) 
            throws ServletException, IOException {
        OrderCreator orderCreator = 
                (OrderCreator) daoFactory.getCreator(DaoEnum.ORDER_CREATOR);
        try {
            if (orderCreator.setOrderStatus(orderId, orderStatus) == 0) {
                sendRedirect("administration.user.order.errormessage.notaccepted", null);
                return false;
            }
        } catch (SQLException ex) {
            sendRedirect(null, SQL_EXCEPTION);
            return false;
        } catch (ServerOverloadedException ex) {
            sendRedirect(null, SERVER_OVERLOADED_EXCEPTION);
            return false;
        }
        return true;
    }
    
}
