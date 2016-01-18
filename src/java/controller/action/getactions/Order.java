/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions;

import controller.action.Action;
import controller.action.LanguageBlock;
import controller.action.SetAuthorizationBlock;
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
public class Order extends Action {

    /**
     * Show selected order
     * 
     * @throws ServletException
     * @throws IOException 
     */
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
        model.entity.Order order = null;
        OrderCreator orderCreator = new OrderCreator();
        try {
            order = (model.entity.Order) orderCreator.getEntityById(orderId);
        } catch (SQLException e) {
            startOver("exception.errormessage.sqlexception");
            return;
        } catch (ServerOverloadedException ex) {
            startOver("exception.errormessage.serveroverloaded");
            return;
        }
        if (order == null) {
            startOver("order.errormessage.nosuchorder");
            return;
        }
        int userId = user.getId();
        int orderUserId = order.getUserId();
        if (userId == orderUserId) {
            createPage(order);
        } else {
            goToHome("login.errormessage.loginplease");
        }
    }
    
    /**
     * Create jsp page
     * 
     * @param order order selected or created by user
     * @throws ServletException
     * @throws IOException 
     */
    private void createPage(model.entity.Order order) throws ServletException, IOException {
        request.setAttribute("title", "order.text.title");
        new LanguageBlock().execute(request, response);
        new SetAuthorizationBlock().execute(request, response);
        request.setAttribute("order", order);
        request.setAttribute("items", order.getOrderItems());
        request.getRequestDispatcher("/view/user/order.jsp").
                include(request, response);
    }
    
    /**
     * Back to the same page couse of exception
     * 
     * @param errorMessage text value of text property file which corresponds 
     * to the error message
     * @throws ServletException
     * @throws IOException 
     */
    private void startOver(String errorMessage) throws ServletException, 
            IOException {
        request.setAttribute("errorMessage", errorMessage);
        new Orders().execute(request, response);
    }
    
}
