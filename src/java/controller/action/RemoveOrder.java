/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

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
public class RemoveOrder extends Action {

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
            new Redirection().showMessage(request, response, 
                    "exception.errormessage.sqlexception");
            return;
        } catch (ServerOverloadedException ex) {
            new Redirection().showMessage(request, response, 
                    "exception.errormessage.serveroverloaded");
            return;
        }
        if (isRemoved) {
            makeRedirect();
        } else {
            new Redirection().showMessage(request, response, 
                    "exception.errormessage.serveroverloaded");
        }
    }
    
    private void makeRedirect() throws ServletException, IOException {
        request.setAttribute("message", "order.message.orderwasremoved");
        request.setAttribute("action", "orders");
        new Orders().execute(request, response);
    }
    
}
