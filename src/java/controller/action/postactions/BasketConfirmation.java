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
public class BasketConfirmation extends PostAction {

    @Override
    protected void doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            goToHome("login.errormessage.loginplease");
            return;
        }
        String userIdString = request.getParameter("userId");
        if (userIdString == null) {
            sendRedirect("basket.message.notconfirmed", null, "basket");
            return;
        }
        int userId = Integer.parseInt(userIdString);
        if (userId != user.getId()) {
            sendRedirect(null, "login.errormessage.loginplease", "home");
            return;
        }
        OrderCreator orderCreator = new OrderCreator();
        try {
            if (orderCreator.confirmBasket(userId) == 0) {
                sendRedirect("basket.message.notconfirmed", null, "basket");
                return;
            }
        } catch (SQLException ex) {
            showMessage("exception.errormessage.sqlexception");
            return;
        } catch (ServerOverloadedException ex) {
            showMessage("exception.errormessage.serveroverloaded");
            return;
        }
        String orderIdString = request.getParameter("orderId");
        if (orderIdString == null) {
            sendRedirect(null, "basket.errormessage.nosuchorder", "basket");
            return;
        }
        int orderId = Integer.parseInt(orderIdString);
        sendRedirect(null, "basket.errormessage.nosuchorder", "getOrder&orderId=" + orderId);
//        makeRedirect(orderId);
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
//        session.setAttribute("errorMessage", errorMessage);
////        session.setAttribute("lastPath", request.getContextPath() + "/servlet?getAction=basket");
////        new Basket().execute(request, response);
//        response.sendRedirect(request.getContextPath() 
//                + "/servlet?getAction=basket");
//        
//    }

//    private void makeRedirect(int orderId) throws ServletException, 
//            IOException{
//        response.sendRedirect(request.getContextPath() + 
//                "/servlet?getAction=getOrder&orderId=" + orderId);
//        
//        
//    }
    
}
