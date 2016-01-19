/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import model.dao.OrderCreator;
import model.dao.ServerOverloadedException;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public class RemoveOrder extends PostAction {

    @Override
    protected void doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            goToHome("login.errormessage.loginplease");
            return;
        }
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        OrderCreator orderCreator = new OrderCreator();
        boolean isRemoved = false;
        try {
            isRemoved = orderCreator.removeOrder(orderId);
        } catch (SQLException ex) {
            showMessage("exception.errormessage.sqlexception");
            return;
        } catch (ServerOverloadedException ex) {
            showMessage("exception.errormessage.serveroverloaded");
            return;
        }
        if (isRemoved) {
            sendRedirect("order.message.orderwasremoved", null, "orders");
//            makeRedirect();
        } else {
            showMessage("exception.errormessage.serveroverloaded");
        }
    }
    
//    private void makeRedirect() throws ServletException, IOException {
//        session.setAttribute("message", "order.message.orderwasremoved");
////        request.setAttribute("action", "orders");
////        session.setAttribute("lastPath", request.getContextPath() + "/servlet?getAction=orders");
////        new Orders().execute(request, response);
//        response.sendRedirect(request.getContextPath() + "/servlet?getAction=orders");
//        
//    }
    
}
