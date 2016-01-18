/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions;

import controller.action.Action;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import model.dao.OrderCreator;
import model.dao.ServerOverloadedException;
import model.entity.Order.OrderStatus;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public class OrderConfirmation extends Action {

    @Override
    protected void doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            goToHome("login.errormessage.loginplease");
            return;
        }
        String orderIdString = request.getParameter("orderId");
        if (orderIdString == null) {
            goToHome(null);
            return;
        }
        int orderId = Integer.parseInt(orderIdString);
        OrderCreator orderCreator = new OrderCreator();
        try {
            orderCreator.setStatus(orderId, OrderStatus.CREATED);
        } catch (SQLException ex) {
            showMessage("exception.errormessage.sqlexception");
            return;
        } catch (ServerOverloadedException ex) {
            showMessage("exception.errormessage.serveroverloaded");
            return;
        } 
        makeRedirect(orderId);
    }
    
    private void makeRedirect(int orderId) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/servlet?getAction=getOrder&orderId=" + orderId);
    }
    
//    /**
//     * Back to filling the form couse of uncorrect field filling and sending 
//     * correspond error message
//     * 
//     * @param errorMessage text value of text property file which corresponds 
//     * to the error message
//     * @throws ServletException
//     * @throws IOException 
//     */
//    private void startOver(String errorMessage) throws ServletException, 
//            IOException {
//        request.setAttribute("errorMessage", errorMessage);
//        new MainMenu().execute(request, response);
//    }
    
}
