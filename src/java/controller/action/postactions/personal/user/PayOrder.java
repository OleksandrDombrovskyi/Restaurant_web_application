/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions.personal.user;

import controller.action.postactions.personal.SetOrderStatus;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import javax.servlet.ServletException;
import model.dao.DaoEnum;
import model.dao.ServerOverloadedException;
import model.dao.SingletonPaymentTransaction;
import model.entity.Order;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public class PayOrder extends SetOrderStatus {

    /**
     * Perform payment of the order
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            sendRedirect(null, "login.errormessage.loginplease", "link.home");
            return;
        }
        String orderIdString = request.getParameter("orderId");
        if (orderIdString == null) {
            sendRedirect(null, "basket.errormessage.nosuchorder");
            return;
        }
        int orderId = Integer.parseInt(orderIdString);
        Order order = getOrderById(orderId);
        if (order == null) {
            sendRedirect(null, "basket.errormessage.nosuchorder");
            return;
        }
        User updatedUser = getUserById(user.getId());
        if ((updatedUser == null) || (updatedUser.getId() != user.getId())) {
            sendRedirect("login.errormessage.loginplease", null);
            return;
        }
        session.setAttribute("user", updatedUser);
        BigDecimal price = order.getTotalPrice();
        BigDecimal account = updatedUser.getAccount();
        if (account.compareTo(price) < 0) {
            sendRedirect("order.message.insufficientfunds", null);
            return;
        }
        if (!remitPayment(updatedUser, order)) {
            return;
        }
        sendRedirect("order.message.orderwaspayed", null);
    }

    /**
     * Remit payment
     * @param updatedUser user who do make payment
     * @param order order under payment
     * @return true if remit was successful
     * @throws ServletException
     * @throws IOException 
     */
    private boolean remitPayment(User updatedUser, Order order) 
            throws ServletException, IOException {
        SingletonPaymentTransaction engine = 
                (SingletonPaymentTransaction) daoFactory.getCreator(DaoEnum.PAYMENT_TRANSACTION);
        try {
            if (!engine.makePayment(updatedUser, order)) {
                sendRedirect(null, "order.errormessage.transactionwasnotperformed");
            } else {
                return true;
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
            sendRedirect(null, "exception.errormessage.sqlexception");
        } catch (ServerOverloadedException e) {
            logger.info(e.getMessage());
            sendRedirect(null, "exception.errormessage.serveroverloaded");
        }
        return false;
    }
    
}
