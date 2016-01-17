/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import java.io.IOException;
import java.io.PrintWriter;
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

    @Override
    protected void doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            new Redirection().goToLogin(request, response);
            return;
        }
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        model.entity.Order order = null;
        OrderCreator orderCreator = new OrderCreator();
//        try (PrintWriter out = response.getWriter()) {
//            out.println("orderId: "); // input this expression to the jsp file
//        }
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
        createPage(order);
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
