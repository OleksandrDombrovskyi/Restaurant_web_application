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
public class ClearBasket extends PostAction {

    @Override
    protected void doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            goToHome("login.errormessage.loginplease");
            return;
        }
        String orderIdString = request.getParameter("orderId");
        if (orderIdString == null) {
            sendRedirect(null, "basket.errormessage.nosuchorder", "basket");
            return;
        }
        int orderId = Integer.parseInt(orderIdString);
        OrderCreator orderCreator = new OrderCreator();
        boolean isRemoved = false;
        try {
            isRemoved = orderCreator.removeOrder(orderId);
        } catch (SQLException ex) {
            sendRedirect(null, "exception.errormessage.sqlexception", "basket");
            return;
        } catch (ServerOverloadedException ex) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "basket");
            return;
        }
        if (isRemoved) {
            sendRedirect(null, null, "basket");
//            makeRedirect();
        } else {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "basket");
        }
    }
    
//    private void makeRedirect() throws ServletException, IOException {
//        response.sendRedirect(request.getContextPath() + "/servlet?getAction=basket");
//        
//    }
    
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
//        session.setAttribute("errorMessage", errorMessage);
////        session.setAttribute("lastPath", request.getContextPath() + "/servlet?getAction=basket");
////        new Basket().execute(request, response);
//        response.sendRedirect(request.getContextPath() 
//                + "/servlet?getAction=basket");
//        
//    }
    
}
